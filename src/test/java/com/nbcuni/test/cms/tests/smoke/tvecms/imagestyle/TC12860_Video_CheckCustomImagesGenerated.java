package com.nbcuni.test.cms.tests.smoke.tvecms.imagestyle;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 26-Oct-15.
 */
public class TC12860_Video_CheckCustomImagesGenerated extends BaseAuthFlowTest {

    /**
     * Step 1. go to Roku CMS
     * Verify: user is on CMS
     *
     * Step 2. go to /admin/content
     * Verify: click "edit" next to test OTT Video
     * OTT Program page is opened
     *
     * Step 3. see THUMBNAILS block
     * find "tile1" line
     * Verify: 1tile image URL is displayed
     *
     * Step 4. open Roku Image Tab
     * Verify: All Roku Custom images are present
     *
     * Step 5: open android Image Tab
     * Verify:All Android Custom Images are present
     * */

    private String initialTitle;

    @Test(groups = {"video_thumbnails", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkCustomThumbnailAreGeneratedForOTTVideo(final String brand) {
        Utilities.logInfoMessage("Check that 1tile thumbnail is generated for OTT Program");
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        initialTitle = contentPage.getRandomAsset(ContentType.TVE_VIDEO);
        EditTVEVideoContentPage videoContentPage = contentPage.openEditTVEVideoPage(initialTitle);

        softAssert.assertTrue(videoContentPage.onImagesTab().isOriginalCustomLinkPresent(softAssert), "The original images are not present for video",
                "The original images are present for video: " + initialTitle, webDriver);

        softAssert.assertTrue(videoContentPage.onImagesTab().isVideoThreeTileCustomLinkPresent(softAssert), "The 3_Tile custom images are not present for video",
                "The 3_Tile custom images are present for video: " + initialTitle, webDriver);

        softAssert.assertTrue(videoContentPage.onImagesTab().isVideoPortrait720x960CustomLinkPresent(softAssert), "The Android Portrait 720 960 images are not present for video",
                "The Android Portrait 720 960 images are present for video: " + initialTitle, webDriver);

        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}
