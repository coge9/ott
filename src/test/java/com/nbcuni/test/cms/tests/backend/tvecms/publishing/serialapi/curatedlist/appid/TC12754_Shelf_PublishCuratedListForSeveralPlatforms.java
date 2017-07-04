package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.curatedlist.appid;

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
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuQueueJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by aleksandra_lishaeva on 9/24/15.
 */
public class TC12754_Shelf_PublishCuratedListForSeveralPlatforms extends BaseAuthFlowTest {
    private PageForm pageInfoRoku;
    private PageForm pageInfoAndroid;
    private DraftModuleTab draftModuleTab;
    private Shelf shelf;

    /**
     *
     * Pre-Condition:
     * 1. Create a Page 1 with usa-roku app
     * 2. Create a Page 2 with usa-android app
     * 3. Create a shelf with content list
     * 4. Assign Shelf to the Page 1 and Page 2
     *
     * Step 1. Go to Roku as Admin
     * Verify: Admin Panel is presnt
     * <p/>
     * Step 2. Assign Module to the Page 1
     * Verify: The module is assigned to the page
     *
     * Step 3. Assign Module to the Page 2
     * Verify: The module is assigned to the page
     *
     * <p/>
     * Step 4. Publish Page to an instance
     * Verify: The status message is present
     * POST request is send
     * Response from API code=201(0)
     * <p/>
     * Step 5. Check POST request of the List
     * Verify: The list JSON contains appId array with values
     * appId[usa-roku
     * usa-android]
     *
     */

    public void preconditionTC12754() {
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        shelf = EntityFactory.getShelfsList().get(0);
        shelf = draftModuleTab.createShelf(shelf);
        draftModuleTab.clickSave();
        pageInfoRoku = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
        pageInfoAndroid = rokuBackEndLayer.createPage(CmsPlatforms.ANDROID);
    }

    @Test(groups = {"list_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void listPostContainSeveralAppId(String brand) {
        Utilities.logInfoMessage("Check that LIst POST contains array of appIds of all pages on each Module with List assigned to");
        // Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-Condition
        preconditionTC12754();

        // Step 2
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfoRoku.getTitle());
        pageInfoRoku = editPage.updateFields(Arrays.<Module>asList(shelf));
        editPage.save();

        //Step 3
        editPage = rokuBackEndLayer.openEditOttPage(pageInfoAndroid.getTitle());
        pageInfoAndroid = editPage.updateFields(Arrays.<Module>asList(shelf));
        editPage.save();

        //Step 4
        editPage.elementPublishBlock().publishByTabName();
        String url = draftModuleTab.getLogURL(brand);
        softAssert.assertTrue(draftModuleTab.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        RokuQueueJson actualAssetJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.LIST);
        RokuQueueJson expectedAssetJson = rokuBackEndLayer.getObject(null, shelf.getAssets(), CuratedListType.CURATED, shelf, pageInfoAndroid, pageInfoRoku);
        softAssert.assertReflectEquals(expectedAssetJson, actualAssetJson, "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage12754() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfoRoku.getTitle());
            rokuBackEndLayer.deleteOttPage(pageInfoAndroid.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
            mainRokuAdminPage.logOut(brand);
        } catch (Exception e) {
            Utilities.logSevereMessageThenFail("This page can't be deleted!");
        }
    }
}
