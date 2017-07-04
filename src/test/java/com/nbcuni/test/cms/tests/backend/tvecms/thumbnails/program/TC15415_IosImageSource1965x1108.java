package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.program;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;
import com.nbcuni.test.cms.utils.thumbnails.ios.program.IosProgramThumbnails;
import com.nbcuni.test.cms.utils.thumbnails.ios.program.ProgramIos1965x1108;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by Aliaksei_Klimenka1 on 8/22/2016.
 */
public class TC15415_IosImageSource1965x1108 extends BaseAuthFlowTest {


    private MainRokuAdminPage mainRokuAdminPage;
    private String brand;
    private Map<IosProgramThumbnails, BufferedImage> expectedImages;
    private String programTitle;

    public void checkInitialThumbnails(String brand) {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateSourceWithTestData(ProgramIos1965x1108.getSourcePerBrand(brand));
        expectedImages = IosProgramThumbnails.getExpectedIosImages(brand, ProgramIos1965x1108.getDimensionForBrand(brand), ProgramIos1965x1108.getSourcePerBrand(brand).getTestSourceMpxId());
        programTitle = mpxLayer.getAssetTitle();
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.openMpxUpdaterPage(brand).updateAsset(config.getRokuMPXProgramID(brand, Instance.STAGE));
    }


    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkIos1965x1108ThumbnailIsGeneratedForProgram(String brand) {
        this.brand = brand;
        Utilities.logInfoMessage("Check that  is generated for OTT Program");
        checkInitialThumbnails(brand);
        SoftAssert softAssert = new SoftAssert();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(programTitle);

        Map<ThumbnailsCutInterface, String> ios1965x1108images = programContentPage.onIosImagesTab().getIosProgram1965x1108Images();

        for (Map.Entry<ThumbnailsCutInterface, String> entry : ios1965x1108images.entrySet()) {
            ThumbnailsCutInterface key = entry.getKey();
            String value = entry.getValue();
            softAssert.assertTrue(ImageUtil.compareImageAndUrl(expectedImages.get(key), value, 95.0), "Images [" + key.getImageName() + "] are not equals. second one is acrual variant ", "Images [" + key.getImageName() + "] are equals");
        }
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void setAllBack() {
        Utilities.logInfoMessage("Set all back");
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        mpxLayer.updateSourceWithOldData(ProgramIos1965x1108.getSourcePerBrand(brand));
    }
}
