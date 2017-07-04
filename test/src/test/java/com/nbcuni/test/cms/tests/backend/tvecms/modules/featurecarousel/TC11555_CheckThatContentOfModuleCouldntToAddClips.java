package com.nbcuni.test.cms.tests.backend.tvecms.modules.featurecarousel;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.factory.CreateFactoryFeatureCarousel;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ContentFormat;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * TC11555
 *
 * Step 1: Go To Roku As admin
 *
 * Step 2: Click 'Add Featured Carousel' link
 *
 * Step 3: Fill title
 *
 * Step 4: Put 'Program Test' into autocomplete and select
 *
 * Step 5: Put 'Full Episode Test' into autocomplete and select
 *
 * Step 6: Put 'Short Episode Test' into autocomplete and try select
 * Verify: The Short Episode Test could not be selected
 */
public class TC11555_CheckThatContentOfModuleCouldntToAddClips extends BaseAuthFlowTest {

    FeatureCarouselForm featureCarouselForm = CreateFactoryFeatureCarousel.createFeatureCarouselWithRandomTitle();

    @Test(groups = {"feature_carousel", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkThatContentOfModuleCouldntToAddClips(String brand) {

        Utilities.logInfoMessage("Check that content of module couldn't to add clips");

        SoftAssert softAssert = new SoftAssert();

        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);

        //Step 1
        MainRokuAdminPage mainRokuAdminPage = backEndLayer.openAdminPage();

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        String shortEpisode = contentPage.getRandomAsset(ContentType.TVE_VIDEO, ContentFormat.SHORT_EPISODE);
        String fullEpisode = contentPage.getRandomAsset(ContentType.TVE_VIDEO, ContentFormat.FULL_EPISODE);

        String program = contentPage.getRandomAsset(ContentType.TVE_PROGRAM, ContentFormat.FULL_EPISODE);

        List<String> assetsList = Arrays.asList(program, fullEpisode, shortEpisode);
        List<String> expectedAssetsList = Arrays.asList(program, fullEpisode);

        featureCarouselForm.setAssets(assetsList);

        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);

        //Step 2
        DraftModuleTab featureCarouselPage = modulesPage.clickAddFeatureCarouselLink();

        //Step 3 - 6
        featureCarouselPage.createFeatureCarousel(featureCarouselForm);
        featureCarouselPage.customSave();

        featureCarouselForm.setAssets(expectedAssetsList);

        softAssert.assertTrue(featureCarouselPage.isStatusMessageShown(), "Status message isn't shown",
                "Status message is shown", webDriver);

        List<String> actualAssets = featureCarouselForm.getAssets();
        List<String> expectedAssets = featureCarouselPage.getFeatureCarouselInfo().getAssets();
        softAssert.assertTrue(featureCarouselForm.equalsAssets(actualAssets, expectedAssets),
                "Assets are not equals. Expected: " + expectedAssets + ", Actual: " + actualAssets, "Assets are equals", webDriver);

        softAssert.assertAll();
        Utilities.logInfoMessage("Test has passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteModule11555() {
        try {
            rokuBackEndLayer.deleteModule(featureCarouselForm.getTitle());
        } catch (Exception e) {
            Utilities.logInfoMessage("This page can't be deleted!");
        }
    }
}
