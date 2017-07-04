package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.video;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import org.testng.annotations.Test;

/**
 * Created by Aliaksei_Klimenka1 on 8/25/2016.
 */
public class TC15828_PublishingDeleteMessage_DeleteVideoByBulkOperation extends BaseAuthFlowTest {

    /**Pre-condition:
     * Login in CMS as Editor
     * Create Video with required fields.
     * Step 1: Navigate TVE Video page
     * Verify: TVE Video page is opened.
     * Step 2: Delete the Video we have created in pre-conditions by bulk operation.
     * Verify: The Video is deleted successfully. Status message is present. Delete message sent to Concerto API
     * Step 3: Check post tequest for node.
     * Verify: There are attributes:
     *          1. action = "delete"
     *          2. entityType = "videos"
     */

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkDeleteMessage(String brand) {

        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        mainRokuAdminPage.openMpxUpdaterPage(brand).runUpdaterByMPXID(mpxLayer.getAssetID());

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.checkAnItem(mpxLayer.getAssetTitle());
        contentPage.executeDelete();

        LocalApiJson actualMetadata = requestHelper.getSingleLocalApiJson(mainRokuAdminPage.getLogURL(brand));
        String action = actualMetadata.getAttributes().getAction().getStringValue();
        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");
        String entityType = actualMetadata.getAttributes().getAction().getStringValue();
        softAssert.assertEquals(ItemTypes.VIDEO.getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");
        softAssert.assertAll();

    }

}
