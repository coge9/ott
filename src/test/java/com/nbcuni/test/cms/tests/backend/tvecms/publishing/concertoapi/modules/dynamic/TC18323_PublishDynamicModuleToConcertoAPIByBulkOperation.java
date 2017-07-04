package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.modules.dynamic;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.dynamic.DynamicModulePage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic.Dynamic;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic.factory.CreationFactoryDynamicModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.transformers.ModuleJsonTransformer;
import com.nbcuni.test.cms.verification.roku.ModuleIosVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Pre-Conditions:
 * Go to CMS and create a Page with platform wich published to concerto!
 * Make sure:
 * There is a primary (e.g Development )API Endpoint
 * There is an Amazon endpoint
 * Amazon Endponit is configured to be point to the primary Development
 * Steps:

 * 1. Go to CMS  as user
 * The CMS User Panel is present
 * 2. Go to TVE Modules page /admin/ott/modules and create a Dynamic Module
 * The Dynamic module has Created
 * 3. Go to Pages page /admin/ott/pages
 * The list of Pages is present
 * There is Page craeted in Precondition
 * 4. Open Page on edit and add careted in step #2 Dynamic module. Save
 * The Pages is saved with added Dynamic module
 * 5. Publish Module to Concerto API by bulk operation
 * The POST request has sent
 * 6. Validate data in JSON
 * The JSON data is matched with data within module and suite to the scheme like example below:
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

public class TC18323_PublishDynamicModuleToConcertoAPIByBulkOperation extends BaseAuthFlowTest {

    /**
     * Pre-conditions:
     * 1. Login in CMS
     * 2. Make sure concerto API instance is configured on brand
     * 3. Make sure Ios platform is configured on brand
     * 4. Create Page with required fields.
     * Steps:
     * 1. Navigate TVE Pages
     * Verify: TVE Pages is opened.
     * <p>
     * 2. Delete the page we have created in pre-conditions by bulk operation.
     * Verify: Page is deleted successfully. Statue message is present. Delete message sent to Concerto API.
     * <p>
     * 3. Check Post request for node.
     */

    private static PageForm pageForm;
    private Dynamic dynamic = CreationFactoryDynamicModule.createDynamicForVideo();

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void publishDynamicModuleToConcertoAPIByBulkOperation(String brand) {

        //Step 1
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
        CollectionJson expectedCollectionJson = ModuleJsonTransformer.fromDynamicModuleToCollection(modulePage.getData(), brand);

        //Step 6

        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        modulesPage.checkAnItem(dynamic.getTitle());
        modulesPage.executePublishToServices();
        //Step 5
        String url = modulesPage.getLogURL(brand);
        //Get Actual Post Request

        CollectionJson actualCollectionJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.MODULE);

        softAssert.assertTrue(new ModuleIosVerificator().verify(expectedCollectionJson, actualCollectionJson),
                "The actual data is not matched", "The JSON data is matched");

        LocalApiJson actualMetadata = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.MODULE).get(0);
        String action = actualMetadata.getAttributes().getAction().getStringValue();
        softAssert.assertEquals(Action.POST.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");
        String entityType = actualMetadata.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(ItemTypes.COLLECTIONS.getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.deleteModule(dynamic.getTitle());
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error in tear down method, " + Utilities.convertStackTraceToString(e));
        }
    }
}
