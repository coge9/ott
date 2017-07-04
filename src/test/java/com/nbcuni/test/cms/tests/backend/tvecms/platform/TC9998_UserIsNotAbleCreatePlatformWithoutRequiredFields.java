package com.nbcuni.test.cms.tests.backend.tvecms.platform;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.AddPlatformPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova on 11/27/2015.
 */
public class TC9998_UserIsNotAbleCreatePlatformWithoutRequiredFields extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private AddPlatformPage addPlatformPage;

    /**
     * Step 1: Go to Roku CMS and login
     * Step 2: Go to OTT -> Platforms
     * Step 3: Click "Add platform" link
     * Step 4: Click "Save"
     * Verify: Status message isn't shown. Error message text:
     */
    @Test(groups = {"master_config"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkTc9998(final String brand) {
        Utilities.logInfoMessage("Check that User Is Not Able Create Platform Without Required Fields");

        //Step 1 Go to Roku CMS and login
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        //Step2-4
        addPlatformPage = mainRokuAdminPage.openAddPlatformPage(brand);
        addPlatformPage.clickSaveButton();
        softAssert.assertFalse(addPlatformPage.isStatusMessageShown(), "Status Message is Shown");
        softAssert.assertEquals(MessageConstants.EXPECTED_ERROR_PLATFORM_ALL_FIELDS_ARE_REQUIRED, addPlatformPage.getErrorMessage(), "Error Message Text");
        softAssert.assertAll();
    }

}
