package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.shelf;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.DateUtil;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuShelfJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.ModuleSerialVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by aleksandra_lishaeva on 9/24/15.
 */
public class TC10920_ShelfUpdatesSavedByPublishingAutomaticaly extends BaseAuthFlowTest {
    private PageForm pageInfo;
    private DraftModuleTab draftModuleTab;
    private Shelf shelf;

    /**
     *
     * Pre-Condition:
     * 1. Go To roku as admin
     * 2. Create a Page
     * 3. Create a shelf and assign to the Page through Panelizer
     *
     * Step 1. Go to Roku as Admin
     * Verify: Admin Panel is presnt
     * <p/>
     * Step 2. Go to OTT Module list view
     * Verify: List of Ott Modules are present
     * <p/>
     * Step 3. Select the shelf from pre-condition and go to the edit Page
     * Verify: Ott edit Shelf is present
     * <p/>
     * Step 4. Make changes for the shelf and save
     * Verify: There is button 'Save and Publish' there is no buton 'Publish'
     *
     * <p/>
     * Step 5. Click Button 'Save and Publish' to an {$instance}
     * Verify: Changes are saved automatically<br />
     * Status message is present
     * Post request is send To API on Shelf create
     * Request data is matched with data set in the shelf
     * Post response are: Status=201 Message=Created
     *
     */

    public void preconditionTC10920() {
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelf);
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        editPage.updateFields(Arrays.<Module>asList(shelf));
        editPage.getContentBlocks().get(1).clickAddContent().selectShelf(shelf.getTitle());
        editPage.save();
    }

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void shelfChangesAreSavedAutomaticaly(String brand) {
        // Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-Condition
        preconditionTC10920();

        // Step 2
        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        softAssert.assertTrue(modulesPage.isShelfExist(shelf.getTitle()), "Void Shelf was not created", "Void Shelf was created", webDriver);

        //Step 3
        draftModuleTab = modulesPage.clickEditLink(shelf.getTitle());

        //Step 4
        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelf, PublishState.PUBLISHED);

        softAssert.assertTrue(draftModuleTab.elementPublishBlock().isSaveAndPublishEnable(), "Save and Publish Button is not present", "The button 'Save and Publish' is present", webDriver);

        //Step 5
        draftModuleTab.elementPublishBlock().publishByTabName();
        shelf.setCreatedDate(DateUtil.getCurrentDateInUtcString());
        shelf.setPublishedDate(DateUtil.getCurrentDateInUtcString());

        String url = draftModuleTab.getLogURL(brand);
        softAssert.assertTrue(draftModuleTab.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        RokuShelfJson expectedShelfJson = new RokuShelfJson().getObject(shelf);
        RokuShelfJson actualShelfJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.MODULE);
        softAssert.assertEquals(expectedShelfJson, actualShelfJson, "The actual data is not matched", "The JSON data is matched", new ModuleSerialVerificator());
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage10920() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("This page can't be deleted!");
        }
    }
}
