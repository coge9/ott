package com.nbcuni.test.cms.tests.backend.tvecms.modules.managerevision;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.ManageRevisionsTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 12/18/15.
 */
public class TC11569_ManageRevision_EditDraftRevision extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;

    /**
     * Pre-Condition:
     * Create a shelf
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
     * -There is Draft revisions
     *
     * Step 5: Check options available for the Draft Revision
     * Verify: There are : View, Edit Draft, Delete
     *
     * Step 6: Click 'Edit Draft' link
     * Verify: The 'Edit Draft' module tab is opened
     *
     * Step 7: Make changes and save
     * Verify: The draft revision is saved with changes
     * */

    @Test(groups = "module_revision", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkAbilityToEditDraftModuleRevision(final String brand) {
        Utilities.logInfoMessage("Check that user could edit module's revision in draft");
        SoftAssert softAssert = new SoftAssert();

        //Step 1
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 2-3
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        Shelf shelfFirst = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelfFirst);
        draftModuleTab.clickSave();
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "There are error after saving with out required fields", "Error message is not present", webDriver);

        //Step 4
        ManageRevisionsTab revisionsTab = draftModuleTab.clickManageRevisionTab();

        //Step 5
        softAssert.assertTrue(revisionsTab.isViewButtonVisible(shelfFirst), "The view button is not visible for shelf: " + shelfFirst.getTitle(), "The view button is visible", webDriver);
        softAssert.assertFalse(revisionsTab.isDeleteButtonVisible(shelfFirst), "The Delete button is not visible for shelf: " + shelfFirst.getTitle(), "The Delete button is visible", webDriver);
        softAssert.assertTrue(revisionsTab.isEditButtonVisible(shelfFirst), "The Edit button is not visible for shelf: " + shelfFirst.getTitle(), "The Edit button is visible", webDriver);
        softAssert.assertFalse(revisionsTab.isRevertButtonVisible(shelfFirst), "The Revert button is present for the current revision of the shelf: " + shelfFirst.getTitle(), "The Revert button is not visible for current revision", webDriver);

        //Step 6
        draftModuleTab = revisionsTab.clickEditButton(shelfFirst);
        Shelf shelfUpdated = EntityFactory.getShelfsList().get(1);
        draftModuleTab.createShelf(shelfUpdated);

        //Step 7
        draftModuleTab.clickSave();
        revisionsTab = draftModuleTab.clickManageRevisionTab();
        softAssert.assertTrue(revisionsTab.isViewButtonVisible(shelfFirst), "The view button is not visible for shelf: " + shelfFirst.getTitle(), "The view button is visible", webDriver);
        softAssert.assertTrue(revisionsTab.isDeleteButtonVisible(shelfFirst), "The Delete button is not visible for shelf: " + shelfFirst.getTitle(), "The Delete button is visible", webDriver);
        softAssert.assertTrue(revisionsTab.isEditButtonVisible(shelfFirst), "The Edit button is not visible for shelf: " + shelfFirst.getTitle(), "The Edit button is visible", webDriver);
        softAssert.assertTrue(revisionsTab.isRevertButtonVisible(shelfFirst), "The Revert button is present for the current revision of the shelf: " + shelfFirst.getTitle(), "The Revert button is not visible for current revision", webDriver);
        ;

        //Step 7.1 checking new draft revision
        softAssert.assertTrue(revisionsTab.isViewButtonVisible(shelfUpdated), "The view button is not visible for current shelf revision: " + shelfUpdated.getTitle(), "The view button is visible for current shelf revision", webDriver);
        softAssert.assertFalse(revisionsTab.isDeleteButtonVisible(shelfUpdated), "The Delete button is visible for current shelf revision: " + shelfUpdated.getTitle(), "The Delete button is not visible for current shelf revision", webDriver);
        softAssert.assertTrue(revisionsTab.isEditButtonVisible(shelfUpdated), "The Edit button is not visible for current shelf revision: " + shelfUpdated.getTitle(), "The Edit button is visible for current shelf revision", webDriver);
        softAssert.assertFalse(revisionsTab.isRevertButtonVisible(shelfUpdated), "The Revert button is present for the current revision of the shelf: " + shelfUpdated.getTitle(), "The Revert button is not visible for current revision", webDriver);
        ;
        softAssert.assertAll();

    }
}
