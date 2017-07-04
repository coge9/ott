package com.nbcuni.test.cms.tests.backend.tvecms.modulepagemanager.referencetoapp;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import org.testng.annotations.Test;


/**
 *
 * @author Aliaksei_Dzmitrenka 12/17/2015
 * -= Precondition =-
 * Create shelf
 * Create Page with
 *
 * -= Step =-
 *    1)Add Shelf to page
 *    2)Open shelf
 *    3)Make sure that assignment info about relations to app does not present any more
 */

public class TC11532_AssignmentInfoDoesNotPresentAnyMore extends BaseAuthFlowTest {

    private PageForm firstPageForm = CreateFactoryPage.createDefaultPageWithAlias();
    private String app = "Roku";
    private Shelf shelf;


    @Test(groups = {"shelf_management_admin", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9635asAdmin(final String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        creation();
        tc11530logic();
    }

    @Test(groups = {"shelf_management_editor", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9635asEditor(final String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        creation();
        mainRokuAdminPage.logOut(brand);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();
        tc11530logic();
    }

    private void tc11530logic() {
        SoftAssert softAssert = new SoftAssert();
        EditPageWithPanelizer addNewPage = mainRokuAdminPage.openOttPage(brand).clickEdit(firstPageForm.getTitle());
        addNewPage.setModule(shelf.getTitle());
        addNewPage.save();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present", webDriver);
        ModulesPage modulePage = mainRokuAdminPage.openOttModulesPage(brand);
        softAssert.assertFalse(modulePage.clickEditLink(shelf.getTitle()).isAssignmentPresent(), "Assignment info still present", "Assignment is not present", webDriver);
        softAssert.assertAll();
    }

    private void creation() {
        shelf = EntityFactory.getShelfsList().get(0);
        DraftModuleTab addShelfOttModulePage = mainRokuAdminPage.openAddShelfPage(brand);
        addShelfOttModulePage.createShelf(shelf);
        rokuBackEndLayer.createPage(firstPageForm);
    }


}
