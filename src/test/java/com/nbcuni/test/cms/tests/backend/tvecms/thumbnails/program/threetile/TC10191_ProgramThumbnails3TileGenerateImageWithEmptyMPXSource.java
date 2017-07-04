package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.program.threetile;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.ProgramRokuImagesTab;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 09-Nov-15.
 */

/**
 * TC10191
 *
 * Step 1: Login to Roku CMS as Editor
 *
 * Step 2: Run crone
 *
 * Step 3: go to /admin/content
 * click on "edit" for published OTT Program
 *
 * Step 4: Go to Basic information tab
 * Verify: Upload image field for 3-Tile is shown
 */
public class TC10191_ProgramThumbnails3TileGenerateImageWithEmptyMPXSource extends BaseAuthFlowTest {

    private static final String INITIAL_TITLE = "AQA_OTT_PROGRAM";
    private static final String INITIAL_IMAGE = "NBCU_USA_RoyalPains_show_retina_480x270_480x270_29209155595.jpg";
    private static final String UPDATE_IMAGE = "480x270.jpg";

    public void checkInitialThumbnails() {
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageDimensionsByTitle(INITIAL_IMAGE, "2", "2");
        mpxLayer.updateImageDimensionsByTitle(UPDATE_IMAGE, "2", "2");
    }

    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkOneTileThumbnailIsGeneratedForOTTProgram(String brand) {
        Utilities.logInfoMessage("Check that 3tile thumbnail is generated for OTT Program");
        SoftAssert softAssert = new SoftAssert();
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        checkInitialThumbnails();
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        mainRokuAdminPage.runCron(brand);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(INITIAL_TITLE);

        ProgramRokuImagesTab programImagesTab = programContentPage.onRokuImagesTab();
        softAssert.assertTrue(programImagesTab.elementThreeTitleArea().isThreeTileSourceUploaderPresent(),
                "3tile source uploader is not present", "3tile source uploader is present");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void restoreInitMpxValues() {
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageDimensionsByTitle(INITIAL_IMAGE, "480", "270");
        mpxLayer.updateImageDimensionsByTitle(UPDATE_IMAGE, "2", "2");
    }
}
