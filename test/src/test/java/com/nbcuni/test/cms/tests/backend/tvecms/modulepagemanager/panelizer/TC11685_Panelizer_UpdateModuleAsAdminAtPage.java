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
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;


public class TC11685_Panelizer_UpdateModuleAsAdminAtPage extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageInfo = null;
    private Shelf shelfFirst;
    private Shelf shelfSecond;

    /**
     * Pre-condition:
     * Create a shelf 'test shelf 1'
     * Create a shelf 'test shelf 2'
     * Create a Page 'test Page'
     * Add 'test Shelf1' to the Page
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
     * Step 6: Check that there is a section 'test Shelf 1' , related to module added to the Page in pre-condition
     * Verify: There is a section 'test Shelf 1'
     * <p/>
     * Step 7. Click on the actions button and then click 'Settings'
     * Verify: The Page to Edit Shelf is added
     * test Shelf 1 is present within autocomplete
     * <p/>
     * Step 8. Clear autocomplete and put 'test shelf 2' to the field
     * Verify: The autocomplete list with 'test shelf 2' is present
     * <p/>
     * Step 9. Select 'test shelf 2' from autocomplete and save
     * Verify: The panelizer content management page is present
     * <p/>
     * Step 10. Check modules' sections at the content types block
     * Verify: There is no section 'test Shelf 1
     * There is section 'test Shelf 2'
     * <p/>
     * Step 11.Save changes and check View of Ott Page
     * Verify: The Edit of 'test Page' with panelizer is present
     * The view of Page doesn't displayed 'test Shelf 2'
     */

    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatAdminCouldUpdateModuleByPanelizer(String brand) {
        pageInfo = CreateFactoryPage.
                createDefaultPage().
                setPlatform(RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getConcertoPlatforms().get(0));
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-condition
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        shelfFirst = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelfFirst);
        draftModuleTab.clickSave();
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        shelfSecond = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelfSecond);
        draftModuleTab.clickSave();

        rokuBackEndLayer.createPage(pageInfo);
        rokuBackEndLayer.addModule(pageInfo, shelfFirst).save();

        //Step2-10
        EditPageWithPanelizer editPageWithPanelizer = rokuBackEndLayer.updateModule(shelfFirst, shelfSecond);

        //Step 11
        editPageWithPanelizer.save();
        PageWithPanelizer pageWithPanelizer = rokuBackEndLayer.openViewPageWithPanelizer(pageInfo.getTitle());
        Assertion.assertEquals(pageWithPanelizer.getShelfName(), shelfSecond.getTitle(),
                "The shelf name is not matched with set and those that present at View", webDriver);
        Utilities.logInfoMessage("The shelf name on OTT Page view is matched with those that was set");

    }

    @AfterMethod(alwaysRun = true)
    public void deletePanelizerTC11685() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            rokuBackEndLayer.deleteModule(shelfFirst.getTitle());
            rokuBackEndLayer.deleteModule(shelfSecond.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("Couldn't clean up the content");
        }
    }
}
