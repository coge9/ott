package com.nbcuni.test.cms.tests.backend.tvecms.modulepagemanager.referencetoapp;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import org.testng.annotations.Test;

import java.util.Arrays;


/**
 *
 * @author Aliaksei_Dzmitrenka 12/17/2015
 * -= Precondition =-
 * Create shelf
 * Create Page with one app
 * Create Page with another app
 *
 * -= Step =-
 *    1)Add Shelf to first page
 *    2)Make sure that shelf added
 *    3)Add Shelf to second page
 *  4)Make sure that shelf added
 */

public class TC11530_OneShelfForSeveralPagesWithDifferentApp extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm firstPageForm = CreateFactoryPage.createDefaultPageWithAlias();
    private PageForm secondPageForm = CreateFactoryPage.createDefaultPageWithAlias();
    private Shelf shelf = null;


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
        mainRokuAdminPage.openOttPage(brand).clickEdit(secondPageForm.getTitle());
        addNewPage.setModule(shelf.getTitle());
        addNewPage.save();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present", webDriver);
        softAssert.assertAll();
    }

    private void creation() {
        shelf = EntityFactory.getShelfsList().get(0);
        DraftModuleTab addShelfOttModulePage = mainRokuAdminPage.openAddShelfPage(brand);
        addShelfOttModulePage.createShelf(shelf);
        firstPageForm.setModules(Arrays.<Module>asList(shelf));
        secondPageForm.setModules(Arrays.<Module>asList(shelf));
        rokuBackEndLayer.createPage(firstPageForm);
        rokuBackEndLayer.createPage(secondPageForm);
    }


}
