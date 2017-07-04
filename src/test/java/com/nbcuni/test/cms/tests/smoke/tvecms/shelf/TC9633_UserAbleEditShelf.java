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
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Aliaksei_Dzmitrenka
 *
 *         Step 1: open create shelf page Step 2: fill all fields Step 3: save
 *         shelf Step 4: verify that shelf is present on modules page Step 5:
 *         open edit page for this shelf Step 6: edit all field except assets
 *         and save Step 7: open that shelf again and verify all fields
 *         Expected: fields the same as typed
 *
 *
 */

public class TC9633_UserAbleEditShelf extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private ModulesPage modulesPage;
    private Shelf actualShelf;
    private String brand;

    @Test(groups = {"shelf_management_admin", "roku_smoke", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9633asAdmin(final String brand) {
        this.brand = brand;
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        tc9633logic();
    }

    @Test(groups = {"shelf_management_editor", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9633asEditor(final String brand) {
        this.brand = brand;
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();
        tc9633logic();
    }

    private void tc9633logic() {
        Shelf shelf = EntityFactory.getShelfsList().get(0);
        Shelf newShelf = EntityFactory.getShelfsList().get(3);
        SoftAssert softAssert = new SoftAssert();
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        Assert.assertFalse(mainRokuAdminPage.isErrorMessagePresent());
        modulesPage = mainRokuAdminPage.openOttModulesPage(brand);

        softAssert.assertTrue(modulesPage.isShelfExist(shelf.getTitle()), "Shelf was not created", "Shelf was created", webDriver);
        draftModuleTab = modulesPage.clickEditLink(shelf.getTitle());
        actualShelf = draftModuleTab.createShelf(newShelf);

        softAssert.assertEquals(newShelf, actualShelf, "Shelf's fields is wrong after edition",
                "Shelf's fields is correct after edition", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteShelf9633() {
        try {
            rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
            rokuBackEndLayer.deleteModule(actualShelf.getTitle());
        } catch (Exception e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
