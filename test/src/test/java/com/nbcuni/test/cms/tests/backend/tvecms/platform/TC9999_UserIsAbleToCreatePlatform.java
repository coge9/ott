package com.nbcuni.test.cms.tests.backend.tvecms.platform;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.AddPlatformPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.TvePlatformsPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.factory.CreateFactoryPlatform;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova on 11/25/2015.
 */
public class TC9999_UserIsAbleToCreatePlatform extends BaseAuthFlowTest {

    private RokuBackEndLayer backEndLayer;
    private MainRokuAdminPage mainRokuAdminPage;
    private PlatformEntity platformEntity;

    /**
     * Step 1: Go to Roku CMS and login
     * Step 2: Go to OTT -> Platforms
     * Step 3: Click "Add platform" link
     * Step 4:   Enter all mandatory fields
     * - Name
     * - Machine Name;
     * - VOD - Brand Logo for VOD Player
     * - "Browse Watchlist" Functional Tile Image
     * - Watchlist - Empty image
     * - "View All Shows" Functional Tile Image
     * - Brand Logo for General Error page
     * - MVPD entitlement service url
     * - Global Header - Brand Logo
     * - Viewports
     * Step 5: Click "Save Platform" button
     * Verify: Error Message isn't Present. Status message: "OTT App "[app title]" has been saved."
     */
    @Test(groups = {"master_config"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkTc9999(final String brand) {
        Utilities.logInfoMessage("Check that user is able to create Platform");
        //Step 1 Go to Roku CMS and login
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        //Step 2-5
        platformEntity = CreateFactoryPlatform.createDefaultPlatform();
        AddPlatformPage addPlatformPage = mainRokuAdminPage.openAddPlatformPage(brand);
        TvePlatformsPage platformListPage = addPlatformPage.createAndSavePlatform(platformEntity);
        softAssert.assertFalse(platformListPage.isErrorMessagePresent(), "Error Message is Present");
        softAssert.assertEquals(platformListPage.getExpectedMessageAfterSaving(platformEntity.getName()), platformListPage.getStatusMessage(), "Status message text");
        AddPlatformPage editPlatformPage = platformListPage.clickEditPlatform(platformEntity.getName());
        softAssert.assertEquals(platformEntity, editPlatformPage.getPlatformInfo(), "Platform data values");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedPlatform() {
        backEndLayer.deleteCreatedPlatform(platformEntity.getName());
    }
}
