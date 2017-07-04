package com.nbcuni.test.cms.tests.backend.tvecms.shelf.newui;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Aliaksei_Dzmitrenka
 *
 *Steps:
 *Step 1: open add shelf page
 *Step 2: fill all required fields
 *Step 3: add content and save
 *Step 4: open shelfs menu
 *Step 5: open edit page for just created shelf
 *Step 6: delete random asset and save
 *Step 7: open edit page for just created shelf
 *Step 8: Verify that asset was deleted
 *
 *
 */

public class TC11733_NewUI_UserIsAbleDeleteItemsFromShelf extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;

    @Test(groups = {"shelf_management_admin", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc11733asAdmin(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        tc11733logic();
    }

    @Test(groups = {"shelf_management_editor", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc11733asEditor(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();
        tc11733logic();
    }

    private void tc11733logic() {
        Shelf shelf = EntityFactory.getShelfsList().get(2);
        SoftAssert softAssert = new SoftAssert();
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        Assert.assertFalse(mainRokuAdminPage.isErrorMessagePresent());

        mainRokuAdminPage.openOttModulesPage(brand);
        ModulesPage modulesPage = new ModulesPage(webDriver, aid);
        softAssert.assertTrue(modulesPage.isShelfExist(shelf.getTitle()), "Shelf was not created", "Shelf was created", webDriver);
        modulesPage.clickEditLink(shelf.getTitle());
        draftModuleTab.removeRandomAsset();
        draftModuleTab.clickSave();
        shelf = draftModuleTab.getShelfInfo();
        mainRokuAdminPage.openOttModulesPage(brand);
        modulesPage.clickEditLink(shelf.getTitle());
        softAssert.assertEquals(shelf, draftModuleTab.getShelfInfo(), "Shelf's fields is wrong", "Shelf's fields is correct",
                webDriver);
        softAssert.assertAll();
    }

}
