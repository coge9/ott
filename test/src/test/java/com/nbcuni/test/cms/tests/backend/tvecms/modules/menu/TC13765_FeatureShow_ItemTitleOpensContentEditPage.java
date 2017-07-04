package com.nbcuni.test.cms.tests.backend.tvecms.modules.menu;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MediaContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.FeatureShowModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.factory.CreateFeatureShowModule;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 4/5/16.
 */
public class TC13765_FeatureShow_ItemTitleOpensContentEditPage extends BaseAuthFlowTest {

    private FeatureShowModule featureShowModuleData = CreateFeatureShowModule.createDefault();
    private DraftModuleTab draftModuleTab;

    /**
     *Step 1: Go To CMS
     * Verify: Admin Panel is present
     *
     * Step 2: Go To Module page /admin/ott/modules
     * Verify: The list of modules are present
     *
     * Step 3: Create a feature show module
     * Verify: The module is created
     *
     * Step 4: Open created module and add an item
     * Verify: The item is added
     *
     * Step 5: Click on item
     * Verify: The content edit page of the item is opened
     * */

    @Test(groups = {"module_menu"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkContentEditPageIsOpened(final String brand) {
        this.brand = brand;
        featureShowModuleData = CreateFeatureShowModule.createUpdatedModuleForAdmin(featureShowModuleData);

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        draftModuleTab = mainRokuAdminPage.openAddFeatureShowPage(brand);

        //Step 3-4
        draftModuleTab.createFeatureShowModule(featureShowModuleData);

        //Step 5
        String asset = featureShowModuleData.getAssets().get(0);
        MediaContentPage contentPage = draftModuleTab.clickByAssetTitle(asset);
        WebDriverUtil.getInstance(webDriver).switchToWindowByNumber(2);
        softAssert.assertTrue(contentPage.getPageTitle().contains(asset), "The opened page is not Content Edit Page of the asset: " + asset,
                "The opened content page is edit page of the asset: " + asset, webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("test is passed");
    }
}
