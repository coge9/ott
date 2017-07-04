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
 *         Step 1: open create shelf page Step 2: fill all fields Step 3: save
 *         shelf Step 4: verify that shelf is present on modules page Step 5:
 *         open edit page for this shelf Step 6: reorder some asset Step 7: open
 *         that shelf again and that changes are present
 *
 *
 */

public class TC9638_UserAbleChangeAssetsOrder extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;

    @Test(groups = {"shelf_management_admin", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9638asAdmin(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        tc9638logic();
    }

    @Test(groups = {"shelf_management_editor", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9638asEditor(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();
        tc9638logic();
    }

    private void tc9638logic() {
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
        softAssert.assertNotEquals(shelf, newShelf, "Reorder wasn't maded", "Reorder was maded", webDriver);
        draftModuleTab.clickSave();
        modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        modulesPage.clickEditLink(newShelf.getTitle());
        softAssert.assertEquals(newShelf, draftModuleTab.getShelfInfo(), "Chenges related to reodering wasn't succesfull maded",
                "Chenges related to reodering was succesfull maded", webDriver);
        softAssert.assertAll();
    }

}
