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
 *Step 6: change assets order
 *Step 7: open edit page for just created shelf
 *Step 8: Verify that asset was deleted
 *
 *
 */

public class TC11734_NewUI_UserIsAbleChangeAssetsOrder extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;

    @Test(groups = {"shelf_management_admin", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc11734asAdmin(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        tc11734logic();
    }

    @Test(groups = {"shelf_management_editor", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc11734asEditor(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();
        tc11734logic();
    }

    private void tc11734logic() {
        Shelf shelf = EntityFactory.getShelfsList().get(2);
        SoftAssert softAssert = new SoftAssert();
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        Assert.assertFalse(mainRokuAdminPage.isErrorMessagePresent());

        mainRokuAdminPage.openOttModulesPage(brand);
        ModulesPage modulesPage = new ModulesPage(webDriver, aid);
        softAssert.assertTrue(modulesPage.isShelfExist(shelf.getTitle()), "Shelf was not created", "Shelf was created", webDriver);
        modulesPage.clickEditLink(shelf.getTitle());
        draftModuleTab.dragAndDrop(3, 1);
        draftModuleTab.pause(5);
        Shelf newShelf = draftModuleTab.getShelfInfo();
        draftModuleTab.clickSave();
        softAssert.assertNotEquals(shelf, newShelf, "Reorder wasn't maded Shelf must be not equals", "Reorder was maded", webDriver);
        mainRokuAdminPage.openOttModulesPage(brand);
        modulesPage.clickEditLink(newShelf.getTitle());
        softAssert.assertEquals(newShelf, draftModuleTab.getShelfInfo(), "Chenges related to reodering wasn't succesfull maded",
                "Chenges related to reodering was succesfull maded", webDriver);
        softAssert.assertAll();
    }

}
