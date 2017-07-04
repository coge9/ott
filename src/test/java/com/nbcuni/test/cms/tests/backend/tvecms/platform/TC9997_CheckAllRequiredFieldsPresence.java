package com.nbcuni.test.cms.tests.backend.tvecms.platform;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.AddPlatformPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova on 11/27/2015.
 */
public class TC9997_CheckAllRequiredFieldsPresence extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;
    private AddPlatformPage addPlatformPage;

    /**
     * Step 1: Go to Roku CMS and login
     * Step 2: Go to OTT -> Platforms
     * Step 3: Click "Add Platform" link
     * Verified: Check elements on page presence:
     * - Basic information tab:
     * Name field as Text field
     * Machine Name field as Text field
     * Viewports as checkbox: Android Default
     * Android Mobile Portrait
     * Android Mobile Landscape
     * Android Tablet Portrait
     * Android Tablet Landscape
     * Roku Default
     * <p>
     * - Brand Configuration tab:
     * Upload Image: VOD - Brand Logo for VOD Player
     * Upload Image: "Browse Watchlist" Functional Tile Image
     * Upload Image: Watchlist - Empty image
     * Upload Image: "View All Shows" Functional Tile Image
     * Upload Image: Brand Logo for General Error page
     * -  3rd Party Services Configuration tab:
     * Text field: MVPD entitlement service url
     * - Burn-in Configuration tab:
     * Upload Image: Global Header - Brand Logo
     */
    @Test(groups = {"master_config"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkTc9997(final String brand) {

        Utilities.logInfoMessage("Check that All Required Fields is Presented");
        //Step 1
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        //check fields presence
        addPlatformPage = mainRokuAdminPage.openAddPlatformPage(brand);
        softAssert.assertTrue(addPlatformPage.verifyPage().isEmpty(), "All elements aren't presented");
        softAssert.assertAll();
    }
}