package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.page;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 7/19/16.
 */
public class TC15332_MessageAttributesPageIos extends BaseAuthFlowTest {

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
     * 5.Check Message Attributes with Header of POST request
     * Verify:There are next messages
     * action=post
     * entityTypes=lists
     * private-e3bfd-concertoapiingestmaster.apiary-mock.com/json+schema/list
     **/

    private PageForm pageForm = null;

    @Test(groups = {"page_publishing", "roku-smoke", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasConcertoAPIDataProvider")
    public void checkHeaderAttributesOfPageIOSPublish(String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2-4
        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getConcertoPlatforms().get(0));
        rokuBackEndLayer.createPage(pageForm);
        EditPageWithPanelizer editPage = new EditPageWithPanelizer(webDriver, aid);

        //Step 3-4
        editPage.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        //Step 5
        String url = editPage.getLogURL(brand);
        Assertion.assertTrue(!requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.PAGE).isEmpty(),
                "Impossible to get POST request for Page pubishing to Concerto API");

        LocalApiJson localApiJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.PAGE).get(0);

        softAssert.assertTrue(localApiJson.getAttributes().getAction() != null, "The action message attribute are not present",
                "The action message attribute are present");

        softAssert.assertTrue(localApiJson.getAttributes().getEntityType() != null, "The entityType message attribute are not present",
                "The entityType message attribute are present");

        String action = localApiJson.getAttributes().getAction().getStringValue();

        softAssert.assertEquals(Action.POST.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");

        String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(ItemTypes.COLLECTIONS.getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logWarningMessage("This page can't be deleted!");
        }
    }
}
