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
 * TC11542
 *
 * Step 1: Go To Roku As admin
 *
 * Step 2: Go To Module list
 *
 * Step 3: Type module title and save
 * Verify: The module is saved
 * The Next Properties are set by default:
 * -Tile variant - 1
 * -Title: "Feature Carousel"
 * -Alias:"carousel"
 */
public class TC11542_CheckDefaultPropertiesForModuleCreation extends BaseAuthFlowTest {

    FeatureCarouselForm featureCarouselForm = CreateFactoryFeatureCarousel.createDefaultFeatureCarousel();

    @Test(groups = {"feature_carousel"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkDefaultPropertiesForModuleCreation(String brand) {
        Utilities.logInfoMessage("Check default Properties for module creation");
        SoftAssert softAssert = new SoftAssert();

        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);

        //Step 1
        MainRokuAdminPage mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 2
        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        DraftModuleTab featureCarouselPage = modulesPage.clickAddFeatureCarouselLink();

        //Step 3
        featureCarouselPage.createFeatureCarousel(featureCarouselForm);
        FeatureCarouselForm actualFeatureCarousel = featureCarouselPage.getFeatureCarouselInfo()
                .setCreatedDate(featureCarouselForm.getCreatedDate())
                .setModifiedDate(featureCarouselForm.getModifiedDate());
        softAssert.assertEquals(featureCarouselForm, actualFeatureCarousel,
                "Feature Carousel is not corrected", "Feature Carousel is corrected");

        softAssert.assertAll();
        Utilities.logInfoMessage("Test has passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteModule11542() {
        try {
            rokuBackEndLayer.deleteModule(featureCarouselForm.getTitle());
        } catch (Exception e) {
            Utilities.logInfoMessage("This page can't be deleted!");
        }
    }
}
