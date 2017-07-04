package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.video;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.PublishInstance;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.RokuVideoJsonVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Ivan on 19.11.2015.
 */

/**
 * TC10139
 *
 * Step 1: Go to content page
 *
 * Step 2: Open edit page for any video
 *
 * Step 3: Fill all fields
 *
 * Step 4: Click publish to development
 *
 * Validation: Verify status message and verify log
 */

public class TC10139_PublishVideoToAPIWithAllFilledFields extends BaseAuthFlowTest {

    private static final String NEW_CTA = SimpleUtils.getRandomString(6);

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkPublishButton(String brand) {
        Utilities.logInfoMessage("Check Revert button");
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        SoftAssert softAssert = new SoftAssert();

//      Step 1 and 2
        String title = rokuBackEndLayer.openRandomContentAsset(ContentType.TVE_VIDEO, brand);

        EditTVEVideoContentPage videoContentPage = new EditTVEVideoContentPage(webDriver, aid);

//      Step 3
        videoContentPage.typeCTA(NEW_CTA);

        videoContentPage.clickSave();

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        videoContentPage = contentPage.openEditTVEVideoPage(title);

//      Step 4
        RokuVideoJson expectedData = videoContentPage.getVideoObjectFromNodeMetadata();
        videoContentPage.elementPublishBlock().publishByTabName();

        RokuVideoJson actualMetadata = requestHelper.getSingleParsedResponse(mainRokuAdminPage.getLogURL(brand), SerialApiPublishingTypes.VIDEO);

        softAssert.assertEquals(expectedData, actualMetadata, "Metadata is not correct in publish json",
                "Metadata is correct in publish json", new RokuVideoJsonVerificator());

        contentPage = mainRokuAdminPage.openContentPage(brand);
        videoContentPage = contentPage.openEditTVEVideoPage(title);

        softAssert.assertFalse(videoContentPage.elementPublishBlock().isPublishEnable(PublishInstance.DEV), "Publish button is enable",
                "Publish button is disable");
        softAssert.assertTrue(videoContentPage.isStatusMessageShown(), "Status message is not present",
                "Status message is pesent", webDriver);
        softAssert.assertAll();
    }
}
