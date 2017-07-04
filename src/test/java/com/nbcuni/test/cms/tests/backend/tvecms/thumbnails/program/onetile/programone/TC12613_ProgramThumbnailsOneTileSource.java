package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.program.onetile.programone;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.pageobjectutils.tvecms.TemplateStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.ImageFiles;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.MpxProgramMetadata;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.ProgramOneTileShowPageSource;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.ProgramOneTileSource;
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
 *  Open AQA_OTT_PROGRAM edit page
 *  Open RokuImages tab
 *  Open preview for 1 tile source
 *  Get url of images
 *  Compare expected and actual images
 * Postcondition:
 *  set all back
 */


public class TC12613_ProgramThumbnailsOneTileSource extends BaseAuthFlowTest {


    private MainRokuAdminPage mainRokuAdminPage;
    private EditTVEProgramContentPage programContentPage;
    private String brand;

    public void checkInitialThumbnails(String brand) {
        Utilities.logInfoMessage("Check Initial Thumbnails");
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateSourceWithTestData(ProgramOneTileShowPageSource.getSourcePerBrand(brand));
        mpxLayer.updateProgramMetadata(MpxProgramMetadata.getTestMetadata());
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.openMpxUpdaterPage(brand).updateAsset(config.getRokuMPXProgramID(brand, Instance.STAGE));
    }

    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkOneTileThumbnailIsGeneratedForProgram(String brand) {
        this.brand = brand;
        Utilities.logInfoMessage("Check that 1tile thumbnail is generated for OTT program");
        checkInitialThumbnails(brand);
        SoftAssert softAssert = new SoftAssert();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        programContentPage = contentPage.openEditOTTProgramPage(Config.getInstance().getMPXTestShowName());
        programContentPage.typeHeadline(MpxProgramMetadata.getTestMetadata().getHeadLine());
        programContentPage.typeCTA(MpxProgramMetadata.getTestMetadata().getFeatureCTA());
        programContentPage.selectTemplateStyle(TemplateStyle.DARK);
        programContentPage.clickSave();
        Map<CustomImageTypes, String> oneTileImagesProgramSource = programContentPage.onRokuImagesTab().getOneTileProgramOneCustomLinks();

        ImageFiles images = new ImageFiles(brand);
        for (Entry<CustomImageTypes, String> entry : oneTileImagesProgramSource.entrySet()) {
            CustomImageTypes key = entry.getKey();
            String value = entry.getValue();
            softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(images.getProgramOneTileThumbnail(key),
                    value, 100.0), "Images '" + key.getName() + "' are not equals", "Images '" + key.getName() + "' are equals");
        }

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void setAllBack() {
        Utilities.logInfoMessage("Set all back");
        programContentPage.typeHeadline(MpxProgramMetadata.getOldMetadata().getHeadLine());
        programContentPage.typeCTA(MpxProgramMetadata.getOldMetadata().getFeatureCTA());
        programContentPage.clickSave();
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateSourceWithOldData(ProgramOneTileSource.getSourcePerBrand(brand));
        mpxLayer.updateProgramMetadata(MpxProgramMetadata.getOldMetadata());
    }

}
