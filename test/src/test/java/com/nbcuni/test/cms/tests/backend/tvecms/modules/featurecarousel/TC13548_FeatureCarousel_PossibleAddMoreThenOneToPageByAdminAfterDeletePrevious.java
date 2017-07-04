package com.nbcuni.test.cms.tests.backend.tvecms.modules.featurecarousel;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.ModuleAddPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.factory.CreateFactoryFeatureCarousel;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Aleksandra_Lishaeva on 3/18/16.
 */
public class TC13548_FeatureCarousel_PossibleAddMoreThenOneToPageByAdminAfterDeletePrevious extends BaseAuthFlowTest {

    private DraftModuleTab draftModuleTab;
    private PageForm pageInfo;
    private FeatureCarouselForm featureCarouselForm = CreateFactoryFeatureCarousel.createFeatureCarouselWithRandomTitle();
    private FeatureCarouselForm featureCarouselForm2 = CreateFactoryFeatureCarousel.createFeatureCarouselWithRandomTitle();

    /**
     * Pre-Conditions:Create a Page
     * Step 1: Go CMS as Admin
     * Verify: Admin Panel is present
     *
     * Step 2: Go To Module List and create 2 feature carousel modules: Module 1 and Module 2
     * Verify: The modules are created
     *
     * Step 3: Go to Page list and select Page created in precondition
     * Verify: Edit of the Page is opened
     *
     * Step 4. Try to Assign Feature Carousel Module1 to the Page
     * Verify: It's possible to assigned any feature carousel module to the page
     *
     * Step 5. Without saving try to Assign Feature Carousel Module2 to the Page
     * Verify: There is no any feature carousel modules to select.
     * It's impossible to add more then one feature carousel module
     *
     * Step 6: Delete Module 1 from the Page and save
     * Verify: The module 1 was deleted
     *
     * Step 7: Try to Assign Modules to the page again
     * Verify: There are feature carousel Module 1 and feature Carousel Module 2 within addItem Pop-up for selection
     *
     * Step 8: Add Module 2 to the page
     * Verify: Module is added.
     * Impossible to add another feature carousel modules to taht page.
     * */

    @Test(groups = {"feature_carousel_page"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkFeatureCarouselAddingByAdmin(String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        draftModuleTab = mainRokuAdminPage.openAddFeatureCarouselPage(brand);
        draftModuleTab.createFeatureCarousel(featureCarouselForm);
        draftModuleTab.clickSave();

        draftModuleTab = mainRokuAdminPage.openAddFeatureCarouselPage(brand);
        draftModuleTab.createFeatureCarousel(featureCarouselForm2);
        draftModuleTab.clickSave();

        //Step 3
        //Create a page
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);

        //Assign modules to the page
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());

        //Step 4
        pageInfo = editPage.updateFields(Arrays.<Module>asList(featureCarouselForm));

        //Step 5
        ModuleAddPage moduleAddPage = editPage.clickAddContent();
        softAssert.assertFalse(moduleAddPage.isModuleExistWithinList(featureCarouselForm2.getTitle()), "The second feature carousel Module is present within Add Module Pop-up, but should not be",
                "There is no second feature carousel modules within Add Module Pop-up list", webDriver);
        moduleAddPage.clickCancel();

        //Step 6
        rokuBackEndLayer.deleteModule(featureCarouselForm.getTitle());

        //Step 7
        editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        moduleAddPage = editPage.clickAddContent();
        softAssert.assertTrue(moduleAddPage.isModuleExistWithinList(featureCarouselForm2.getTitle()), "The second feature carousel Module is not present within Add Module Pop-up, after page saving",
                "There is feature carousel modules within Add Module Pop-up list, after page saving", webDriver);

        //Step 8
        editPage.setModule(featureCarouselForm2.getTitle());
        editPage.save();
        softAssert.assertTrue(editPage.getModulesName().contains(featureCarouselForm2.getTitle()), "The feature carousel module: " + featureCarouselForm2.getTitle() + " is not added to the Page",
                "The Module: " + featureCarouselForm2.getTitle() + " is added to the page", webDriver);

        softAssert.assertAll();
        Utilities.logInfoMessage("The test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage13548() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            rokuBackEndLayer.deleteModule(featureCarouselForm2.getTitle());
        } catch (Exception e) {
            Utilities.logInfoMessage("This page can't be deleted!");
        }
    }
}
