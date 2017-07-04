package com.nbcuni.test.cms.tests.backend.tvecms.modules.managerevision;


import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

public class TC11566_ModuleUI_CheckDefaultTabs extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;

    /**
     *
     * Step 1: Go to CMS as admin
     * Verify: Admin panel is present
     *
     * Step 2. Go To Module list Dashboard » Modules
     * Verify: The list of shelves is present
     *
     * Step 3. Click add any module
     * Verify: The 'New draft' module tab is present
     *
     * Step 4. Type title and save
     * Verify: The new shelf is created
     *
     * Step 5. Open created shelf in the step #4 on edit and check tabs
     * Verify: The next tabs are present:
     * -View Published
     * -New Draft
     * -Manage Schedule
     * -Manage Revision
     * */

    @Test(groups = "module_revision", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkDefaultModuleTabs(final String brand) {
        Utilities.logInfoMessage("Check that Default Modules' tabs are present");

        //Step 1
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 2
        SoftAssert softAssert = new SoftAssert();
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);

        //Step 3
        Shelf shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelf);

        //Step 4
        draftModuleTab.clickSave();
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "There are error after saving with out required fields", "Error message is not present", webDriver);

        //Step 5
        softAssert.assertTrue(draftModuleTab.isViewPublishedVisible(), "The 'View Published' tab is not present or does not available",
                "The 'View Published' tab is present and available");

        softAssert.assertTrue(draftModuleTab.isEditDraftVisible(), "The 'Edit Draft' tab is not present or does not available",
                "The 'Edit Draft' tab is present and available");

        softAssert.assertTrue(draftModuleTab.isManageRevisionVisible(), "The 'Manage Revision' tab is not present or does not available",
                "The 'Manage Revision' tab is present and available");

        softAssert.assertTrue(draftModuleTab.isManageScheduleVisible(), "The 'Manage Schedule' tab is not present or does not available",
                "The 'Manage Schedule' tab is present and available");

        softAssert.assertAll();
    }
}
