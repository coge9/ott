package com.nbcuni.test.cms.tests.backend.tvecms.modulepagemanager.panelizer;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.PageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;


public class TC11684_Panelizer_AddModuleAsEditorToPageCreatedByEditor extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageInfo;
    private Shelf shelf;

    /**
     * Pre-condition:
     * Create a shelf 'test shelf'
     * <p/>
     * Step 1. Go to Roku as Editor
     * Verify: User is logged in
     * <p/>
     * <p/>
     * Step 2: Go To the Shelf list and create shelf 'editor Shelf'
     * Verify: Shelf ''editor Shelf'' is created.
     * <p/>
     * Step 3. Navigate to the OTT Page list
     * Verify: The page list is present
     * There is 'Test Page' created in pre-condition
     * <p/>
     * Step 4.Open the page on edit with panelizer
     * (use URL http://qa.roku.usa.nbcuni.com/admin/ott/pages/manage-panels/3706/edit as example instaed of old http://qa.roku.usa.nbcuni.com/admin/ott/pages/manage/3706/edit)
     * Verify: The view OTT Page is present
     * <p/>
     * Step 5. Click on action 'wheel' at left corner of the page
     * Verify: The action menu is present:
     * -Edit Settings
     * -Edit Context
     * -Edit Content
     * -Edit Layout
     * <p/>
     * Step 6: Click on the Edit Content link
     * Verify: The PanelizerContent Page is present
     * The section with enities(module, header, context, etc) are present
     * <p/>
     * Step 7: Click on the action 'wheel' at header of the section
     * Verify: The menu is present
     * There is an option 'Add content'
     * <p/>
     * Step 8. Click on the Option 'Add Content'
     * Verify: The pop-up with interface of selection a content type is present
     * <p/>
     * Step 9. Click at Page Components
     * Verify: The Page setup components are present
     * There is 'Module' component
     * <p/>
     * Step 10. Click at Module Component
     * Verify: The Page to add a module is present
     * There is autocomplete field to select module
     * <p/>
     * Step 11. Put shelf name, created in pre-condition 'test Shelf'
     * Verify: The autocomplete is present 'test Shelf'' for selection
     * <p/>
     * Step 12. Select 'test Shelf' and save
     * Verify: The panelizerContent page is present
     * There is new section 'test Shelf' at the page preview
     * <p/>
     * Step 13. Click save button
     * Verify: The Edit of 'test Page' with panelizer is present
     * The view of Page displayed 'test Shelf name'
     */

    @Test(groups = "panelizer_module", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatEditorCouldAddModuleByPanelizerCreatedByEditor(String brand) {
        this.brand = brand;
        SoftAssert softAssert = new SoftAssert();

        //Step 1
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Pre-condition
        pageInfo = backEndLayer.createPage();
        mainRokuAdminPage.logOut(brand);
        //Step 1
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();

        //Step 2
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelf);
        draftModuleTab.clickSave();

        //Step 3-12
        EditPageWithPanelizer editPageWithPanelizer = backEndLayer.addModule(pageInfo, shelf);

        //Step 13
        editPageWithPanelizer.save();
        PageWithPanelizer pageWithPanelizer = backEndLayer.openViewPageWithPanelizer(pageInfo.getTitle());
        Assertion.assertEquals(pageWithPanelizer.getShelfName(), shelf.getTitle(),
                "The shelf name is not matched with set and those that present at View", webDriver);
        Utilities.logInfoMessage("The shelf name on OTT Page view is matched with those that was set");
    }
}
