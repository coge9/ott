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
 *Step 1: Open add shelf page
 *Step 2: fill all required fields
 *Step 3: add content and save
 *Step 4: open shelfs menu
 *Step 5: open edit page for just created shelf
 *Step 6: Make sure that all data is correct and all items is added
 *
 *
 */

public class TC11732_NewUI_UserIsAbleAddItemsToShelf extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;

    @Test(groups = {"shelf_management_admin", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc11732asAdmin(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        tc11732logic();
    }

    @Test(groups = {"shelf_management_editor", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc11732asEditor(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();
        tc11732logic();
    }

    private void tc11732logic() {
        Shelf shelf = EntityFactory.getShelfsList().get(1);
        SoftAssert softAssert = new SoftAssert();
        DraftModuleTab addShelfOttModulePage = mainRokuAdminPage.openAddShelfPage(brand);
        addShelfOttModulePage.createShelf(shelf);
        Assert.assertFalse(mainRokuAdminPage.isErrorMessagePresent());
        mainRokuAdminPage.openOttModulesPage(brand);

        ModulesPage modulesPage = new ModulesPage(webDriver, aid);
        softAssert.assertTrue(modulesPage.isShelfExist(shelf.getTitle()), "Shelf was not created", "Shelf was created", webDriver);
        modulesPage.clickEditLink(shelf.getTitle());
        softAssert.assertEquals(shelf, addShelfOttModulePage.getShelfInfo(), "Shelf's fields is wrong", "Shelf's fields is correct", webDriver);
        softAssert.assertAll();
    }

}
