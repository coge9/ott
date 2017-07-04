package com.nbcuni.test.cms.tests.backend.tvecms.modulepagemanager;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.ModuleAddPage;
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
public class TC9752_EditorCouldNotAddFixedSameShelfTwice extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private EditPageWithPanelizer editPage;
    private PageForm pageForm = CreateFactoryPage.createDefaultPage().setPlatform(CmsPlatforms.IOS);
    private Shelf shelf1;
    private DraftModuleTab draftModuleTab;

    /**
     * Pre-Conditions:
     * 1. Create OTT Page
     * 2. Create 2 Shelf (Shelf 1/Shelf 2)
     * 3. Add Shelf 1 as fixed to page
     *
     * Test case:
     * Step 1: go to Roku CMS as editor
     * Verify: Editor menu is appeared
     * <p/>
     * Step2: go to /admin/ott/pages
     * Verify: "Pages" menu is appeared
     * <p/>
     * Step 3: click om "Edit" next to test page's label
     * Verify: "Edit [pagename]" page is opened
     * <p/>
     * Step 4: go to "OTT MODULES" block
     * make sure that there is fixed shelf_1 in "OTT MODULES" list
     * Verify: fixed shelf is added in list
     * <>p</>
     * Step 5: Click on empty "OTT Module" autocomplete field
     * start to enter shelf_1 title
     * Verify:  OTT Module" autocomplete field is offering shelf_2
     * OTT Module" autocomplete field is NOT offering shelf_1
     * <>p</>
     * Step 6: click on shelf_2 shelf's name in offered list
     * Verify: shelf_2 is added
     * <>p</>
     * Step 7: Click on " Save OTT page" button
     * Verify: OTT page is saved
     * <>p</>
     * Step 8: Check that shelf_2 is added
     * Verify: shelf_2 is added
     * <>p</>
     */


    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkThatEditorCouldNotAddFixedShelfTwice(final String brand) {
        Utilities.logInfoMessage("Check that Admin cannot add shelf which already added as fixed");
        SoftAssert softAssert = new SoftAssert();

        //Pre-Conditions Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createPage(pageForm);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present", webDriver);
        mainRokuAdminPage.logOut(brand);
        rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2
        shelf1 = EntityFactory.getShelfsList().get(0);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf1);

        //Step 3
        editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        editPage.setModule(shelf1.getTitle()).save();

        softAssert.assertTrue(editPage.isStatusMessageShown(), "Success message isn't present",
                "Success message is present", webDriver);

//      Step 4
        softAssert.assertTrue(editPage.getModulesName().contains(shelf1.getTitle()), "Shelf isn't added in list",
                "Shelf is added in list", webDriver);

//      Step 5
        ModuleAddPage moduleAddPage = editPage.getModuleInfoBlocks().get(0).editBlock();
        boolean status = moduleAddPage.isModuleExistWithinList(shelf1.getTitle());
        softAssert.assertFalse(status, "It's possible to set more than 1 same shelf to the page", "It's impossible to set more than 1 same shelf to a page", webDriver);

        moduleAddPage.clickCancel();
        editPage.save();
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9752() {
        try {
            rokuBackEndLayer.openAdminPage();
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            rokuBackEndLayer.deleteModule(shelf1.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("There is an error during page nad module deletion");
        }

        Utilities.logInfoMessage("The page and shelf were delete successfully");
    }
}
