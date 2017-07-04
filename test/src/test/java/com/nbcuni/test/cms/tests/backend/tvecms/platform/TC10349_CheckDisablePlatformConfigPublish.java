package com.nbcuni.test.cms.tests.backend.tvecms.platform;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.TvePlatformsPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.elements.PublishBlock;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova on 11/27/2015.
 */
public class TC10349_CheckDisablePlatformConfigPublish extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private TvePlatformsPage tvePlatformsPage;
    private RokuBackEndLayer backEndLayer;

    /**
     * Step 1: go to Roku CMS
     * Step 2: go to "Platforms" menu (/admin/ott/apps)
     * Step 3: edit any platform
     * Verify: "Edit" page is opened
     * Step 4: try to publish test platform to any endpoint
     * Verify: impossible to publish - there is no appropriate button
     * TEST case is not valid anymore!!!!
     */
    @Test(groups = {"master_config"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkTc10349(final String brand) {
        Utilities.logInfoMessage("Check that user isn't able to publish platform");
        //Step 1 Go to Roku CMS and login
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        tvePlatformsPage = mainRokuAdminPage.openTvePlatformPage(brand);
        tvePlatformsPage.clickEditRandomPlatform();
        softAssert.assertFalse(new PublishBlock(webDriver).isVisible(), "Publish block is presented");
        softAssert.assertAll();
    }
}
