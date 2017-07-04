package com.nbcuni.test.cms.tests.smoke.tvecms.shelf;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Aliaksei_Dzmitrenka
 *
 *         Step 1: open create shelf page Step 2: fill all fields Step 3: save
 *         shelf Step 4: verify that shelf is present on modules page Step 5:
 *         open shelf and verify all fields Expected: fields the same as typed
 *
 */

public class TC9628_UserAbleCreateShelf extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private ModulesPage modulesPage;
    private Shelf shelf;
    private String brand;

    @Test(groups = {"shelf_management_admin", "roku_smoke", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9628asAdmin(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        this.brand = brand;
        mainRokuAdminPage = backEndLayer.openAdminPage();
        tc9628logic();
    }

    @Test(groups = {"shelf_management_editor", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9628asEditor(final String brand) {
        this.brand = brand;
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();
        tc9628logic();

    }

    private void tc9628logic() {
        shelf = EntityFactory.getShelfsList().get(0);
        SoftAssert softAssert = new SoftAssert();
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "Error message is present",
                "Error message i snot present per shelf creation");
        mainRokuAdminPage.openOttModulesPage(brand);

        modulesPage = new ModulesPage(webDriver, aid);
        softAssert.assertTrue(modulesPage.isShelfExist(shelf.getTitle()), "Shelf was not created", "Shelf was created", webDriver);
        modulesPage.clickEditLink(shelf.getTitle());
        softAssert.assertEquals(shelf, draftModuleTab.getShelfInfo(), "Shelf's fields are wrong", "Shelf's fields are correct",
                webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteShelf9628() {
        try {
            rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage(e.getMessage());
        }
    }

}
