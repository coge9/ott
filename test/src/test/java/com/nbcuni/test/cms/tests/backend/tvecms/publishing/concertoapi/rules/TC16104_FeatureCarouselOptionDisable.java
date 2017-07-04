package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.rules;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.PublishInstance;
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
 * Created by Ivan_Karnilau on 9/28/2016.
 */
public class TC16104_FeatureCarouselOptionDisable extends BaseAuthFlowTest {

    /**
     * Pre-Condition:
     * Create Page with iOS platform
     * Steps:
     *
     * 1. Create feature Carousel Module
     * 2. Add Program to the list
     * 3. Check latest episode checkbox next to selected program
     * 4. Update assets By MPX Id (or Run cron) to update set data for latest epsiodes
     * 5. Assign module to the page created in pre-condition
     * 6. Publish Module to Concerto API
     *
     * Verify POST request to API:
     * 1. The 'collection list' was published to API with 'Success' response
     * 2. List of items contains GUID of latest episode related to the selected program
     * 3. UUID of the selected program do not present within items array from POST requestnt
     */

    private static PageForm pageForm = null;
    private FeatureCarouselForm featureCarousel;

    @Test(groups = {"rule_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    private void featureCarouselRuleIOS(String brand) {

        //Pre-condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Create page
        pageForm = rokuBackEndLayer.createPage(CmsPlatforms.IOS);

        //Step 1
        featureCarousel = (FeatureCarouselForm) rokuBackEndLayer.getModulesStrategyForConcerto(ShelfType.FEATURE_CAROUSEL);

        //Step 2
        featureCarousel = (FeatureCarouselForm) rokuBackEndLayer.createModule(featureCarousel);

        //Step 3
        DraftModuleTab draftModuleTab = new DraftModuleTab(webDriver, aid);
        featureCarousel = draftModuleTab.getFeatureCarouselInfo(featureCarousel);

        //Step 4
        mainRokuAdminPage.runCron(brand);

        //Step 5
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        pageForm = editPage.updateFields(Arrays.<Module>asList(featureCarousel));
        editPage.save();

        softAssert.assertTrue(draftModuleTab.elementPublishBlock().isPublishTabPresent(PublishInstance.DEV), "The Development instance tab is not present",
                "The Development tab is shown");


        //Step 6
        draftModuleTab.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(draftModuleTab.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        String url = rokuBackEndLayer.getLogURL(brand);
        //Get Expected result
        CollectionJson expectedCollectionJson = new CollectionJson(featureCarousel);

        //Get Actual Post Request
        CollectionJson actualCollectionJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.COLLECTIONS);
        softAssert.assertTrue(new ModuleIosVerificator().verify(expectedCollectionJson, actualCollectionJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            rokuBackEndLayer.deleteModule(featureCarousel.getTitle());
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error in tear down method, " + Utilities.convertStackTraceToString(e));
        }
    }
}
