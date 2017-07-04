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
 * 1. Go to CMS
 * 2. Create new dynamic module with:
 * Content type: Program
 * Maximum content items: [0-50]
 * 3. Publish

 * Validation:
 * Check publish log
 * Expected:
 * Dynamic vodule was published
 * Items are programs
 * Items are sorted by title
 */

public class TC18320_PublishPrograms extends BaseAuthFlowTest {

    private static PageForm pageForm;
    private Dynamic dynamic = CreationFactoryDynamicModule.createDynamicForProgram();

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void publishPrograms(String brand) {

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

        //Step 6
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
            rokuBackEndLayer.deleteModule(dynamic.getTitle());
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error in tear down method, " + Utilities.convertStackTraceToString(e));
        }
    }
}
