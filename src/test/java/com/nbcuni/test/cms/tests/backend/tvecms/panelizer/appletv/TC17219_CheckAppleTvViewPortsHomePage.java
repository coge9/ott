package com.nbcuni.test.cms.tests.backend.tvecms.panelizer.appletv;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PreviewPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.factory.CreateFactoryFeatureCarousel;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.FeatureShowModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.factory.CreateFeatureShowModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ContentFormat;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizer.PanelizerTemplates;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.thumbnails.appletv.program.AppleTVProgramThumbnails;
import com.nbcuni.test.cms.utils.thumbnails.appletv.video.AppleTVVideoThumbnails;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * TC11966
 * <p>
 * Step 1: Go to Roku as Admin
 * <p>
 * Step 2: Go to panelizer template list (admin/config/content/panelizer/ott_page/ott_page.page_manager/list)
 * <p>
 * Step 3: Expand "OPERATIONS" dropdown next to AppleTV AllShows layout
 * Click on "Content" option
 * <p>
 * Validation:     Check rows
 * Expected result: One row is present and named as "All shows
 */
public class TC17219_CheckAppleTvViewPortsHomePage extends BaseAuthFlowTest {

    EditPageWithPanelizer editPage;
    PanelizerTemplates panelizerTemplate = PanelizerTemplates.APPLETV_HOMEPAGE;
    FeatureCarouselForm featureCarouselForm = CreateFactoryFeatureCarousel.createFeatureCarouselWithRandomTitle();
    FeatureShowModule featureShowModuleData = CreateFeatureShowModule.createDefault();
    private DraftModuleTab draftModuleTab;
    private Shelf shelf1Tile;
    private Shelf shelf3Tile;
    private PageForm pageInfo = CreateFactoryPage.createDefaultPageWithAlias().setLayout(panelizerTemplate).setPlatform(CmsPlatforms.APPLETV);

    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = false)
    public void checkAndroidHomePageTemplate(final String brand) {
        this.brand = brand;
        //Step 1
        SoftAssert softAssert = new SoftAssert();
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-condition create layout
        preconditionTC17219();

        PreviewPage previewPage = editPage.clickPreviewLink();
        softAssert.assertContains(previewPage.getUrlOfImage(ContentType.TVE_PROGRAM, shelf1Tile),
                AppleTVProgramThumbnails.landscape_540x304_x1_5.getImageName(),
                "The image style for Program in the shelf 1 tile module doesn't matched on preview with expected: " + AppleTVProgramThumbnails.landscape_540x304_x1_5.getImageName(),
                "The Image Style for Program in the shelf 1 tile module for AppleTV Home Page view port is matched");
        softAssert.assertContains(previewPage.getUrlOfImage(ContentType.TVE_VIDEO, shelf1Tile),
                AppleTVVideoThumbnails.landscape_720x405_x1.getImageName(),
                "The image style for Video in the shelf 1 tile module doesn't matched on preview with expected: " + AppleTVVideoThumbnails.landscape_720x405_x1.getImageName(),
                "The Image Style for Video in the shelf 1 tile module for AppleTV Home Page view port is matched");
        softAssert.assertContains(previewPage.getUrlOfImage(ContentType.TVE_PROGRAM, shelf3Tile),
                AppleTVProgramThumbnails.landscape_255x143_x1_5.getImageName(),
                "The image style for Program in the shelf 3 tile module doesn't matched on preview with expected: " + AppleTVProgramThumbnails.landscape_255x143_x1_5.getImageName(),
                "The Image Style for Program in the shelf 3 tile module for AppleTV Home Page view port is matched");
        softAssert.assertContains(previewPage.getUrlOfImage(ContentType.TVE_VIDEO, shelf3Tile),
                AppleTVVideoThumbnails.landscape_393x221_x1.getImageName(),
                "The image style for Video in the shelf 3 tile module doesn't matched on preview with expected: " + AppleTVVideoThumbnails.landscape_393x221_x1.getImageName(),
                "The Image Style for Video in the shelf 3 tile module for AppleTV Home Page view port is matched");
        softAssert.assertContains(previewPage.getUrlOfImage(ContentType.TVE_PROGRAM, featureCarouselForm),
                AppleTVProgramThumbnails.landscape_540x304_x1_5.getImageName(),
                "The image style for Program in the Feature Carousel module doesn't matched on preview with expected: " + AppleTVProgramThumbnails.landscape_540x304_x1_5.getImageName(),
                "The Image Style for Program in the Feature Carousel module for AppleTV Home Page view port is matched");
        softAssert.assertContains(previewPage.getUrlOfImage(ContentType.TVE_VIDEO, featureCarouselForm),
                AppleTVVideoThumbnails.landscape_720x405_x1.getImageName(),
                "The image style for Video in the Feature Carousel module doesn't matched on preview with expected: " + AppleTVVideoThumbnails.landscape_720x405_x1.getImageName(),
                "The Image Style for Video in the Feature Carousel module for AppleTV Home Page view port is matched");
        softAssert.assertContains(previewPage.getUrlOfImage(ContentType.TVE_PROGRAM, featureShowModuleData),
                AppleTVProgramThumbnails.landscape_255x143_x1_5.getImageName(),
                "The image style for Program in the Feature Show module doesn't matched on preview with expected: " + AppleTVProgramThumbnails.landscape_255x143_x1_5.getImageName(),
                "The Image Style for Program in the Feature Show module for AppleTV Home Page view port is matched");

        softAssert.assertAll();
    }

    /**
     * The Method creates 2 Shelves,Feature Carousel and Show modules
     * and Creates Page with Apple TV platform with assignee those modules within
     */
    private void preconditionTC17219() {
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        String fullEpisode = contentPage.getRandomAsset(ContentType.TVE_VIDEO, ContentFormat.FULL_EPISODE);
        String program = contentPage.getRandomAsset(ContentType.TVE_PROGRAM);
        //  programTwo = programs.get(1);
        List<String> assetsList = Arrays.asList(program, fullEpisode);
        shelf1Tile = EntityFactory.getShelfsList().get(0);
        shelf3Tile = EntityFactory.getShelfsList().get(3);
        shelf1Tile.setAssets(assetsList);
        shelf3Tile.setAssets(assetsList);
        featureCarouselForm.setAssets(assetsList);
        featureShowModuleData.setAssets(program);

        //Create test feature carousel module
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf1Tile);
        draftModuleTab = mainRokuAdminPage.openAddFeatureCarouselPage(brand);
        draftModuleTab.createFeatureCarousel(featureCarouselForm);
        draftModuleTab = mainRokuAdminPage.openAddFeatureShowPage(brand);
        draftModuleTab.createFeatureShowModule(featureShowModuleData);

        pageInfo = rokuBackEndLayer.createPage(pageInfo);
        editPage = new EditPageWithPanelizer(webDriver, aid, panelizerTemplate);
        editPage.setModules(Arrays.<Module>asList(featureCarouselForm, shelf1Tile, featureShowModuleData));
        editPage.save();
    }
}
