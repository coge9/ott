package com.nbcuni.test.cms.tests.backend.tvecms.modules.featurecarousel.additemspopup;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist.additempopup.AddItemsPopupBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Alena_Aukhukova on 2/15/2016.
 */
public class TC12904_CheckThatItemIsSelectedAfterPageNavigation extends BaseAuthFlowTest {
    /**
     * Pre-condition:
     * 1. Login in TVE CMS as Admin
     * 2. Go to /admin/ott/modules/add/feature_carousel
     * 3. Click on Add Items popup
     *
     * Step 1: Click on random item [item name]
     * Verify: [item name] is selected. [item name] is present in added items list on the top of table
     *
     * Step 2: Go to next page
     * Verify: User is on next page
     *
     * Step 3: Return to 1 page
     * Verify: [item name] is selected. [item name] is present in added items list on the top of table
     *
     */

    @Test(groups = {"feature_carousel", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkTc12904(String brand) {
        Utilities.logInfoMessage("Check that Item is selected when user navigates between pages");
        //Pre-condition
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        this.brand = brand;
        MainRokuAdminPage mainRokuAdminPage = backEndLayer.openAdminPage();
        DraftModuleTab modulePage = mainRokuAdminPage.openOttModulesPage(brand).openAddFeatureCarouselPage(brand);
        AddItemsPopupBlock addItemsPopupBlock = (AddItemsPopupBlock) modulePage.elementContentList().clickAddItems();
        //Step 1
        String selectedAsset = addItemsPopupBlock.checkRandomAsset();
        softAssert.assertTrue(addItemsPopupBlock.isItemSelectedOnCurrentPage(selectedAsset), "Item is not selected");
        softAssert.assertEquals(Arrays.asList(selectedAsset), addItemsPopupBlock.elementAddedItemsBlock().getSelectedItems(), "Added Items list values");
        //Step 2
        addItemsPopupBlock.elementTableContent().getPager().clickNextLink();
        //Step 3
        addItemsPopupBlock.elementTableContent().getPager().clickPreviousLink();
        softAssert.assertTrue(addItemsPopupBlock.isItemSelectedOnCurrentPage(selectedAsset), "Item is selected");
        softAssert.assertEquals(Arrays.asList(selectedAsset), addItemsPopupBlock.elementAddedItemsBlock().getSelectedItems(), "Added Items list values");
        softAssert.assertAll();
    }
}
