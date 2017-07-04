package com.nbcuni.test.cms.tests.smoke.tvecms.publishing.serialapi.curatedlist;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.CuratedListType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuQueueJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * 1. Create new shelf with required fields and some items in curated list.
 * 2. Create new page with previously created shelf
 * 3. Publish this page.
 * <p/>
 * Steps:
 * 1.
 * Go to shelf created in precondition for editing
 * Verify:
 * User on edit shelf page
 * 2.
 * Add some queue items and press "Save module" button
 * Verify:
 * Updated shelf is saved.
 * No error message is present.
 * 3.
 * Click "Publish to ${env} button"
 * Verify:
 * There is status message "Successfully published Queue (Program & Video) #${id} "${Shelf_Title}" to ${env}.".
 * There is "Open full publishing report" link.
 * There is no error messages
 * 4.
 * Click "Open full publishing report"
 * Verify:
 * The OTT Log page is opened
 * <p/>
 * Validation    Check: queues
 * Response Message
 * Response Status
 * Data request - Json that was pushed to API
 * Log for publishing list is shown
 * Log has following info:
 * Request URL: endpoint URL
 * Request Status: OK
 * HTTP Code: 200
 * Response Data: [contains response from API services]
 */
public class TC10120_PublishCuratedListAfterUpdating extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;
    private DraftModuleTab draftModuleTab;
    private Shelf shelf;
    private PageForm pageInfo;
    private SoftAssert softAssert = new SoftAssert();

    @Test(groups = {"module_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider", enabled = true)
    public void validateCuratedListForSerialAPI(String brand) {
        // Pre-condition steps
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        shelf = draftModuleTab.createShelf(shelf);
        Assert.assertFalse(mainRokuAdminPage.isErrorMessagePresent());

        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ANDROID);

        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        editPage.setModule(shelf.getTitle());
        editPage.save();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after save",
                "The status message is shown after saving", webDriver);

        editPage.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after publishing",
                "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "The error message is present after publishing with message"
                + mainRokuAdminPage.getErrorMessage(), "The error message is not present after publishing with message", webDriver);

        // read info about assets and collect
        // Step 1, 2 - modify created shelf
        draftModuleTab = mainRokuAdminPage.openOttModulesPage(brand).clickEditLink(shelf.getTitle());
        draftModuleTab.addAssets(Arrays.asList(new NodeApi(brand).getRandomPublishedVideoNode().getTitle()));
        draftModuleTab.clickSave();
        Assert.assertFalse(mainRokuAdminPage.isErrorMessagePresent());
        shelf = draftModuleTab.getShelfInfo();
        draftModuleTab.elementPublishBlock().publishByTabName();

        // Validation
        String url = editPage.getLogURL(brand);
        RokuQueueJson expectedPageJson = rokuBackEndLayer.getObject(null, shelf.getAssets(), CuratedListType.CURATED, shelf, pageInfo);
        RokuQueueJson actualQueueJson = (RokuQueueJson) requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.LIST);
        softAssert.assertEquals(expectedPageJson, actualQueueJson, "The data is not matched", "The data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage10120() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logInfoMessage("This content could not be deleted!");
        }
    }

}
