package com.nbcuni.test.cms.tests.backend.tvecms.modulepagemanager;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
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
 * Created by Ivan_Karnilau on 14-Sep-15.
 */
public class TC9724_AdminIsAbleToAddFixedShelfToOTTPage extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageForm = CreateFactoryPage.createDefaultPage().setPlatform(CmsPlatforms.IOS);
    private DraftModuleTab draftModuleTab;
    private Shelf shelf;
    private EditPageWithPanelizer editPage;

    /**
     * Pre-Conditions:
     * 1. Create OTT Page
     * 2. Create Shelf
     *
     * Test case:
     * Step 1: go to Roku CMS as Admin
     * Verify: Admin menu is appeared
     * <p/>
     * Step2: go to /admin/ott/pages
     * Verify: "Pages" menu is appeared
     * <p/>
     * Step 3: click om "Edit" next to test page's label
     * Verify: "Edit [pagename]" page is opened
     * <p/>
     * Step 4: go to "OTT MODULES" block
     * click on empty "OTT Module" autocomplete field
     * start to enter test shelf's title
     * Verify: OTT Module" autocomplete field is offering all appropriate shelfs
     * <>p</>
     * Step 5: click on test shelf's name in offered list
     * Verify: test ahelf is added
     * <>p</>
     * Step 6: set "Locked" checkbox as checked
     * click on " Save OTT page" button
     * Verify: user friendly message is displayed, OTT page is saved
     * <>p</>
     * Step 7: check that fixed shelf is added
     * Verify: fixed shelf is added
     */


    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatAdminIsAbleToAddLockedShelfToPage(final String brand) {
        Utilities.logInfoMessage("Check that Admin is able to add locked shelf to page");
        SoftAssert softAssert = new SoftAssert();

//      Pre-Conditions
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 1
        rokuBackEndLayer.createPage(pageForm);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present");

//      Step 2
        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);

//      Step 3
        editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());

//      Step 4-6
        editPage.setModule(shelf.getTitle());
        editPage.save();
        editPage.getModuleBlock(shelf.getTitle()).changeBlock().lockModule();
        editPage.save();
        softAssert.assertTrue(editPage.isStatusMessageShown(), "Success message isn't present",
                "Success message isn't present");

//      Step 7
        softAssert.assertTrue(editPage.getModuleBlock(shelf.getTitle()).isModuleLock(), "The module is not locked",
                "The module is locked");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9724() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("There is an error during page deletion");
        }

        Utilities.logInfoMessage("The page and shelf were delete successfully");
    }
}
