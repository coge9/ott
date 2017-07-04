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
import org.testng.annotations.Test;

/**
 * TC12562
 *
 * Pre-Conditions:
 * 1. Go to CMS as admin and select Program
 *
 * Steps:
 * 1. Go to MPX and select Program from pre-condition
 * The program is present
 * 2. Go to files tab
 * the list of sources is present
 * 3. Make sure (Upload in case if absent) there is a source with size 2560x1440
 * The source is present
 * 4. Set for source assetType = thumbnail2 only! and save
 * the settings for Program is saved
 * 5. Go to CMS as admin and open content
 * The Program from pre-condition there is
 * 6. Go to the Edit page of program and check source for 3tile_program_3 image on the Roku images tab
 * The source is present and matched with those that set in MPX
 */

public class TC12562_SourceImageIsTriggeredInCMSForThumbnail2AssetTypeOnly extends BaseAuthFlowTest {

    private static final String INITIAL_TITLE = Config.getInstance().getMPXTestShowName();
    private static final String INITIAL_IMAGE = "3tile_program_3_source_2560x1440.png";

    public void checkInitialThumbnails() {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageAssetTypeByTitle(INITIAL_IMAGE, "Thumbnail2");

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
        mpxLayer.updateImageAssetTypeByTitle(INITIAL_IMAGE, "Thumbnail2");
        mainRokuAdminPage.runCron(brand);

        String expectedSource = mpxLayer.getMpxThumbnailUrlByTitle(INITIAL_IMAGE);

        EditTVEProgramContentPage programContentPage = mainRokuAdminPage.openContentPage(brand)
                .openEditOTTProgramPage(INITIAL_TITLE);

        String actualSource = programContentPage.onRokuImagesTab().elementThreeTitleProgramThreeArea().getLinkToSourceImage();

        softAssert.assertTrue(ImageUtil.compareUrlAndUrl(expectedSource, actualSource, 100.0),
                "Images source '3 tile program 3' are not equals", "Images source '3 tile program 3' are equals");

        softAssert.assertAll();
    }
}
