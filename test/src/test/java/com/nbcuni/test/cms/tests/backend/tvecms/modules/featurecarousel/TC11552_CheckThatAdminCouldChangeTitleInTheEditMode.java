package com.nbcuni.test.cms.tests.backend.tvecms.modules.featurecarousel;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.factory.CreateFactoryFeatureCarousel;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * TC11552
 *
 * Step 1: Go To Roku CMS as Admin
 *
 * Step 2: Go to Module list /admin/ott/modules
 *
 * Step 3: Create 'Feature Carousel module'
 *
 * Step 4: Open the created module on Edit
 *
 * Step 5: Edit title to 'test' and save
 * Verify: The module saved with title 'test'
 */
public class TC11552_CheckThatAdminCouldChangeTitleInTheEditMode extends BaseAuthFlowTest {
    FeatureCarouselForm featureCarouselForm = CreateFactoryFeatureCarousel.createFeatureCarouselWithRandomTitle();
    FeatureCarouselForm featureCarouselNewForm = CreateFactoryFeatureCarousel.createFeatureCarouselWithRandomTitle();

    @Test(groups = {"feature_carousel", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkThatAdminCouldChangeTitleInTheEditMode(String brand) {
        Utilities.logInfoMessage("Check that Admin could change title in the Edit Mode");
        SoftAssert softAssert = new SoftAssert();

        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);

        //Step 1
        MainRokuAdminPage mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 2
        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        modulesPage.clickAddFeatureCarouselLink();

        DraftModuleTab featureCarouselPage = new DraftModuleTab(webDriver, aid);

        //Step 3
        featureCarouselPage.createFeatureCarousel(featureCarouselForm);

        featureCarouselPage.clickSave();

        softAssert.assertTrue(featureCarouselPage.isStatusMessageShown(), "Status message isn't shown",
                "Status message is shown");

        softAssert.assertEquals(featureCarouselForm.getTitle(), featureCarouselPage.getFeatureCarouselInfo().getTitle(),
                "Feature Carousel Title is not corrected", "Feature Carousel Title is corrected");

        //Step 4 - 5
        featureCarouselPage.createFeatureCarousel(featureCarouselNewForm);

        softAssert.assertTrue(featureCarouselPage.isStatusMessageShown(), "Status message isn't shown",
                "Status message is shown");

        softAssert.assertEquals(featureCarouselNewForm.getTitle(), featureCarouselPage.getFeatureCarouselInfo().getTitle(),
                "Feature Carousel Title is not corrected", "Feature Carousel Title is corrected");

        softAssert.assertAll();
        Utilities.logInfoMessage("Test has passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteModule11552() {
        try {
            rokuBackEndLayer.deleteModule(featureCarouselForm.getTitle());
        } catch (Exception e) {
            Utilities.logInfoMessage("This page can't be deleted!");
        }
    }
}
