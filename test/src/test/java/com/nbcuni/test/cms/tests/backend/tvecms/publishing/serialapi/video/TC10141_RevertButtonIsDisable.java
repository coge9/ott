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
 * Created by Ivan_Karnilau on 13-Nov-15.
 */

/**
 * TC10141
 *
 * Step 1: Go to Roku CMS
 * Go to content page
 *
 * Step 2: Click "edit" next to test OTT Video
 *
 * Step 3: Update any data
 * Save
 *
 * Step 4: Click "edit" next to test OTT Video
 * Click publish to development
 * Expected result: Data is published
 * User is on list of content
 *
 * Step 5: Click "edit" next to test OTT Video
 * Update data from step3 again with new value
 * Save
 *
 * Step 6: Click "edit" next to test OTT Video
 * Click "revert"
 *
 * Step 7: Click "edit" next to test OTT Video
 * Check updated data
 * Expected result: Data has value from step3
 *
 * Validation: Verify buttons
 * Expected result: Revert and publish buttons are disabled
 */
public class TC10141_RevertButtonIsDisable extends BaseAuthFlowTest {

    private static final String NEW_CTA = SimpleUtils.getRandomString(6);
    private static final String UPDATE_NEW_CTA = SimpleUtils.getRandomString(6);

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkRevertButton(String brand) {
        Utilities.logInfoMessage("Check Revert button");
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        SoftAssert softAssert = new SoftAssert();

        String title = rokuBackEndLayer.openRandomContentAsset(ContentType.TVE_VIDEO);

        EditTVEVideoContentPage videoContentPage = new EditTVEVideoContentPage(webDriver, aid);

        videoContentPage.typeCTA(NEW_CTA);

        videoContentPage.clickSave();

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        videoContentPage = contentPage.openEditTVEVideoPage(title);

        RokuVideoJson expectedData = videoContentPage.getVideoObjectFromNodeMetadata();
        videoContentPage.elementPublishBlock().publishByTabName(PublishInstance.DEV);

        RokuVideoJson actualMetadata = requestHelper.getSingleParsedResponse(mainRokuAdminPage.getLogURL(brand), SerialApiPublishingTypes.VIDEO);

        softAssert.assertEquals(expectedData, actualMetadata, "Metadata is not correct in publish json",
                "Metadata is correct in publish json");

        contentPage = mainRokuAdminPage.openContentPage(brand);
        videoContentPage = contentPage.openEditTVEVideoPage(title);
        videoContentPage.typeCTA(UPDATE_NEW_CTA);
        videoContentPage.clickSave();

        contentPage = mainRokuAdminPage.openContentPage(brand);
        videoContentPage = contentPage.openEditTVEVideoPage(title);
        videoContentPage.elementPublishBlock().revertByTabName(PublishInstance.DEV);

        RokuVideoJson actualOldMetadata = videoContentPage.getVideoObjectFromNodeMetadata();

        softAssert.assertEquals(expectedData, actualOldMetadata, "Metadata is not correct in publish json",
                "Metadata is correct in publish json", new RokuVideoJsonVerificator());

        contentPage = mainRokuAdminPage.openContentPage(brand);
        videoContentPage = contentPage.openEditTVEVideoPage(title);

        softAssert.assertFalse(videoContentPage.elementPublishBlock().isPublishEnable(PublishInstance.DEV), "Publish button is enable",
                "Publish button is disable");
        softAssert.assertFalse(videoContentPage.elementPublishBlock().isRevertEnable(PublishInstance.DEV), "Revert button is enable",
                "Revert button is disable");

        softAssert.assertAll();
    }
}
