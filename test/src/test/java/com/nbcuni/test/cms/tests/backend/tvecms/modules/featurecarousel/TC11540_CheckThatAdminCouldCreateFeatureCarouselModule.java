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
 * TC11540
 *
 * Step 1: Go To Roku As admin
 *
 * Step 2: Go To Module list
 *
 * Step 3: Fill all required fields and click save
 * Verify: The Module is saved.
 * -Filled information is present
 * -There are next tabs:
 * 1)View Published
 * 2)New Draft
 * 3)Manage schedule
 * 4)Manage revision
 */
public class TC11540_CheckThatAdminCouldCreateFeatureCarouselModule extends BaseAuthFlowTest {

    FeatureCarouselForm featureCarouselForm = CreateFactoryFeatureCarousel.createFeatureCarouselWithRandomTitle();

    @Test(groups = {"feature_carousel", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkThatAdminCouldCreateFeatureCarouselModule(String brand) {
        Utilities.logInfoMessage("Check that Admin could create Feature Carousel module");
        SoftAssert softAssert = new SoftAssert();

        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);

        //Step 1
        MainRokuAdminPage mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 2
        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        modulesPage.clickAddFeatureCarouselLink();

        DraftModuleTab featureCarouselPage = new DraftModuleTab(webDriver, aid);

        //Step 3
        featureCarouselForm = featureCarouselPage.createFeatureCarousel(featureCarouselForm);
        FeatureCarouselForm actualFeatureCarousel = featureCarouselPage.getFeatureCarouselInfo()
                .setCreatedDate(featureCarouselForm.getCreatedDate())
                .setModifiedDate(featureCarouselForm.getModifiedDate());

        softAssert.assertTrue(featureCarouselPage.isStatusMessageShown(), "Status message isn't shown",
                "Status message is shown");

        softAssert.assertEquals(featureCarouselForm, actualFeatureCarousel,
                "Feature Carousel is not corrected", "Feature Carousel is corrected");

        softAssert.assertTrue(featureCarouselPage.isViewPublishedVisible(), "The 'View Published' tab is not present or does not available",
                "The 'View Published' tab is present and available");

        softAssert.assertTrue(featureCarouselPage.isEditDraftVisible(), "The 'Edit Draft' tab is not present or does not available",
                "The 'Edit Draft' tab is present and available");

        softAssert.assertTrue(featureCarouselPage.isManageRevisionVisible(), "The 'Manage Revision' tab is not present or does not available",
                "The 'Manage Revision' tab is present and available");

        softAssert.assertAll();
        Utilities.logInfoMessage("Test has passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteModule11540() {
        try {
            rokuBackEndLayer.deleteModule(featureCarouselForm.getTitle());
        } catch (Exception e) {
            Utilities.logInfoMessage("This page can't be deleted!");
        }
    }
}
