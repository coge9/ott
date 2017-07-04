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
 * Created by Alena_Aukhukova on 2/17/2016.
 */
public class TC12966_CheckClearSelectedFunctionality extends BaseAuthFlowTest {
    /**
     * Pre-condition:
     * 1. Login in TVE CMS as Admin
     * 2. Go to /admin/ott/modules/add/feature_carousel
     * 3. Click on Add Items popup
     * <p/>
     * Step 1: Click on random item [item name]
     * Verify: [item name] is selected. [item name] is present in added items list at the top of table
     * <p/>
     * Step 2: Click on Clear all
     * Verify: [item name] isn't selected. added items list is empty on the top of table
     * <p/>
     * Step 3: Click on select all items
     * Verify: all [item name] are selected. all [item name] are presented in added items list at the top of table
     *
     * Step 4: Click on Clear all
     * Verify: all [item name] is selected. added items list is empty at the top of table
     */
    @Test(groups = {"feature_carousel", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkTc12966(String brand) {
        Utilities.logInfoMessage("Check that user is able to clear all selected items");
        //Pre-condition
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        this.brand = brand;
        MainRokuAdminPage mainRokuAdminPage = backEndLayer.openAdminPage();
        DraftModuleTab modulePage = mainRokuAdminPage.openOttModulesPage(brand).openAddFeatureCarouselPage(brand);
        AddItemsPopupBlock addItemsPopupBlock = (AddItemsPopupBlock) modulePage.elementContentList().clickAddItems();

        //Step 1
        String selectedAsset = addItemsPopupBlock.checkRandomAsset();
        softAssert.assertTrue(addItemsPopupBlock.isItemSelectedOnCurrentPage(selectedAsset),
                "Item is not selected", "Item is selected");
        softAssert.assertEquals(Arrays.asList(selectedAsset), addItemsPopupBlock.elementAddedItemsBlock().getSelectedItems(),
                "Added Items list values");
        //Step 2
        addItemsPopupBlock.elementAddedItemsBlock().clearSelection();
        softAssert.assertFalse(addItemsPopupBlock.isItemSelectedOnCurrentPage(selectedAsset),
                "Item is not selected", "Item is selected");
        softAssert.assertTrue(CollectionUtils.isEmpty(addItemsPopupBlock.elementAddedItemsBlock().getSelectedItems()),
                "Added Items list is not empty", "Added Items list is empty");
        //Step 3
        List<String> allTitlesFromPage = addItemsPopupBlock.getAllAssetTitlesFromPage();
        addItemsPopupBlock.clickSelectAll();
        softAssert.assertContainsAll(addItemsPopupBlock.elementAddedItemsBlock().getSelectedItems(), allTitlesFromPage,
                "Added Items list doesn't contain assets from page",
                "Added Items list contains assets from page");

        //Step 4
        addItemsPopupBlock.elementAddedItemsBlock().clearSelection();
        softAssert.assertTrue(CollectionUtils.isEmpty(addItemsPopupBlock.elementAddedItemsBlock().getSelectedItems()),
                "Added Items list is not empty", "Added Items list is empty");
        softAssert.assertAll();
    }
}


