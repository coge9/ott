package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.video.onetile;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageTileType;
import com.nbcuni.test.cms.utils.Config;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TC9900_RokuCmsUpdateTitleAtMpx extends BaseAuthFlowTest {

    /**
     * TC9900 - Roku CMS: Update title at MPX
     *
     * precondition: verify current state and image for 1 tile image is correct
     *
     * Step 1: Update Show name for particular episode at MPX (Series field)
     * Step 2: Run cron Step 3: Go to episode edit page Step 4: Verify that 1
     * tile image updated
     *
     * */

    private static final String TITLE = "AQA_OTT_VIDEO";
    private static final String INIT_CTA = "FIRST SET CTA";
    private static final String INIT_HEADLINE = "FIRST SET HEAD";
    private static final String FIRST_IMAGE_NAME = "allFields.jpeg";
    private static final String SECOND_IMAGE_NAME = "title.jpeg";
    private static final String SECOND_CATEGORIES = "Mr. Robot";
    private String initTitle;
    private String initCategories;
    private Map<String, String> updateDataForMpx;

    //Lyoha: Disabled. Not actual anymore
    @Test(groups = "video_thumbnails", enabled = false)
    public void updateCtaText() {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        // precondition
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEVideoContentPage editVideo = contentPage
                .openEditTVEVideoPage(TITLE);
        initTitle = editVideo.collectMPXData().get("Title");
        initCategories = editVideo.collectMPXData().get("Categories");
        String editPageTitle = webDriver.getCurrentUrl().split("\\?")[0];
        editVideo.fillForm(INIT_CTA,
                INIT_HEADLINE);
        editVideo.clickSave();
        webDriver.get(editPageTitle);
        //String imageLink = editVideo.onRokuImagesTab().getOneTileCustomLinks()
        //        .get(CustomImageTypes.ONE_TILE_VIDEO_1);
        String patterFilePath = Config.getInstance()
                .getLocalPathToRokuVideoThubnailImage(ImageTileType.ONE_TILE,
                        FIRST_IMAGE_NAME, 1);
        //Assertion.assertTrue(ImageUtil.compareScreenshotAndUrl(patterFilePath,
        //        imageLink, 100.0), "error");
        // state 1
        updateDataForMpx = new HashMap<String, String>();
        updateDataForMpx.put("Categories", SECOND_CATEGORIES);
        rokuBackEndLayer.loginAndSearchAssetInMPX(initTitle);
        rokuBackEndLayer.updateMpxData(updateDataForMpx);
        webDriver.get(editPageTitle);
        // state 2
        editVideo.runCron(brand);
        // state 3
        webDriver.get(editPageTitle);
        // state 4
        //imageLink = editVideo.onRokuImagesTab().getOneTileCustomLinks()
        //        .get(CustomImageTypes.ONE_TILE_VIDEO_1);
        patterFilePath = Config.getInstance()
                .getLocalPathToRokuVideoThubnailImage(ImageTileType.ONE_TILE,
                        SECOND_IMAGE_NAME, 1);
        //Assertion.assertTrue(ImageUtil.compareScreenshotAndUrl(patterFilePath,
        //        imageLink, 100.0), "error");
        rokuBackEndLayer.loginAndSearchAssetInMPX(initTitle);
        rokuBackEndLayer.updateMpxData(updateDataForMpx);
    }

    @AfterMethod(alwaysRun = true)
    public void restoreInitMpxValues() {
        updateDataForMpx.put("Categories", initCategories);
        rokuBackEndLayer.loginAndSearchAssetInMPX(initTitle);
        rokuBackEndLayer.updateMpxData(updateDataForMpx);
    }
}
