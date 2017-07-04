package com.nbcuni.test.cms.tests.backend.concerto.chiller.collections.collectiongroup;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionAbstractPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 27-Jul-16.
 */

/**
 * TC15579
 *
 * 1. Go to CMS as Editor
 * Admin panel is present
 * 2. Create new Collection Group with required fields [collection_group_name]
 * Collection Group is created
 * 3. Try create new Collection Group with required fields [collection_group_name]
 * Error message "Title is not unique" is present. New Collection Group is not created
 * 4. Change title and save
 * Error message is not present
 * Collection with new title is created
 */

public class TC15579_CollectionGroup_CheckUniqueTitle extends BaseAuthFlowTest {

    private Collection collection;
    private Collection collectionWithSameTitle;
    private Collection newCollection;

    @Autowired
    @Qualifier("collectionGroupWithRequiredFields")
    private CollectionCreationStrategy collectionTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        collection = collectionTypeCreationStrategy.createCollection();
        collectionWithSameTitle = collectionTypeCreationStrategy.createCollection();
        collectionWithSameTitle.getGeneralInfo().setTitle(collection.getTitle());
        newCollection = collectionTypeCreationStrategy.createCollection();
    }

    @Test(groups = {"collection_group"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationTitle(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createCollection(collection);

        CollectionAbstractPage collectionPage = rokuBackEndLayer.createCollection(collectionWithSameTitle);

        softAssert.assertFalse(collectionPage.isStatusMessageShown(), "Status message is presented",
                "Status message is not presented", webDriver);
        softAssert.assertTrue(collectionPage.isErrorMessagePresent(), "Error message is not presented",
                "Error message is presented", webDriver);

        softAssert.assertContains(collectionPage.getErrorMessage(), String.format(MessageConstants.TITLE_IS_ALREADY_USED_FOR_COLLECTION_GROUP,
                collection.getTitle()), "Error message is incorrect", webDriver);

        softAssert.assertTrue(rokuBackEndLayer.checkCollectionIsPresent(collection), "Collection is not corrected");

        collectionPage = rokuBackEndLayer.createCollection(collectionWithSameTitle);

        softAssert.assertContains(collectionPage.getErrorMessage(), String.format(MessageConstants.TITLE_IS_ALREADY_USED_FOR_COLLECTION_GROUP,
                collection.getTitle()), "Error message is incorrect", webDriver);

        collectionPage.createCollection(newCollection);

        softAssert.assertTrue(collectionPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        softAssert.assertFalse(collectionPage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        softAssert.assertTrue(rokuBackEndLayer.checkCollectionIsPresent(newCollection), "Collection is not corrected");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContentType() {
        rokuBackEndLayer.deleteCollection(collection);
        rokuBackEndLayer.deleteCollection(newCollection);
    }
}
