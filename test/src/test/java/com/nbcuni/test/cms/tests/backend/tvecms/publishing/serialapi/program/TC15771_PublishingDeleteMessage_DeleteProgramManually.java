package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.program;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import org.testng.annotations.Test;

/**
 * Created by Aliaksei_Klimenka1 on 8/25/2016.
 */
public class TC15771_PublishingDeleteMessage_DeleteProgramManually extends BaseAuthFlowTest {

    /**Pre-condition:
     * Login in CMS
     * Create Program with required fields.
     * Step 1: Navigate TVE Program page
     * Verify: TVE Program page is opened.
     * Step 2: Delete the Program we have created in pre-condition.
     * Verify: The Program is deleted successfully. Status message is present. Delete message sent to Concerto API
     * Step 3: Check post tequest for node.
     * Verify: There are attributes:
     *          1. action = "delete"
     *          2. entityType = "series"
     */

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkDeleteMessage(String brand) {

        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
//      Step 1 and 2
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        mainRokuAdminPage.openMpxUpdaterPage(brand).runUpdaterByMPXID(mpxLayer.getAssetID());

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.searchByTitle(mpxLayer.getAssetTitle()).apply();
        contentPage.clickDeleteButton(mpxLayer.getAssetTitle());

        LocalApiJson localApiJson = requestHelper.getLocalApiJsons(mainRokuAdminPage.getLogURL(brand), SerialApiPublishingTypes.PROGRAM).get(0);

        String action = localApiJson.getAttributes().getAction().getStringValue();
        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");
        String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(ItemTypes.SERIES.getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");
        softAssert.assertAll();
    }

}
