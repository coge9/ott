package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.program.onetile.programtwo;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.GettingImages;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.ProgramOneTileShowPageSource;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 24-Nov-15.
 */
public class TC10379_UpdateCTAText extends BaseAuthFlowTest {

    private static final String INITIAL_TITLE = Config.getInstance().getMPXTestShowName();
    private static final String INITIAL_IMAGE = "1973x918_initial.jpeg";
    private static final String UPDATE_IMAGE = "1973x918_update.jpeg";
    private static final String INITIAL_PROGRAM_HERO_CTA = "Initial Program Hero CTA";
    private static final String UPDATED_PROGRAM_HERO_CTA = "Updated Program Hero CTA";
    private static final String INITIAL_LOGO = "royalpains-logo-large.png";
    private static final String UPDATE_LOGO = "logo_720x52.png";

    public void checkInitialThumbnails() {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageDimensionsByTitle(INITIAL_IMAGE, ProgramOneTileShowPageSource.getSourcePerBrand(brand).getWidth(),
                ProgramOneTileShowPageSource.getSourcePerBrand(brand).getHeight());
        mpxLayer.updateImageDimensionsByTitle(UPDATE_IMAGE, "3", "3");

        mpxLayer.updateImageDimensionsByTitle(INITIAL_LOGO, "100", "20");
        mpxLayer.updateImageDimensionsByTitle(UPDATE_LOGO, "120", "20");

        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        mainRokuAdminPage.runCron(brand);

        EditTVEProgramContentPage programContentPage = mainRokuAdminPage.openContentPage(brand)
                .openEditOTTProgramPage(INITIAL_TITLE);

        programContentPage.onRokuImagesTab().typeProgramHeroCTA(INITIAL_PROGRAM_HERO_CTA);
        programContentPage.clickSave();
    }

    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkOneTileThumbnailIsGeneratedForOTTProgram(final String brand) {
        Utilities.logInfoMessage("Check that 1tile thumbnail is generated for OTT Program");
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        checkInitialThumbnails();
        SoftAssert softAssert = new SoftAssert();
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(INITIAL_TITLE);

        programContentPage.onRokuImagesTab().typeProgramHeroCTA(UPDATED_PROGRAM_HERO_CTA);
        programContentPage.clickSave();

        contentPage = mainRokuAdminPage.openContentPage(brand);
        programContentPage = contentPage.openEditOTTProgramPage(INITIAL_TITLE);

        String actual1tileProgram2 = programContentPage.onRokuImagesTab().getOneTileProgramTwoCustomLinks()
                .get(CustomImageTypes.ONE_TILE_PROGRAM_2);

        String expected1tileProgram2 = GettingImages.getCTAFieldUpdated1TileProgram2();

        softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(expected1tileProgram2,
                actual1tileProgram2, 100.0), "Images '1tile_program_2' are not equals", "Images '1tile_program_2' are equals");

        softAssert.assertAll();
    }
}
