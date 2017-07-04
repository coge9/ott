package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.page;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.PlatformApi;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.ConcertoJsonValidatorApiary;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 7/19/16.
 */
public class TC15331_ValidateSchemePageIos extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * Make sure:
     * There is a primary (e.g Development ) TVE API Endpoint
     * There is an Amazon endpoint
     * Amazon Endponit is configured to be point to the primary Development
     * <p/>
     * Steps:
     * 1.Go to the CMS As Admin
     * Verify: The Admin Panel is present
     * <p/>
     * 2.Go to the pages list page /admin/ott/pages
     * Verify: The list of pages is present
     * <p/>
     * 3.Create Page with all fields for IOS platform
     * Verify: The Page is created
     * Page assigned to IOS platform and layout
     * There is available Publishing Controls
     * Only Primary (development) endpoint is available, not Amazon!
     * <p/>
     * 4.Publish Page to API by clicking button 'publish to ${Development}'
     * Verify: The POST request is sent to Amazon API! not TVE API
     * response status is success
     * <p/>
     * 5.Validate JSON scheme within POST request
     * Verify: The JSON scheme is matched with scheme availbale by link below:
     * private-e3bfd-concertoapiingestmaster.apiary-mock.com/json+schema/list
     **/

    private PageForm pageForm = null;

    @Test(groups = {"page_publishing", "roku_smoke", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void validateSchemeOfPageIOSPublish(String brand) {

        //Step 1
        CmsPlatforms platform = RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getRandomConcertoPlatform();
        softAssert.assertTrue(new PlatformApi(brand).isPlatformConfigured(platform), "Platform " + platform + " was chosen for test but it is not configured in CMS Admin");
        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(platform);
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2-4
        pageForm = rokuBackEndLayer.createPage(pageForm);
        EditPageWithPanelizer editPage = new EditPageWithPanelizer(webDriver, aid);

        //Step 4
        editPage.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        //Step 5
        String url = rokuBackEndLayer.getLogURL(brand);

        //Get Actual Post Request
        Assertion.assertTrue(!requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.PAGE).isEmpty(),
                "It's impossible to get POST request, result is empty: " + requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.PAGE));

        LocalApiJson localApiJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.PAGE).get(0);
        softAssert.assertTrue(ConcertoJsonValidatorApiary.validateJson(localApiJson.getRequestData().toString(), ItemTypes.COLLECTIONS), "The validation has failed", "The validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error in tear down method, " + Utilities.convertStackTraceToString(e));
        }
    }
}
