package com.nbcuni.test.cms.tests.backend.tvecms.modules.shelf;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import org.testng.annotations.Test;

/**
 *
 * @author Aliaksei_Dzmitrenka
 *
 * Step 1: open create shelf page
 * Step 2: click save
 * Step 3: verify that error message is present
 *
 *
 */

public class TC9629_UserCantSaveShelfWithOutRequiredFields extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;


    @Test(groups = {"shelf_management_admin", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9629asAdmin(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        tc9629logic();
    }

    @Test(groups = {"shelf_management_editor", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9629asEditor(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();
        tc9629logic();
    }

    private void tc9629logic() {
        SoftAssert softAssert = new SoftAssert();
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.clickSave();
        softAssert.assertTrue(draftModuleTab.isErrorMessagePresent(), "There are no any error after saving with out required fields", "Error message is present", webDriver);
        softAssert.assertAll();
    }

}
