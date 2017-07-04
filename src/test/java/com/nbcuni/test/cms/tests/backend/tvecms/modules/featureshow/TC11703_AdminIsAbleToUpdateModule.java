package com.nbcuni.test.cms.tests.backend.tvecms.modules.featureshow;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.FeatureShowModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.factory.CreateFeatureShowModule;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova
 */
public class TC11703_AdminIsAbleToUpdateModule extends BaseAuthFlowTest {
    MainRokuAdminPage mainRokuAdminPage;
    String moduleTitle;
    RokuBackEndLayer backEndLayer;

    /**
     * Pre-condition
     * 1. Login in Roku CMS
     * 2. Go to m/admin/ott/modules/add/feature_shows
     * 3. Fill all required fields
     * 4. Save [module name]
     * <p/>
     * Step 1: Open [module name] for edit
     * Verify: Page title is 'Edit [module name]'
     * Step 2: Update all fields and click Save
     * Verify:         Status message is shown:
     *                  Queue [program name] saved.
     *                  [module name]" has been saved.
     *                  Error message isn't shown
     *                  All fields is updated
     * <p/>
     */

    @Test(groups = {"feature_show_module", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc11703(final String brand) {
        Utilities.logInfoMessage("Check that admin is able to update module");
        this.brand = brand;
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        DraftModuleTab addModulePage = mainRokuAdminPage.openAddFeatureShowPage(brand);
        FeatureShowModule featureShowModuleData = CreateFeatureShowModule.createDefault();
        moduleTitle = featureShowModuleData.getTitle();
        featureShowModuleData = addModulePage.createFeatureShowModule(featureShowModuleData);
        //Step 1
        FeatureShowModule updatedFeatureShowModuleData = CreateFeatureShowModule.createUpdatedModuleForAdmin(featureShowModuleData);
        FeatureShowModule expectedUpdatedModule = addModulePage.createFeatureShowModule(updatedFeatureShowModuleData);
        softAssert.assertFalse(addModulePage.isErrorMessagePresent(), "Error message presence");
        softAssert.assertTrue(addModulePage.isStatusMessageShown(), "Status message presence");
        softAssert.assertEquals(featureShowModuleData, addModulePage.getFeatureShowModuleInfo(), "Check all Fields values");
        moduleTitle = expectedUpdatedModule.getTitle();
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteFeatureShowModule() {
        backEndLayer.deleteModule(moduleTitle);
    }
}
