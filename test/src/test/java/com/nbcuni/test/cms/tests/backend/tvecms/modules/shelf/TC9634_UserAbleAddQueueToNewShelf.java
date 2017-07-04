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
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Aliaksei_Dzmitrenka
 *
 *         Step 1: open create shelf page Step 2: fill all fields Step 3: add
 *         assets Step 4: save shelf Step 5: verify that shelf is present on
 *         modules page Step 6: open shelf and verify all fields Expected:
 *         fields the same as typed
 *
 *
 */

public class TC9634_UserAbleAddQueueToNewShelf extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;

    @Test(groups = {"shelf_management_admin", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9634asAdmin(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        tc9634logic();
    }

    @Test(groups = {"shelf_management_editor", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9634asEditor(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();
        tc9634logic();
    }

    private void tc9634logic() {
        Shelf shelf = EntityFactory.getShelfsList().get(2);
        SoftAssert softAssert = new SoftAssert();
        DraftModuleTab addShelfOttModulePage = mainRokuAdminPage.openAddShelfPage(brand);
        addShelfOttModulePage.createShelf(shelf);
        Assert.assertFalse(mainRokuAdminPage.isErrorMessagePresent());
        mainRokuAdminPage.openOttModulesPage(brand);

        ModulesPage modulesPage = new ModulesPage(webDriver, aid);
        softAssert.assertTrue(modulesPage.isShelfExist(shelf.getTitle()), "Shelf was not created", "Shelf was created", webDriver);
        modulesPage.clickEditLink(shelf.getTitle());
        softAssert.assertEquals(shelf, addShelfOttModulePage.getShelfInfo(), "Shelf's fields is wrong", "Shelf's fields is correct",
                webDriver);
        softAssert.assertAll();
    }

}
