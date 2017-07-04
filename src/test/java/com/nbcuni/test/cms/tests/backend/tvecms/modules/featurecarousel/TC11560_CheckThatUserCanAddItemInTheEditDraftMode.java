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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TC11560
 *
 * Step 1: Go To CMS as admin
 *
 * Step 2: Go To module list /admin/ott/modules
 *
 * Step 3: Select module created in pre-condition 'Test Featured Carousel' and open to Edit
 * Verify: The 'Edit Draft' moule tab is present
 * There is button 'Add Item'
 * The module is opened with values set in pre-condition
 */
public class TC11560_CheckThatUserCanAddItemInTheEditDraftMode extends BaseAuthFlowTest {

    FeatureCarouselForm featureCarouselForm = CreateFactoryFeatureCarousel.createRandomFeatureCarousel();

    @Test(groups = {"feature_carousel", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkThatUserCouldDeleteAsset(String brand) {

        Utilities.logInfoMessage("Check that user can add item in the Edit Draft Mode");

        SoftAssert softAssert = new SoftAssert();

        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);

        //Step 1
        MainRokuAdminPage mainRokuAdminPage = backEndLayer.openAdminPage();

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        String fullEpisode = contentPage.getRandomAsset(ContentType.TVE_VIDEO, ContentFormat.FULL_EPISODE);

        List<String> assetsList = new ArrayList<>(Arrays.asList(fullEpisode));

        featureCarouselForm.setAssets(assetsList).setAssetsCount(assetsList.size());

        //Step 2 - 3
        DraftModuleTab featureCarouselPage = mainRokuAdminPage.openAddFeatureCarouselPage(brand);

        featureCarouselPage.createFeatureCarousel(featureCarouselForm);

        featureCarouselPage = mainRokuAdminPage.openOttModulesPage(brand).clickEditLink(featureCarouselForm.getTitle());
        String asset = featureCarouselPage.addRandomAsset();
        featureCarouselPage = featureCarouselPage.clickSave();

        featureCarouselForm.setAssets(asset);
        featureCarouselForm.setRevision(featureCarouselPage.getRevisionEntity());

        FeatureCarouselForm actualFeatureCarousel = featureCarouselPage.getFeatureCarouselInfo()
                .setCreatedDate(featureCarouselForm.getCreatedDate())
                .setModifiedDate(featureCarouselForm.getModifiedDate());

        softAssert.assertTrue(featureCarouselPage.isAddAnotherItemPresent(), "'Add Item' link is not present",
                "Add Item link is present", webDriver);

        softAssert.assertEquals(featureCarouselForm, actualFeatureCarousel,
                "Feature carousel is not correct", "Feature carousel is correct");

        softAssert.assertAll();
        Utilities.logInfoMessage("Test has passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteModule11560() {
        try {
            rokuBackEndLayer.deleteModule(featureCarouselForm.getTitle());
        } catch (Exception e) {
            Utilities.logInfoMessage("This page can't be deleted!");
        }
    }
}
