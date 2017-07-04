package com.nbcuni.test.cms.tests.smoke.tvecms.publishing.concertoapi;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.AddPlatformPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.factory.CreateFactoryPlatform;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 11/18/2016.
 */
public class TC16742_PublishingPlatform_ValidateScheme extends BaseAuthFlowTest {

    private PlatformEntity platformEntity = CreateFactoryPlatform.createDefaultPlatform();

    /**
     * Pre-Condition:
     * 1. Login in CMS D7 as admin
     * 2. Create new platform
     * <p>
     * Step 1: Click on Publish button
     * Result: Link to the publishing log is present.
     * The API log present 'success' status message of POST request
     * Step 2: Verify publishing log
     * Result: All fields are present and values are correct according
     * http://docs.concertoapiingestmaster.apiary.io/#reference/platforms/post-platforms/generate-message-body-to-create-or-update-platforms
     */

    @Test(groups = {"platform_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void validateScheme(final String brand) {
        Utilities.logInfoMessage("Verify json scheme");

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        AddPlatformPage editPlatformPage = rokuBackEndLayer.createPlatform(platformEntity);

        editPlatformPage.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(editPlatformPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        String url = mainRokuAdminPage.getLogURL(brand);

        //Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validatePlatformBySchema(localApiJson.getRequestData().toString()), "The validation has failed", "The validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedPlatform() {
        try {
            if (StringUtils.isEmpty(platformEntity.getName())) {
                rokuBackEndLayer.deleteCreatedPlatform(platformEntity.getName());
            }
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error in tear-down method " + Utilities.convertStackTraceToString(e));
        }
    }
}
