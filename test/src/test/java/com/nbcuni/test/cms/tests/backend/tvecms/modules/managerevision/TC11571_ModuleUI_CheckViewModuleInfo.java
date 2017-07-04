package com.nbcuni.test.cms.tests.backend.tvecms.modules.managerevision;


import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.ViewPublishTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

public class TC11571_ModuleUI_CheckViewModuleInfo extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;

    /**
     * Pre-Condition:
     * Create a shelf and Publish that
     * <p/>
     * Step 1: Go to CMS as admin
     * Verify: Admin panel is present
     * <p/>
     * Step 2. Go To Module list Dashboard » Modules
     * Verify: The list of shelves is present
     * <p/>
     * Step 3. Open Shelf created in pre-condition
     * Verify: The 'Edit Draft' tab is present
     * <p/>
     * Step 4. Click 'View published' tab
     * Verify: The next info is present:
     * -Shelf title
     * -Tile variant
     * - List of items names
     * - Type
     * - Option display Title
     * - Last Update by
     * - Created
     */

    @Test(groups = "module_revision", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkViewModuleTabs(final String brand) {
        Utilities.logInfoMessage("Check View Modules' tab");

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
        softAssert.assertTrue(draftModuleTab.isStatusMessageShown(), "There is error after saving shelf", "Error message is not present,Shelf is saved", webDriver);

        //Step 5
        ViewPublishTab viewPublishTab = draftModuleTab.clickViewPublishTab();
        softAssert.assertContains(viewPublishTab.getTitle(), shelf.getTitle(), "The shelf title is not matched with set", "The shelf title is matched with set");
        softAssert.assertEquals(shelf.getAssets(), viewPublishTab.getListItems(), "The expected assets within curated list is not matched with actual on View Published", "The expected assets is not matched with actual");
        softAssert.assertContains(shelf.getType(), viewPublishTab.getShelfType(), "The shelf type is not matched at View Published Info with expected", "The shelf type is matched at View Published Info with expected");
        softAssert.assertAll();
    }
}
