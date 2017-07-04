package com.nbcuni.test.cms.tests.backend.tvecms.modules.shelf.additemspopup;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist.additempopup.AddItemsPopupBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Alena_Aukhukova on 2/15/2016.
 */
public class TC12909_CheckSelectAllItemsOnThePage extends BaseAuthFlowTest {
    /**
     * Pre-condition:
     * 1. Login in TVE CMS as Admin
     * 2. Go to /admin/ott/modules/add/shelf
     * 3. Click on Add Items popup
     * <p/>
     * Step 1: Click on select all items
     * Verify: all [item name] is selected. all [item name] is presented in added items list on the top of table
     * <p/>
     * Step 2: Go to next page
     * Verify: User is on next page
     * <p/>
     * Step 3: Return to 1 page
     * Verify: all [item name] is selected. all [item name] is presented in added items list on the top of table
     */
    @Test(groups = {"custom_shelf", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkTc12909(String brand) {
        Utilities.logInfoMessage("Check that Item is selected when user select all items on the page");
        //Pre-condition
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        this.brand = brand;
        MainRokuAdminPage mainRokuAdminPage = backEndLayer.openAdminPage();
        DraftModuleTab modulePage = mainRokuAdminPage.openOttModulesPage(brand).openAddShelfPage(brand);
        AddItemsPopupBlock addItemsPopupBlock = (AddItemsPopupBlock) modulePage.elementContentList().clickAddItems();
        //Test Data preparation
        List<String> allTitlesFromPage = addItemsPopupBlock.getAllAssetTitlesFromPage();
        //Step 1
        addItemsPopupBlock.clickSelectAll();

        softAssert.assertContainsAll(addItemsPopupBlock.elementAddedItemsBlock().getSelectedItems(), allTitlesFromPage,
                "Added Items list doesn't contain assets from page",
                "Added Items list contains assets from page");
        //Step 2
        addItemsPopupBlock.elementTableContent().getPager().clickNextLink();
        allTitlesFromPage = addItemsPopupBlock.getAllAssetTitlesFromPage();
        softAssert.assertContainsAll(addItemsPopupBlock.elementAddedItemsBlock().getSelectedItems(), allTitlesFromPage,
                "Added Items list doesn't contain assets from page",
                "Added Items list contains assets from page");
        //Step 3
        addItemsPopupBlock.elementTableContent().getPager().clickPreviousLink();

        allTitlesFromPage = addItemsPopupBlock.getAllAssetTitlesFromPage();
        softAssert.assertContainsAll(addItemsPopupBlock.elementAddedItemsBlock().getSelectedItems(), allTitlesFromPage,
                "Added Items list doesn't contain assets from page",
                "Added Items list contains assets from page");
        softAssert.assertAll();
    }

}
