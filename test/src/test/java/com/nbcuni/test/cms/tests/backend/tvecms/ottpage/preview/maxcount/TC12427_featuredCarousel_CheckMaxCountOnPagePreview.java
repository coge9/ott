package com.nbcuni.test.cms.tests.backend.tvecms.ottpage.preview.maxcount;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PreviewPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.factory.CreateFactoryFeatureCarousel;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 2/5/16.
 */
public class TC12427_featuredCarousel_CheckMaxCountOnPagePreview extends BaseAuthFlowTest {

    FeatureCarouselForm featureCarouselForm = CreateFactoryFeatureCarousel.createFeatureCarouselWithRandomTitle();
    private PageForm pageInfo;
    private DraftModuleTab draftModuleTab;
    private MainRokuAdminPage mainRokuAdminPage;
    private String program;
    private List<String> imagesNames;
    private int maxCount = 1;

    /**
     * Pre-Condition:
     * 1 Create a Featured Carousel module.
     * 2 Create a Page
     * 3 Assign module to the Page
     *
     * Step 1: Go To roku CMS as admin
     * Verify: Admin panel is present
     *
     * Step 2: Go to module created in precondition
     * Verify: Edit module Page is present
     *
     * Step 3: Assign a Program to the module
     * Verify: The Program is displayed in the content list
     *
     * Step 4: Check 'show latest' checkbox
     * Verify: Program in the content list marked as show latest
     *
     * Step 5: Go to the edit of Page created in precondition (with new panelizer view)
     * Verify: The Edit Page is present.There is button "Preview"
     *
     * Step 6: Click 'Preview' button
     * Verify: The Preview page is opened. The Module info is present
     *
     * Step 7. Check Latest episode for tea Module
     * Verify: There are 1 thumbnails for the latest episodes(episodes that are have more latest Updated date) related to Program in the content list of the Shelf
     */

    public void tc12426PreCondition() {
        //get random program
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        program = contentPage.getRandomAsset(ContentType.TVE_PROGRAM);
        imagesNames = rokuBackEndLayer.getLatestEpisodes(program, maxCount);

        // Create test Shelf module
        featureCarouselForm.setAssets(Arrays.asList(program));
        draftModuleTab = mainRokuAdminPage.openAddFeatureCarouselPage(brand);

        //create page and assign shelf
        draftModuleTab.createFeatureCarousel(featureCarouselForm);
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
        EditPageWithPanelizer editPage = rokuBackEndLayer.addModule(pageInfo, featureCarouselForm);
        editPage.save();
    }

    @Test(groups = {"max_count"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkFeatureCarouselMaxCountOnPreview(final String brand) {
        this.brand = brand;

        //Step 1 Login
        SoftAssert softAssert = new SoftAssert();
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step Pre-condition
        tc12426PreCondition();

        //Step 2
        draftModuleTab = rokuBackEndLayer.openModuleEdit(featureCarouselForm);

        //Step 3-4
        draftModuleTab.checkLatestEpisodeByName(program);
        softAssert.assertTrue(draftModuleTab.isMaxCountPresent(program), "The Max Count field is not visible for :" + program, "Max Count field is present for :" + program, webDriver);

        //Step 5
        draftModuleTab.setMaxCount(program, String.valueOf(maxCount));
        draftModuleTab.clickSave();

        //Step 6
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());

        //Step 7
        PreviewPage previewPage = editPage.clickPreviewLink();

        //Step 8
        List<String> modulesNames = Arrays.asList(featureCarouselForm.getTitle());
        List<String> actualModules = previewPage.getTitles();
        softAssert.assertEquals(modulesNames, actualModules, "Modules are not corrected", "Modules are corrected");
        softAssert.assertEquals(imagesNames, previewPage.getAllImagesNames(), "Images are not corrected", "Images are corrected");
        softAssert.assertAll("Test failed");
        Utilities.logInfoMessage("Test passed");
    }
}
