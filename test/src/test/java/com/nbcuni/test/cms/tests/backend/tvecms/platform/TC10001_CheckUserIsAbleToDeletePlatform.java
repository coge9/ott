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
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova on 11/24/2015.
 */
public class TC10001_CheckUserIsAbleToDeletePlatform extends BaseAuthFlowTest {
    /**
     * Pre-condition: Go to OTT -> Apps and Create new platform
     * Verified: The platform [platform name] is created
     * <p>
     * Step 1: Go to OTT -> Platforms
     * Step 2: Find created platform in precondition and click "Delete" link
     * Verified: Successful message is shown "Deleted platform ${APP NAME}." Error message isn't presented. Test platform (created in precondition) is not present in the list of apps any more.
     */
    private PlatformEntity platformEntity;
    private MainRokuAdminPage mainRokuAdminPage;
    private TvePlatformsPage tvePlatformsPage;
    private AddPlatformPage addPlatformPage;
    private RokuBackEndLayer backEndLayer;
    private String platformTitle;

    @Test(groups = {"master_config"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkTc10001(final String brand) {
        Utilities.logInfoMessage("Check that user is able to delete App");
        //Step 1 Go to Roku CMS and login
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        //Step 2
        platformEntity = CreateFactoryPlatform.createDefaultPlatform();
        platformTitle = platformEntity.getName();
        addPlatformPage = mainRokuAdminPage.openAddPlatformPage(brand);
        tvePlatformsPage = addPlatformPage.createAndSavePlatform(platformEntity);
        softAssert.assertFalse(tvePlatformsPage.isErrorMessagePresent(), "Error Message is Present");
        softAssert.assertEquals(tvePlatformsPage.getExpectedMessageAfterSaving(platformTitle), tvePlatformsPage.getStatusMessage(), "Status message text");
        //delete the platform
        tvePlatformsPage = backEndLayer.deleteCreatedPlatform(platformTitle);
        softAssert.assertFalse(tvePlatformsPage.isErrorMessagePresent(), "Error Message is Present");
        softAssert.assertFalse(tvePlatformsPage.isPlatformPresent(platformTitle), "Platform [" + platformTitle + "] presence in the list of platforms");
        softAssert.assertContains(tvePlatformsPage.getStatusMessage(), tvePlatformsPage.getExpectedMessageAfterDeleting(platformTitle), "Status message text");

        softAssert.assertAll();
    }
}
