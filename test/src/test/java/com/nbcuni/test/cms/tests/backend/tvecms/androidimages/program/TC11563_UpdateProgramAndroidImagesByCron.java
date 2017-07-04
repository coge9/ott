package com.nbcuni.test.cms.tests.backend.tvecms.androidimages.program;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.android.program.AndroidProgramThumbnails;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Aliaksei_Dzmitrenka
 * Precondition:
 *     Set images settings
 *  Generate expected images
 *  Run cron
 * Test
 *     Step 1: Open edit page for aqa program
 *  Step 2: Open android tab;
 *  Step 3: Verify all images on all sources preview
 *
 */


public class TC11563_UpdateProgramAndroidImagesByCron extends BaseAuthFlowTest {


    private static final String INITIAL_TITLE = "AQA_OTT_PROGRAM";
    private static final String BRAND = RokuBrandNames.USA.getBrandName();

    private MainRokuAdminPage mainRokuAdminPage;
    private RokuBackEndLayer backEndLayer;
    private Map<AndroidProgramThumbnails, BufferedImage> expectedImages;

    public void checkInitialThumbnails() {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(BRAND, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageAssetTypeByID(AndroidProgramThumbnails.program_landscape_1280_435.getImageIdAtMpx(), AndroidProgramThumbnails.program_landscape_1280_435.getAssetTypes());
        mpxLayer.updateImageAssetTypeByID(AndroidProgramThumbnails.program_portrait_720_960.getImageIdAtMpx(), AndroidProgramThumbnails.program_portrait_720_960.getAssetTypes());
        mpxLayer.updateImageAssetTypeByID(AndroidProgramThumbnails.program_landscape_1270_1200.getImageIdAtMpx(), AndroidProgramThumbnails.program_landscape_1270_1200.getAssetTypes());
        mpxLayer.updateImageAssetTypeByID(AndroidProgramThumbnails.program_landscape_1284_1080.getImageIdAtMpx(), AndroidProgramThumbnails.program_landscape_1284_1080.getAssetTypes());
        mpxLayer.updateImageAssetTypeByID(AndroidProgramThumbnails.program_landscape_390_462.getImageIdAtMpx(), AndroidProgramThumbnails.program_landscape_390_462.getAssetTypes());
        Utilities.logInfoMessage("Getting expected images");
        expectedImages = AndroidProgramThumbnails.getExpectedAndroidImage(BRAND);

        backEndLayer = new RokuBackEndLayer(webDriver, BRAND, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        mainRokuAdminPage.runCron(BRAND);
    }

    @Test(groups = {"program_android_thumbnails"})
    public void checkOneTileThumbnailIsGeneratedForOTTProgram() {

        checkInitialThumbnails();
        Utilities.logInfoMessage("Check that android thumbnail is generated for OTT Program");
        SoftAssert softAssert = new SoftAssert();
        mainRokuAdminPage = backEndLayer.openAdminPage();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(BRAND);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(INITIAL_TITLE);
        Map<ThumbnailsCutInterface, String> actualImages = programContentPage.onAndroidImagesTab().getActualAndroidImages();
        for (Entry<ThumbnailsCutInterface, String> entry : actualImages.entrySet()) {
            ThumbnailsCutInterface key = entry.getKey();
            String value = entry.getValue();
            softAssert.assertTrue(ImageUtil.compareImageAndUrl(expectedImages.get(key), value, 95.0), "Images [" + key.getImageName() + "] are not equals. second one is acrual variant ", "Images [" + key.getImageName() + "] are equals");
        }
        softAssert.assertAll();

    }

}
