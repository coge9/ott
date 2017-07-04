package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.modules.dynamic;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.dynamic.DynamicModulePage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic.Dynamic;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic.factory.CreationFactoryDynamicModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.transformers.ModuleJsonTransformer;
import com.nbcuni.test.cms.verification.roku.ModuleIosVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 7/12/16.
 */
public class TC18345_PublishUpdatesOfDynamicModuleConcerto extends BaseAuthFlowTest {

    private static PageForm pageForm;
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
     * 2.Go to TVE Modules page /admin/ott/modules and create a Custom Module
     * Verify: The custom module has Created
     * <p/>
     * 3.Go to Pages page /admin/ott/pages
     * Verify: The list of Pages is present
     * There is Page created in Precondition with IOS platform
     * <p/>
     * 4.Open Page on edit and add created in step #2 custom module. Save
     * Verify: The Pages is saved with added custom module
     * <p/>
     * 5.Go to the Module Edit page created in step #2
     * Verify: The Module Edit Page has Opened
     * There is Enabled Publishing Control
     * Only Primary, not Amazon endpoint is available(e.g Development)
     * <p/>
     * 6.Publish Module to Amazon API by clicking on Primary(Dev endpoint)
     * The POST request has sent
     * <p/>
     * 7.Go to THe Edit page of the module above and update all fields including list.
     * Save changes.
     * Verify: The changes has saved
     * <p/>
     * 8.Publish module to API by clicking on Primary endpoint (publish to Development button)
     * The POST request has send to Amzon endpoint! (not TVE API)
     * success status is displayed within response
     * <p/>
     * 9.Validate data in JSON
     * Verify: The JSON data is matched with data within updated module and suite to the scheme like example below:
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

    private Dynamic dynamic = CreationFactoryDynamicModule.createDynamicForVideo();
    private Dynamic updatedDynamic = CreationFactoryDynamicModule.createDynamicForProgram();

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void publishAllVideosSortByAirDate(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        rokuBackEndLayer.createModule(dynamic);

        //Step 3

        List<CmsPlatforms> platforms = RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getConcertoPlatforms();
        CmsPlatforms concertoPlatform = platforms.get(random.nextInt(platforms.size()));
        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(concertoPlatform);
        rokuBackEndLayer.createPage(pageForm);

        //Step 4
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        pageForm = editPage.updateFields(Arrays.<Module>asList(dynamic));
        editPage.save();

        //Step 5
        DynamicModulePage modulePage = mainRokuAdminPage.openOttModulesPage(brand).editModule(dynamic.getTitle(), DynamicModulePage.class);

        //Step 6
        modulePage.publish();

        modulePage = mainRokuAdminPage.openOttModulesPage(brand).editModule(dynamic.getTitle(), DynamicModulePage.class);
        modulePage.create(updatedDynamic);

        modulePage = mainRokuAdminPage.openOttModulesPage(brand).editModule(updatedDynamic.getTitle(), DynamicModulePage.class);

        modulePage.publish();
        softAssert.assertTrue(modulePage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(modulePage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        //Step 7
        String url = modulePage.getLogURL(brand);
        //Get Expected result
        CollectionJson expectedCollectionJson = ModuleJsonTransformer.fromDynamicModuleToCollection(modulePage.getData(), brand);

        //Get Actual Post Request

        CollectionJson actualCollectionJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.MODULE);
        softAssert.assertTrue(new ModuleIosVerificator().verify(expectedCollectionJson, actualCollectionJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.deleteModule(updatedDynamic.getTitle());
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error in tear down method, " + Utilities.convertStackTraceToString(e));
        }
    }
}
