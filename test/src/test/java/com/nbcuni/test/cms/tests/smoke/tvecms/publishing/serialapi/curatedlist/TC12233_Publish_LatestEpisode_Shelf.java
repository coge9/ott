package com.nbcuni.test.cms.tests.smoke.tvecms.publishing.serialapi.curatedlist;

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
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuQueueJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Aleksandra_Lishaeva on 1/22/16.
 */
public class TC12233_Publish_LatestEpisode_Shelf extends BaseAuthFlowTest {

    private PageForm pageInfo;
    private Shelf shelf;
    private GlobalNodeJson programNode;
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

    public void preconditionTC12233() {
        //Get 2 Programs and 1 video for test
        NodeApi nodeApi = new NodeApi(brand);
        programNode = nodeApi.getRandomPublishedProgramNode();

        shelf = EntityFactory.getShelfsList().get(0);
        shelf.setAssets(Arrays.asList(programNode.getTitle()));
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        draftModuleTab.checkLatestEpisodeByName(programNode.getTitle());
        draftModuleTab.clickSave();

    }

    @Test(groups = {"module_publishing", "roku_smoke", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider")
    public void checkPostRuleForShelf(final String brand) {
        //Pre-condition
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        preconditionTC12233();

        //Step 1-3
        //Create test page and assign module to that
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ANDROID);

        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        pageInfo = editPage.updateFields(Arrays.<Module>asList(shelf));
        editPage.save();
        editPage.elementPublishBlock().publishByTabName();

        //Step 4
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        //Step 5
        String url = editPage.getLogURL(brand);
        expectedShelfJson = rokuBackEndLayer.getObject(Arrays.asList(programNode.getTitle()), null, CuratedListType.HYBRID, shelf, pageInfo);
        RokuQueueJson actualShelfJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.LIST);
        softAssert.assertEquals(expectedShelfJson, actualShelfJson, "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage12233() {
        try {
            rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
            if (pageInfo.getTitle() != null) {
                rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            }
            if (shelf.getTitle() != null) {
                rokuBackEndLayer.deleteModule(shelf.getTitle());
            }
        } catch (Throwable e) {
            Utilities.logSevereMessage("Tear-down method can't be performed " + Utilities.convertStackTraceToString(e));
        }
    }
}
