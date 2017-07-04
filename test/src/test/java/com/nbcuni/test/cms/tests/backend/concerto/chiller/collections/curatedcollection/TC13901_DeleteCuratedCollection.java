package com.nbcuni.test.cms.tests.backend.concerto.chiller.collections.curatedcollection;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionsContentPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Aliaksei_Dzmitrenka
 * Step 1: Open add collection page
 * Step 2: Fill info and save
 * Step 3: Go to content collection page
 * Step 4: Make sure that collection was added
 */


public class TC13901_DeleteCuratedCollection extends BaseAuthFlowTest {

    private Collection collection;

    @Autowired
    @Qualifier("curatedCollectionWithRequiredFields")
    private CollectionCreationStrategy collectionCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        collection = collectionCreationStrategy.createCollection();
    }

    @Test(groups = {"curated_collection"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationMediaGallery(final String brand) {
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        rokuBackEndLayer.createCollection(collection);
        CollectionsContentPage collectionsPage = mainRokuAdminPage.openPage(CollectionsContentPage.class, brand);
        softAssert.assertTrue(collectionsPage.isCollectionPresent(collection), "Collection was not created", "Collection was created", webDriver);
        collectionsPage = rokuBackEndLayer.deleteCollection(collection);
        softAssert.assertFalse(collectionsPage.isCollectionPresent(collection), "Collection was not deleted", "Collection was deleted", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

}
