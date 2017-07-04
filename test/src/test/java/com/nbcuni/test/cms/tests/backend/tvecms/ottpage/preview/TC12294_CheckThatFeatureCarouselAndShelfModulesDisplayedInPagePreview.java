package com.nbcuni.test.cms.tests.backend.tvecms.ottpage.preview;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PreviewPage;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.SourceMatcher;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.factory.CreateFactoryFeatureCarousel;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * TC12294
 *
 * Pre-condition:
 * 1. Create new page
 * 2. Add shelf and feature carousel module in page
 *
 * Step 1: Go To roku CMS as Admin
 *
 * Step 2: Edit page from pre-conditions
 *
 * Step 3: Click Preview button
 *
 * Validation: Check assigned modules
 * Expected result: Shelf and feature carousel module titles is present
 * 1tile source from module's assets is present
 */
public class TC12294_CheckThatFeatureCarouselAndShelfModulesDisplayedInPagePreview extends BaseAuthFlowTest {

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

        GlobalNodeJson programOne = new NodeApi(brand).getRandomProgramNode();

        List<String> assetOneList = Arrays.asList(programOne.getTitle());

        featureCarouselForm.setAssets(assetOneList);
        shelf.setAssets(assetOneList);
        /*
        contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(programOne);*/
        String expectedProgramOneSource = programOne.getSourceByType(SourceMatcher.Program1TileSource).getMachineName();
        /* expectedProgramOneSource = programContentPage.onRokuImagesTab().elementOneTileArea().getImageSourceName();

        contentPage = mainRokuAdminPage.openContentPage(brand);
        programContentPage = contentPage.openEditOTTProgramPage(programTwo);
        String expectedProgramTwoSource = programContentPage.onRokuImagesTab().elementOneTileArea().getImageSourceName();*/

        List<String> imagesNames = Arrays.asList(expectedProgramOneSource);

        DraftModuleTab featureCarouselPage = mainRokuAdminPage.openAddFeatureCarouselPage(brand);
        featureCarouselPage.createFeatureCarousel(featureCarouselForm);
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);

        //Pre-condition 1
        PageForm pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());

        //Pre-condition 2
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
