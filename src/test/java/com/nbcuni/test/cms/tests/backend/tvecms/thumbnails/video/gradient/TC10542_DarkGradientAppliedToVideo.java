package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.video.gradient;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.TemplateStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.thumbnails.ImageFiles;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.MpxVideoMetadata;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.Video1TileSource;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 3/14/16.
 */
public class TC10542_DarkGradientAppliedToVideo extends BaseAuthFlowTest {

    private String assetTitle;
    private String assetMPXId;
    private ContentPage contentPage;
    private EditTVEVideoContentPage videoContentPage;

    /**
     * Step 1. Go to content page
     * Verify: User is on content page
     * <p/>
     * Step 2. Open edit page for any TVE video
     * Verify: User is on edit video page
     * <p/>
     * Step 3: Select Dark style template
     * Verify: Selected value is Dark
     * <p/>
     * Step 4: Click save
     * Verify: Changes are saved, system message is appeared
     * <p/>
     * Step 5: Go to Roku image tab for this video again
     * Verify: User is on Roku image tab
     * <p/>
     * Step 6: Verify generated images on preview pages
     * Verify: All images that should be with dark gradient
     */

    public void checkInitialThumbnails(String brand) {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        mpxLayer.updateSourceWithTestData(Video1TileSource.getSourcePerBrand(brand));
        mpxLayer.updateVideoMetadata(MpxVideoMetadata.getTestMetadata());
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.openMpxUpdaterPage(brand).updateAsset(config.getRokuMPXVideoID(brand, Instance.STAGE));
    }

    //Lyoha: Disabled as not actual any more
    @Test(groups = {"video_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkDarkGradientIsDefault(String brand) {

        //Pre-Condition
        checkInitialThumbnails(brand);

        //Step 1
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        //Step 2
        videoContentPage = contentPage.openEditTVEVideoPage(Config.getInstance().getMPXTestVideoName());
        videoContentPage.typeHeadline(MpxVideoMetadata.getTestMetadata().getHeadLine());
        videoContentPage.typeCTA(MpxVideoMetadata.getTestMetadata().getFeatureCTA());

        //Step 3
        videoContentPage.selectTemplateStyle(TemplateStyle.DARK);

        //Step 4
        videoContentPage.clickSave();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after saving"
                , "The status message is not shown after save", webDriver);

        //Step 5
        //Getting actual result
        //Map<CustomImageTypes, String> thumbnails = videoContentPage.onRokuImagesTab().getOneTileCustomLinks();

        //Step 6
        //Comporation
        ImageFiles images = new ImageFiles(brand);
//        for (Map.Entry<CustomImageTypes, String> entry : thumbnails.entrySet()) {
//            CustomImageTypes key = entry.getKey();
//            String filePath = entry.getValue();
//            softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(images.getVideoOneTileThumbnail(key, TemplateStyle.DARK),
//                    filePath, 100.0), "Images '" + key.getName() + "' are not equals", "Images '" + key.getName() + "' are equals");
//        }

        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");

    }

    @AfterMethod(alwaysRun = true)
    public void setAllBackTC10542() {
        Utilities.logInfoMessage("Set all back");
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        //Step 2
        videoContentPage = contentPage.openEditTVEVideoPage(Config.getInstance().getMPXTestVideoName());
        videoContentPage.typeHeadline(MpxVideoMetadata.getOldMetadata().getHeadLine());
        videoContentPage.typeShowPageCTA(MpxVideoMetadata.getOldMetadata().getFeatureCTA());
        videoContentPage.clickSave();
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        mpxLayer.updateSourceWithOldData(Video1TileSource.getSourcePerBrand(brand));
        mpxLayer.updateVideoMetadata(MpxVideoMetadata.getOldMetadata());
    }
}
