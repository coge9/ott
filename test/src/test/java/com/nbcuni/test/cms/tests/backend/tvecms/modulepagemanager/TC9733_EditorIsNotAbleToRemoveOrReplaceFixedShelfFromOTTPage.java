package com.nbcuni.test.cms.tests.backend.tvecms.modulepagemanager;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.block.PanelizerContentBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 17-Sep-15.
 */
public class TC9733_EditorIsNotAbleToRemoveOrReplaceFixedShelfFromOTTPage extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageForm = CreateFactoryPage.createDefaultPage().setPlatform(CmsPlatforms.IOS);
    private DraftModuleTab draftModuleTab;
    private Shelf shelf;
    private EditPageWithPanelizer editPage;

    /**
     * Pre-Conditions:
     * 1. Create OTT Page
     * 2. Create Shelf
     * 3. Add Shelf as fixed to page
     *
     * Test case:
     * Step 1: go to Roku CMS as editor
     * Verify: editor menu is appeared
     * <p/>
     * Step2: go to /admin/ott/pages
     * Verify: "Pages" menu is appeared
     * <p/>
     * Step 3: click om "Edit" next to test page's label
     * Verify: "Edit [pagename]" page is opened
     * <p/>
     * Step 4: go to "OTT MODULES" block
     * make sure that there is fixed shelf in "OTT MODULES" list
     * Verify: fixed shelf is added in list
     * <>p</>
     * Step 5: try to remove fixed shelf's name from autocomplete field
     * Verify: OTT Module" autocomplete field is disabled - Editor is not able to remove or replace fixed shelf
     */


    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkEditorCouldNotRemoveLockedModule(final String brand) {
        Utilities.logInfoMessage("Check that Admin is able to add locked shelf to page");
        SoftAssert softAssert = new SoftAssert();

//      Pre-Conditions
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 1
        rokuBackEndLayer.createPage(pageForm);

//      Step 2
        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);

//      Step 3
        editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        editPage.setModule(shelf.getTitle());
        editPage.getModuleBlock(shelf.getTitle()).changeBlock().lockModule();

        //step 4
        softAssert.assertTrue(editPage.getModuleBlock(shelf.getTitle()).isModuleLock(), "The module is not locked",
                "The module is locked");
        editPage.save();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present");
        mainRokuAdminPage.logOut(brand);

        //step 5
        rokuBackEndLayer.openAdminPageAsEditor();

        //Step 6
        editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        PanelizerContentBlock module = editPage.getModuleBlock(shelf.getTitle());
        softAssert.assertFalse(module.isLockingBlockPresent(),
                "The Lock/Unlock functionality is visible for editor",
                "The Lock/Unlock functionality is not visible for editor", webDriver);

        mainRokuAdminPage.logOut(brand);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9733() {
        try {
            rokuBackEndLayer.openAdminPage();
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("There is an error during page deletion");
        }

        Utilities.logInfoMessage("The page and shelf were delete successfully");
    }
}
