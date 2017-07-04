package com.nbcuni.test.cms.tests.backend.tvecms.platform;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.AddPlatformPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.TvePlatformsPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformConstant;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.factory.CreateFactoryPlatform;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova on 11/27/2015.
 */
public class TC10002_CheckUserIsNotAbleCreatePlatformWithWrongMvpdEntitlementServiceUrl extends BaseAuthFlowTest {

    /**
     * Step 1: Go to Roku CMS and login
     * Step 2: Go to OTT -> Platforms
     * Step 3: Click "Add platform" link
     * Step 4: Enter all required fields
     * Step 5: Go to - 3rd Party Services Configuration tab
     * Step 6: Enter into field value which is not URL (simple text) and try to save
     * Verify: Status message isn't present. Error message text: "Please enter the valid MVPD entitlement service url"
     * Step 7: Enter into field value which is correct URL and correspond to entitlements service format
     * Step 8: Click on 'Save'
     * Verify: Error message isn't present. Status message text: "Platform "[platform title]" has been saved."
     */

    private PlatformEntity platformEntity;
    private MainRokuAdminPage mainRokuAdminPage;
    private TvePlatformsPage tvePlatformsPage;
    private RokuBackEndLayer backEndLayer;
    private AddPlatformPage addPlatformPage;


    @Test(groups = {"master_config"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkTc10002(final String brand) {
        String wrongMvpdEntitlementServiceUrl = "http://google.com";
        Utilities.logInfoMessage("Check that user isn't Able Create Platform With Wrong Mvpd Entitlement Service Url");
        //create app with wrong url
        platformEntity = CreateFactoryPlatform.createPlatformWithWrongMvpdEntitlementServiceUrl();
        //Step 1 Go to Roku CMS and login
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        //Step 2-6
        addPlatformPage = mainRokuAdminPage.openAddPlatformPage(brand);
        addPlatformPage.createAndSavePlatform(platformEntity);
        softAssert.assertFalse(addPlatformPage.isStatusMessageShown(), "Status Message is Presented");
        softAssert.assertEquals(MessageConstants.EXPECTED_ERROR_PLATFORM_WRONG_MVPD_ENTITLEMENT_SERVICE_URL, addPlatformPage.getErrorMessage(), "Error message text");
        //Step 7-8
        addPlatformPage.typeMvpdEntitlementServicesUrl(PlatformConstant.MVPD_ENTITLEMENT_SERVICE_URL);
        tvePlatformsPage = addPlatformPage.clickSaveButton();
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "Error Message is Presented");
        softAssert.assertEquals(tvePlatformsPage.getExpectedMessageAfterSaving(platformEntity.getName()), addPlatformPage.getStatusMessage(), "Status message text");
        softAssert.assertAll();
    }

    @AfterTest
    public void deleteApp() {
        backEndLayer.deleteCreatedPlatform(platformEntity.getName());
    }
}
