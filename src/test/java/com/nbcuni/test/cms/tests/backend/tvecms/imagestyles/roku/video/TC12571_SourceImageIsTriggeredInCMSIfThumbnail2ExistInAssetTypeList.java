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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * TC12571
 *
 * Pre-Conditions:
 * Go to CMS as admin and
 * select Program
 * select a Video related to Program above
 *
 * Steps:
 * 1. Go to MPX and select Program from pre-condition
 * The program is present
 * 2. Go to files tab
 * the list of sources is present
 * 3. Make sure (Upload in case if absent) there is a source with size 2560x1440
 * The source is present
 * 4. Set for source assetType = thumbnail2, thumbnail 2 test, android and save
 * the settings for Program is saved
 * 5. Go to CMS as admin and open content ad find Video from pre-condition
 * The Video from pre-condition is present
 * 6. Go to the Edit page of video and check source for 3tile_video_3,3tile_video_3_resume, 3tile_video_3a, 3tile_video_3a_resume images on the Roku images tab
 * The source is present and matched with those that set in MPX
 */

public class TC12571_SourceImageIsTriggeredInCMSIfThumbnail2ExistInAssetTypeList extends BaseAuthFlowTest {

    private static final String INITIAL_PROGRAM_TITLE = Config.getInstance().getMPXTestShowName();
    private static final String INITIAL_VIDEO_TITLE = Config.getInstance().getMPXTestVideoName();
    private static final String INITIAL_IMAGE = "3tile_program_3_source_2560x1440.png";

    public void checkInitialThumbnails() {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayerProgram = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayerProgram.updateAssetTitle(INITIAL_PROGRAM_TITLE);

        MPXLayer mpxLayerVideo = new MPXLayer(brand, ContentType.TVE_VIDEO);
        mpxLayerVideo.updateCategories(INITIAL_PROGRAM_TITLE);
    }

    //Lyoha: Disabled as not actual any more
    @Test(groups = {"video_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkOneTileThumbnailIsGeneratedForOTTProgram(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Check that 3 tile thumbnail is generated for OTT Video");
        checkInitialThumbnails();

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageAssetTypeByTitle(INITIAL_IMAGE, "Thumbnail2", "Thumbnail", "Android");
        mainRokuAdminPage.runCron(brand);

        String expectedSource = mpxLayer.getMpxThumbnailUrlByTitle(INITIAL_IMAGE);

        EditTVEVideoContentPage videoContentPage = mainRokuAdminPage.openContentPage(brand)
                .openEditTVEVideoPage(INITIAL_VIDEO_TITLE);

        String actualSource = videoContentPage.onImagesTab().elementThreeTileProgramArea().getLinkToSourceImage();

        softAssert.assertTrue(ImageUtil.compareUrlAndUrl(expectedSource, actualSource, 100.0),
                "Images source '3 Tile Program source' are not equals", "Images source '3 Tile Program source' are equals");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void after() {
        Utilities.logInfoMessage("Return data");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageAssetTypeByTitle(INITIAL_IMAGE, "Thumbnail2");

        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.runCron(brand);
    }
}
