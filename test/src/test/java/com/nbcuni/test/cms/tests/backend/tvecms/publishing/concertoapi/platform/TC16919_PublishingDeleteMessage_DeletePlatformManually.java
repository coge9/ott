package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.platform;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.AddPlatformPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.factory.CreateFactoryPlatform;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.ContentTypeDeleteJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Dzianis_Kulesh on 12/09/2016.
 */
public class TC16919_PublishingDeleteMessage_DeletePlatformManually extends BaseAuthFlowTest {

    /**
     * TC16919 - CMS D7: Publish Platform: Check publishing delete message
     * <p>
     * <p>
     * Pre-conditions:
     * 1. Login in CMS D7 as admin
     * 2. Create new platform
     * <p>
     * Steps:
     * 1. Click on Publish button
     * Verify: Link to the publishing log is present. The API log present 'success' status message of POST request.
     * <p>
     * 2. Delete just published platform.
     * Verify: Platform is deleted. Message about successful delete is show. Link to the publishing log is present.
     * <p>
     * 3. Verify publishing log.
     * Verify: All fields are present and values are correct according http://docs.concertoapiingestmaster.apiary.io/#reference/-staticpage/post-staticpage/generate-message-body-to-delete-platforms
     */

    private PlatformEntity platformEntity = CreateFactoryPlatform.createDefaultPlatform();

    @Test(groups = {"platform_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkDeletingPlatform(String brand) {
        Utilities.logInfoMessage("Check publishing new platform");
        //Pre-Condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        AddPlatformPage editPlatformPage = rokuBackEndLayer.createPlatform(platformEntity);
        // step 1
        editPlatformPage.elementPublishBlock().publishByTabName();
        // step 2
        rokuBackEndLayer.deleteCreatedPlatform(platformEntity.getName());
        // step 3
        String url = rokuBackEndLayer.getLogURL(brand);
        ContentTypeDeleteJson expectedDeleteJson = new ContentTypeDeleteJson(platformEntity);
        ContentTypeDeleteJson actualDeleteJson = requestHelper.getSingleDeleteResponses(url, ConcertoApiPublishingTypes.PLATFORM);
        String action = requestHelper.getSingleLocalApiJson(url).getAttributes().getAction().getStringValue();
        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");
        softAssert.assertEquals(expectedDeleteJson, actualDeleteJson, "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}


