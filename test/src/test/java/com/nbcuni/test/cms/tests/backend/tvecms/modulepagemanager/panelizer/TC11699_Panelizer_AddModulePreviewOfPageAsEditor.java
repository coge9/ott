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


public class TC11699_Panelizer_AddModulePreviewOfPageAsEditor extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageInfo = null;
    private Shelf shelf;

    /**
     * Pre-condition:
     * Login as Admin
     * Create a shelf 'test shelf'
     * Create a Page 'test Page'
     * <p/>
     * Step 1. Go to Roku as editor
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
     * Step 6: Click on the action 'wheel' at header of the section
     * Verify: The menu is present
     * There is an option 'Add content'
     * <p/>
     * Step 7. Click on the Option 'Add Content'
     * Verify: The pop-up with interface of selection a content type is present
     * <p/>
     * Step 8. Click at Page Components
     * Verify: The Page setup components are present
     * There is 'Module' component
     * <p/>
     * Step 9. Click at Module Component
     * Verify: The Page to add a module is present
     * There is autocomplete field to select module
     * <p/>
     * Step 10. Put shelf name, created in pre-condition 'test Shelf'
     * Verify: The autocomplete is present 'test Shelf'' for selection
     * <p/>
     * Step 11. Select 'test Shelf' and save
     * Verify: The panelizerContent page is present
     * There is new section 'test Shelf' at the page preview
     * <p/>
     * Step 12. Click Preview button
     * Verify: The preview form of Page displayed 'test Shelf name'
     */

    @Test(groups = {"panelizer", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatAdminCouldSeeModuleOnPreviewAseditor(String brand) {
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

        rokuBackEndLayer.createPage(pageInfo);
        mainRokuAdminPage.logOut(brand);

        //Step 1
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2-11
        EditPageWithPanelizer editPageWithPanelizer = rokuBackEndLayer.addModule(pageInfo, shelf);
        editPageWithPanelizer.save();

        //Step 12
        PageWithPanelizer pageWithPanelizer = rokuBackEndLayer.openViewPageWithPanelizer(pageInfo.getTitle());
        Assertion.assertEquals(pageWithPanelizer.getShelfName(), shelf.getTitle(),
                "The shelf name is not present at preview of the Page", webDriver);
        mainRokuAdminPage.logOut(brand);
        Utilities.logInfoMessage("The shelf name is present at preview of the Page");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePanelizerTC11699() {
        try {
            rokuBackEndLayer.openAdminPage();
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("Impossible to delete objects");
        }
    }
}
