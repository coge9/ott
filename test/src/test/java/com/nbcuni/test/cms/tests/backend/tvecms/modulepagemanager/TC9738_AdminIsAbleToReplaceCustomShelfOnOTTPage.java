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
public class TC9738_AdminIsAbleToReplaceCustomShelfOnOTTPage extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private EditPageWithPanelizer editPage;
    private PageForm pageForm = CreateFactoryPage.createDefaultPage().setPlatform(CmsPlatforms.IOS);
    private DraftModuleTab draftModuleTab;
    private Shelf shelf1;
    private Shelf shelf2;

    /**
     * Pre-Conditions:
     * 1. Create OTT Page
     * 2. Create 2 Shelf
     * 3. Add Shelf as custom to page
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
     * make sure that there is custom shelf in "OTT MODULES" list
     * Verify: custom shelf is added in list
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
     * Step 9: check that custom shelf is replaced
     * Verify: custom shelf is replaced
     */


    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkThatAdminIsAbleToReplaceCustomShelfOnOTTPage(final String brand) {
        Utilities.logInfoMessage("Check that Admin is able to replace custom shelf on OTT page");
        SoftAssert softAssert = new SoftAssert();

//      Pre-Conditions
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 1
        rokuBackEndLayer.createPage(pageForm);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not present", webDriver);

//      Step 2
        shelf1 = EntityFactory.getShelfsList().get(0);
        shelf2 = EntityFactory.getShelfsList().get(0);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf1);

        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf2);

//      Step 3
        editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        editPage.setModules(Arrays.asList(shelf1, shelf2)).save();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "Success message isn't present",
                "Success message isn't present", webDriver);

        //Step 4
        editPage.getModuleBlock(shelf1.getTitle()).changeBlock().lockModule();
        softAssert.assertTrue(editPage.getModuleBlock(shelf1.getTitle()).isModuleLock(), "The module is not locked",
                "The module is locked");
        softAssert.assertFalse(editPage.getModuleBlock(shelf2.getTitle()).isModuleLock(), "The second module is locked too",
                "The second module is not locked");
        editPage.save();

        //step 5
        editPage.getModuleBlock(shelf1.getTitle()).changeBlock().unlockModule();
        softAssert.assertFalse(editPage.getModuleBlock(shelf1.getTitle()).isModuleLock(), "The module is still locked",
                "The module is not locked");

        //step 6
        editPage.getModuleBlock(shelf2.getTitle()).changeBlock().lockModule();
        softAssert.assertTrue(editPage.getModuleBlock(shelf2.getTitle()).isModuleLock(), "The second module is not locked",
                "The second module is locked for now");
        editPage.save();
        mainRokuAdminPage.logOut(brand);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePageTC9738() {
        try {
            rokuBackEndLayer.openAdminPage();
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            rokuBackEndLayer.deleteModule(shelf1.getTitle());
            rokuBackEndLayer.deleteModule(shelf2.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("There is an error during page deletion");
        }

        Utilities.logInfoMessage("The page and shelf were delete successfully");
    }
}
