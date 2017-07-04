package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.video.threetilevideo;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.utils.SoftAssert;
import org.testng.annotations.Test;

/**
 * TC10825
 *
 * Step 1: Go to Roku CMS as Editor
 *
 * Step 2: Go to content page
 *
 * Step 3: Open edit video page for any video
 *
 * Step 4: Go to image tab
 *
 * Step 5: Click on "Image preview" link below 3 Tile source image
 *
 * Validation: Verify thumbnails
 * Expected: Preview page should contains relevant custom thumbnail styles only:
 * 3tile_video_1
 * 3tile_video_1_now_playing
 * 3tile_video_1_resume
 * 3tile_video_2
 * 3tile_video_2_resume
 * 3tile_video_2_now_playing
 * 3tile_video_4
 * 3tile_video_5
 * 3tile_video_6
 * 3tile_video_6_resume
 * 3tile_video_7
 * 3tile_video_8
 */
public class TC10825_ImagePreview_Video_3TileVideoSource extends BaseAuthFlowTest {

    private static final String VIDEO_TITLE = "AQA_OTT_VIDEO";

    //Lyoha: Disabled. Not actual anymore
    @Test(groups = {"video_thumbnails"}, enabled = false)
    public void checkImagePreview() {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        SoftAssert softAssert = new SoftAssert();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEVideoContentPage videoContentPage = contentPage.openEditTVEVideoPage(VIDEO_TITLE);

//        videoContentPage.onRokuImagesTab().elementThreeTitleArea().verifyThumbnailsPresent(
//                CustomImageTypes.getVideoThreeTileVideoSourceTypes(), softAssert);

        softAssert.assertAll();
    }
}
