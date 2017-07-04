package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.curatedlist;

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
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.CuratedListType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuQueueJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by aleksandra_lishaeva on 9/24/15.
 */
public class TC10921_CuratedListSavedByPublishingAutomaticaly extends BaseAuthFlowTest {
    private PageForm pageInfo;
    private DraftModuleTab draftModuleTab;
    private Shelf shelf;

    /**
     * Pre-Condition:
     * 1. Go To roku as admin
     * 2. Create a Page
     * 3. Create a shelf and assign to the Page through Panelizer
     * <p>
     * Step 1. Go to Roku as Admin
     * Verify: Admin Panel is presnt
     * <p/>
     * Step 2. Go to OTT Module list view
     * Verify: List of Ott Modules are present
     * <p/>
     * Step 3. Select the shelf from pre-condition and go to the edit Page
     * Verify: Ott edit Shelf is present
     * <p/>
     * Step 4. Make changes for the curated list and save
     * Verify: There is button 'Save and Publish' there is no button 'Publish'
     * <p>
     * <p/>
     * Step 5. Click Button 'Save and Publish' to an {$instance}
     * Verify: Changes are saved automatically<br />
     * Status message is present
     * Post request is send To API on Shelf create
     * Request data is matched with data set in the shelf
     * Post response are: Status=201 Message=Created
     */

    public void preconditionTC10921() {
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelf);
        draftModuleTab.clickSave();
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ANDROID);
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        pageInfo = editPage.updateFields(Arrays.<Module>asList(shelf));
        editPage.save();
        editPage.elementPublishBlock().publishByTabName();
    }

    @Test(groups = {"module_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider")
    public void curatedListChangesAreSavedAutomaticaly(String brand) {
        // Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-Condition
        preconditionTC10921();

        // Step 2
        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        softAssert.assertTrue(modulesPage.isShelfExist(shelf.getTitle()), "Void Shelf was not created", "Void Shelf was created", webDriver);

        //Step 3
        draftModuleTab = modulesPage.clickEditLink(shelf.getTitle());

        //Step 4
        shelf = EntityFactory.getShelfsList().get(0);
        shelf = draftModuleTab.createShelf(shelf, PublishState.PUBLISHED);
        //Step 5
        draftModuleTab.elementPublishBlock().publishByTabName();
        String url = draftModuleTab.getLogURL(brand);
        softAssert.assertTrue(draftModuleTab.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        RokuQueueJson expectedResult = new RokuQueueJson().getObject(brand, CuratedListType.CURATED, shelf, pageInfo);
        RokuQueueJson actualResult = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.LIST);

        softAssert.assertReflectEquals(expectedResult, actualResult,
                "The POST request of  List JSON doesn't matched with expected",
                "The POST request of List JSON is matched with expected");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage10921() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("This page can't be deleted!");
        }
    }
}
