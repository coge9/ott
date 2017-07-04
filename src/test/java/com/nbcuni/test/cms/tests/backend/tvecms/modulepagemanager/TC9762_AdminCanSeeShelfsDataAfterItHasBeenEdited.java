package com.nbcuni.test.cms.tests.backend.tvecms.modulepagemanager;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 24-Sep-15.
 */
public class TC9762_AdminCanSeeShelfsDataAfterItHasBeenEdited extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private EditPageWithPanelizer editPage;
    private PageForm pageInfo;
    private Shelf shelf;
    private DraftModuleTab draftModuleTab;

    /**
     * Pre-Conditions:
     * 1. Create OTT Page
     * 2. Create Shelf and add in page
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
     * Step 4: check for already added test shelf
     * Verify: test shelf is present in list
     * shelf's data table is displayed with valid data
     * <>p</>
     * Step 5: go to test shelf's page
     * change:
     * -title
     * -type
     * -list of added nodes
     * -tile variant
     * -image style
     * -display title value
     * save shelf
     * Verify: shelf has been edited
     * <>p</>
     * Step 6: return to test page
     * Verify: "Edit [pagename]" page is opened
     * <>p</>
     * Step 7: check shelf's data table
     * Verify: there is shelf's data table above configuration block
     * all data in table is updated accordingly to step 5
     */


    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkAdminCanSeeShelfsDataAfterItHasBeenEdited(final String brand) {
        Utilities.logInfoMessage("Check Admin can see shelf's data after it has been edited");
        SoftAssert softAssert = new SoftAssert();

//      Pre-Conditions
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 1
        pageInfo = rokuBackEndLayer.createPage();

//      Step 2
        shelf = EntityFactory.getShelfsList().get(1);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);

        //      Step 3
        editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        editPage.setModule(shelf.getTitle());
        editPage.save();
        softAssert.assertTrue(editPage.isStatusMessageShown(), "Success message isn't present",
                "Success message isn't present");

//      Step 4
        softAssert.assertTrue(editPage.getModulesName().contains(shelf.getTitle()), "Shelf '" + shelf.getTitle() + "' isn't present",
                "Shelf is present", webDriver);

//      Step 5 - updating the shelf
        rokuBackEndLayer.openModuleEdit(shelf);
        shelf = EntityFactory.getShelfsList().get(1);
        draftModuleTab.createShelf(shelf);

//      Step 6
        rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());

//      Step 7
        softAssert.assertTrue(editPage.getModulesName().contains(shelf.getTitle()), "Updated Shelf '" + shelf.getTitle() + "' isn't present",
                "Updated Shelf is present", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    private void cleanUpTC9762() {
        rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
        rokuBackEndLayer.deleteModule(shelf.getTitle());
    }
}
