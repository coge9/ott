package com.nbcuni.test.cms.tests.smoke.tvecms.publishing.serialapi.page;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuPageJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by aleksandra_lishaeva on 9/24/15.
 */
public class TC9986_ValidatePageAfterPageCreationAllFields extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageInfo;
    private Shelf shelf;
    private DraftModuleTab draftModuleTab;
    private SoftAssert softAssert = new SoftAssert();
    private RokuBackEndLayer backEndLayer;
    private String brand;

    /**
     * Pre-condition: Create test shelf Get a published asset from content to be
     * added as context
     * <p/>
     * Step 1.Go to the roku as admin Verify: The admin panel is present No
     * error is present
     * <p/>
     * Step 2. Go to the OTT Page page admin/ott/pages Verify: The list of
     * existing pages is present
     * <p/>
     * Step 3. Add new page with all fields Verify: The new created page
     * displayed in the page list
     * <p/>
     * Step 4. Go to the edit of page created in step '3' Verify: The edit page
     * is opened
     * <p/>
     * Step 5. Click 'Publish Development' Verify: There is status message that
     * request is sent to the API There is no error messages
     * <p/>
     * Step 6. Go to the Watch Log page 'admin/reports/dblog Verify: The table
     * with data in db log is present There is 'OTT Publishing [page]' filter in
     * the filter list
     * <p/>
     * Step 7. Select 'OTT Publishing [page]' and click filter Verify: There is
     * info about POST request in the table related to the page in step '3'
     * <p/>
     * Step 8. Check: Request Message Status Request HTTP Code response from API
     * Data request - Json that was pushed to API
     * <p/>
     * Verify: The next data is present: Request Message: POST request has been
     * sent successfully. The new "step 3" page has been Created in TVE API
     * service. Status Request: Created HTTP Code:201 Data request example
     * :{"pageId"
     * :"Title6J","appId":"usa-roku","name":"Title6J","alias":"Title6J"
     * ,"modules":[],"pages":[],"description":"test description","meta":[]}
     * <p/>
     * Step 9: Validate JSON that was used to API by Shem Verify: Scheme is
     * valid
     */

    @Test(groups = {"page_publishing", "roku_smoke", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider")
    public void checkPagePublishingByCreationWithAllFields(String brand) {
        Utilities.logInfoMessage("Check that page could be published after creation with all fields from edit page");
        this.brand = brand;

        // Step 1
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();

        // Pre-condition create Shelf and get an Item
        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "There is error by Shelf creation end",
                "There is no error by Shelf creation end", webDriver);

        // Step 2
        pageInfo = backEndLayer.createPage();

        //Step 3
        EditPageWithPanelizer editPage = backEndLayer.openEditOttPage(pageInfo.getTitle());
        editPage.setModule(shelf.getTitle());
        editPage.save();
        pageInfo = editPage.getAllFieldsForPage();
        // Step 4-5
        editPage.elementPublishBlock().publishByTabName();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after publishing",
                "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "The error message is present after publishing with message"
                + mainRokuAdminPage.getErrorMessage(), "The error message is not present after publishing with message", webDriver);

        RokuPageJson expectedPageJson = new RokuPageJson().getObject(pageInfo, brand);
        RokuPageJson actualPageJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.PAGE);

        softAssert.assertEquals(expectedPageJson, actualPageJson, "The data is not matched", "The data is matched", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage9986() {
        try {
            backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
            backEndLayer.deleteOttPage(pageInfo.getTitle());
            backEndLayer.deleteModule(shelf.getTitle());
        } catch (Exception e) {
            Utilities.logSevereMessage("This page can't be deleted!");
        }
    }
}
