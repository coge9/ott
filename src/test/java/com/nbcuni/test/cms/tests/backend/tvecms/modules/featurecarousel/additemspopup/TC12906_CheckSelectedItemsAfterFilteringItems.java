package com.nbcuni.test.cms.tests.backend.tvecms.modules.featurecarousel.additemspopup;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist.additempopup.AddItemsPopupBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.collections.CollectionUtils;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alena_Aukhukova on 2/15/2016.
 */
public class TC12906_CheckSelectedItemsAfterFilteringItems extends BaseAuthFlowTest {
    /**
     * Pre-condition:
     * 1. Login in TVE CMS as Admin
     * 2. Go to /admin/ott/modules/add/feature_carousel
     * 3. Click on Add Items popup
     * <p/>
     * Step 1: Choose randome 2 items [item name1] [item name2] and select [item name1]
     * Verify: [item name1] is selected. [item name1] is present in added items list on the top of table
     * <p/>
     * Step 2: Filter by [item name2]
     * Verify: [item name2] is found
     * <p/>
     * Step 3: Select [item name2]
     * Verify: [item name2] is selected. [item name2] is present in added items list on the top of table
     * <p/>
     * Step 4: Reset filter
     * Verify: [item name1], [item name2] are selected. Added items list is has two items: [item name1], [item name2]
     */

    @Test(groups = {"feature_carousel", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkTc12906(String brand) {
        Utilities.logInfoMessage("Check that Item is selected when user filtering content by title");
        //Pre-condition
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        this.brand = brand;
        MainRokuAdminPage mainRokuAdminPage = backEndLayer.openAdminPage();
        DraftModuleTab modulePage = mainRokuAdminPage.openOttModulesPage(brand).openAddFeatureCarouselPage(brand);
        AddItemsPopupBlock addItemsPopupBlock = (AddItemsPopupBlock) modulePage.elementContentList().clickAddItems();
        //Test Data preparation
        List<String> assetTitlesFromPage = addItemsPopupBlock.getAllAssetTitlesFromPage();
        String firstItem = assetTitlesFromPage.get(1);
        String secondItem = assetTitlesFromPage.get(5);
        List<String> expectedAssetTitles = Arrays.asList(firstItem, secondItem);
        //Step 1
        addItemsPopupBlock.checkAsset(firstItem);
        softAssert.assertTrue(addItemsPopupBlock.isItemSelectedOnCurrentPage(firstItem), "Item is selected");
        softAssert.assertEquals(Arrays.asList(firstItem), addItemsPopupBlock.elementAddedItemsBlock().getSelectedItems(), "Added Items list values");
        //Step 2
        List<String> searchResult = addItemsPopupBlock.filterAssetsByTitle(secondItem);
        softAssert.assertFalse(CollectionUtils.isEmpty(searchResult), "Filtering items presence");
        //Step 3
        addItemsPopupBlock.checkAsset(secondItem);
        softAssert.assertTrue(addItemsPopupBlock.isItemSelectedOnCurrentPage(secondItem), "Item is selected");
        softAssert.assertEquals(expectedAssetTitles, addItemsPopupBlock.elementAddedItemsBlock().getSelectedItems(), "Added Items list values");
        //Step 4
        addItemsPopupBlock.clickFilterReset();
        softAssert.assertEquals(expectedAssetTitles, addItemsPopupBlock.elementAddedItemsBlock().getSelectedItems(), "Added Items list values");
        softAssert.assertTrue(addItemsPopupBlock.isItemSelectedOnCurrentPage(firstItem), "Item is selected");
        softAssert.assertTrue(addItemsPopupBlock.isItemSelectedOnCurrentPage(secondItem), "Item is selected");
        softAssert.assertAll();
    }
}



