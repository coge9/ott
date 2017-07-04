package com.nbcuni.test.cms.tests.backend.tvecms.androidimages.video;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.android.video.AndroidVideoThumbnails;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 *
 * @author Aliaksei_Dzmitrenka
 * Precondition:
 *     Set images settings
 *  Generate expected images
 *  Run cron
 * Test
 *     Step 1: Open edit page for aqa video
 *  Step 2: Open android tab;
 *  Step 3: Verify all images on sources preview
 *
 */

public class TC11556_UpdateVideoAndroidImagesByCron extends BaseAuthFlowTest {


    private static final String INITIAL_TITLE = "AQA_OTT_VIDEO";

    private MainRokuAdminPage mainRokuAdminPage;
    private RokuBackEndLayer backEndLayer;
    private Map<ThumbnailsCutInterface, BufferedImage> expectedImages;

    public void checkInitialThumbnails(String brand) {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        mpxLayer.updateImageAssetTypeByID(AndroidVideoThumbnails.video_landscape_1280_435.getImageIdAtMpx(), AndroidVideoThumbnails.video_landscape_1280_435.getAssetTypes());
        Utilities.logInfoMessage("Getting expected images");
        expectedImages = AndroidVideoThumbnails.getExpectedAndroidImage(brand);

        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        mainRokuAdminPage.runCron(brand);
    }

    //Lyoha: Disabled as not actual any more
    @Test(groups = {"Video_android_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkOneTileThumbnailIsGeneratedForOTTVideo(String brand) {

        checkInitialThumbnails(brand);
        Utilities.logInfoMessage("Check that android thumbnail is generated for OTT Video");
        SoftAssert softAssert = new SoftAssert();
        mainRokuAdminPage = backEndLayer.openAdminPage();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEVideoContentPage VideoContentPage = contentPage.openEditTVEVideoPage(INITIAL_TITLE);
//        Map<ThumbnailsCutInterface, String> actualImages = VideoContentPage.onAndroidImagesTab().getActualAndroidImages();
//        for(Entry<ThumbnailsCutInterface, String> entry : actualImages.entrySet()){
//            ThumbnailsCutInterface key = entry.getKey();
//            String value = entry.getValue();
//            softAssert.assertTrue(ImageUtil.compareImageAndUrl(expectedImages.get(key), value, 95.0), "Images [" + key.getImageName() + "] are not equals. second one is acrual variant ", "Images [" + key.getImageName() + "] are equals");
//        }
        softAssert.assertAll();
    }

}
