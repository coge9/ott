package com.nbcuni.test.cms.tests.backend.tvecms.modulepagemanager;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.people.PeoplePage;
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

import java.util.List;

/**
 * Created by Alena_Aukhukova on 9/14/2015.
 */
public class TC9732_CheckEditorIsNotAbleToAddFixedShelf extends BaseAuthFlowTest {
    private static String PAGE_TITLE;
    private static String SHELF_FIXED_TITLE = "AQA_Shelf_" + SimpleUtils.getRandomString(2);
    private static String SHELF_CUSTOM_TITLE = "AQA_Shelf_" + SimpleUtils.getRandomString(2);
    /**
     * TC9732     Roku CMS: Editor is not able to add fixed shelf, but can add custom one
     *
     *
     * Step 1: Log in to admin site as admin
     *
     * Step 2: Create shelf modules (fixed and custom) with 'shelf name'
     *
     * Step 3: Create OTT page with all required fields
     *
     * Step 4: Verify permission for editor
     *
     * Step 5: Login as editor
     *
     * Step 6: Open OTT page for edit
     *
     * Step 7: Add fixed shelf
     * Verify: Editor is not able to add fixed shelf
     *
     * Step 8: Add custom shelf
     * Verify:  Editor  can add custom shelf
     *
     */

    private MainRokuAdminPage mainRokuAdminPage;
    private TVEPage tvePage;
    private AddNewPage addNewPage;
    private DraftModuleTab draftModuleTab;
    private RokuBackEndLayer backEndLayer;
    private PeoplePage peoplePage;

    @Test(groups = {"shelf_manager"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkTc9732Test(final String brand) {
        this.brand = brand;
        SoftAssert softAssert = new SoftAssert();
        //Step 1: Log in to admin site as admin
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        //Step 2: Create shelf modules (fixed and custom) with 'shelf name'
        createNewShelf(SHELF_FIXED_TITLE);
        createNewShelf(SHELF_CUSTOM_TITLE);
        //Step 3:  Create OTT page with all requared fields
        createNewOttPage(softAssert);
        //Step 4: Verify permission for editor
        checkEditorPermission(softAssert);
        //Step 5: Login as editor
        mainRokuAdminPage.logOut(brand);
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();
        //Step 6: Edit page 'ott page name'
        tvePage = mainRokuAdminPage.openOttPage(brand);
        EditPageWithPanelizer addNewPage = tvePage.clickEdit(PAGE_TITLE);

        //Step 7: Clear input for 'shelf name'
        //TODO step 7,8
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
        PAGE_TITLE = addNewPage.setRequiredFieldsForNewPageAndSave().getTitle();
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
        tvePage.clickDelete(PAGE_TITLE);
        //TODO delete 2 shelf
    }

}








