package com.nbcuni.test.cms.tests.backend.tvecms.modulepagemanager;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
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

import java.util.List;

/**
 * Created by Ivan_Karnilau on 24-Sep-15.
 */
public class TC9761_EditorCanSeeShelfsDataAfterItHasBeenAdded extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private EditPageWithPanelizer editPage;
    private PageForm pageInfo = CreateFactoryPage.createDefaultPage().setPlatform(CmsPlatforms.IOS);
    private Shelf shelf;
    private DraftModuleTab draftModuleTab;

    /**
     * Pre-Conditions:
     * 1. Create OTT Page
     * 2. Create Shelf
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
     * add test shelf with autocomplete functionality
     * Verify: test shelf is added
     * <>p</>
     * Step 5: click on " Save OTT page" button
     * Verify: user friendly message is displayed
     * OTT page is saved
     * <>p</>
     * Step 6: click om "Edit" next to test page's label
     * Verify: "Edit [pagename]" page is opened
     * <>p</>
     * Step 7: check for added shelf
     * Verify: shelf is added
     * there is shelf's data table above configuration block
     * <>p</>
     * Step 8: check shelf's data table
     * Verify: There are columns: TITLE, TYPE, LIST, TILE VARIANT, IMAGE STYLE, DISPLAY TITLE, UPDATED
     * <>p</>
     * Step 9: check data in table
     * Verify: all data matches with shelf's data
     * <>p</>
     * Step 10: click on TITLE link
     * Verify: shelf's page is opened in new tab
     */


    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = false)
    public void checkEditorCanSeeShelfsDataAfterItHasBeenAdded(final String brand) {
        Utilities.logInfoMessage("Check Editor can see shelf's data after it has been added");
        SoftAssert softAssert = new SoftAssert();

//      Pre-Conditions
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 1
        pageInfo = rokuBackEndLayer.createPage();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present");

//      Step 2
        shelf = EntityFactory.getShelfsList().get(2);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);

        editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        editPage.setModule(shelf.getTitle()).save();
        List<Module> expectedData = editPage.getModules();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present");
        mainRokuAdminPage.logOut(brand);

//      Test steps:
//      Step 1
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

//      Step 2
        editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());

//      Step 3
        softAssert.assertTrue(editPage.getModulesName().contains(shelf.getTitle()), "Shelf '" + shelf.getTitle() + "' isn't present",
                "Shelf is present", webDriver);

//      Step 4
        List<Module> actualData = editPage.getModules();

//      Step 5
        softAssert.assertEquals(expectedData, actualData, "The data of expected Module's info doen't matched with actual",
                "The data of expected Module's info doen't matched with actual");
        mainRokuAdminPage.logOut(brand);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9761() {
        try {
            rokuBackEndLayer.openAdminPage();
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("There is an error during page nad module deletion");
        }

        Utilities.logInfoMessage("The page and shelf were delete successfully");
    }
}
