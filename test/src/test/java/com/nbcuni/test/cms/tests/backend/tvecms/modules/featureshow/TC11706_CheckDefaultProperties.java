package com.nbcuni.test.cms.tests.backend.tvecms.modules.featureshow;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import static com.nbcuni.test.cms.pageobjectutils.MessageConstants.FEATURE_SHOW_DEFAULT_ALIAS;
import static com.nbcuni.test.cms.pageobjectutils.MessageConstants.FEATURE_SHOW_DEFAULT_TITLE;

/**
 * Created by Alena_Aukhukova
 */
public class TC11706_CheckDefaultProperties extends BaseAuthFlowTest {
    /**
     * Pre-condition: Login in Roku CMS as Admin
     *
     * Step 1: Go to /admin/ott/modules/add/feature_shows
     * Verify: Title is 'Add feature shows module'
     *
     * Step 2: Check default properties for Title and Alias fields
     * Verify:  Title: "Featured Shows"; Alias:"grid"
     *
     */
    MainRokuAdminPage mainRokuAdminPage;

    @Test(groups = {"feature_show_module", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void tc11706(final String brand) {
        Utilities.logInfoMessage("Check default properties for Feature show");
        //Pre-condition
        this.brand = brand;
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        //Step 1
        DraftModuleTab addModulePage = mainRokuAdminPage.openAddFeatureShowPage(brand);
        //Step 2
        softAssert.assertEquals(FEATURE_SHOW_DEFAULT_TITLE, addModulePage.getTitle(), "Default title text");
        softAssert.assertEquals(FEATURE_SHOW_DEFAULT_ALIAS, addModulePage.getAlias().getSlugValue(), "Default alias text");

        softAssert.assertAll();
    }
}
