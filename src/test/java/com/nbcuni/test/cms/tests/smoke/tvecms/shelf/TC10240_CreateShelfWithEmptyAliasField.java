package com.nbcuni.test.cms.tests.smoke.tvecms.shelf;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
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
 * Step 3: Fill all required fields except 'Alias'
 *
 * Step 4: Click on 'Save OTT Module'
 * Verify:      Status message isn't shown
 Error message is shown.
 Error message has text 'Alias field is required.'
 */

public class TC10240_CreateShelfWithEmptyAliasField extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private Slug slug = new Slug().setSlugValue("");
    private String expectedError = "Error message\nAlias field is required.";

    @Test(groups = {"roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void tc10240asAdmin(final String brand) {
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        //Step 1
        mainRokuAdminPage = backEndLayer.openAdminPage();
        Shelf shelf = EntityFactory.getShelfsList().get(0);
        shelf.setSlug(slug);
        SoftAssert softAssert = new SoftAssert();
        //Step 2
        DraftModuleTab draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        //Step 3,4
        draftModuleTab.fillTitle(shelf.getTitle());
        draftModuleTab.fillAlias(shelf.getSlug());
        draftModuleTab.setDisplayTitle(shelf.getDisplayTitle());
        draftModuleTab.checkTileVariant(shelf.getTileVariant());
        draftModuleTab.clickSave();

        softAssert.assertFalse(mainRokuAdminPage.isStatusMessageShown(), "Status message is shown");
        softAssert.assertTrue(mainRokuAdminPage.isErrorMessagePresent(), "Error message isn't present");
        softAssert.assertEquals(expectedError, mainRokuAdminPage.getErrorMessage(), "Error message text");
        softAssert.assertAll();
    }
}
