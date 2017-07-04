package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.collections.curatedcollection;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.ContentTypeDeleteJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.CollectionDeleteJsonVerificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 07-Jul-16.
 */

/**
 * TC15289
 *
 * Pre-Conditions:
 * 1. Login in CMS D7 as admin
 * 2. Create Curated Collection with all required fields
 *
 * Steps
 * 1.
 * Go to content page
 * Content page is opened
 * 2.
 * Delete Curated Collection manually on content list screen
 * Curated Collection is deleted.
 * Publish delete message:
 * Request data:
 * uuid: e29a1f78-cdfc-4022-9a77-ab721dd370da
 * itemType: "list"
 * There are attributes:
 * action = 'delete'
 * entityType = 'lists'
 */
public class TC15289_DeleteCuratedCollectionManually extends BaseAuthFlowTest {

    private Collection collection;

    @Autowired
    @Qualifier("curatedCollectionWithRequiredFields")
    private CollectionCreationStrategy collectionCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        collection = collectionCreationStrategy.createCollection();
    }

    @Test(groups = {"collection_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkDeleteMessage(final String brand) {

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        rokuBackEndLayer.createCollection(collection).publish();
        rokuBackEndLayer.deleteCollection(collection);

        String url = rokuBackEndLayer.getLogURL(brand);

        ContentTypeDeleteJson expectedDeleteJson = new ContentTypeDeleteJson(collection);

        ContentTypeDeleteJson actualDeleteJson = requestHelper.getDeleteResponses(url, ConcertoApiPublishingTypes.COLLECTIONS).get(0);

        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        String action = localApiJson.getAttributes().getAction().getStringValue();

        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");

        String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(collection.getCollectionInfo().getItemType().getEntityType(), entityType,
                "The entityType message attribute are not matched", "The entityType message attribute are matched");

        softAssert.assertTrue(new CollectionDeleteJsonVerificator().verify(expectedDeleteJson, actualDeleteJson), "The actual data is not matched",
                "The JSON data is matched");

        softAssert.assertAll();

    }
}
