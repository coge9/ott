package com.nbcuni.test.cms.tests.smoke.tvecms.shelf;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by aleksandra_lishaeva on 10/1/15.
 */
public class TC10009_UserAbleDeleteShelf extends BaseAuthFlowTest {
    /**
     * Pre-condition:
     * Create shelf with required fields
     *
     * Step 1: Go To the Roku site as admin http://qa.roku.usa.nbcuni.com/
     * Verify: The admin panel is present
     *
     * Step 2: Go to the OTT Module page: admin/ott/modules
     * Verify: The list of shelves is present. There is shelf created in pre-condition
     *
     * Step 3: Delete shelf from precondition. Confirm deletion
     * Verify: The shelf is deleted
     * */

    @Test(groups = {"shelf_management", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkTHatShelfPossibleToDelete(String brand) {
        Utilities.logInfoMessage("Check that user is able to delete shelf");
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-condition Create shelf
        Shelf shelf = EntityFactory.getShelfsList().get(0);
        SoftAssert softAssert = new SoftAssert();
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "The error message is present",
                "There is no any error message per shelf creation");
        mainRokuAdminPage.openOttModulesPage(brand);

        ModulesPage modulesPage = new ModulesPage(webDriver, aid);
        softAssert.assertTrue(modulesPage.isShelfExist(shelf.getTitle()), "Shelf was not created", "Shelf was created", webDriver);

        //Step2-3
        rokuBackEndLayer.deleteModule(shelf.getTitle());
        softAssert.assertFalse(modulesPage.isShelfExist(shelf.getTitle()), "Shelf still exist", "Shelf was deleted", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }
}
