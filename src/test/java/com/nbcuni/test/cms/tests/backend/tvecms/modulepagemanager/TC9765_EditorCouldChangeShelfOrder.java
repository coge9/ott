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

import java.util.Arrays;

/**
 * Created by Ivan_Karnilau on 17-Sep-15.
 */
public class TC9765_EditorCouldChangeShelfOrder extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private EditPageWithPanelizer editPage;
    private PageForm pageForm;
    private Shelf shelf1;
    private Shelf shelf2;
    private DraftModuleTab draftModuleTab;

    /**
     * Pre-Conditions:
     * 1. Create OTT Page
     * 2. Create 2 Shelf (Shelf 1/Shelf 2)
     * 3. add test shelves to test OTT page
     *
     * Test case:
     * Step 1: go to Roku CMS as Editor
     * Verify: Admin menu is appeared
     * <p/>
     * Step2: go to /admin/ott/pages
     * Verify: "Pages" menu is appeared
     * <p/>
     * Step 3: click on "Edit" next to test page's label
     * Verify: "Edit [pagename]" page is opened
     * <p/>
     * Step 4: go to "OTT MODULES" block
     * change shelves's order with drag/drop functionality
     * Verify: shelves order is changed
     * <>p</>
     * Step 5: Click on " Save OTT page" button
     * Verify:  User friendly message is displayed
     * OTT page is saved
     * <>p</>
     * Step 6: check shelves' order
     * Verify: order is changed accordingly to step 4
     * <>p</>
     */


    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkThatEditorCouldChangeShelfOrder(final String brand) {
        Utilities.logInfoMessage("Check that Admin is able to add change shelves' order on OTT page");
        SoftAssert softAssert = new SoftAssert();

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        shelf1 = EntityFactory.getShelfsList().get(0);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf1);

        shelf2 = EntityFactory.getShelfsList().get(1);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf2);

        //Step 3
        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(CmsPlatforms.IOS);
        rokuBackEndLayer.createPage(pageForm);
        editPage = new EditPageWithPanelizer(webDriver, aid);
        editPage.setModules(Arrays.asList(shelf1, shelf2)).save();
        softAssert.assertTrue(editPage.isStatusMessageShown(), "Success message isn't present",
                "Success message is present", webDriver);

        mainRokuAdminPage.logOut(brand);
        // Test Steps:Step 1-2
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        // Step 3
        rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        softAssert.assertTrue(editPage.getModulesName().contains(shelf1.getTitle()), "Shelf isn't added in list",
                "Shelf is added in list", webDriver);

        //Step 4
        //TODO implement drag and drop for new edit page UI
        mainRokuAdminPage.logOut(brand);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }


    @AfterMethod(alwaysRun = true)
    private void deletePageShelves() {
        try {
            rokuBackEndLayer.openAdminPage();
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            rokuBackEndLayer.deleteModule(shelf1.getTitle());
            rokuBackEndLayer.deleteModule(shelf2.getTitle());
        } catch (Exception e) {
            Utilities.logInfoMessage("Couldn't delete the objects by reason: " + e.getMessage());
        }
    }
}
