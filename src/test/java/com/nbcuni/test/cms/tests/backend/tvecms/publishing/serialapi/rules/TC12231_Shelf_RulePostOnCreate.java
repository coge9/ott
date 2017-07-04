package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.rules;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.CuratedListType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuQueueJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 1/22/16.
 */
public class TC12231_Shelf_RulePostOnCreate extends BaseAuthFlowTest {

    private PageForm pageInfo;
    private String programOne;
    private String programTwo;
    private Shelf shelf;
    private DraftModuleTab draftModuleTab;
    private RokuQueueJson expectedShelfJson;

    /**
     * Pre-Condition:
     * 1. Go roku as Admin
     * 2. Create a Page
     * 3. Create Shelf with set of Programs(2 Published) and 1 video
     * 4. Assigh Shelf to the Page
     * <p/>
     * Step 1: Navigate to the Module 'Shelf' created in pre-condition
     * Verify: The edit Form of module is opened
     * There are 2 published programs
     * <p/>
     * Step 2: Check first program 'show latest' and save
     * Verify: The settings are saved
     * <p/>
     * Step 3: Go To OTT Page created in precondition
     * Verify: The edit Page is present
     * There is 'Shelf' module assigned in precondition
     * There is 2 published programs within the curated list block
     * <p/>
     * Step 4: Publish Page to an API instnace
     * Verify: There is status message that POST request is sent
     * <p/>
     * Step 5: Review POST request on curated list
     * Verify: There is POST request for Curated list<br/>
     * The POST request of curated list is next: <br/>
     * The curatedlistType = hybrid<br/>
     */

    public void preconditionTC12231() {
        //Get 2 Programs and 1 video for test
        String fullEpisode = new NodeApi(brand).getRandomPublishedVideoNode().getTitle();
        programOne = new NodeApi(brand).getRandomProgramNode().getTitle();
        //  programTwo = programs.get(1);
        List<String> assetsList = Arrays.asList(programOne, fullEpisode);
        shelf = EntityFactory.getShelfsList().get(0);
        shelf.setAssets(assetsList);

        //Create test feature carousel module
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        //Step 2
        draftModuleTab.checkLatestEpisodeByName(programOne);
        draftModuleTab.clickSave();

        //Get Expected RokuQueue Object
        expectedShelfJson = rokuBackEndLayer.getObject(Arrays.asList(programOne), Arrays.asList(fullEpisode), CuratedListType.HYBRID, shelf);

        //Create test page and assign module to that
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);

        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        pageInfo = editPage.updateFields(Arrays.<Module>asList(shelf));
        editPage.save();
    }

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkPostRuleForShelf(final String brand) {
        //Pre-condition
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 1-2
        preconditionTC12231();

        //Step 3
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        //Step 4
        editPage.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        //Step 5
        String url = editPage.getLogURL(brand);

        RokuQueueJson actualShelfJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.LIST);
        softAssert.assertEquals(expectedShelfJson, actualShelfJson, "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage12231() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logSevereMessage("This page can't be deleted!");
        }
    }
}
