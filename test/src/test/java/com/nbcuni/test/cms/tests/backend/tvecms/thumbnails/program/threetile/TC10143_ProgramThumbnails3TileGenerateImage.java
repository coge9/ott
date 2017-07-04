package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.program.threetile;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.GettingImages;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 02-Nov-15.
 */

/**
 * TC10143
 *
 * Step 1: Login to Roku CMS as Admin
 *
 * Step 2: Run crone
 *
 * Step 3: go to /admin/content
 * click on "edit" for published OTT Program
 *
 * Step 4: Go to Basic information tab
 *
 * Validation: Click the ‘preview’ link below the image to see the relevant burn-in image styles
 * Expected: '3tile_program_1', '3tile_program_3' is presented.
 * Size of images is  340x191.
 * Gradient is presented.
 * '3tile_program_3' has a text "Go to Show Page"
 */
public class TC10143_ProgramThumbnails3TileGenerateImage extends BaseAuthFlowTest {

    private static final String INITIAL_TITLE = "AQA_OTT_PROGRAM";
    private static final String INITIAL_IMAGE = "NBCU_USA_RoyalPains_show_retina_480x270_480x270_29209155595.jpg";
    private static final String UPDATE_IMAGE = "480x270.jpg";

    @BeforeMethod(alwaysRun = true)
    public void checkInitialThumbnails() {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageDimensionsByTitle(INITIAL_IMAGE, "480", "270");
        mpxLayer.updateImageDimensionsByTitle(UPDATE_IMAGE, "2", "2");

        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        mainRokuAdminPage.runCron(brand);
    }

    @Test(groups = {"program_thumbnails"})
    public void checkOneTileThumbnailIsGeneratedForOTTProgram() {
        Utilities.logInfoMessage("Check that 3tile thumbnail is generated for OTT Program");
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        SoftAssert softAssert = new SoftAssert();
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(INITIAL_TITLE);

        String threeTileProgramOne = programContentPage.onRokuImagesTab().getThreeTileCustomLinks()
                .get(CustomImageTypes.THREE_TILE_PROGRAM_1);

        String threeTileProgramThree = programContentPage.onRokuImagesTab().getThreeTileCustomLinks()
                .get(CustomImageTypes.THREE_TILE_PROGRAM_3);

        String initialThumbnailsOnePath = GettingImages.getInitialThreeTileProgramOneThumbnails();
        String initialThumbnailsThreePath = GettingImages.getInitialThreeTileProgramThreeThumbnails();

        softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(initialThumbnailsOnePath,
                threeTileProgramOne, 100.0), "Images '3 tile program 1' are not equals", "Images '3 tile program 1' are equals");

        softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(initialThumbnailsThreePath,
                threeTileProgramThree, 100.0), "Images '3 tile program 3' are not equals", "Images '3 tile program 3' are equals");

        softAssert.assertAll();
    }
}
