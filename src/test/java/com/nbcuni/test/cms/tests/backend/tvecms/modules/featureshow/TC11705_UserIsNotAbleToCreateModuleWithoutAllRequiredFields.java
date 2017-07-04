package com.nbcuni.test.cms.tests.backend.tvecms.modules.featureshow;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.FeatureShowModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.factory.CreateFeatureShowModule;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import static com.nbcuni.test.cms.pageobjectutils.MessageConstants.EXPECTED_ERROR_FEATURE_SHOW_ALL_FIELDS_ARE_REQUIRED;
import static com.nbcuni.test.cms.pageobjectutils.MessageConstants.TITLE_ADD_FEATURE_SHOW;

/**
 * Created by Alena_Aukhukova
 */
public class TC11705_UserIsNotAbleToCreateModuleWithoutAllRequiredFields extends BaseAuthFlowTest {
    MainRokuAdminPage mainRokuAdminPage;
    RokuBackEndLayer backEndLayer;

    /**
     * Pre-condition
     * 1. Login in Roku CMS
     * 2. Go to m/admin/ott/modules/add/feature_shows
     * 3. Clear alias and title
     * 4. Save [module name]
     * <p/>
     * Step 1: Go to m/admin/ott/modules/add/feature_shows
     * Verify: Page title is 'Add feature show module'
     *
     * Step 2: Clear title and alias and click Save
     * Verify:         Status message isn't shown:
     *                 [module name]" has been saved.
     *                 Error message is shown
     *                 Error message text: Title field is required. Alias field is required.
     * <p/>
     */

    @Test(groups = {"feature_show_module", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc11705(final String brand) {
        Utilities.logInfoMessage("Check that user is not able to create module without all required fields");
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        this.brand = brand;
        mainRokuAdminPage = backEndLayer.openAdminPage();
        DraftModuleTab addModulePage = mainRokuAdminPage.openAddFeatureShowPage(brand);
        softAssert.assertEquals(TITLE_ADD_FEATURE_SHOW, addModulePage.getPageTitle(), "Text of page title");
        FeatureShowModule emptyModule = CreateFeatureShowModule.createEmpty();
        addModulePage.createFeatureShowModule(emptyModule);
        softAssert.assertTrue(addModulePage.isErrorMessagePresent(), "Error message presence");
        softAssert.assertFalse(addModulePage.isStatusMessageShown(), "Status message presence");
        softAssert.assertEquals(EXPECTED_ERROR_FEATURE_SHOW_ALL_FIELDS_ARE_REQUIRED, addModulePage.getErrorMessage(), "Error message text");
        softAssert.assertAll();
    }
}
