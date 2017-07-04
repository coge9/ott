package com.nbcuni.test.cms.tests.backend.tvecms.modules.managerevision;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.ManageRevisionsTab;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.ViewPublishTab;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 12/18/15.
 */
public class TC11568_ManageRevision_ViewModeOfPublishedModuleRevision extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private ModulesPage modulePage;
    private AddNewPage addNewPage;
    private PageForm pageInfo;
    private TVEPage tvePage;

    /**
     * Pre-Condition:
     * Create a shelf and Publish that
     *
     * Step 1: Go to CMS as admin
     * Verify: Admin panel is present
     *
     * Step 2. Go To Module list Dashboard » Modules
     * Verify: The list of shelves is present
     *
     * Step 3. Select Module created in precondition on edit
     * Verify: The 'Edit Draft' tab is present. There is tab 'Manage Revision'
     *
     * Step 4. Click on tab 'Manage Revision' and check elements
     * Verify: The Manage revision tab is rendered
     * The next elements are present:
     * -Table with list of revisions per module is present
     * -The Published revision has View option
     * -The Published revision has Revert option
     * -The Published revision has no Delete option
     * -The Published revision has Edit option
     *
     * Step 5. Click on link 'View'
     * Verify: The View published tab is present with information
     * */

    @Test(groups = "module_revision", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkViewModeOfPublishedModuleRevision(final String brand) {
        Utilities.logInfoMessage("Check that View Mode Of Published Module's revision ");
        SoftAssert softAssert = new SoftAssert();

        //Step 1
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Pre-condition
        pageInfo = backEndLayer.createPage();
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        Shelf shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelf);
        draftModuleTab.clickSave();
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "There are error after saving with out required fields", "Error message is not present", webDriver);
        tvePage = mainRokuAdminPage.openOttPage(brand);
        EditPageWithPanelizer addNewPage = tvePage.clickEdit(pageInfo.getTitle());

        addNewPage.setModule(shelf.getTitle());
        addNewPage.save();
        addNewPage.elementPublishBlock().publishByTabName();

        //Step 2
        modulePage = mainRokuAdminPage.openOttModulesPage(brand);

        //Step 3
        draftModuleTab = modulePage.clickEditLink(shelf.getTitle());

        //Step 4
        ManageRevisionsTab revisionsTab = draftModuleTab.clickManageRevisionTab();
        softAssert.assertTrue(revisionsTab.isViewButtonVisible(shelf), "The view button is not visible for Published shelf revision: " + shelf.getTitle(), "The view button is visible for Published shelf revision");
        softAssert.assertFalse(revisionsTab.isDeleteButtonVisible(shelf), "The Delete button is visible for Published shelf revision: " + shelf.getTitle(), "The Delete button is not visible for Published shelf revision");
        softAssert.assertTrue(revisionsTab.isEditButtonVisible(shelf), "The Edit button is not visible for Published shelf revision: " + shelf.getTitle(), "The Edit button is visible for Published shelf revision");
        softAssert.assertFalse(revisionsTab.isRevertButtonVisible(shelf), "The Revert button is present for the current revision of the Published shelf revision: " + shelf.getTitle(), "The Revert button is not visible for current revision for Published shelf revision");

        //Step 5
        ViewPublishTab viewPublishTab = revisionsTab.clickViewButton(shelf);
        softAssert.assertContains(viewPublishTab.getTitle().trim(), shelf.getTitle(), "The shelf title is not matched with set", "The shelf title is matched with set");
        softAssert.assertEquals(shelf.getAssets(), viewPublishTab.getListItems(), "The expected assets within curated list is not matched with actual on View Published", "The expected assets is not matched with actual");
        softAssert.assertContains(viewPublishTab.getShelfType().toLowerCase(), shelf.getType().toLowerCase(), "The shelf type is not matched at View Published Info with expected", "The shelf type is matched at View Published Info with expected");
        softAssert.assertAll();

    }
}
