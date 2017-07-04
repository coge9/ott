package com.nbcuni.test.cms.tests.backend.tvecms.modules.featureshow;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.FeatureShowModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.factory.CreateFeatureShowModule;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova
 */
public class TC11709_AdminIsAbleToDeleteModule extends BaseAuthFlowTest {
    /**
     * Pre-condition:
     * 1. Login in Roku CMS as Admin
     * 2. Go to /admin/ott/modules/add/feature_shows
     * 3. Fill all required fields
     * 4. Save [module name]
     *
     * Step 1: Go to Modules page
     * Verify: [module name] module is presented
     *
     * Step 2: Click on delete link
     * Verify: Page Title is ' Are you sure you want to delete the [module name]?'
     *
     * Step 3: Click on Confirm
     * Verify: [module name] module isn't presented on Modules page
     *      Status message text 'Deleted[module name].'
     *      Error message isn't presented
     */
    MainRokuAdminPage mainRokuAdminPage;

    @Test(groups = {"feature_show_module", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc11709(final String brand) {
        Utilities.logInfoMessage("Check that admin is able to delete module");
        //Pre-condition - 1
        this.brand = brand;
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        DraftModuleTab addModulePage = mainRokuAdminPage.openAddFeatureShowPage(brand);
        FeatureShowModule featureShowModuleData = CreateFeatureShowModule.createDefault();
        String moduleTitle = featureShowModuleData.getTitle();
        addModulePage.createFeatureShowModule(featureShowModuleData);
        //Step 1
        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        softAssert.assertTrue(modulesPage.isShelfExist(moduleTitle), "Feature Show Module presence");
        //Step 2-3
        modulesPage.deleteModule(moduleTitle);
        softAssert.assertFalse(modulesPage.isErrorMessagePresent(), "Error message presence");
        softAssert.assertTrue(modulesPage.isStatusMessageShown(), "Status message presence");
        String expectedStatusMessage = String.format(MessageConstants.STATUS_ON_SHOW_DELETING, moduleTitle);
        softAssert.assertEquals(expectedStatusMessage, modulesPage.getStatusMessage(), "Status message text");
        softAssert.assertFalse(modulesPage.isShelfExist(moduleTitle), "Feature Show Module presence");
        softAssert.assertAll();
        softAssert.assertFalse(modulesPage.isShelfExist(moduleTitle), "Feature Show Module presence");
    }
}
