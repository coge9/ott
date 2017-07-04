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
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Ivan_Karnilau on 17-Sep-15.
 */
public class TC9736_AdminIsAbleToRedesignateShelfsLockedProperty extends BaseAuthFlowTest {

    private static String FIXED_SHELF_TITLE = "AQA_Fixed_Shelf" + SimpleUtils.getRandomString(2);
    private static String CUSTOM_SHELF_TITLE = "AQA_Custom_Shelf" + SimpleUtils.getRandomString(2);
    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageForm;
    private DraftModuleTab draftModuleTab;

    /**
     * Pre-Conditions:
     * 1. Create OTT Page
     * 2. Create 2 Shelf
     * 3. Add Shelf as fixed to page
     * 4. Add Shelf as custom to page
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
     * make sure that there is fixed shelf in "OTT MODULES" list
     * Verify: fixed shelf is added in list
     * <>p</>
     * Step 5: find fixed shelf ("Locked" checkbox is checked)
     * uncheck the checkbox
     * Verify: "Locked" checkbox is unchecked - Editor is able to re-designate shelf's "locked" property
     * <>p</>
     * Step 6: find custom shelf ("Locked" checkbox is unchecked)
     * check the checkbox
     * Verify: "Locked" checkbox is checked - Editor is able to re-designate shelf's "locked" property
     */


    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatAdminIsAbleToReplaceFixedShelfOnOTTPage(final String brand) {
        Utilities.logInfoMessage("Check that Admin is able to replace fixed shelf on OTT page");
        SoftAssert softAssert = new SoftAssert();


//      Pre-Conditions
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 1
        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(CmsPlatforms.IOS);

//      Step 2
        Shelf fixedShelf = new Shelf();
        fixedShelf.setTitle(FIXED_SHELF_TITLE);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(fixedShelf);

        Shelf customShelf = new Shelf();
        customShelf.setTitle(CUSTOM_SHELF_TITLE);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(customShelf);

        //Step 3
        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(CmsPlatforms.IOS);
        rokuBackEndLayer.createPage(pageForm);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "Success message isn't present",
                "Success message isn't present", webDriver);

        EditPageWithPanelizer editPage = new EditPageWithPanelizer(webDriver, aid);
        editPage.setModules(Arrays.asList(fixedShelf, customShelf)).save();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "Success message isn't present",
                "Success message isn't present", webDriver);

        //Step 4
        editPage.getModuleBlock(fixedShelf.getTitle()).changeBlock().lockModule();
        softAssert.assertTrue(editPage.getModulesName().contains(fixedShelf.getTitle()), "Shelf '" + fixedShelf.getTitle() + "' isn't added in list",
                "Shelf is added in list", webDriver);
        softAssert.assertTrue(editPage.getModuleBlock(fixedShelf.getTitle()).isModuleLock(), "Shelf '" + fixedShelf.getTitle() + "' isn't fixed",
                "Shelf is fixed", webDriver);

//      Step 5
        editPage.getModuleBlock(fixedShelf.getTitle()).changeBlock().unlockModule();
        softAssert.assertFalse(editPage.getModuleBlock(fixedShelf.getTitle()).isModuleLock(), "Shelf '" + fixedShelf.getTitle() + "' still fixed",
                "Shelf is not fixed", webDriver);

//      Step 6
        editPage.getModuleBlock(customShelf.getTitle()).changeBlock().lockModule();
        softAssert.assertTrue(editPage.getModuleBlock(customShelf.getTitle()).isModuleLock(), "Second Shelf '" + customShelf.getTitle() + "' is fixed/locked",
                "Second Shelf is not fixed", webDriver);
        editPage.save();

        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    private void deletePageShelves() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            rokuBackEndLayer.deleteModule(FIXED_SHELF_TITLE);
            rokuBackEndLayer.deleteModule(CUSTOM_SHELF_TITLE);
        } catch (Exception e) {
            Utilities.logInfoMessage("Couldn't delete the objects by reason: " + e.getMessage());
        }
    }
}
