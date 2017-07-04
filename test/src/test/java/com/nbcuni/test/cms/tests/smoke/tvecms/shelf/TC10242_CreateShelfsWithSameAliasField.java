package com.nbcuni.test.cms.tests.smoke.tvecms.shelf;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova on 10/16/2015.
 */

/**
 * Step 1: Login to Roku CMS
 * Verify: Roku CMS is opened
 *
 * Step 2: Go to /admin/ott/modules/add/shelf
 * Verify: 'Create OTT Module page' is opened
 *
 * Step 3: Create shelf with [shelf name] and [alias name]
 *
 * Step 4: Click on 'Save OTT Module'
 * Verify:      Status message is shown
 Error message isn't shown.
 * Step 5: Create shelf with [shelf new name] and [alias name]
 * Verify:  Status message is shown
 Error message is'n shown.
 */

public class TC10242_CreateShelfsWithSameAliasField extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private String expectedError = "Error message\nThe alias should be unique!";

    @Test(groups = {"roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc10240asAdmin(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        //Step 1
        mainRokuAdminPage = backEndLayer.openAdminPage();
        Shelf shelf = EntityFactory.getShelfsList().get(0);
        Shelf shelfWithSameAlias = EntityFactory.getShelfsList().get(0);
        shelfWithSameAlias.setSlug(shelf.getSlug());
        SoftAssert softAssert = new SoftAssert();
        //Step 2
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        //Step 3,4
        draftModuleTab.createShelf(shelf);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "Error message is presented");
        //Step 5
        draftModuleTab.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelfWithSameAlias);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "Status message is not shown", "Status message is shown", webDriver);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "Error message is presented", "Error message is not present", webDriver);
        softAssert.assertAll();
    }
}
