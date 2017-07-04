package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.collections.curatedcollection;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionAbstractPage;
import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionsContentPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.ContentTypeDeleteJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.CollectionDeleteJsonVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 3/2/2017.
 */

/**
 * Pre-Conditions:
 * 1. Login in CMS D7 as admin
 * 2. Create Curated Collection with all required fields
 * Steps:
 * 1. Go to Collection list page
 * Collection list page is opened
 * 2. Delete Curated Collection by bulk operation
 * Curated Collection is deleted.
 * Publish delete message:

 * Request data:
 * uuid: e29a1f78-cdfc-4022-9a77-ab721dd370da
 * itemType: "list"
 * There are attributes:

 * action = 'delete'
 * entityType = 'lists'
 */

public class TC17555_PublishingDeleteMessageDeleteCuratedCollectionByBulkOperation extends BaseAuthFlowTest {

    private Collection collection;

    @Autowired
    @Qualifier("curatedCollectionWithRequiredFields")
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
        collectionPage.publish();
        //set Data for expected json

        CollectionsContentPage collectionsContentPage = mainRokuAdminPage.openPage(CollectionsContentPage.class, brand);
        collectionsContentPage.checkAnItem(collection);
        collectionsContentPage.executeDelete();

        softAssert.assertTrue(collectionPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        String url = rokuBackEndLayer.getLogURL(brand);

        //Get Expected result

        ContentTypeDeleteJson actualDeleteJson = requestHelper.getDeleteResponses(url, ConcertoApiPublishingTypes.COLLECTIONS).get(0);
        ContentTypeDeleteJson expectedDeleteJson = new ContentTypeDeleteJson(collection);

        softAssert.assertTrue(new CollectionDeleteJsonVerificator().verify(expectedDeleteJson, actualDeleteJson), "Delete jsons are not matched", "Delete jsons are matched");

        LocalApiJson actualMetadata = requestHelper.getSingleLocalApiJson(url);
        String action = actualMetadata.getAttributes().getAction().getStringValue();
        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");
        String entityType = actualMetadata.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(ItemTypes.COLLECTIONS.getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}
