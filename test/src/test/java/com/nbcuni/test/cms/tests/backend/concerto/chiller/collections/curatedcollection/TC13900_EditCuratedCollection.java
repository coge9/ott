package com.nbcuni.test.cms.tests.backend.concerto.chiller.collections.curatedcollection;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionsContentPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Aliaksei_Dzmitrenka
 * Step 1: Open add collection page
 * Step 2: Fill info and save
 * Step 3: Go to content collection page
 * Step 4: Make sure that collection was added
 */


public class TC13900_EditCuratedCollection extends BaseAuthFlowTest {

    private Collection collection;
    private Collection newCollection;

    @Autowired
    @Qualifier("curatedCollectionWithRequiredFields")
    private CollectionCreationStrategy collectionCreationStrategy;

    @Autowired
    @Qualifier("defaultCuratedCollection")
    private CollectionCreationStrategy defaultCollectionCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        collection = collectionCreationStrategy.createCollection();
        newCollection = defaultCollectionCreationStrategy.createCollection();
    }

    @Test(groups = {"curated_collection"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationMediaGallery(final String brand) {
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        rokuBackEndLayer.createCollection(collection);

        CollectionsContentPage collectionsPage = mainRokuAdminPage.openPage(CollectionsContentPage.class, brand);
        collectionsPage.openEditCuratedCollectionPage(collection).editCollection(newCollection);
        collectionsPage = mainRokuAdminPage.openPage(CollectionsContentPage.class, brand);
        Collection actualCollection = collectionsPage.openEditCuratedCollectionPage(newCollection).getPageInfo();

        softAssert.assertEquals(newCollection, actualCollection, "Collection was not edited", "Collection was edited", webDriver);
        softAssert.assertAll();

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCollection() {
        rokuBackEndLayer.deleteCollection(newCollection);
    }
}
