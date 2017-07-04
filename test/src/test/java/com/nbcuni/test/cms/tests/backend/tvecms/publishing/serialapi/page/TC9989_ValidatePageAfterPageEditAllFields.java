package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.page;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuPageJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.PageVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by aleksandra_lishaeva on 9/24/15.
 */
public class TC9989_ValidatePageAfterPageEditAllFields extends BaseAuthFlowTest {
    public PageForm pageForm;
    private MainRokuAdminPage mainRokuAdminPage;
    private EditPageWithPanelizer editPage;
    private DraftModuleTab draftModuleTab;
    private Shelf shelf = null;
    private SoftAssert softAssert = new SoftAssert();

    /**
     * Pre-Condition
     * Create test shelf1 and shelf 2
     * Get 2 published item from content, asset 1 and asset 2
     * Create Page with all fields 'test page', set shelf 1 and asset 1 to context
     *
     * Step 1.Go to the roku as admin
     * Verify: The admin panel is present
     * No error is present
     * <p/>
     * Step 2. Go to the OTT Page page admin/ott/pages
     * Verify: The list of existing pages is present
     * <p/>
     * Step 3. Go to the edit of page created in precondition 'test page'
     * Verify: The edit page is opened
     * <p/>
     * Step 4. Update title to "test page updated"
     * Replace shelf1 by shelf 2
     * Replace asset1 by asset 2 in the context
     * Click 'Publish to Development'
     * Verify: There is status message that request is sent to the API
     * There is no error messages
     * <p/>
     * Step 5. Go to the Watch Log page 'admin/reports/dblog
     * Verify: The table with data in db log is present
     * There is 'OTT Publishing [page]' filter in the filter list
     * <p/>
     * Step 6. Select 'OTT Publishing [page]' and click filter
     * Verify: There is info about POST request in the table related to the page in step '3'
     * <p/>
     * Step 7. Check: Request Message
     * Status Request
     * HTTP Code response from API
     * Data request - Json that was pushed to API
     * <p/>
     * Verify: The next data is present:
     * Request Message: POST request has been sent successfully. The existing "step 3" page has been updated in TVE API service.
     * Status Request: OK
     * HTTP Code:200
     * Data request :{"pageId":"test page updated","appId":"usa-roku","name":"test page updated","alias":"test page updated","modules":[{"id":"31","moduleType":null}],"pages":[],"description":"","meta":[]}
     *
     * Step 8: Validate JSON that was used to API by Shem
     * Verify: Scheme is valid
     */

    @Test(groups = {"page_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider")
    public void validatePageScheme(String brand) {
        Utilities.logInfoMessage("Check publishing the Page after editing required fields");
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-condition
        shelf = EntityFactory.getShelfsList().get(1);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        Assert.assertFalse(mainRokuAdminPage.isErrorMessagePresent());

        //Step 2
        pageForm = rokuBackEndLayer.createPage(CmsPlatforms.ANDROID);

        //Step 3
        pageForm.setModules(Arrays.asList(shelf));
        pageForm = rokuBackEndLayer.updatePage(pageForm);

        //Step 4
        new EditPageWithPanelizer(webDriver, aid).elementPublishBlock().publishByTabName();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing with message", webDriver);

        //Step 5
        String url = mainRokuAdminPage.getLogURL(brand);
        //Get Expected result
        RokuPageJson expectedPageJson = new RokuPageJson().getObject(pageForm, brand);
        RokuPageJson actualPageJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.PAGE);
        softAssert.assertEquals(expectedPageJson, actualPageJson, "The data is not matched", "The data is matched", new PageVerificator());
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Throwable e) {
            Utilities.logWarningMessage("Error in tear down method, " + e.getMessage());
        }
    }
}
