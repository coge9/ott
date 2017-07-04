package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.platform;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.AddPlatformPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.factory.CreateFactoryPlatform;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto.platform.PlatformJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 11/17/2016.
 */
public class TC16724_PublishPlatform_CheckPublishingNewPlatform extends BaseAuthFlowTest {

    private PlatformEntity platformEntity = CreateFactoryPlatform.createDefaultPlatform();

    /**
     * Pre-Condition:
     * 1. Login in CMS D7 as admin
     * 2. Create new platform
     *
     * Step 1: Click on Publish button
     * Result: Link to the publishing log is present.
     * The API log present 'success' status message of POST request
     * Step 2: Verify publishing log
     * Result: All fields are present and values are correct according
     * http://docs.concertoapiingestmaster.apiary.io/#reference/platforms/post-platforms/generate-message-body-to-create-or-update-platforms
     */

    @Test(groups = {"platform_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkTc16724(final String brand) {
        Utilities.logInfoMessage("Check publishing new platform");
        //Pre-Condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        AddPlatformPage editPlatformPage = rokuBackEndLayer.createPlatform(platformEntity);

        editPlatformPage.elementPublishBlock().publishByTabName();
        String url = editPlatformPage.getLogURL(brand);
        softAssert.assertTrue(editPlatformPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Expected result
        PlatformJson expectedPlatformJson = new PlatformJson(platformEntity);

        //Get Actual Post Request
        PlatformJson actualPlatformJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.PLATFORM);
        softAssert.assertTrue(actualPlatformJson.verifyObject(expectedPlatformJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedPlatform() {
        rokuBackEndLayer.deleteCreatedPlatform(platformEntity.getName());
    }
}
