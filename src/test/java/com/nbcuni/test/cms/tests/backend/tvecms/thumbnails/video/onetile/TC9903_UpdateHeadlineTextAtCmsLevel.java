package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.video.onetile;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageTileType;
import com.nbcuni.test.cms.utils.Config;
import org.testng.annotations.Test;

public class TC9903_UpdateHeadlineTextAtCmsLevel extends BaseAuthFlowTest {

    /**
     * TC9903 - Roku CMS: Update Headline text at Episode level
     *
     * precondition: verify current state and image for 1 tile image is correct
     *
     * Step 1: Open edit page for particular episode
     * Step 2: Update Headline text and save
     * Step 3: Go to episode edit page again
     * Step 4: Verify that 1 tile image updated
     *
     * */

    private static final String TITLE = "AQA_OTT_VIDEO";
    private static final String INIT_CTA = "FIRST SET CTA";
    private static final String INIT_HEADLINE = "FIRST SET HEAD";
    private static final String FIRST_IMAGE_NAME = "allFields.jpeg";
    private static final String SECOND_IMAGE_NAME = "head.jpeg";
    private static final String SECOND_HEADLINE = "SECOND SET HEAD";

    //Lyoha: Disabled. Not actual anymore
    @Test(groups = {"video_thumbnails"}, enabled = false)
    public void updateCtaText() {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        // precondition
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEVideoContentPage editVideo = contentPage
                .openEditTVEVideoPage(TITLE);
        String editPageTitle = webDriver.getCurrentUrl().split("\\?")[0];
        editVideo.fillForm(INIT_CTA, INIT_HEADLINE);
        editVideo.clickSave();
        webDriver.get(editPageTitle);
        //String imageLink = editVideo.onRokuImagesTab().getOneTileCustomLinks().get(CustomImageTypes.ONE_TILE_VIDEO_1);
        String patterFilePath = Config.getInstance().getLocalPathToRokuVideoThubnailImage(ImageTileType.ONE_TILE, FIRST_IMAGE_NAME, 1);
        //Assertion.assertTrue(ImageUtil.compareScreenshotAndUrl(patterFilePath, imageLink, 100.0), "error");
        // step 1
        webDriver.get(editPageTitle);
        // step 2
        editVideo.fillForm(INIT_CTA, SECOND_HEADLINE);
        editVideo.clickSave();
        // step 3
        webDriver.get(editPageTitle);
        // step 4
        //imageLink = editVideo.onRokuImagesTab().getOneTileCustomLinks().get(CustomImageTypes.ONE_TILE_VIDEO_1);
        patterFilePath = Config.getInstance().getLocalPathToRokuVideoThubnailImage(ImageTileType.ONE_TILE, SECOND_IMAGE_NAME, 1);
        //Assertion.assertTrue(ImageUtil.compareScreenshotAndUrl(patterFilePath, imageLink, 100.0), "error");
    }

}
