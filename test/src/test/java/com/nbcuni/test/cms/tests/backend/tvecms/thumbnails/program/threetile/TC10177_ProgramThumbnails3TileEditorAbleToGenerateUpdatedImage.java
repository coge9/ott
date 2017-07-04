package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.program.threetile;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.GettingImages;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 02-Nov-15.
 */

/**
 * TC10177
 *
 * Step 1: Login to Roku CMS as Editor
 *
 * Step 2: Run crone
 *
 * Step 3: Go to /admin/content
 * Click on "edit" for published OTT Program
 *
 * Step 4: Go to Basic information tab
 *
 * Step 5: Click the ‘preview’ link below the image to see the relevant burn-in image styles
 *
 * Step 6: Open Thumbnails image link for 3tile_program_1
 * Verify: Size of image is 340x191. Gradient is presented
 *
 * Step 7: Open Thumbnails image link for 3tile_program_3
 * Verify: Size of image is 340x191. Gradient is presented
 *
 * Step 8: In MPX update image for "Main, Retina, Thumbnail, Win8 Phone" for the show asset 480x270
 *
 * Step 9: Repeat steps 1-5
 *
 * Step 10: Open Thumbnails image link for 3tile_program_1
 * Verify: Size of image is 340x191. Gradient is presented
 *
 * Step 11: Open Thumbnails image link for 3tile_program_3
 * Verify: Size of image is 340x191. Gradient is presented. Text "Go to show page" is present.
 */
public class TC10177_ProgramThumbnails3TileEditorAbleToGenerateUpdatedImage extends BaseAuthFlowTest {

    private static final String INITIAL_TITLE = "AQA_OTT_PROGRAM";
    private static final String INITIAL_IMAGE = "NBCU_USA_RoyalPains_show_retina_480x270_480x270_29209155595.jpg";
    private static final String UPDATE_IMAGE = "480x270.jpg";

    public void checkInitialThumbnails() {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageDimensionsByTitle(INITIAL_IMAGE, "480", "270");
        mpxLayer.updateImageDimensionsByTitle(UPDATE_IMAGE, "2", "2");

        SoftAssert softAssert = new SoftAssert();
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        mainRokuAdminPage.runCron(brand);

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

    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkOneTileThumbnailIsGeneratedForOTTProgram(final String brand) {
        Utilities.logInfoMessage("Check that 3tile thumbnail is generated for OTT Program");
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        SoftAssert softAssert = new SoftAssert();

        checkInitialThumbnails();
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageDimensionsByTitle(UPDATE_IMAGE, "480", "270");
        mpxLayer.updateImageDimensionsByTitle(INITIAL_IMAGE, "2", "2");

        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        mainRokuAdminPage.runCron(brand);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(INITIAL_TITLE);

        String threeTileProgramOne = programContentPage.onRokuImagesTab().getThreeTileCustomLinks()
                .get(CustomImageTypes.THREE_TILE_PROGRAM_1);

        String threeTileProgramThree = programContentPage.onRokuImagesTab().getThreeTileCustomLinks()
                .get(CustomImageTypes.THREE_TILE_PROGRAM_3);

        String updatedThumbnailsOnePath = GettingImages.getOnlyImageUpdatedThreeTileProgramOneThumbnails();
        String updatedThumbnailsThreePath = GettingImages.getOnlyImageUpdatedThreeTileProgramThreeThumbnails();

        softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(updatedThumbnailsOnePath,
                threeTileProgramOne, 100.0), "Images '3 tile program 1' are not equals", "Images '3 tile program 1' are equals");

        softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(updatedThumbnailsThreePath,
                threeTileProgramThree, 100.0), "Images '3 tile program 3' are not equals", "Images '3 tile program 3' are equals");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void restoreInitMpxValues() {
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageDimensionsByTitle(INITIAL_IMAGE, "480", "270");
        mpxLayer.updateImageDimensionsByTitle(UPDATE_IMAGE, "2", "2");
    }
}
