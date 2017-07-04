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
public class TC11707_EditorIsAbleToCreateFeatureShowModule extends BaseAuthFlowTest {
    MainRokuAdminPage mainRokuAdminPage;
    String moduleTitle;
    RokuBackEndLayer backEndLayer;

    /**
     * Pre-condition
     * 1. Login in Roku CMS as Editor
     *
     * Step 1: Go to /admin/ott/modules/add/feature_shows
     * Verify: Page Title is 'Add feature shows module'
     *
     * Step 2: Fill all required fields and click 'Save'
     * Verify: Status message is presented.
     Status message text: ''
     Error message isn't present.
     Check all fields values. Alias value is the same as title value. Alias is disabled.
     *
     */

    @Test(groups = {"feature_show_module", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc11707(final String brand) {
        Utilities.logInfoMessage("Check that editor is able to create feature show module");
        //Pre-condition
        this.brand = brand;
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();
        DraftModuleTab addModulePage = mainRokuAdminPage.openAddFeatureShowPage(brand);
        FeatureShowModule featureShowModuleData = CreateFeatureShowModule.createDefault();
        moduleTitle = featureShowModuleData.getTitle();
        featureShowModuleData = addModulePage.createFeatureShowModule(featureShowModuleData);
        softAssert.assertFalse(addModulePage.isErrorMessagePresent(), "Error message presence");
        softAssert.assertTrue(addModulePage.isStatusMessageShown(), "Status message presence");
        softAssert.assertEquals(moduleTitle, addModulePage.getTitle(), "Text of edit page title");
        softAssert.assertEquals(featureShowModuleData, addModulePage.getFeatureShowModuleInfo(), "Check fields values");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteFeatureShowModule() {
        mainRokuAdminPage.logOut(brand);
        backEndLayer.openAdminPage();
        backEndLayer.deleteModule(moduleTitle);
    }
}
