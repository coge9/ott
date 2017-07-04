package com.nbcuni.test.cms.tests.smoke.tvecms.shelfmanager;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 17-Sep-15.
 */
public class TC9764_AdminCouldChangeShelfOrder extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private AddNewPage addNewPage;
    private PageForm text;
    private TVEPage tvePage;
    private DraftModuleTab draftModuleTab;

    /**
     * Pre-Conditions:
     * 1. Create OTT Page
     * 2. Create 2 Shelf (Shelf 1/Shelf 2)
     * 3. add test shelves to test OTT page
     *
     * Test case:
     * Step 1: go to Roku CMS as Admin
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


    @Test(groups = {"roku_page"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = false)
    public void checkThatAdminCouldChangeShelfOrder(final String brand) {
        Utilities.logInfoMessage("Check that Admin is able to add change shelves' order on OTT page");
        SoftAssert softAssert = new SoftAssert();

        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        tvePage = mainRokuAdminPage.openOttPage(brand);

        //Pre-Conditions Step 1
        addNewPage = tvePage.clickAddNewPage();

        text = addNewPage.setRequiredFieldsForNewPageAndSave();
        WaitUtils.perform(webDriver).waitForPageLoad();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present", webDriver);

        //Step 2
        Shelf shelf1 = EntityFactory.getShelfsList().get(0);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf1);

        Shelf shelf2 = EntityFactory.getShelfsList().get(1);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf2);

        //Step 3
        tvePage = mainRokuAdminPage.openOttPage(brand);
        EditPageWithPanelizer addNewPage = tvePage.clickEdit(text.getTitle());

        //TODO implement new drog and drop functionality for new UI
        /*List<OttModuleForm> ottModuleForms = new LinkedList<>();
        ottModuleForms.add(CreateFactoryOttModule.createOttModuleWithCustomTitle(shelf1.getTitle()));
        ottModuleForms.add(CreateFactoryOttModule.createOttModuleWithCustomTitle(shelf2.getTitle()));
        addNewPage.typeOTTModules(ottModuleForms);
        addNewPage.submit();
        softAssert.assertTrue(addNewPage.isStatusMessageShown(), "Success message isn't present",
                "Success message is present", webDriver);

        // Test Steps:Step 1-3
        ottPage = mainRokuAdminPage.openOttPage(brand);
        addNewPage = ottPage.clickEdit(text.getTitle());
        softAssert.assertTrue(addNewPage.isOTTModule(shelf1.getTitle()), "Shelf isn't added in list",
                "Shelf is added in list", webDriver);

        //Step 4
        List<OttModuleBlock>ottModuleBlocks = addNewPage.getOTTModulesList();
        ActionsUtil.perform(webDriver).dragAndDrop(ottModuleBlocks.get(0).getShelfDragDrop(), ottModuleBlocks.get(1).getShelfDragDrop());
        softAssert.assertTrue(mainRokuAdminPage.isWarningMessagePresent(),"The warning message is not present after drag and drop","Warning message is present",webDriver);

        //Step 5
        addNewPage.submit();
        softAssert.assertTrue(addNewPage.isStatusMessageShown(), "Success message isn't present",
                "Success message isn't present", webDriver);

        //Step 6
        ottPage = mainRokuAdminPage.openOttPage(brand);
        addNewPage = ottPage.clickEdit(text.getTitle());
        ottModuleBlocks = addNewPage.getOTTModulesList();
        softAssert.assertContains(ottModuleBlocks.get(0).getShelfData().getTitle(), shelf2.getTitle(),
                        "The shelf number 1 is not contain second shelf, seem like order wasn't changed",
                        "The order was changed correctly, second shelf displayed as first");
        softAssert.assertContains(ottModuleBlocks.get(1).getShelfData().getTitle(),shelf1.getTitle(),"The shelf number 2 is not contain first shelf, seem like order wasn't changed",
                "The order was changed correctly, first shelf displayed as second one");*/
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage() {
        try {
            TVEPage TVEPage = mainRokuAdminPage.openOttPage(brand);
            TVEPage.clickDelete(text.getTitle()).clickSubmit();
        } catch (Exception e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
