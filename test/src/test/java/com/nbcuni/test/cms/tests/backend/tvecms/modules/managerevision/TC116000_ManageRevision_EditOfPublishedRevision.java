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
public class TC116000_ManageRevision_EditOfPublishedRevision extends BaseAuthFlowTest {

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
     *
     * Step 1: Go to CMS as admin
     * Verify: Admin panel is present
     *
     * Step 2. Go To Module list Dashboard » Modules
     * Verify: The list of shelves is present
     *
     * Step 3. Select shelf from pre-condition on edit
     * Verify: The 'Edit Draft' tab is present
     *
     * Step 4. Click on tab 'Manage Revision' and check elements
     * Verify: The Manage revision tab is rendered
     * There is Published revision sa current
     * The next elements are present:
     * -Table with list of revisions per module is present
     * -The Published revision has View option
     * -The Published revision has no Revert option
     * -The Published revision has no Delete option
     * -The Published revision has Edit option
     *
     * Step 5. Click link 'edit' next to the Published revision
     * Verify: The 'Edit Draft' tab of the module is present
     *
     * Step 6: Make changes and save
     * Verify: The changes are saved
     *
     * Step 7: Go To Revision Tab
     * Verify: There are next revision:
     * 1)Published
     * 2)new Draft revision - current revision
     *
     * */

    @Test(groups = "module_revision", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkEditModeOfPublishedModuleRevision(final String brand) {
        Utilities.logInfoMessage("Check that Edit Mode Of Published Module's revision");
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

        //Step 4
        ManageRevisionsTab revisionsTab = draftModuleTab.clickManageRevisionTab();
        softAssert.assertTrue(revisionsTab.isViewButtonVisible(shelfPublished), "The view button is not visible for Published shelf revision: " + shelfPublished.getTitle(), "The view button is visible for Published revision", webDriver);
        softAssert.assertFalse(revisionsTab.isDeleteButtonVisible(shelfPublished), "The Delete button is visible for Published shelf revision: " + shelfPublished.getTitle(), "The Delete button is not visible for Published revision", webDriver);
        softAssert.assertTrue(revisionsTab.isEditButtonVisible(shelfPublished), "The Edit button is not visible for Published shelf revision: " + shelfPublished.getTitle(), "The Edit button is visible for Published revision", webDriver);
        softAssert.assertFalse(revisionsTab.isRevertButtonVisible(shelfPublished), "The Revert button is present for the current Published revision of the shelf: " + shelfPublished.getTitle(), "The Revert button is not visible for current Published revision", webDriver);
        softAssert.assertTrue(revisionsTab.isRevisionCurrent(revisionsTab.getIdRevision(shelfPublished)), "The Published revision for each revert was performed, is not marked as current"
                , "The Published revision for each revert was performed, is marked as current", webDriver);

        //Step 5
        draftModuleTab = revisionsTab.clickEditButton(shelfPublished);
        Shelf shelfUpdated = EntityFactory.getShelfsList().get(3);
        draftModuleTab.createShelf(shelfUpdated);
        draftModuleTab.clickSave();
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "There are error after saving with out required fields", "Error message is not present", webDriver);

        //Step 6
        revisionsTab = draftModuleTab.clickManageRevisionTab();
        softAssert.assertTrue(revisionsTab.isViewButtonVisible(shelfUpdated), "The view button is not visible for current Draft shelf revision: " + shelfUpdated.getTitle(), "The view button is visible for Draft revision", webDriver);
        softAssert.assertFalse(revisionsTab.isDeleteButtonVisible(shelfUpdated), "The Delete button is visible for current shelf revision: " + shelfUpdated.getTitle(), "The Delete button is not visible for current Draft revision", webDriver);
        softAssert.assertTrue(revisionsTab.isEditButtonVisible(shelfUpdated), "The Edit button is not visible for current Draft shelf revision: " + shelfUpdated.getTitle(), "The Edit button is visible for Draft revision", webDriver);
        softAssert.assertFalse(revisionsTab.isRevertButtonVisible(shelfUpdated), "The Revert button is present for current Draft revision of the shelf: " + shelfUpdated.getTitle(), "The Revert button is not visible for Draft revision", webDriver);
        softAssert.assertTrue(revisionsTab.isRevisionCurrent(revisionsTab.getIdRevision(shelfUpdated)), "The new Draft revision is not marked as current"
                , "The New Draft revision is marked as current", webDriver);
        softAssert.assertAll();

    }
}
