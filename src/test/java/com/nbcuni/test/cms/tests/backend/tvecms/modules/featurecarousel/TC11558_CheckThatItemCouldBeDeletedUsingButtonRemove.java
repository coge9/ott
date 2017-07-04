package com.nbcuni.test.cms.tests.backend.tvecms.modules.featurecarousel;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
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

import java.util.List;

/**
 * TC11558
 *
 * Step 1: Go To Roku As Admin
 *
 * Step 2: Go To Module List Page /admin/ott/modules
 *
 * Step 3: Select Shelf created in pre-condition 'Test Feature Carousel shelf' and open to edit
 *
 * Step 4: Click 'Delete' button next to an item within curated list
 *
 * Step 5: Click Save
 * Verify: The module is saved with changes
 */
public class TC11558_CheckThatItemCouldBeDeletedUsingButtonRemove extends BaseAuthFlowTest {

    FeatureCarouselForm featureCarouselForm = CreateFactoryFeatureCarousel.createFeatureCarouselWithRandomTitle();

    @Test(groups = {"feature_carousel", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkThatContentOfModuleCouldntToAddClips(String brand) {

        Utilities.logInfoMessage("Check that item could be deleted using button 'remove'");

        SoftAssert softAssert = new SoftAssert();

        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);

        //Step 1
        MainRokuAdminPage mainRokuAdminPage = backEndLayer.openAdminPage();

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        List<String> fullEpisodes = contentPage.getRandomAssets(2, ContentType.TVE_VIDEO, ContentFormat.FULL_EPISODE);

        featureCarouselForm.setAssets(fullEpisodes);

        //Step 2 - 3
        DraftModuleTab featureCarouselPage = mainRokuAdminPage.openAddFeatureCarouselPage(brand);

        featureCarouselPage.createFeatureCarousel(featureCarouselForm);
        featureCarouselPage.clickSave();

        //Step 4
        featureCarouselPage.removeAsset(fullEpisodes.get(0));
        fullEpisodes.remove(0);

        //Step 5
        featureCarouselPage.clickSave();

        softAssert.assertEquals(featureCarouselPage.getAssets(), fullEpisodes,
                "Assets are equals. Expected: " + fullEpisodes + ", Actual: " + featureCarouselPage.getAssets() + "Assets are equals"
                , "The all assets were removed", webDriver);

        softAssert.assertAll();
        Utilities.logInfoMessage("Test has passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteModule11558() {
        try {
            rokuBackEndLayer.deleteModule(featureCarouselForm.getTitle());
        } catch (Exception e) {
            Utilities.logInfoMessage("This page can't be deleted!");
        }
    }
}
