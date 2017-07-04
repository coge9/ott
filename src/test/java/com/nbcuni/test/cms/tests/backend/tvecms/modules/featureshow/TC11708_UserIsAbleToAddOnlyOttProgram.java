package com.nbcuni.test.cms.tests.backend.tvecms.modules.featureshow;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist.additempopup.AddItemsPopupForShowBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ContentFormat;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

import static com.nbcuni.test.cms.pageobjectutils.MessageConstants.TITLE_ADD_FEATURE_SHOW;

/**
 * Created by Alena_Aukhukova
 */
public class TC11708_UserIsAbleToAddOnlyOttProgram extends BaseAuthFlowTest {
    /**
     * Pre-condition
     * 1. Login in Roku CMS as Admin
     * 2. Go to content page
     * 3. Choose random published ott video [video name]
     *
     * Step 1: Go to /admin/ott/modules/add/feature_shows
     * Verify: Page Title is 'Add feature shows module'
     *
     * Step 2: Click on Add Items popup
     * Verify: Popup is opened
     *
     * Step 3: Try to find [video name]
     * Verify: Result "No assets found"
     */
    @Test(groups = {"feature_show_module", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc11708(final String brand) {
        Utilities.logInfoMessage("Check that user is able to add only ott program");
        //Pre-condition
        this.brand = brand;
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        MainRokuAdminPage mainRokuAdminPage = backEndLayer.openAdminPage();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.searchByPublishedState(PublishState.YES);
        String videoTitle = contentPage.getRandomAsset(ContentType.TVE_VIDEO, ContentFormat.SHORT_EPISODE);
        //Step 1
        DraftModuleTab addFeatureShowModule = mainRokuAdminPage.openAddFeatureShowPage(brand);
        softAssert.assertEquals(TITLE_ADD_FEATURE_SHOW, addFeatureShowModule.getPageTitle(), "Text of page title");
        AddItemsPopupForShowBlock popup = (AddItemsPopupForShowBlock) addFeatureShowModule.elementContentList().clickAddItems();
        List<String> result = popup.getAssetTitles();
        softAssert.assertFalse(result.contains(videoTitle), "Video node presence");
        softAssert.assertAll();
    }

}
