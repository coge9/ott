package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.video.onetile;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageTileType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Map;

public class TC9898_RokuCmsUploadImageAtMpx extends BaseAuthFlowTest {

    /**
     * TC9898 - Roku CMS: Upload image at MPX
     *
     * precondition: verify current state and image for 1 tile image is correct
     *
     * Step 1: Upload new image for particular episode at MPX
     * Step 2: Set assets type (Large 1, Thumbnail, Win8 Tablet
     * Step 3: Run cron
     * Step 4: Go to episode edit page
     * Step 5: Verify that 1 tile image updated
     *
     * */

    private static final String TITLE = "AQA_OTT_VIDEO";
    private static final String INIT_CTA = "FIRST SET CTA";
    private static final String INIT_HEADLINE = "FIRST SET HEAD";
    private static final String FIRST_IMAGE_NAME = "allFields.jpeg";
    private static final String SECOND_IMAGE_NAME = "allFields.jpeg";
    private String initTitle;
    private Map<String, String> updateDataForMpx;


    //Lyoha: Disabled. Not actual anymore
    @Test(groups = "video_thumbnails", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void updateCtaText(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        // precondition
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEVideoContentPage editVideo = contentPage
                .openEditTVEVideoPage(TITLE);
        initTitle = editVideo.collectMPXData().get("Title");
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
        // step 1 - 2
        rokuBackEndLayer.loginAndSearchAssetInMPX(initTitle);
        rokuBackEndLayer.updateImages("1X1", "864X486");
        // step 3
        webDriver.get(editPageTitle);
        editVideo.runCron(brand);
        // step 4
        webDriver.get(editPageTitle);
        // step 5
        //imageLink = editVideo.onRokuImagesTab().getOneTileCustomLinks()
        //        .get(CustomImageTypes.ONE_TILE_VIDEO_1);
        patterFilePath = Config.getInstance()
                .getLocalPathToRokuVideoThubnailImage(ImageTileType.ONE_TILE,
                        SECOND_IMAGE_NAME, 2);
        //Assertion.assertTrue(ImageUtil.compareScreenshotAndUrl(patterFilePath,
        //        imageLink, 100.0), "error");
        rokuBackEndLayer.loginAndSearchAssetInMPX(initTitle);
        rokuBackEndLayer.updateMpxData(updateDataForMpx);
    }

    @AfterMethod(alwaysRun = true)
    public void restoreInitMpxValues() {
        rokuBackEndLayer.loginAndSearchAssetInMPX(initTitle);
        rokuBackEndLayer.updateImages("1X1", "864X486");
    }
}
