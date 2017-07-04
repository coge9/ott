package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.rules;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
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
public class TC12232_Shelf_UncheckRulePost extends BaseAuthFlowTest {

    private PageForm pageInfo;
    private String programOne;
    private String programTwo;
    private Shelf shelf;
    private RokuQueueJson expectedShelfJson;
    private DraftModuleTab draftModuleTab;

    /**
     * Pre-Condition:
     * 1. Go roku as Admin
     * 2. Create a Page
     * 3. Create Shelf with set of Programs(2 Published) and 1 video
     * 4. Check first program 'show latest' and save
     * 5. Assigh Shelf to the Page and Publish that
     * <p/>
     * Step 1: Navigate to the Module 'Shelf' created in pre-condition
     * Verify: The edit Form of module is opened
     * There are 2 published programs
     * <p/>
     * Step 2: UnCheck first program 'show latest' and save
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
     * The curatedlistType = curated<br/>
     */

    public void preconditionTC12232() {
        //Get 2 Programs and 1 video for test
        String fullEpisode = new NodeApi(brand).getRandomPublishedVideoNode().getTitle();
        List<String> nodes = rokuBackEndLayer.getPublishedPrograms(2);
        programOne = nodes.get(0);
        programTwo = nodes.get(1);
        List<String> assetsList = Arrays.asList(programOne, programTwo, fullEpisode);


        //Create test Shelf carousel module
        shelf = EntityFactory.getShelfsList().get(0);
        shelf.setAssets(assetsList);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        draftModuleTab.checkLatestEpisodeByName(programOne);
        draftModuleTab.clickSave();
        expectedShelfJson = rokuBackEndLayer.getObject(null, assetsList, CuratedListType.CURATED, shelf);

        //Create test page and assign module to that
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        pageInfo = editPage.updateFields(Arrays.<Module>asList(shelf));
        editPage.save();
        editPage.elementPublishBlock().publishByTabName();
    }

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkPostUncheckedRuleForShelf(final String brand) {
        //Pre-condition
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        preconditionTC12232();

        //Step 1
        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        draftModuleTab = modulesPage.clickEditLink(shelf.getTitle());

        //Step 2
        draftModuleTab.uncheckLatestEpisodeByName(programOne);
        draftModuleTab.clickSave();

        //Step 4
        draftModuleTab.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(draftModuleTab.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        //Step 5
        String url = draftModuleTab.getLogURL(brand);

        RokuQueueJson actualShelfJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.LIST);
        softAssert.assertEquals(expectedShelfJson, actualShelfJson, "The actual data is not matched", "The JSON data is matched", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage12232() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logSevereMessage("This page can't be deleted!");
        }
    }
}
