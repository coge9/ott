package com.nbcuni.test.cms.tests.backend.tvecms.modules.managerevision;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.ManageRevisionsTab;
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
public class TC11573_ManageRevision_RevertModuleToPublishedRevision extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private ModulesPage modulePage;
    private AddNewPage addNewPage;
    private PageForm pageInfo;
    private TVEPage tvePage;

    /**
     * Pre-Condition:
     * Create Page
     * Create a shelf
     * assign shelf to the Page and Publish that
     * <p>
     * Step 1: Go to CMS as admin
     * Verify: Admin panel is present
     * <p>
     * Step 2. Go To Module list Dashboard » Modules
     * Verify: The list of shelves is present
     * <p>
     * Step 3. Make any changes and save
     * Verify: The new Draft revision is created per module
     * <p>
     * Step 4. Click on tab 'Manage Revision' and check elements
     * Verify: The Manage revision tab is rendered
     * The next elements are present:
     * -Table with list of revisions per module is present
     * -The Published revision has View option
     * -The Published revision has Revert option
     * -The Published revision has no Delete option
     * -The Published revision has Edit option
     * -The Draft revision has View option
     * -The Draft revision has no Revert option as current
     * -The Draft revision has not Delete option as current
     * -The Draft revision has Edit option
     * <p>
     * Step 5. Click link 'revert' next to a First Published revision
     * Verify:
     * 1)The module state is reverted to the First revision
     * 2)Published revision is marked as 'current'
     */

    @Test(groups = "module_revision", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkRevertModeOfPublishedModuleRevision(final String brand) {
        Utilities.logInfoMessage("Check that Revert Mode Of Published Module's revision");
        SoftAssert softAssert = new SoftAssert();

        //Step 1
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Pre-condition
        pageInfo = backEndLayer.createPage();
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        Shelf shelfPublished = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelfPublished);
        draftModuleTab.clickSave();
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "There are error after saving with out required fields", "Error message is not present", webDriver);
        tvePage = mainRokuAdminPage.openOttPage(brand);
        EditPageWithPanelizer addNewPage = tvePage.clickEdit(pageInfo.getTitle());

        addNewPage.setModule(shelfPublished.getTitle());
        addNewPage.save();
        addNewPage.elementPublishBlock().publishByTabName();

        //Step 2
        modulePage = mainRokuAdminPage.openOttModulesPage(brand);

        //Step 3
        draftModuleTab = modulePage.clickEditLink(shelfPublished.getTitle());
        Shelf shelfDraft = EntityFactory.getShelfsList().get(1);
        draftModuleTab.createShelf(shelfDraft);
        draftModuleTab.clickSave();
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "There are error after saving with out required fields", "Error message is not present", webDriver);

        //Step 4.1
        ManageRevisionsTab revisionsTab = draftModuleTab.clickManageRevisionTab();
        softAssert.assertTrue(revisionsTab.isViewButtonVisible(shelfPublished), "The view button is not visible for Published shelf revision: " + shelfPublished.getTitle(), "The view button is visible for Published revision", webDriver);
        softAssert.assertFalse(revisionsTab.isDeleteButtonVisible(shelfPublished), "The Delete button is visible for Published shelf revision: " + shelfPublished.getTitle(), "The Delete button is not visible for Published revision", webDriver);
        softAssert.assertTrue(revisionsTab.isEditButtonVisible(shelfPublished), "The Edit button is visible not for Published shelf revision: " + shelfPublished.getTitle(), "The Edit button is visible for Published revision", webDriver);
        softAssert.assertTrue(revisionsTab.isRevertButtonVisible(shelfPublished), "The Revert button is not present for the Published revision of the shelf: " + shelfPublished.getTitle(), "The Revert button is visible for Published revision", webDriver);

        //Step 4.2
        softAssert.assertTrue(revisionsTab.isViewButtonVisible(shelfDraft), "The view button is not visible for Draft shelf revision: " + shelfDraft.getTitle(), "The view button is visible for Draft revision", webDriver);
        softAssert.assertFalse(revisionsTab.isDeleteButtonVisible(shelfDraft), "The Delete button is visible for current shelf revision: " + shelfDraft.getTitle(), "The Delete button is not visible for current Draft revision", webDriver);
        softAssert.assertTrue(revisionsTab.isEditButtonVisible(shelfDraft), "The Edit button is not visible for Draft shelf revision: " + shelfDraft.getTitle(), "The Edit button is visible for Draft revision", webDriver);
        softAssert.assertFalse(revisionsTab.isRevertButtonVisible(shelfDraft), "The Revert button is present for current Draft revision of the shelf: " + shelfDraft.getTitle(), "The Revert button is not visible for Draft revision", webDriver);

        //Step 5.1
        revisionsTab.clickRevertButton(shelfPublished);
        softAssert.assertTrue(revisionsTab.isRevisionCurrent(revisionsTab.getIdRevision(shelfPublished)), "The Published revision for each revert was performed, is not marked as current"
                , "The Published revision for each revert was performed, is marked as current", webDriver);

        draftModuleTab = revisionsTab.clickEditDraftTab();
        Shelf shelfEdit = draftModuleTab.getShelfInfo();

        softAssert.assertEquals(shelfEdit, shelfPublished, "The shelf's info: " + shelfEdit + "  is not the same with published: " + shelfPublished + " after revert", "The shelf info is matched with those that was Published", webDriver);
        softAssert.assertAll();

    }
}
