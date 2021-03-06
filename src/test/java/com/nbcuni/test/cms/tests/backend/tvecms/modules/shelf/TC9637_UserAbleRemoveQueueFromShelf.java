package com.nbcuni.test.cms.tests.backend.tvecms.modules.shelf;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import org.testng.annotations.Test;

/**
 *
 * @author Aliaksei_Dzmitrenka
 *
 *         Step 1: open create shelf page Step 2: fill all fields Step 3: save
 *         shelf Step 4: verify that shelf is present on modules page Step 5:
 *         open edit page for this shelf Step 6: remove any asset and save Step
 *         7: open this shelf and verify that asset are removed
 *
 *
 */

public class TC9637_UserAbleRemoveQueueFromShelf extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;

    @Test(groups = {"shelf_management_admin", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9637asAdmin(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        tc9637logic();
    }

    @Test(groups = {"shelf_management_editor", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9637asEditor(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();
        tc9637logic();
    }

    private void tc9637logic() {
        Shelf shelf = EntityFactory.getShelfsList().get(2);
        SoftAssert softAssert = new SoftAssert();
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(),
                "Error message is present after creation custom module");

        mainRokuAdminPage.openOttModulesPage(brand);
        ModulesPage modulesPage = new ModulesPage(webDriver, aid);
        softAssert.assertTrue(modulesPage.isShelfExist(shelf.getTitle()), "Shelf was not created", "Shelf was created", webDriver);
        modulesPage.clickEditLink(shelf.getTitle());
        draftModuleTab.removeRandomAsset();
        shelf = draftModuleTab.getShelfInfo();
        draftModuleTab.clickSave();
        modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        modulesPage.clickEditLink(shelf.getTitle());
        softAssert.assertEquals(shelf, draftModuleTab.getShelfInfo(), "Shelf's fields is wrong", "Shelf's fields is correct",
                webDriver);
        softAssert.assertAll();
    }

}
