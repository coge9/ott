package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.rules;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.Video;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.ShelfType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.ModuleIosVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 9/29/2016.
 */
public class TC16106_IngestedLatestEpisodeIsPublishedFeatureCarousel extends BaseAuthFlowTest {

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
     * 3. UUID of the selected program do not present within items array from POST request
     */

    private static PageForm pageForm = null;
    private FeatureCarouselForm featureCarousel;
    private FeatureCarouselForm newFeatureCarousel;
    private List<String> programsToAdd;

    private List<String> getProgram() {
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByPublishedState(PublishState.YES).searchByType(ContentType.TVE_PROGRAM).apply();
        programsToAdd = contentPage.getAllContentList();
        return programsToAdd;
    }

    @Test(groups = {"rule_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    private void featureCarouselRuleIOS(String brand) {

        //Pre-condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Create page
        pageForm = rokuBackEndLayer.createPage(CmsPlatforms.IOS);

        List<String> programs = getProgram();

        //Step 1
        featureCarousel = (FeatureCarouselForm) rokuBackEndLayer.getRulesStrategyForConcerto(ShelfType.FEATURE_CAROUSEL, programs, 1);

        //Step 2
        featureCarousel = (FeatureCarouselForm) rokuBackEndLayer.createModule(featureCarousel);

        //Step 3
        DraftModuleTab draftModuleTab = new DraftModuleTab(webDriver, aid);
        draftModuleTab.checkLatestEpisodeByName(programsToAdd).clickSave();
        featureCarousel = draftModuleTab.getFeatureCarouselInfo(featureCarousel);

        //Step 4
        mainRokuAdminPage.runCron(brand);

        //Step 5
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        pageForm = editPage.updateFields(Arrays.<Module>asList(featureCarousel));
        editPage.save();

        //Step 6
        draftModuleTab.elementPublishBlock().publishByTabName();

        String url = rokuBackEndLayer.getLogURL(brand);
        //Get Expected result
        CollectionJson expectedCollectionJson = new CollectionJson(featureCarousel);

        //Get Actual Post Request
        CollectionJson actualCollectionJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.COLLECTIONS);
        softAssert.assertTrue(new ModuleIosVerificator().verify(expectedCollectionJson, actualCollectionJson), "The actual data is not matched", "The JSON data is matched");

        newFeatureCarousel = (FeatureCarouselForm) rokuBackEndLayer.getRulesStrategyForConcerto(ShelfType.FEATURE_CAROUSEL, programs, 1);
        featureCarousel.setContents(newFeatureCarousel.getContents());
        expectedCollectionJson = new CollectionJson(featureCarousel);

        for (Content content : featureCarousel.getContents()) {
            mainRokuAdminPage.openMpxUpdaterPage(brand).runUpdaterByMPXID(((Video) content).getMpxAsset().getId());
        }

        url = rokuBackEndLayer.getLogURL(brand);
        actualCollectionJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.COLLECTIONS);
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
