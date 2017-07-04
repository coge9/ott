package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.video.onetile;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.utils.SoftAssert;
import org.testng.annotations.Test;

/**
 * TC10829
 *
 * Step 1: Go to Roku CMS as Editor
 *
 * Step 2: Go to content page
 *
 * Step 3: Open edit video page for any video
 *
 * Step 4: Go to home page feature carousel tab
 *
 * Step 5: Click on "Image preview" link below 1 Tile source image
 *
 * Validation: Verify thumbnails
 * Expected: Preview page should contains relevant custom thumbnail styles only:
 * -1tile_video_1
 */
public class TC10829_ImagePreview_Video_1TileSource extends BaseAuthFlowTest {

    private static final String VIDEO_TITLE = "AQA_OTT_VIDEO";

    //Lyoha: Disabled. Not actual anymore
    @Test(groups = {"video_thumbnails"}, enabled = false)
    public void checkImagePreview() {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        SoftAssert softAssert = new SoftAssert();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEVideoContentPage videoContentPage = contentPage.openEditTVEVideoPage(VIDEO_TITLE);
//        videoContentPage.onRokuImagesTab().elementOneTileArea().verifyThumbnailsPresent(
//                CustomImageTypes.getVideoOneTileTypes(), softAssert);
        softAssert.assertAll();
    }
}
