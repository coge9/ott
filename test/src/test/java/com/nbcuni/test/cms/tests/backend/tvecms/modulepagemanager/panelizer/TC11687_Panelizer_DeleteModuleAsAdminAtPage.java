package com.nbcuni.test.cms.tests.backend.tvecms.modulepagemanager.panelizer;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.PageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;


public class TC11687_Panelizer_DeleteModuleAsAdminAtPage extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageInfo = null;
    private Shelf shelf;

    /**
     * Pre-condition:
     * Create a shelf 'test shelf'
     * Create a Page 'test Page'
     * Add 'test Shelf' to the Page
     * <p/>
     * Step 1. Go to Roku as Admin
     * Verify: User is logged in
     * <p/>
     * Step 2. Navigate to the OTT Page list
     * Verify: The page list is present
     * There is 'Test Page' created in pre-condition
     * <p/>
     * Step 3.Open the page on edit with panelizer
     * (use URL http://qa.roku.usa.nbcuni.com/admin/ott/pages/manage-panels/3706/edit as example instaed of old http://qa.roku.usa.nbcuni.com/admin/ott/pages/manage/3706/edit)
     * Verify: The view OTT Page is present
     * <p/>
     * Step 4. Click on action 'wheel' at left corner of the page
     * Verify: The action menu is present:
     * -Edit Settings
     * -Edit Context
     * -Edit Content
     * -Edit Layout
     * <p/>
     * Step 5: Click on the Edit Content link
     * Verify: The PanelizerContent Page is present
     * The section with enities(module, header, context, etc) are present
     * <p/>
     * Step 6: Check that there is a section 'test Shelf' , related to module added to the Page in pre-condition
     * Verify: There is a section 'test Shelf'
     * <p/>
     * Step 7. Click on the actions button and then click 'Remove'
     * Verify: The section 'test shelf' is deleted
     * <p/>
     * Step 8.Save changes and check View of Ott Page
     * Verify: The Edit of 'test Page' with panelizer is present
     * There is no 'test Shelf'
     */

    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatAdminCouldDeleteModuleByPanelizer(String brand) {
        this.brand = brand;
        SoftAssert softAssert = new SoftAssert();
        pageInfo = CreateFactoryPage.
                createDefaultPage().
                setPlatform(RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getConcertoPlatforms().get(0));

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-condition
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelf);
        draftModuleTab.clickSave();
        mainRokuAdminPage.openAddShelfPage(brand);

        rokuBackEndLayer.createPage(pageInfo);
        EditPageWithPanelizer editPageWithPanelizer = rokuBackEndLayer.addModule(pageInfo, shelf);

        //Step2-7
        editPageWithPanelizer.getModuleBlock(shelf.getTitle()).deleteBlock();
        editPageWithPanelizer.save();
        //Step 8
        softAssert.assertFalse(editPageWithPanelizer.getModuleBlock(shelf.getTitle()) == null,
                "The Module: " + shelf.getTitle() + " still present", "The old Module block : " + shelf.getTitle() + " is not present", webDriver);

        PageWithPanelizer pageWithPanelizer = rokuBackEndLayer.openViewPageWithPanelizer(pageInfo.getTitle());
        Assertion.assertFalse(pageWithPanelizer.isModulePresent(),
                "The shelf still present at View of OTT Page", webDriver);
        Utilities.logInfoMessage("The shelf no longer present at OTT Page view ");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePanelizerTC11687() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("Couldn't clean up the content");
        }
    }
}
