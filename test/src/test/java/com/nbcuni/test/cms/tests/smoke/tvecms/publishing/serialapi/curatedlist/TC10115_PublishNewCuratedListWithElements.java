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
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuQueueJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Step 1. Go to the roku as admin Verify: The admin panel is present No error
 * is present
 * <p/>
 * Step 2. Go to the /admin/ott/modules/add/shelf
 * <p/>
 * Step 3. Fill required fields and save new shelf Verify: Information message
 * "This module is not assigned to any page. Module cannot be published until it
 * is assigned to a page. Manage pages." has been shown. No error is present
 * <p/>
 * Step 4. Go to the OTT Page page admin/ott/pages Verify: The list of existing
 * pages is present.
 * <p/>
 * Step 5. Add new page with required fields and add just created shelf Verify:
 * Success message "OTT page "New_page_name" has been saved." has been shown. No
 * error is present
 * <p/>
 * Step 6. Go to the OTT Page page admin/ott/pages Verify: The new created page
 * displayed in the page list
 * <p/>
 * Step 7. Go to the edit of page created in step '5' Verify: The edit page is
 * opened.
 * <p/>
 * Step 8. Click 'Publish to ${env}' Verify: There is status message that
 * request is sent to the API. There is no error messages
 * <p/>
 * Step 9. Click "Open full publishing report" The OTT Log page is opened
 * <p/>
 * Validation Check: queues Response Message Response Status Data request - Json
 * that was pushed to API Log for publishing list is shown Log has following
 * info: Request Data Response Message: Created Response Status: 201 Response
 * Data: [contains response from API services]
 * <p/>
 * The next data is present: Status Request: Created HTTP Code:201 Data request
 * :{"id":"${id}","listType":"curated", "name": "${name}", "items": [{"id":
 * "${id}","itemType": "video"},{"id":"${id}","itemType": "program"}]}
 * <p/>
 * Please keep in mind the Response Message might be OK and Response Status 200
 * in case the curated list has been created previously.
 */
public class TC10115_PublishNewCuratedListWithElements extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;
    private DraftModuleTab draftModuleTab;
    private Shelf shelf;
    private PageForm pageInfo;
    private SoftAssert softAssert = new SoftAssert();

    @Test(groups = {"module_publishing", "roku_smoke", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider")
    public void validatePageScheme(String brand) {
        // Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        // Step 2, 3
        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        shelf = draftModuleTab.createShelf(shelf);
        Assert.assertFalse(mainRokuAdminPage.isErrorMessagePresent());

        // Step 4, 5,6
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ANDROID);
        RokuQueueJson expectedPageJson = rokuBackEndLayer.getObject(null, shelf.getAssets(), CuratedListType.CURATED, shelf, pageInfo);

        // Step 7
        EditPageWithPanelizer addNewPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        addNewPage.setModule(shelf.getTitle());
        addNewPage.save();

        // Step 8
        addNewPage.elementPublishBlock().publishByTabName();
        String url = addNewPage.getLogURL(brand);

        // Validation
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after publishing",
                "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "The error message is present after publishing with message"
                + mainRokuAdminPage.getErrorMessage(), "The error message is not present after publishing with message", webDriver);

        RokuQueueJson actualQueueJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.LIST);
        softAssert.assertReflectEquals(expectedPageJson, actualQueueJson, "The data is not matched", "The data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage10115() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("This page can't be deleted!");
        }
    }

}
