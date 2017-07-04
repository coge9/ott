package com.nbcuni.test.cms.tests.backend.tvecms.modulepagemanager;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.people.PeoplePage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.ottmodule.OttModuleForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.ottmodule.factory.CreateFactoryOttModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.Role;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alena_Aukhukova on 9/14/2015.
 */
public class TC9735_CheckEditorIsNotAbleToRedesignateShelfLocked extends BaseAuthFlowTest {
    private static final String PAGE_TITLE = "AQAPage" + SimpleUtils.getRandomString(2);
    private static String SHELF_TITLE = "AQA_Shelf" + SimpleUtils.getRandomString(2);
    private static String TYPE = SimpleUtils.getSpecialCharacters(5);
    /**
     * TC9735     Roku CMS: Editor is not able to re-designate shelf's "locked" property
     *
     * Step 1: Log in to admin site as admin
     * Verify: Status Message is shown
     *
     * Step 2: Create shelf module with 'shelf name'
     *
     * Step 3: Create OTT page with all required fields with 'ott page name' and add Ott Module 'shelf name'
     * Verify: The status message is shown
     *
     * Step 4: Verify permission for editor
     * Verify: Status for editor is 'active'. Verify role is 'editor'
     *
     * Step 5: Login as editor
     *
     * Step 6: Edit page 'ott page name'
     * Verify: Locked block for Ott Module is disabled
     */

    private MainRokuAdminPage mainRokuAdminPage;
    private TVEPage tvePage;
    private AddNewPage addNewPage;
    private DraftModuleTab draftModuleTab;
    private RokuBackEndLayer backEndLayer;
    private String pageTitle;

    private PeoplePage peoplePage;

    @Test(groups = {"shelf_manager"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkTc9735Test(final String brand) {
        this.brand = brand;
        SoftAssert softAssert = new SoftAssert();
        //Step 1: Log in to admin site as admin
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        //Step 2: Create shelf module with 'shelf name'
        createNewShelf(SHELF_TITLE);
        //Step 3: Create OTT page with all requared fields with 'ott page name' and add Ott Module 'shelf name'
        createNewOttPage(softAssert);
        //Step 4: Verify permission for editor
        checkEditorPermission(softAssert);
        //Step 5: Login as editor
        mainRokuAdminPage.logOut(brand);
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();
        //Step 6: Edit page 'ott page name'
        tvePage = mainRokuAdminPage.openOttPage(brand);
        EditPageWithPanelizer editPage = tvePage.clickEdit(pageTitle);
        //TODO update tests case due the LOCK/Unlock functionality from new edit page
        softAssert.assertFalse(editPage.getModulesName().contains(SHELF_TITLE), "Locked block is enabled", webDriver);
        softAssert.assertAll();
    }

    private void createNewShelf(String shelfTitle) {
        Shelf shelf = new Shelf();
        shelf.setTitle(shelfTitle);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
    }

    /**
     * Create OTT page with all requared fields with ''
     * @param softAssert
     */
    private void createNewOttPage(SoftAssert softAssert) {
        tvePage = mainRokuAdminPage.openOttPage(brand);
        addNewPage = tvePage.clickAddNewPage();
        List<OttModuleForm> ottModuleForms = new LinkedList<>();
        ottModuleForms.add(CreateFactoryOttModule.createOttModule(SHELF_TITLE, true, true));
        addNewPage.setAllRequiredFields();
        pageTitle = addNewPage.getTitle();
        addNewPage.submit();
        WaitUtils.perform(webDriver).waitForPageLoad();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present");
    }

    /**
     * Method Verify permission for editor
     * @param softAssert
     */
    private void checkEditorPermission(SoftAssert softAssert) {
        peoplePage = mainRokuAdminPage.openPeoplePage(brand);
        String editorName = Config.getInstance().getProperty("roku.editor.name");
        //Verify status for editor is 'active'
        softAssert.assertEquals(Role.EDITOR.getStatus(), peoplePage.getUserStatus(editorName), "Editor status", webDriver);
        //Verify only one role. Verify role is 'editor'
        List<String> editorRoles = peoplePage.getUserRoles(editorName);
        softAssert.assertEquals(Role.EDITOR.getRoles().size(), editorRoles.size(), "Editor roles count", webDriver);
        String expectedEditorRole = Role.EDITOR.getRoles().get(0);
        softAssert.assertEquals(expectedEditorRole, editorRoles.get(0), "Editor roles count", webDriver);
    }

    @AfterTest
    public void deleteOttPageAndShelf() {
        mainRokuAdminPage = backEndLayer.openAdminPage();
        tvePage = mainRokuAdminPage.openOttPage(brand);
        tvePage.clickDelete(pageTitle);
        //TODO delete shelf
    }

}








