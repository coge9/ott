package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.collections.collectiongroup;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionAbstractPage;
import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionsContentPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.CollectionVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Created by Ivan on 02.03.2017.
 */

/**
 * Pre-Conditions:
 * 1. Login in CMS D7 as admin
 * 2. Create Collection [collection name]
 * 3. Create collection group with assing [collection name]
 * 4. Open created collection list page
 * Steps:
 * 1.
 * Publish by bulk operaton
 * Link to the publishing log is present
 * 2.
 * Verify publishing log
 */

public class TC17556_PublishCollectionGroupCheckPublishingWithFilledFieldsByBulkOperation extends BaseAuthFlowTest {

    private Collection collection;

    @Autowired
    @Qualifier("collectionGroupWithRequiredFields")
    private CollectionCreationStrategy collectionCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        collection = collectionCreationStrategy.createCollection();
    }

    @Test(groups = {"collection_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void test(final String brand) {
        //Pre-condition - creation
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        CollectionAbstractPage collectionPage = rokuBackEndLayer.createCollection(collection);
        //set Data for expected json

        CollectionsContentPage collectionsContentPage = mainRokuAdminPage.openPage(CollectionsContentPage.class, brand);
        collectionsContentPage.checkAnItem(collection);
        collectionsContentPage.executePublishToServices();

        softAssert.assertTrue(collectionPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        String url = rokuBackEndLayer.getLogURL(brand);

        //Get Expected result
        CollectionJson expectedCollectionJson = new CollectionJson(collection);

        //Get Actual Post Request
        CollectionJson actualCollectionJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.COLLECTIONS);
        softAssert.assertTrue(new CollectionVerificator().verify(expectedCollectionJson, actualCollectionJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCollection() {
        try {
            rokuBackEndLayer.deleteCollection(collection);
        } catch (Exception e) {
            Utilities.logWarningMessage("Could not to delete the content");
        }
    }
}
