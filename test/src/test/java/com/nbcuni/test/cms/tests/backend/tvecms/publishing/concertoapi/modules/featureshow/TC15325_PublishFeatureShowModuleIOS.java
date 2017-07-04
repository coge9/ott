package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.modules.featureshow;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.FeatureShowModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.ShelfType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.ModuleIosVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Aleksandra_Lishaeva on 7/12/16.
 */
public class TC15325_PublishFeatureShowModuleIOS extends BaseAuthFlowTest {

    /**
     * Go to CMS and create a Page with IOS platform!
     * Make sure:
     * <p/>
     * There is a primary (e.g Development )API Endpoint
     * There is an Amazon endpoint
     * Amazon Endponit is configured to be point to the primary Development
     * <p/>
     * Steps:
     * 1.Go to CMS  as user
     * Verify: The CMS User Panel is present
     * <p/>
     * 2.Go to TVE Modules page /admin/ott/modules and create a Feature Show Module
     * Verify: The feature show module has Created
     * <p/>
     * 3.Go to Pgaes page /admin/ott/pages
     * Verify: The list of Pages is present
     * There is Page created in Precondition
     * <p/>
     * 4.Open Page on edit and add created in step #2 feature show module. Save
     * Verify: The Pages is saved with added feature show module
     * <p/>
     * 5.Go to the Module Edit page created in step #2
     * Verify: The Module Edit Page has Opened
     * There is Enabled Publishing Control
     * Only Primary, not Amazon endpoint is available(e.g Development)
     * <p/>
     * 6.Publish Module to Amazon API by clicking on Primary(Dev endpoint)
     * The POST request has sent
     * 7.Validate data in JSON
     * Verify: The JSON data is matched with data within module and suite to the scheme like example below:
     * {
     * "uuid": "70db102c-52d8-4a78-8cb0-0cdf79713b3f",
     * "itemType": "list",
     * "revision": 4635432,
     * "title": "Slasher Hero List",
     * "slug": "slasher-hero-list-1",
     * "shortDescription": null,
     * "mediumDescription":null,
     * "longDescription":null
     * "tileType": 3,
     * "categories": [],
     * "tags": [],
     * "listItems": [
     * {"uuid": "70db102c-52d8-4a78-8cb0-0cdf79713b3f",
     * "itemType": "video" (or program)}]
     * "program": {}}
     */

    private PageForm pageForm = null;
    private DraftModuleTab draftModuleTab;
    private FeatureShowModule featureShow;

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkAllFieldsModulePublishingConcerto(String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //pre-condition get Module
        featureShow = (FeatureShowModule) rokuBackEndLayer.getModulesStrategyForConcerto(ShelfType.FEATURE_SHOWS);

        //Step 2
        featureShow = (FeatureShowModule) rokuBackEndLayer.createModule(featureShow);

        //Step 3

        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getConcertoPlatforms().get(0));
        rokuBackEndLayer.createPage(pageForm);

        //Step 4
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        pageForm = editPage.updateFields(Arrays.<Module>asList(featureShow));
        editPage.save();

        //Step 5
        draftModuleTab = mainRokuAdminPage.openOttModulesPage(brand).clickEditLink(featureShow.getTitle());

        //Step 6
        draftModuleTab.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(draftModuleTab.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        //Step 7
        String url = draftModuleTab.getLogURL(brand);
        //Get Expected result
        CollectionJson expectedCollectionJson = new CollectionJson(featureShow);

        //Get Actual Post Request
        CollectionJson actualCollectionJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.MODULE);
        softAssert.assertTrue(new ModuleIosVerificator().verify(expectedCollectionJson, actualCollectionJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.deleteModule(featureShow.getTitle());
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logWarningMessage("Error in tear down method, " + e.getMessage());
        }
    }

}
