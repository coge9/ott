package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.program.threetile;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.ImageFiles;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.Program3TileProgramSource;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Aliaksei_Dzmitrenka
 * Precondition:
 *  Set tested image "Original Image" asset type.
 *  Remove "Original Image" asset type from already used image
 * Steps:
 *  Open AQA_OTT_VIDEO edit page
 *  Open RokuImages tab
 *  Open preview for 3 tile video source
 *  Get url of images
 *  Compare expected and actual images
 * Postcondition:
 *  set all back
 *
 *
 */


public class TC12617_ProgramThumbnailsThreeTileProgramSource extends BaseAuthFlowTest {


    private MainRokuAdminPage mainRokuAdminPage;
    private String brand;


    public void checkInitialThumbnails(String brand) {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateSourceWithTestData(Program3TileProgramSource.getSourcePerBrand(brand));
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.openMpxUpdaterPage(brand).updateAsset(config.getRokuMPXProgramID(brand, Instance.STAGE));
    }

    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkOneTileThumbnailIsGeneratedForProgram(String brand) {
        this.brand = brand;
        Utilities.logInfoMessage("Check that 3tile thumbnail is generated for OTT program");
        checkInitialThumbnails(brand);
        SoftAssert softAssert = new SoftAssert();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(Config.getInstance().getMPXTestShowName());

        Map<CustomImageTypes, String> threeTileImagesSource = programContentPage.onRokuImagesTab().getThreeTileProgramThreeCustomLinks();

        ImageFiles images = new ImageFiles(brand);
        for (Entry<CustomImageTypes, String> entry : threeTileImagesSource.entrySet()) {
            CustomImageTypes key = entry.getKey();
            String value = entry.getValue();
            softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(images.getProgramThreeTileThumbnail(key),
                    value, 100.0), "Images '" + key.getName() + "' are not equals", "Images '" + key.getName() + "' are equals");
        }

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void setAllBack() {
        Utilities.logInfoMessage("Set all back");
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateSourceWithOldData(Program3TileProgramSource.getSourcePerBrand(brand));
    }

}
