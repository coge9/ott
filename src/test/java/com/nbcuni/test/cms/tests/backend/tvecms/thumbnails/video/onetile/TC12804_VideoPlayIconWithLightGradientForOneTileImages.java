package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.video.onetile;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
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
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.MpxVideoMetadata;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.Video1TileSource;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Aliaksei_Dzmitrenka
 * Precondition:
 *  Set old image with "Origin" assetTypes.
 *  Set tested image with "Origin" assetTypes.
 * Steps:
 *  Open AQA_OTT_Video edit page
 *  Open RokuImages tab
 *  Open preview for 1 tile source
 *  Get url of images
 *  Compare expected and actual images
 *
 */


public class TC12804_VideoPlayIconWithLightGradientForOneTileImages extends BaseAuthFlowTest {


    private MainRokuAdminPage mainRokuAdminPage;
    private EditTVEVideoContentPage videoContentPage;


    public void checkInitialThumbnails(String brand) {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        mpxLayer.updateSourceWithOldData(Video1TileSource.getSourcePerBrand(brand));
        mpxLayer.updateVideoMetadata(MpxVideoMetadata.getOldMetadata());
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.openMpxUpdaterPage(brand).updateAsset(config.getRokuMPXVideoID(brand, Instance.STAGE));
    }

    @Test(groups = {"video_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkOneTileThumbnailIsGeneratedForProgram(String brand) {
        Utilities.logInfoMessage("Check that 1tile thumbnail is generated for OTT program");
        checkInitialThumbnails(brand);
        SoftAssert softAssert = new SoftAssert();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        videoContentPage = contentPage.openEditTVEVideoPage(Config.getInstance().getMPXTestVideoName());
        videoContentPage.typeHeadline(MpxVideoMetadata.getOldMetadata().getHeadLine());
        videoContentPage.typeCTA(MpxVideoMetadata.getOldMetadata().getFeatureCTA());
        videoContentPage.selectTemplateStyle(TemplateStyle.LIGHT);
        videoContentPage.clickSave();

        //Getting actual result
        Map<CustomImageTypes, String> thumbnails = videoContentPage.onImagesTab().getThreeTileCustomLinks();

        //Comporation
        ImageFiles images = new ImageFiles(brand);
        for (Entry<CustomImageTypes, String> entry : thumbnails.entrySet()) {
            CustomImageTypes key = entry.getKey();
            String value = entry.getValue();
            softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(images.getVideoOneTileThumbnail(key, TemplateStyle.LIGHT),
                    value, 100.0), "Images '" + key.getName() + "' are not equals", "Images '" + key.getName() + "' are equals");
        }

        softAssert.assertAll();
    }

}
