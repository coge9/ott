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
 * TC10040
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
 * Verify: 1080x405 image is displayed
 * image consists of:
 * -Custom "Feature Carousel Hero" image
 * -CTA BOX (Dark gradient - 216px Fill Color: Black;
 * Gradient Dark: Black @ 100% > Black @ 0% 190px)
 */
public class TC10040OneTileThumbnailIsRegeneratedInCustomImageIsUploaded extends BaseAuthFlowTest {

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

        mainRokuAdminPage.runCron(brand);

        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
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
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        initialState();

        SoftAssert softAssert = new SoftAssert();

//      Step 1
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 2
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(PROGRAM_TITLE);

//      Step 3
        String actual1tileProgram1 = programContentPage.onRokuImagesTab().getOneTileProgramTwoCustomLinks()
                .get(CustomImageTypes.ONE_TILE_PROGRAM_1);

        String expected1tileProgram1 = GettingImages.getInitialCustom1tileProgram1Thumbnail();

        softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(expected1tileProgram1,
                actual1tileProgram1, 100.0), "Images '1tile_program_1' are not equals", "Images '1tile_program_1' are equals");

        softAssert.assertAll();
    }
}
