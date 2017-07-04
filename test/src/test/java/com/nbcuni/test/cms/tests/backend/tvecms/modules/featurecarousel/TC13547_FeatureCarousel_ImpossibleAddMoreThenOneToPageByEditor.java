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
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Aleksandra_Lishaeva on 3/18/16.
 */
public class TC13547_FeatureCarousel_ImpossibleAddMoreThenOneToPageByEditor extends BaseAuthFlowTest {

    private DraftModuleTab draftModuleTab;
    private PageForm pageInfo;
    private FeatureCarouselForm featureCarouselForm = CreateFactoryFeatureCarousel.createFeatureCarouselWithRandomTitle();
    private FeatureCarouselForm featureCarouselForm2 = CreateFactoryFeatureCarousel.createFeatureCarouselWithRandomTitle();

    /**
     * Pre-Conditions:Create a Page as Admin
     * Step 1: Go CMS as Editor
     * Verify: Editor Panel is present
     * <p/>
     * Step 2: Go To Module List and create 2 feature carousel modules: Module 1 and Module 2
     * Verify: The modules are created
     * <p/>
     * Step 3: Go to Page list and select Page created in precondition
     * Verify: Edit of the Page is opened
     * <p/>
     * Step 4. Try to Assign Feature Carousel Module1 to the Page
     * Verify: It's possible to assigned any feature carousel module to the page
     * <p/>
     * Step 5. Without saving try to Assign Feature Carousel Module2 to the Page
     * Verify: There is no any feature carousel modules to select.
     * It's impossible to add more then one feature carousel module
     * <p/>
     * Step 6: Save Page changes and try to add another feature carousel module to the Page
     * Verify: There is no any feature carousel modules to select.
     * It's impossible to add more then one feature carousel module
     */

    public void pageCreation(String brand) {
        //Login as Admin
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Create Page
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
    }

    @Test(groups = {"feature_carousel_page"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkFeatureCarouselAddingByAdmin(String brand) {

        //Pre-condition
        pageCreation(brand);

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2
        draftModuleTab = mainRokuAdminPage.openAddFeatureCarouselPage(brand);
        draftModuleTab.createFeatureCarousel(featureCarouselForm);
        draftModuleTab.clickSave();

        draftModuleTab = mainRokuAdminPage.openAddFeatureCarouselPage(brand);
        draftModuleTab.createFeatureCarousel(featureCarouselForm2);
        draftModuleTab.clickSave();

        //Step 3
        //Assign modules to the page
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());

        //Step 4
        pageInfo = editPage.updateFields(Arrays.<Module>asList(featureCarouselForm));

        //Step 5
        ModuleAddPage moduleAddPage = editPage.clickAddContent();
        softAssert.assertFalse(moduleAddPage.isModuleExistWithinList(featureCarouselForm2.getTitle()), "The second feature carousel Module is present within Add Module Pop-up, but should not be",
                "There is no second feature carousel modules within Add Module Pop-up list", webDriver);
        moduleAddPage.clickCancel();

        //Step 5
        editPage.save();
        moduleAddPage = editPage.clickAddContent();
        softAssert.assertFalse(moduleAddPage.isModuleExistWithinList(featureCarouselForm2.getTitle()), "The second feature carousel Module is present within Add Module Pop-up, after page saving",
                "There is no second feature carousel modules within Add Module Pop-up list, after page saving", webDriver);

        softAssert.assertAll();
        Utilities.logInfoMessage("The test passed");
    }
}
