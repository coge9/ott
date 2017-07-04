package com.nbcuni.test.cms.tests.backend.tvecms.modules.maxcount;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.factory.CreateFactoryFeatureCarousel;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ContentFormat;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alekca on 29.01.16.
 */
public class TC12430_FeaturedCarousel_CheckMaxCount extends BaseAuthFlowTest {
    FeatureCarouselForm featureCarouselForm = CreateFactoryFeatureCarousel.createFeatureCarouselWithRandomTitle();
    private String programOne;
    private String programTwo;
    private DraftModuleTab draftModuleTab;

    /**
     * Step 1. Go To roku as admin
     * Verify: The admin panel is present
     *
     * Step 2. Go to Modules and select feature carousel created in pre-condition
     * Verify: The Edit module Page is opened
     *
     * Step 3. Add a Program and Video to the Content list
     * Verify: The items are added
     * There is show latest option and max count next to Program only!
     *
     * Step 4. Check max Count field
     * Verify: The field is not editable
     * Max Count is not present. If look into DOM model it should have the default hidden value = 1
     * */


    @Test(groups = {"max_count"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkMaxCountForFeaturedCarousel(final String brand) {
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        List<String> programs = contentPage.getRandomAssets(2, ContentType.TVE_PROGRAM, ContentFormat.FULL_EPISODE);
        programOne = programs.get(0);
        programTwo = programs.get(1);
        List<String> assetsList = Arrays.asList(programOne, programTwo);

        //Step 3 Create test feature carousel module
        featureCarouselForm.setAssets(assetsList);
        draftModuleTab = mainRokuAdminPage.openAddFeatureCarouselPage(brand);
        draftModuleTab.createFeatureCarousel(featureCarouselForm);

        //Step 4
        draftModuleTab.checkLatestEpisodeByName(programOne);
        draftModuleTab.checkLatestEpisodeByName(programTwo);
        draftModuleTab.clickSave();
        softAssert.assertFalse(draftModuleTab.isMaxCountPresent(programOne), "The Max Count field is visible for :" + programOne, "Max Count field is  not present", webDriver);
        softAssert.assertFalse(draftModuleTab.isMaxCountPresent(programTwo), "The Max Count field is visible for :" + programTwo, "Max Count field is not present", webDriver);
        softAssert.assertAll("Test failed");
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void logoutTC12487() {
        mainRokuAdminPage.logOut(brand);
    }
}
