package com.nbcuni.test.cms.tests.backend.tvecms.mpx;

import com.nbcuni.test.cms.backend.tvecms.pages.LoginPage;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ThePlatformMpxSettingsPage;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 31-Aug-15.
 */

/**
 * Precondition: Go to brand site as Admin.
 * Kill all content (/admin/config/system/tve_mpx_debug/kill_all_content)
 *
 * Step 1: Go to brand site as admin
 * Verify: Admin menu is appeared
 *
 * Step 2: Go to Configuration -> Media -> Media: thePlatform mpx settings (/admin/config/media/theplatform)
 * Verify: Media: thePlatform mpx settings page is opened with:
 * - Username for New Account (required),
 * - Password for New Account (required)
 * - "connect to MPX" button
 *
 * Step 3: Provide:
 * - Username for New Account,
 * - Password for New Account.
 * Click on "Connect to MPX" button
 * Verify: "Login successful for new account with username [username]." message is appeared
 * "ADD ACCOUNTS" menu is appeared.
 * "ACCOUNTS LIST" menu is appeared.
 * "IMPORT ACCOUNTS" menu with "Save Import Account settings" button is appeared.
 * "CRON SETTINGS" menu with "Save Configuration" button is appeared
 */
public class TC9567_MPXIngest_ConfigureMPXLogin extends BaseIngestTest {

    @Test(groups = {"roku_ingest"}, alwaysRun = true)
    public void configureMpxLogin() {
        SoftAssert softAssert = new SoftAssert();

        Utilities.logInfoMessage("Check that user can configure MPX Login on Roku " + brand);
        // Step 1
        final LoginPage loginPage = new LoginPage(webDriver, aid, brand);
        final MainRokuAdminPage mainAdminPage = loginPage.loginAsSuperUser();
        softAssert.assertTrue(mainAdminPage.isAdminPanelPresent(), "User is not on admin");

        // Step 2
        final ThePlatformMpxSettingsPage mpxSettingsPage = mainAdminPage.openThePlatformMpxSettingsPage(brand);
        softAssert.assertContains(webDriver.getTitle(), ThePlatformMpxSettingsPage.PAGE_TITLE,
                "Page " + webDriver.getCurrentUrl() + " has unexpected title");

        softAssert.assertTrue(mpxSettingsPage.isConnectToMPXButtonPresent(),
                "Connect to MPX button is absent in MPX Login section",
                "Connect to MPX button is present in MPX Login section", webDriver);

        softAssert.assertTrue(mpxSettingsPage.isUsernameForNewAccountPresent(),
                "Username for new account didn't appeared after 'Add account' action", webDriver);
        softAssert.assertTrue(mpxSettingsPage.isPasswordForNewAccountPresent(),
                "Password for new account didn't appeared after 'Add account' action", webDriver);

        mpxSettingsPage.connectToMpx();
        softAssert.assertTrue(mpxSettingsPage.isErrorMessagePresent(),
                "There is no error message box, there was no validation of Username and Password mandatory fields");
        softAssert.assertContains(mpxSettingsPage.getErrorMessage(),
                MessageConstants.USERNAME_FOR_NEW_ACCOUNT_FIELD_IS_REQUIRED, "Username is not required field");
        softAssert.assertContains(mpxSettingsPage.getErrorMessage(),
                MessageConstants.PASSWORD_FOR_NEW_ACCOUNT_FIELD_IS_REQUIRED, "Password is not required field");

        // Step 3
        final String mpxUsername = config.getRokuMPXUsername(brand);
        mpxSettingsPage.setCredentialsForNewAccount(mpxUsername, config.getRokuMPXPassword(brand));
        mpxSettingsPage.connectToMpx();
        softAssert.assertFalse(mpxSettingsPage.isErrorMessagePresent(),
                "There is error message after 'Connect to MPX' action: " + mpxSettingsPage.getErrorMessage());
        softAssert.assertTrue(mpxSettingsPage.isStatusMessageShown(),
                "There is no status message after 'Connect to MPX' action");
        softAssert.assertContains(mpxSettingsPage.getStatusMessage(),
                String.format(MessageConstants.LOGIN_SUCCESSFUL_FOR_NEW_ACCOUNT_WITH_USERNAME_TEMPLATE, mpxUsername),
                "Unexpected status message");
        softAssert.assertTrue(mpxSettingsPage.isImportAccountSectionPresent(),
                "The Import Account section didn't appeared", "The Import Account section is present", webDriver);

        softAssert.assertTrue(mpxSettingsPage.isCronSettingsSectionPresent(),
                "The Cron Settings section/Save configuration button didn't appeared",
                "The Cron Settings section and Save configuration button are present", webDriver);

        softAssert.assertAll();
    }
}
