package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.mediagallery;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.ContentTypeDeleteJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 08-Jul-16.
 */

/**
 * TC15294
 *
 * Pre-Conditions:
 * 1. Login in CMS D7 as admin
 * 2. Create Media Gallery with all required fields
 *
 * Steps
 * 1.
 * Go to content page
 * Content page is opened
 * 2.
 * Delete Media Gallery by bulk operation
 * Media Gallery is deleted.
 * Publish delete message:
 * Request data:
 * uuid: e29a1f78-cdfc-4022-9a77-ab721dd370da
 * itemType: "mediaGallery"
 * There are attributes:
 * action = 'delete'
 * entityType = 'mediaGallery'
 */
public class TC15297_DeleteMediaGalleryByBulkOperation extends BaseAuthFlowTest {

    private Content mediaGallery;

    @Autowired
    @Qualifier("defaultMediaGallery")
    private ContentTypeCreationStrategy galleryCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        mediaGallery = galleryCreationStrategy.createContentType();
    }

    @Test(groups = {"mediagallery_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testMediaGalleryDeletePublishing(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(mediaGallery).publish();
        rokuBackEndLayer.deleteContentTypesByBulkOperation(mediaGallery);

        String url = rokuBackEndLayer.getLogURL(brand);

        ContentTypeDeleteJson expectedDeleteJson = new ContentTypeDeleteJson(mediaGallery);
        ContentTypeDeleteJson actualDeleteJson = requestHelper.getDeleteResponses(url, ConcertoApiPublishingTypes.MEDIA_GALLERY).get(0);

        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        String action = localApiJson.getAttributes().getAction().getStringValue();

        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");

        String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(mediaGallery.getType().getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");

        softAssert.assertEquals(expectedDeleteJson, actualDeleteJson, "The actual data is not matched", "The JSON data is matched");

        softAssert.assertAll();

    }
}
