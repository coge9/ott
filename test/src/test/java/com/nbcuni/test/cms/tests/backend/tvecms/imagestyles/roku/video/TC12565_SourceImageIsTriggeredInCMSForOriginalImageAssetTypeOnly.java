package com.nbcuni.test.cms.tests.backend.tvecms.imagestyles.roku.video;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * TC12565
 *
 * Pre-Conditions:
 * Go to CMS as admin and
 * select a Video
 *
 * Steps:
 * 1. Go to MPX and select Video from pre-condition
 * The video is present
 * 2. Go to files tab
 * the list of sources is present
 * 3. Make sure (Upload in case if absent) there is a source with asset type 'Original Image'
 * The source is present
 * 4. Make sure assetType has value = Original Image only! and save
 * the settings for Video is saved
 * 5. Go to CMS as admin and open content ad find Video from pre-condition
 * The Video from pre-condition is present
 * 6. Go to the Edit page of video and check source for 1tile_video_1, 3tile_video_1, 3tile_video_1_now_playing, 3tile_video_1_resume, 3tile_video_2, 3tile_video_2_resume, 3tile_video_4, 3tile_video_5,3 tile_video6, 3tile_video_6_resume, 3tile_video_7, 3tile_video_8 images on the Roku images tab
 * The source is present and matched with those that set in MPX
 */

public class TC12565_SourceImageIsTriggeredInCMSForOriginalImageAssetTypeOnly extends BaseAuthFlowTest {

    private static final String INITIAL_TITLE = Config.getInstance().getMPXTestVideoName();
    private static final String INITIAL_IMAGE = "WrongImage.jpg";

    public void checkInitialThumbnails() {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        mpxLayer.updateImageAssetTypeByTitle(INITIAL_IMAGE, "Original Image");

        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.runCron(brand);
    }

    @Test(groups = {"video_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkOneTileThumbnailIsGeneratedForOTTProgram(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Check that 3tile thumbnail is generated for OTT Program");
        checkInitialThumbnails();

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        mpxLayer.updateImageAssetTypeByTitle(INITIAL_IMAGE, "Original Image");
        mainRokuAdminPage.runCron(brand);

        String expectedSource = mpxLayer.getMpxThumbnailUrlByTitle(INITIAL_IMAGE);

        EditTVEVideoContentPage videoContentPage = mainRokuAdminPage.openContentPage(brand)
                .openEditTVEVideoPage(INITIAL_TITLE);

        String actualSource = videoContentPage.onImagesTab().elementThreeTitleArea().getLinkToSourceImage();

        softAssert.assertTrue(ImageUtil.compareUrlAndUrl(expectedSource, actualSource, 100.0),
                "Images source '3 Tile source' are not equals", "Images source '3 Tile source' are equals");

        softAssert.assertAll();
    }
}
