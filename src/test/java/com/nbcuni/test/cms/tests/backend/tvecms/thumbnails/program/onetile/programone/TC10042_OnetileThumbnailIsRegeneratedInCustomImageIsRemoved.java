package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.program.onetile.programone;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.ProgramRokuImagesTab;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.GettingImages;
import org.testng.annotations.Test;

/**
 * TC10042
 *
 * Pre-Conditions: go to MPX
 * make sure that , there is 983x554 Featured, iPhoneRetina, Large5 image for test show
 * go to Roku CMS
 * make sure that there is custom "Feature Carousel Hero" image for test OTT Program,
 * default use is selected for custom image
 *
 * Step 1: go to Roku CMS
 *
 * Step 2: go to /admin/content
 * click "edit" next to test OTT Program
 *
 * Step 3: open 1tile thumbnail URL in browser
 *
 * Step 4: remove custom "Feature Carousel Hero" image
 * uncheck "Override image"
 * save OTT Program
 *
 * Step 5: go to /admin/content
 * click "edit" next to test OTT Program
 *
 * Step 6: open 1tile thumbnail URL in browser
 * Verify: 1080x405 image is displayed image consists of:
 * -Updated MPX image (983x554 Featured, iPhoneRetina, Large5)
 * -CTA BOX (Dark gradient - 216px Fill Color: Black;
 * Gradient Dark: Black @ 100% > Black @ 0% 190px)
 * Image source from MPX
 */

public class TC10042_OnetileThumbnailIsRegeneratedInCustomImageIsRemoved extends BaseAuthFlowTest {

    private static final String INITIAL_IMAGE = "1_tile_source.jpg";
    private static final String UPDATE_IMAGE = "1_tile_source_2.jpg";
    private static final String PROGRAM_TITLE = "AQA_OTT_PROGRAM";
    private static final String INITIAL_CTA = "Initial CTA text";
    private static final String INITIAL_HEADLINE = "Initial Headline text";
    private static final String INITIAL_TEMPLATE_STYLE = "dark";

    public void initialState() {
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageDimensionsByTitle(INITIAL_IMAGE, "983", "554");
        mpxLayer.updateImageDimensionsByTitle(UPDATE_IMAGE, "1", "1");
        mpxLayer.updateAssetTitle(PROGRAM_TITLE);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.runCron(brand);
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(PROGRAM_TITLE);
        ProgramRokuImagesTab featureCarouselTab = programContentPage.onRokuImagesTab();
        featureCarouselTab.overrideOneTileImageSourceImage(GettingImages.getInitialCustom1tileSource());
        programContentPage.fillForm(INITIAL_CTA, INITIAL_HEADLINE);
        programContentPage.elementTemplateStyle().selectRadioButtonByName(INITIAL_TEMPLATE_STYLE);

        programContentPage.clickSave();
    }

    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkCustomSource(final String brand) {
        SoftAssert softAssert = new SoftAssert();
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        initialState();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(PROGRAM_TITLE);

        String actual1tileProgram1 = programContentPage.onRokuImagesTab().getOneTileProgramTwoCustomLinks()
                .get(CustomImageTypes.ONE_TILE_PROGRAM_1);

        String expected1tileProgram1 = GettingImages.getInitialCustom1tileProgram1Thumbnail();

        softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(expected1tileProgram1,
                actual1tileProgram1, 100.0), "Images '1tile_program_1' are not equals", "Images '1tile_program_1' are equals");

        contentPage = mainRokuAdminPage.openContentPage(brand);
        programContentPage = contentPage.openEditOTTProgramPage(PROGRAM_TITLE);

        programContentPage.onRokuImagesTab().setOverrideOneTileImageSourceImageStatus(false);

        programContentPage.clickSave();

        contentPage = mainRokuAdminPage.openContentPage(brand);
        programContentPage = contentPage.openEditOTTProgramPage(PROGRAM_TITLE);

        String actualUpdatedl1tileProgram1 = programContentPage.onRokuImagesTab().getOneTileProgramTwoCustomLinks()
                .get(CustomImageTypes.ONE_TILE_PROGRAM_1);

        String expectedUpdated1tileProgram1 = GettingImages.getInitial1TileProgram1();

        softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(expectedUpdated1tileProgram1,
                actualUpdatedl1tileProgram1, 100.0), "Images '1tile_program_1' are not equals", "Images '1tile_program_1' are equals");

        softAssert.assertAll();
    }
}
