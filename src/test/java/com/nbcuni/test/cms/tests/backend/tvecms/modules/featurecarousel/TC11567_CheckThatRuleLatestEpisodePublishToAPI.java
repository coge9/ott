package com.nbcuni.test.cms.tests.backend.tvecms.modules.featurecarousel;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.factory.CreateFactoryFeatureCarousel;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ContentFormat;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuFeatureCarouselJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * TC11567
 *
 * Step 1: Go to Roku as Admin
 *
 * Step 2: Go To Module list Page Dashboard » Modules
 *
 * Step 3: Open created in pre-condition shelf 'Test Featured Carousel'
 *
 * Step 4: Add Program 1,Program 2 and Program 3 to the content
 *
 * Step 5: Select Program 1 and Program 3 within the carousel item and Save
 *
 * Step 6: Publish module to API
 * Verify: The Module is published
 * The response from server is 201. Created
 * The request send to API contains rule like:
 * 1.Program 1 and Program 2 with latest episode scheme - TO BE UPDATED AS SOON SCHEME WILL BE DEFINED
 */
public class TC11567_CheckThatRuleLatestEpisodePublishToAPI extends BaseAuthFlowTest {

    FeatureCarouselForm featureCarouselForm = CreateFactoryFeatureCarousel.createRandomFeatureCarousel();
    private PageForm pageForm = CreateFactoryPage.createDefaultPageWithAlias();

    @Test(groups = {"feature_carousel"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkRulePublishingToAPI(String brand) {

        Utilities.logInfoMessage("Check That Rule 'Latest Episode' Publish to API");

        SoftAssert softAssert = new SoftAssert();

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        MainRokuAdminPage mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        contentPage.searchByType(ContentType.TVE_PROGRAM).apply();

        String programOne = contentPage.getRandomAsset(ContentType.TVE_PROGRAM, ContentFormat.FULL_EPISODE);
        String programTwo = contentPage.getRandomAsset(ContentType.TVE_PROGRAM, ContentFormat.FULL_EPISODE);
        String programThree = contentPage.getRandomAsset(ContentType.TVE_PROGRAM, ContentFormat.FULL_EPISODE);

        List<String> assetsList = Arrays.asList(programOne, programTwo, programThree);

        featureCarouselForm.setAssets(assetsList).setAssetsCount(assetsList.size());
        //Step 2 - 3
        DraftModuleTab featureCarouselPage = mainRokuAdminPage.openAddFeatureCarouselPage(brand);

        //Step 4
        featureCarouselPage.createFeatureCarousel(featureCarouselForm);
        featureCarouselPage.clickSave();

        mainRokuAdminPage.openOttModulesPage(brand).clickEditLink(featureCarouselForm.getTitle());

        featureCarouselPage = new DraftModuleTab(webDriver, aid);

        //Step 5
        featureCarouselPage.checkLatestEpisodeByName(programOne);
        featureCarouselPage.checkLatestEpisodeByName(programThree);
        featureCarouselPage.clickSave();

        pageForm = rokuBackEndLayer.createPage(pageForm);
        EditPageWithPanelizer editPage = new EditPageWithPanelizer(webDriver, aid);
        editPage.setModule(featureCarouselForm.getTitle());
        editPage.save();

        //Step 6
        editPage.elementPublishBlock().publishByTabName();
        String url = editPage.getLogURL(brand);

        RokuFeatureCarouselJson featureCarouselJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.MODULE);

        softAssert.assertAll();
        Utilities.logInfoMessage("Test has passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp() {
        rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        rokuBackEndLayer.deleteModule(featureCarouselForm.getTitle());
    }
}
