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
public class TC11570_ManageRevision_DeleteDraftRevision extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;

    /**
     * Pre-Condition:
     * Create a shelf
     * <p/>
     * Step 1: Go to CMS as admin
     * Verify: Admin panel is present
     * <p/>
     * Step 2. Go To Module list Dashboard » Modules
     * Verify: The list of shelves is present
     * <p/>
     * Step 3. Select Module created in precondition on edit
     * Verify: The 'Edit Draft' tab is present. There is tab 'Manage Revision'
     * <p/>
     * Step 4. Update shelf and Save
     * Verify: The shelf is saved with new revision in Draft
     * <p/>
     * Step 5. Click on tab 'Manage Revision' and check elements
     * Verify: The Manage revision tab is rendered
     * The next elements are present:
     * -Table with list of revisions per module is present
     * -There are 2 Draft revisions
     * <p/>
     * Step 6: Check options available for the first Draft Revision
     * Verify: There are : View, Edit Draft, Delete,Revert
     * <p/>
     * Step 7: Check options available for the current Draft Revision
     * Verify: There are : View, Edit Draft
     * <p/>
     * Step 8: Click 'Delete' link next to First Draft Revision
     * Verify: The revision is deleted
     * Current revision is displayed
     */

    @Test(groups = "module_revision", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkAbilityToDeleteDraftModuleRevision(final String brand) {
        Utilities.logInfoMessage("Check that user could Delete module's revision in draft");
        SoftAssert softAssert = new SoftAssert();

        //Step 1
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 2-3
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        Shelf shelfDraftFirst = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelfDraftFirst);
        draftModuleTab.clickSave();
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "There are error after saving with out required fields", "Error message is not present", webDriver);

        //step 4
        Shelf shelfDraftSecond = EntityFactory.getShelfsList().get(1);
        draftModuleTab.createShelf(shelfDraftFirst);
        draftModuleTab.clickSave();
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "There are error after saving with out required fields", "Error message is not present", webDriver);

        //Step 5
        ManageRevisionsTab revisionsTab = draftModuleTab.clickManageRevisionTab();

        //Step 6 Checking some Old Draft revision's modes
        softAssert.assertTrue(revisionsTab.isViewButtonVisible(shelfDraftFirst), "The view button is not visible for shelf: " + shelfDraftFirst.getTitle(), "The view button is visible", webDriver);
        softAssert.assertTrue(revisionsTab.isDeleteButtonVisible(shelfDraftFirst), "The Delete button is not visible for shelf: " + shelfDraftFirst.getTitle(), "The Delete button is visible", webDriver);
        softAssert.assertTrue(revisionsTab.isEditButtonVisible(shelfDraftFirst), "The Edit button is not visible for shelf: " + shelfDraftFirst.getTitle(), "The Edit button is visible", webDriver);
        softAssert.assertTrue(revisionsTab.isRevertButtonVisible(shelfDraftFirst), "The Revert button is not present for the Draft revision of the shelf: " + shelfDraftFirst.getTitle(), "The Revert button is visible for Draft revision", webDriver);
        String idRevision = revisionsTab.getIdRevision(shelfDraftFirst);

        //Step 7 Checking current revsion's modes
        softAssert.assertTrue(revisionsTab.isViewButtonVisible(shelfDraftSecond), "The view button is not visible for Current shelf revision: " + shelfDraftSecond.getTitle(), "The view button is visible for Current Draft revision", webDriver);
        softAssert.assertFalse(revisionsTab.isDeleteButtonVisible(shelfDraftSecond), "The Delete button is visible for  Current shelf revision: " + shelfDraftSecond.getTitle(), "The Delete button is not visible for Current Draft revision", webDriver);
        softAssert.assertTrue(revisionsTab.isEditButtonVisible(shelfDraftSecond), "The Edit button is not visible for  Current shelf revision: " + shelfDraftSecond.getTitle(), "The Edit button is visible for Current Draft revision", webDriver);
        softAssert.assertFalse(revisionsTab.isRevertButtonVisible(shelfDraftSecond), "The Revert button is present for the Current shelf revision: " + shelfDraftSecond.getTitle(), "The Revert button is not visible for Current Draft revision", webDriver);

        //Step 8
        revisionsTab.clickDeleteButton(shelfDraftFirst);
        softAssert.assertTrue(revisionsTab.isShelfRevisionPresentByID(idRevision, shelfDraftFirst), "The Shelf revision is still present", "The revision: " + idRevision + " is deleted");

        //Step 8.2 Check that Current revision is stay with the same modes
        softAssert.assertTrue(revisionsTab.isViewButtonVisible(shelfDraftSecond), "The view button is not visible for Current shelf revision: " + shelfDraftSecond.getTitle(), "The view button is visible for Current Draft revision", webDriver);
        softAssert.assertFalse(revisionsTab.isDeleteButtonVisible(shelfDraftSecond), "The Delete button is visible for  Current shelf revision: " + shelfDraftSecond.getTitle(), "The Delete button is not visible for Current Draft revision", webDriver);
        softAssert.assertTrue(revisionsTab.isEditButtonVisible(shelfDraftSecond), "The Edit button is not visible for  Current shelf revision: " + shelfDraftSecond.getTitle(), "The Edit button is visible for Current Draft revision", webDriver);
        softAssert.assertFalse(revisionsTab.isRevertButtonVisible(shelfDraftSecond), "The Revert button is present for the Current shelf revision: " + shelfDraftSecond.getTitle(), "The Revert button is not visible for Current Draft revision", webDriver);

        softAssert.assertAll();
    }
}
