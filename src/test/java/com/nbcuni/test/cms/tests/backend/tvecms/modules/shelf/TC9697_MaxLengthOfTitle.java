package com.nbcuni.test.cms.tests.backend.tvecms.modules.shelf;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import org.testng.annotations.Test;

/**
 *
 * @author Aliaksei_Dzmitrenka
 *
 *         Step 1: open create shelf page Step 2: fill all fields and title for
 *         string with 41 length Step 3: save shelf Step 4: verify that error
 *         message is appeared Step 5: remove one symbol (40 length ) and save
 *         again Step 6: shelf was saved
 *
 *
 */

public class TC9697_MaxLengthOfTitle extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;

    @Test(groups = {"shelf_management"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc9697testMethod(final String brand) {
        Shelf shelf = EntityFactory.getShelfsList().get(4);
        SoftAssert softAssert = new SoftAssert();
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf);
        softAssert.assertTrue(draftModuleTab.isErrorMessagePresent(), "There are no any error after saving with long titles",
                "Error message is present", webDriver);
        draftModuleTab.fillTitle(shelf.getTitle().subSequence(0, shelf.getTitle().length() - 1).toString());

        mainRokuAdminPage.openOttModulesPage(brand);
        ModulesPage modulesPage = new ModulesPage(webDriver, aid);
        softAssert.assertTrue(modulesPage.isShelfExist(shelf.getTitle()), "Shelf was not created", "Shelf was created", webDriver);
        modulesPage.clickEditLink(shelf.getTitle());
        softAssert.assertEquals(shelf, draftModuleTab.getShelfInfo(), "Shelf's fields is wrong", "Shelf's fields is correct",
                webDriver);
        softAssert.assertAll();
    }

}
