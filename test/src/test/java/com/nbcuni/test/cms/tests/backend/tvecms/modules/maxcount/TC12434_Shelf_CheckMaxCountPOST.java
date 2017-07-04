package com.nbcuni.test.cms.tests.backend.tvecms.modules.maxcount;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ContentFormat;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LatestEpisodeJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuQueueJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alekca on 29.01.16.
 */
public class TC12434_Shelf_CheckMaxCountPOST extends BaseAuthFlowTest {
    private PageForm pageInfo;
    private String program1;
    private String program2;
    private DraftModuleTab draftModuleTab;
    private Shelf shelf;
    private int maxCount = SimpleUtils.getRandomInt(20);
    private int maxCountNext = SimpleUtils.getRandomInt(10);
    private RokuQueueJson expectedShelfJson;
    private List<LatestEpisodeJson> items = new ArrayList<>();

    /**
     * Pre-Condition
     * Create Shelf module with Program
     * Create Page
     *
     * Step 1. Go To roku as admin
     * Verify: The admin panel is present
     *
     * Step 2. Go to Modules and select Shelf module created in pre-condition
     * Verify: The Edit module Page is opened. Program is displayed.
     *
     * Step 3. Check 'show latest' for the program.
     * Verify: The item is checked
     * Max count field is shown with default value = 1
     *
     * Step 4. Set max count =2 and save
     * Verify: The module is saved
     *
     * Step 5. Go To the Page created in pre-condition
     * Verify: The edit page is opened
     * There is enable publish block
     *
     * Step 6. Assign module to the page and save
     * Verify: Module is added to the Page
     *
     * Step 7. Publish to an {$instance}
     * Verify: The status message is displayed
     * The POST request is send
     * The response from API with code=20+
     *
     * Step 8. Check POST JSOn for the List
     * Verify: The Max count parameter =2 for Program item in the items list of JSON
     * */

    public void preconditionTC12434() {
        //Create test page and assign module to that
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        List<String> programs = contentPage.getRandomAssets(2, ContentType.TVE_PROGRAM, ContentFormat.FULL_EPISODE);
        program1 = programs.get(0);
        program2 = programs.get(1);
        LatestEpisodeJson latestEpisodeJson1 = rokuBackEndLayer.collectLatestEpisodeInfo(program1, maxCount);
        LatestEpisodeJson latestEpisodeJson2 = rokuBackEndLayer.collectLatestEpisodeInfo(program2, maxCountNext);
        items.add(latestEpisodeJson1);
        items.add(latestEpisodeJson2);
        // Create test Shelf module
        shelf = EntityFactory.getShelfsList().get(0);
        shelf.setAssets(Arrays.asList(program1, program2));
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        expectedShelfJson = rokuBackEndLayer.getObject(items, null, shelf, pageInfo);
    }

    @Test(groups = {"max_count"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkMaxCountForShelf(final String brand) {
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        preconditionTC12434();

        //Step 3
        draftModuleTab.checkLatestEpisodeByName(program1);
        draftModuleTab.checkLatestEpisodeByName(program2);
        softAssert.assertTrue(draftModuleTab.isMaxCountPresent(program1), "The Max Count field is not visible for :" + program1, "Max Count field is present for :" + program1, webDriver);
        softAssert.assertTrue(draftModuleTab.isMaxCountPresent(program2), "The Max Count field is not visible for :" + program2, "Max Count field is present for :" + program2, webDriver);

        //Step 4
        draftModuleTab.setMaxCount(program1, String.valueOf(maxCount));
        draftModuleTab.setMaxCount(program2, String.valueOf(maxCountNext));
        draftModuleTab.clickSave();

        //Step 5
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());

        //Step 6
        editPage.updateFields(Arrays.<Module>asList(shelf));
        editPage.save();

        //Step 7
        editPage.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after publishing", "the status message is shown by publishing Page", webDriver);

        //Step 8
        String url = draftModuleTab.getLogURL(brand);
        RokuQueueJson actualShelfJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.LIST);
        softAssert.assertEquals(expectedShelfJson, actualShelfJson, "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void logoutTC12487() {
        mainRokuAdminPage.logOut(brand);
    }
}
