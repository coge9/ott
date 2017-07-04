package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.page;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.ModuleIosVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 3/1/2017.
 */
public class TC17561_PublishPageToConcertoAPIByBulkOperation extends BaseAuthFlowTest {

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
     * 4.Publish Page to API by bulk operation
     * Verify: The POST request is sent to Amazon API! not TVE API
     * response status is success
     * <p/>
     * 5.Validate data in the POST request
     * Verify: he data is matched with medata set in the page and suite to the scheme as example below:
     {
     "uuid": "70db102c-52d8-4a78-8cb0-0cdf79713b3f",
     "itemType": "list",
     "revision": 4635432,
     "title": "Slasher Hero List",
     "slug": "slasher-hero-list-1",
     "shortDescription": null,
     "mediumDescription":null,
     "longDescription":null
     "tileType": null,
     "categories": [],
     "tags": [],
     "listItems": [
     {"uuid": "related modules UUID",
     "itemType": list"}]
     "program": {}}
     * private-e3bfd-concertoapiingestmaster.apiary-mock.com/json+schema/list
     **/

    private PageForm pageForm = null;

    @Test(groups = {"page_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasConcertoAPIDataProvider")
    public void chcekPageIOSPublish(String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2-4
        pageForm = rokuBackEndLayer.createPage(RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getConcertoPlatforms().get(0));
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());

        //Step 4
        TVEPage tvePage = mainRokuAdminPage.openOttPage(brand);
        tvePage.checkAnItem(pageForm.getTitle());
        tvePage.executePublishToServices();

        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        //Step 5
        String url = rokuBackEndLayer.getLogURL(brand);
        //Get Expected result
        CollectionJson expectedCollectionJson = new CollectionJson(pageForm);

        //Get Actual Post Request
        CollectionJson actualCollectionJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.PAGE);
        softAssert.assertTrue(new ModuleIosVerificator().verify(expectedCollectionJson, actualCollectionJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContentTC102013() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logWarningMessage("This page can't be deleted!");
        }
    }
}
