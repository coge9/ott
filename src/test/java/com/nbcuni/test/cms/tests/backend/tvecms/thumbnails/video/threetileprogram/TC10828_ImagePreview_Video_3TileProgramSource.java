package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.video.threetileprogram;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.utils.SoftAssert;
import org.testng.annotations.Test;

/**
 * TC10828
 *
 * Step 1: Go to Roku CMS as Editor
 *
 * Step 2: Go to content page
 *
 * Step 3: Open edit video page for any video
 *
 * Step 4: Go to image tab
 *
 * Step 5: Click on "Image preview" link below 3 Tile program source image
 *
 * Validation: Verify thumbnails
 * Expected: Preview page should contains relevant custom thumbnail styles only:
 * 3tile_video_3
 * 3tile_video_3a
 * 3tile_video_3_resume
 * 3tile_video_3a_resume
 */
public class TC10828_ImagePreview_Video_3TileProgramSource extends BaseAuthFlowTest {

    private static final String VIDEO_TITLE = "AQA_OTT_VIDEO";

    @Test(groups = {"video_thumbnails"}, enabled = false)
    public void checkImagePreview() {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        SoftAssert softAssert = new SoftAssert();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEVideoContentPage videoContentPage = contentPage.openEditTVEVideoPage(VIDEO_TITLE);

//        videoContentPage.onRokuImagesTab().elementThreeTileProgramArea().verifyThumbnailsPresent(
//                CustomImageTypes.getVideoThreeTileProgramTypes(), softAssert);

        softAssert.assertAll();
    }
}
