package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.modules.featureshow;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.FeatureShowModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.CreateFactoryModule;
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
 * Created by Ivan Karnilau on 3/15/17.
 */

/**
 * Pre-Conditions:
 * 1. Create new IOS/AppleTV page
 * 2. Create new Custom module and add items
 * 3. Add created module to created page
 *
 * Steps:
 * 1. Open edit page for module
 * Edit page is opened
 * 2. Uncheck "Enable" checkbox for random item
 * Checkbox is unchecked
 * 3. Publish module
 * Module is published
 * Validation    Check publish log for module for Concerto API
 * Massive "listItems" doesn't contain exluded item
 */

public class TC17676_PublishFeatureShowlModuleIOSWithUncheckedItem extends BaseAuthFlowTest {

    private PageForm pageForm = null;
    private DraftModuleTab draftModuleTab;
    private FeatureShowModule featureShowModule;

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkAllFieldsModulePublishingConcerto(String brand) {

        //Step 1

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //pre-condition get Module
        featureShowModule = (FeatureShowModule) rokuBackEndLayer.getModulesStrategyForConcerto(ShelfType.FEATURE_SHOWS);
        CreateFactoryModule.disableRandomAsset(featureShowModule);

        //Step 2
        featureShowModule = (FeatureShowModule) rokuBackEndLayer.createModule(featureShowModule);

        //Step 3

        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getConcertoPlatforms().get(0));
        rokuBackEndLayer.createPage(pageForm);

        //Step 4
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        pageForm = editPage.updateFields(Arrays.<Module>asList(featureShowModule));
        editPage.save();

        //Step 5
        draftModuleTab = mainRokuAdminPage.openOttModulesPage(brand).clickEditLink(featureShowModule.getTitle());

        //Step 6
        draftModuleTab.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(draftModuleTab.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        //Step 7
        String url = rokuBackEndLayer.getLogURL(brand);
        //Get Expected result
        CollectionJson expectedCollectionJson = new CollectionJson(featureShowModule);

        //Get Actual Post Request
        CollectionJson actualCollectionJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.MODULE);
        softAssert.assertTrue(new ModuleIosVerificator().verify(expectedCollectionJson, actualCollectionJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.deleteModule(featureShowModule.getTitle());
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logWarningMessage("Error in tear down method, " + e.getMessage());
        }
    }

}
