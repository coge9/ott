package com.nbcuni.test.cms.tests.smoke.tvecms.publishing.serialapi.page;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuPageJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.PageVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by aleksandra_lishaeva on 9/24/15.
 */
public class TC9985_ValidatePageAfterPageCreationReqFields extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageInfo;
    private SoftAssert softAssert = new SoftAssert();

    /**
     * Step 1.Go to the roku as admin Verify: The admin panel is present No
     * error is present
     * <p/>
     * Step 2. Go to the OTT Page page admin/ott/pages Verify: The list of
     * existing pages is present
     * <p/>
     * Step 3. Add new page with required fields only Verify: The new created
     * page displayed in the page list
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
     * service. Status Request: Created HTTP Code:201 Data request
     * :{"pageId":"${alias}"
     * ,"appId":"usa-roku","name":"${title}","alias":"${alias}"
     * ,"modules":[],"pages":[],"description":"","meta":[]}
     *
     * Step 9: Validate JSON that was used to API by Shem Verify: Scheme is
     * valid
     */

    @Test(groups = {"page_publishing", "roku_smoke", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider", enabled = true)
    public void validatePageScheme(String brand) {

        // Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        // Step 2-3
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ANDROID);

        //Step 4-5
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());
        editPage.elementPublishBlock().publishByTabName();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing with message", webDriver);

        RokuPageJson expectedPageJson = new RokuPageJson().getObject(pageInfo, brand);
        RokuPageJson actualPageJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.PAGE);

        softAssert.assertEquals(expectedPageJson, actualPageJson, "The data is not matched", "The data is matched", new PageVerificator());
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage9985() {
        try {
            rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
        } catch (Exception e) {
            Utilities.logInfoMessage("This page can't be deleted!");
        }
    }
}
