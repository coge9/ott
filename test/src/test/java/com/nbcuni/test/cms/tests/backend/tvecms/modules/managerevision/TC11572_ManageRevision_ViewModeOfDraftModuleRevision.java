package com.nbcuni.test.cms.tests.backend.tvecms.modules.managerevision;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.ManageRevisionsTab;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.ViewPublishTab;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 12/18/15.
 */
public class TC11572_ManageRevision_ViewModeOfDraftModuleRevision extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private ModulesPage modulePage;
    private AddNewPage addNewPage;
    private PageForm pageInfo;
    private TVEPage tvePage;

    /**
     * Step 1: Go to CMS as admin
     * Verify: Admin panel is present
     * <p/>
     * Step 2. Go To Module list Dashboard » Modules
     * Verify: The list of shelves is present
     * <p/>
     * Step 3. Create a shelf with published asset 1
     * Verify: Shelf is created
     * <p/>
     * Step 4. Click on tab 'Manage Revision' and check elements
     * Verify: The Manage revision tab is rendered
     * The next elements are present:
     * -Table with list of revisions per module is present
     * -The Draft revision has View option
     * -The Draft revision has no Revert option as current
     * -The Draft revision has no Delete option as current
     * -The Draft revision has Edit option
     * <p/>
     * Step 5. Click on link 'View'
     * Verify: The View published tab is present with information about asset1
     */

    @Test(groups = "module_revision", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkViewModeOfDraftModuleRevision(final String brand) {
        Utilities.logInfoMessage("Check that View Mode Of Draft Module's revision ");
        SoftAssert softAssert = new SoftAssert();

        //Step 1
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 2
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);

        //Step 3
        Shelf shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelf, PublishState.PUBLISHED);
        draftModuleTab.clickSave();
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "There are error after saving with out required fields", "Error message is not present", webDriver);

        //Step 4
        ManageRevisionsTab revisionsTab = draftModuleTab.clickManageRevisionTab();
        softAssert.assertTrue(revisionsTab.isViewButtonVisible(shelf), "The view button is not visible for shelf: " + shelf.getTitle(), "The view button is visible");
        softAssert.assertFalse(revisionsTab.isDeleteButtonVisible(shelf), "The Delete button is visible or the current revision of the shelf: " + shelf.getTitle(), "The Delete button is not visible for current revision");
        softAssert.assertTrue(revisionsTab.isEditButtonVisible(shelf), "The Edit button is not visible for shelf: " + shelf.getTitle(), "The Edit button is visible");
        softAssert.assertFalse(revisionsTab.isRevertButtonVisible(shelf), "The Revert button is present for the current revision of the shelf: " + shelf.getTitle(), "The Revert button is not visible for current revision");

        //Step 5
        ViewPublishTab viewPublishTab = revisionsTab.clickViewButton(shelf);
        softAssert.assertEquals(shelf.getAssets(), viewPublishTab.getAssets(), "The list of assets is not matched within View Published: " + viewPublishTab.getAssets()
                        + " tab table with those that was set: " + shelf.getAssets(),
                "The list of assets within ViePublish tab table is correct", webDriver);
        softAssert.assertTrue(viewPublishTab.verifyPage().isEmpty(), "Some items is missed from page");
        softAssert.assertAll();

    }
}
