package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.video;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.PublishInstance;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
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
 * <p>
 * Step 1: Go to content page get a video
 * <p>
 * Step 2: Update 'Series Type' field of the video in the MPX
 * <p>
 * Step 3: Run Cron
 * <p>
 * Step 4: Open edit page for the video
 * <p>
 * Step 5: Click publish to development
 * <p>
 * Validation: Verify status message and verify log
 */

public class TC16228_CheckSeriesTypePublishVideo extends BaseAuthFlowTest {

    private static final String NEW_CTA = SimpleUtils.getRandomString(6);
    private static final String SERIES_TYPE = SimpleUtils.getRandomString(6);

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkSeriesType(String brand) {
        Utilities.logInfoMessage("Check Revert button");
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        SoftAssert softAssert = new SoftAssert();

        //Step 1
        String title = rokuBackEndLayer.openRandomContentAsset(ContentType.TVE_VIDEO, brand);
        EditTVEVideoContentPage videoContentPage = new EditTVEVideoContentPage(webDriver, aid);

        // Step 2 Update Series Title of an asset
        String mpxID = videoContentPage.getVideo().getMpxAsset().getId();
        MPXLayer mpxLayer = new MPXLayer(brand, Config.getInstance().getRokuMPXOwnerID(brand, Instance.STAGE), mpxID);
        mpxLayer.updateSeriesType(SERIES_TYPE);

        //step 3
        mainRokuAdminPage.runCron(brand);

        //Step 4
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        videoContentPage = contentPage.openEditTVEVideoPage(title);
        videoContentPage.typeCTA(NEW_CTA);

        videoContentPage.clickSave();

        // Step 5
        RokuVideoJson expectedData = videoContentPage.getVideoObjectFromNodeMetadata();
        videoContentPage.elementPublishBlock().publishByTabName(PublishInstance.DEV);

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
