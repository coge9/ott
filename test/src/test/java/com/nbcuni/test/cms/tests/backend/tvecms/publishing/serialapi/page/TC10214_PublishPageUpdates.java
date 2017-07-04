package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.page;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuPageJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.PageVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Aleksandra_Lishaeva on 1/15/16.
 */
public class TC10214_PublishPageUpdates extends BaseAuthFlowTest {

    private PageForm pageInfo;
    private DraftModuleTab draftModuleTab;
    private Shelf shelf;

    /**
     * Pre-Condition:
     * 1. Create new Ott Page with all required fields.
     * 2. Save page [page_name].
     * 3. Open current page for edit and Publish
     * <p/>
     * Steps:
     * Step 1: Open [page_name] for edit with new Panelizer View
     * Verify: Edit page is opened with panelizer.
     * <p/>
     * Step 2: Updated title,alias fields, add a Module and 'save'
     * Verify: Status message is shown. Error message isn't presened.
     * <p/>
     * Step 3: Open [page_name] for edit
     * Verify: Edit page is opened. Publish button is enabled. Revert button is enabled.
     * <p/>
     * Step 4: Click on Publish button.
     * Verify: Status message is shown. Error message isn't presened.
     * Status message contains link to Log page with this session POST requests
     * <p/>
     * Step 5: Click on id of Page item Post request
     * Verify: The JSON with metadata propper to set in step 2 Page's data is present.
     * The response from API has:
     * -Response message = Updated -Response code = 200
     * (in case if that page was deleted in the API code should be 201 and message 'Created')
     */

    @Test(groups = {"page_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkPublishPageUpdatesRoku(String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-Condition
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelf);
        draftModuleTab.clickSave();
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);

        //Step 2
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        editPage.elementPublishBlock().publishByTabName();

        //Step 3
        pageInfo = editPage.updateFields(Arrays.<Module>asList(shelf));
        editPage.save();
        //Step 4
        editPage.elementPublishBlock().publishByTabName();

        //Step 5
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing with message", webDriver);

        RokuPageJson expectedPageJson = new RokuPageJson().getObject(pageInfo, brand);
        RokuPageJson actualPageJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.PAGE);
        softAssert.assertEquals(expectedPageJson, actualPageJson, "The actual data is not matched", "The JSON data is matched", new PageVerificator());
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");

    }

    @Test(groups = {"page_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider")
    public void checkPublishPageUpdatesAndroid(String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-Condition
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab.createShelf(shelf);
        draftModuleTab.clickSave();
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ANDROID);

        //Step 2
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        editPage.elementPublishBlock().publishByTabName();

        //Step 3
        pageInfo = editPage.updateFields(Arrays.<Module>asList(shelf));
        editPage.save();
        //Step 4
        editPage.elementPublishBlock().publishByTabName();

        //Step 5
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing with message", webDriver);

        RokuPageJson expectedPageJson = new RokuPageJson().getObject(pageInfo, brand);
        RokuPageJson actualPageJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.PAGE);
        softAssert.assertEquals(expectedPageJson, actualPageJson, "The actual data is not matched", "The JSON data is matched", new PageVerificator());
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage10214() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("This page can't be deleted!");
        }
    }
}
