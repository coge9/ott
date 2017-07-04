package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.video.onetile;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.TemplateStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.MpxVideoMetadata;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.Video1TileSource;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

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


public class TC12623_VideoThumbnailsOneTileSource extends BaseAuthFlowTest {


    private MainRokuAdminPage mainRokuAdminPage;
    private EditTVEVideoContentPage videoContentPage;
    private String brand;


    public void checkInitialThumbnails(String brand) {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        mpxLayer.updateSourceWithTestData(Video1TileSource.getSourcePerBrand(brand));
        mpxLayer.updateVideoMetadata(MpxVideoMetadata.getTestMetadata());
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.openMpxUpdaterPage(brand).updateAsset(config.getRokuMPXVideoID(brand, Instance.STAGE));
    }

    //Lyoha: Disabled. Not actual anymore
    @Test(groups = {"video_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkOneTileThumbnailIsGeneratedForVideo(String brand) {
        this.brand = brand;
        Utilities.logInfoMessage("Check that 1tile thumbnail is generated for OTT Video");
        checkInitialThumbnails(brand);
        SoftAssert softAssert = new SoftAssert();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        videoContentPage = contentPage.openEditTVEVideoPage(Config.getInstance().getMPXTestVideoName());
        videoContentPage.typeHeadline(MpxVideoMetadata.getTestMetadata().getHeadLine());
        videoContentPage.typeCTA(MpxVideoMetadata.getTestMetadata().getFeatureCTA());
        videoContentPage.selectTemplateStyle(TemplateStyle.DARK);
        videoContentPage.clickSave();

//        Map<CustomImageTypes, String> oneTileImagesVideoSource = videoContentPage.onRokuImagesTab().getOneTileCustomLinks();
//
//        ImageFiles images = new ImageFiles(brand);
//        for(Entry<CustomImageTypes, String> entry : oneTileImagesVideoSource.entrySet()){
//            CustomImageTypes key = entry.getKey();
//            String value = entry.getValue();
//            softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(images.getVideoOneTileThumbnail(key),
//                    value, 100.0), "Images '" + key.getName() +"' are not equals", "Images '" + key.getName() +"' are equals");
//        }

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void setAllBack() {
        Utilities.logInfoMessage("Set all back");
        videoContentPage.typeHeadline(MpxVideoMetadata.getOldMetadata().getHeadLine());
        videoContentPage.typeCTA(MpxVideoMetadata.getOldMetadata().getFeatureCTA());
        videoContentPage.clickSave();
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        mpxLayer.updateSourceWithOldData(Video1TileSource.getSourcePerBrand(brand));
        mpxLayer.updateVideoMetadata(MpxVideoMetadata.getOldMetadata());
    }

}
