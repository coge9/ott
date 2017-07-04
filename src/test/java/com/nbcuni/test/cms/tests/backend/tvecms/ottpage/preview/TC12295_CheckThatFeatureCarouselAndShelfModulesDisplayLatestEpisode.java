package com.nbcuni.test.cms.tests.backend.tvecms.ottpage.preview;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PreviewPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.factory.CreateFactoryFeatureCarousel;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ContentFormat;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * TC12295
 *
 * Pre-condition:
 * 1. Create new page
 * 2. Create shelf and feature carousel module with checked Show latest
 * 3. Add shelf and feature carousel module in page
 *
 * Step 1: Go To roku CMS as Admin
 *
 * Step 2: Edit page from pre-conditions
 *
 * Step 3: Click Preview button
 *
 * Validation: Check assigned modules
 * Expected result: Shelf and feature carousel module titles is present
 * 1tile source from latest episode module's assets is present
 */
public class TC12295_CheckThatFeatureCarouselAndShelfModulesDisplayLatestEpisode extends BaseAuthFlowTest {

    FeatureCarouselForm featureCarouselForm = CreateFactoryFeatureCarousel.createFeatureCarouselWithRandomTitle();
    Shelf shelf;

    //TODO refactor with node api and new logic for getting image sources
    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = false)
    public void checkPreviewModeButton(final String brand) {
        this.brand = brand;
        shelf = EntityFactory.getShelfsList().get(0);

        SoftAssert softAssert = new SoftAssert();
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        MainRokuAdminPage mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        String programOne = contentPage.getRandomAsset(ContentType.TVE_PROGRAM, ContentFormat.FULL_EPISODE);
        String programTwo = contentPage.getRandomAsset(ContentType.TVE_PROGRAM, ContentFormat.FULL_EPISODE);

        List<String> assetOneList = Arrays.asList(programOne);
        List<String> assetTwoList = Arrays.asList(programTwo);

        featureCarouselForm.setAssets(assetOneList);
        shelf.setAssets(assetTwoList);

        String programOneLatestEpisode = new MPXLayer(brand).getLatestEpisodes(programOne, 1).get(0).getTitle();
        contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEVideoContentPage videoContentPage = contentPage.openEditTVEVideoPage(programOneLatestEpisode);
        String expectedProgramOneSource = videoContentPage.onImagesTab().elementOneTileArea().getImageSourceName();

        contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(programTwo);
        String expectedProgramTwoSource = programContentPage.onRokuImagesTab().elementOneTileArea().getImageSourceName();

        List<String> imagesNames = Arrays.asList(expectedProgramOneSource, expectedProgramTwoSource);

        //Pre-condition 2
        DraftModuleTab featureCarouselPage = mainRokuAdminPage.openAddFeatureCarouselPage(brand);
        featureCarouselPage.createFeatureCarousel(featureCarouselForm);

        mainRokuAdminPage.openOttModulesPage(brand).clickEditLink(featureCarouselForm.getTitle());

        featureCarouselPage = new DraftModuleTab(webDriver, aid);

        //Pre-condition 3
        featureCarouselPage.checkLatestEpisodeByName(programOne);
        featureCarouselPage.clickSave();
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);

        //Pre-condition 1
        PageForm pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        editPage.updateFields(Arrays.asList(shelf, featureCarouselForm));

        List<String> modulesNames = Arrays.asList(shelf.getTitle(), featureCarouselForm.getTitle());

        //Step 1 - 2
        EditPageWithPanelizer pageWithPanelizer = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());

        //Step 3
        PreviewPage previewPage = pageWithPanelizer.clickPreviewLink();

        List<String> actualModules = previewPage.getTitles();

        //Validation
        softAssert.assertEquals(modulesNames, actualModules, "Modules are not corrected", "Modules are corrected");

        softAssert.assertEquals(imagesNames, previewPage.getAllImagesNames(), "Images are not corrected", "Images are corrected");

        softAssert.assertAll();
    }
}
