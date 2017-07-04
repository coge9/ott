package com.nbcuni.test.cms.tests.backend.tvecms.imagestyles.roku.program;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
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
 * TC12576
 *
 * Pre-Conditions:
 * Go to CMS as admin and select Program
 *
 * Steps:
 * 1. Go to MPX and select Program from pre-condition
 * The program is present
 * 2. Go to files tab
 * the list of sources is present
 * 3. Make sure (Upload in case if absent) there is a source with size 2560x1440
 * The source is present
 * 4. Set for source assetType = thumbnail3 Test, thumbnail2, original image and save.
 * Make sure threre is no asset type exact = thumbnail3
 * the settings for Program is saved
 * 5. Go to CMS as admin and open content
 * The Program from pre-condition there is
 * 6. Go to the Edit page of program and check source for 3tile_program_1 image on the Roku images tab
 * The source is absent for image
 */

public class TC12576_SourceImageDoesNotTriggeredInCMSIfThumbnail3AbsentFromAssettypeList extends BaseAuthFlowTest {

    private static final String INITIAL_TITLE = Config.getInstance().getMPXTestShowName();
    private static final String INITIAL_IMAGE = "3tile_program_1_source_2560x1440.png";

    public void checkInitialThumbnails() {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageAssetTypeByTitle(INITIAL_IMAGE, "Thumbnail3");


        mainRokuAdminPage.runCron(brand);
    }

    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkOneTileThumbnailIsGeneratedForOTTProgram(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Check that 3tile thumbnail is generated for OTT Program");
        checkInitialThumbnails();

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageAssetTypeByTitle(INITIAL_IMAGE, "Thumbnail", "Thumbnail2", "Original Image");
        mainRokuAdminPage.runCron(brand);

        String expectedSource = mpxLayer.getMpxThumbnailUrlByTitle(INITIAL_IMAGE);

        EditTVEProgramContentPage programContentPage = mainRokuAdminPage.openContentPage(brand)
                .openEditOTTProgramPage(INITIAL_TITLE);

        String actualSource = programContentPage.onRokuImagesTab().elementThreeTitleProgramThreeArea().getLinkToSourceImage();

        softAssert.assertFalse(ImageUtil.compareUrlAndUrl(expectedSource, actualSource, 100.0),
                "Images source '3 tile program 1' are equals", "Images source '3 tile program 1' are not equals");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void after() {
        Utilities.logInfoMessage("Return data");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageAssetTypeByTitle(INITIAL_IMAGE, "Thumbnail3");

        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.runCron(brand);
    }
}
