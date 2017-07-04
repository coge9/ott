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

/**
 * Created by Ivan_Karnilau on 17-Sep-15.
 */
public class TC9737_AdminIsAbleToReplaceFixedShelfOnOTTPage extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;
    private EditPageWithPanelizer editPage;
    private PageForm pageForm = CreateFactoryPage.createDefaultPage().setPlatform(CmsPlatforms.IOS);
    private DraftModuleTab draftModuleTab;
    private Shelf shelf;

    /**
     * Pre-Conditions:
     * 1. Create OTT Page
     * 2. Create 2 Shelf
     * 3. Add Shelf as fixed to page
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
     * Step 5: remove fixed shelf's name from autocomplete field
     * Verify: OTT Module" autocomplete field is empty
     * <>p</>
     * Step 6: click on empty "OTT Module" autocomplete field
     * start to enter another one test shelf's title
     * Verify: OTT Module" autocomplete field is offering all appropriate shelfs
     * <>p</>
     * Step 7: click on test shelf's name in offered list
     * Verify: test shelf is added
     * Step 8: click on " Save OTT page" button
     * Verify: OTT page is saved
     * <>p</>
     * Step 9: check that fixed shelf is removed
     * Verify: fixed shelf is removed
     */


    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkThatAdminIsAbleToReplaceFixedShelfOnOTTPage(final String brand) {
        Utilities.logInfoMessage("Check that Admin is able to replace fixed shelf on OTT page");
        SoftAssert softAssert = new SoftAssert();

//      Pre-Conditions
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 1
        rokuBackEndLayer.createPage(pageForm);

//      Step 2
        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);

//      Step 3
        editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        editPage.setModule(shelf.getTitle()).save();
        editPage.getModuleBlock(shelf.getTitle()).changeBlock().lockModule();
        editPage.save();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "Success message isn't present",
                "Success message isn't present", webDriver);

//      Step 4-9
        editPage.getModuleBlock(shelf.getTitle()).deleteBlock();
        editPage.save();
        softAssert.assertFalse(editPage.getModulesName().contains(shelf.getTitle()), "Shelf '" + shelf.getTitle() + "' still added to Page",
                "Shelf is removed from page", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9737() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("There is an error during page deletion");
        }

        Utilities.logInfoMessage("The page and shelf were delete successfully");
    }

}
