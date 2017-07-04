package com.nbcuni.test.cms.tests.backend.tvecms.modules.menu;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MediaContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.Random;

/**
 * Created by Aleksandra_Lishaeva on 4/5/16.
 */
public class TC13763_CustomModule_ItemTitleOpensContentEditPage extends BaseAuthFlowTest {

    private Shelf shelf;
    private DraftModuleTab draftModuleTab;
    private Random random;

    /**
     * Step 1: Go To CMS
     * Verify: Admin Panel is present
     * <p/>
     * Step 2: Go To Module page /admin/ott/modules
     * Verify: The list of modules are present
     * <p/>
     * Step 3: Create a custom module
     * Verify: The module is created
     * <p/>
     * Step 4: Open created module and add an item
     * Verify: The item is added
     * <p/>
     * Step 5: Click on item
     * Verify: The content edit page of the item is opened
     */

    @Test(groups = {"module_menu"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkContentEditPageIsOpened(final String brand) {
        this.brand = brand;

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        shelf = EntityFactory.getShelfsList().get(0);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);

        //Step 3-4
        draftModuleTab.createShelf(shelf);

        //Step 5
        String asset = shelf.getAssets().get(0);
        MediaContentPage contentPage = draftModuleTab.clickByAssetTitle(asset);
        WebDriverUtil.getInstance(webDriver).switchToWindowByNumber(2);
        softAssert.assertTrue(contentPage.getPageTitle().contains(asset), "The opened page is not Content Edit Page of the asset: " + asset,
                "The opened content page is edit page of the asset: " + asset, webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("test is passed");
    }
}
