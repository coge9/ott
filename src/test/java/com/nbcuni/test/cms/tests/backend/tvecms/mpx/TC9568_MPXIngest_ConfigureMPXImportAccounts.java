package com.nbcuni.test.cms.tests.backend.tvecms.mpx;

import com.nbcuni.test.cms.backend.tvecms.pages.ThePlatformMpxSettingsPage;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 31-Aug-15.
 */
public class TC9568_MPXIngest_ConfigureMPXImportAccounts extends BaseIngestTest {

    /**
     * Precondition: Go to brand site as Admin.
     * Kill all content (/admin/config/system/tve_mpx_debug/kill_all_content)
     *
     * Step 1: Go to brand site ad admin
     * Verify: Admin menu is appeared
     *
     * Step 2: Go to Configuration -> Media -> Media: thePlatform mpx settings (/admin/config/media/theplatform)
     * Verify: Media: thePlatform mpx settings page is opened with:
     * - Username for New Account (required)
     * - Password for New Account (required)
     * - "connect to MPX" button
     *
     * Step 3: Provide:
     * - Username for New Account
     * - Password for New Account
     * Click on "Connect to MPX" button
     * Verify: "Login successful for new account with username [username]." message is appeared
     * "ADD ACCOUNTS" menu is appeared.
     * "ACCOUNTS LIST" menu is appeared.
     * "IMPORT ACCOUNTS" menu with "Save Import Account settings" button is appeared.
     * "CRON SETTINGS" menu with "Save Configuration" button is appeared
     *
     * Step 4: Provide:
     * - Select Import Account for account = [any value from dropdown],
     * - Import players from this account = checked,
     * - Import videos from this account = checked.
     * Click on "Save Import Account settings".
     * Verify: "Import Account settings updated." message is appeared.
     * "Default player" dropdown is active.
     *
     * Step 5: Set Default player = "[Brand] VOD Player".
     * Click on "Save Import Account settings"
     * Verify: "Import Account settings updated." message is appeared
     * */


    @Test(groups = {"roku_ingest"}, alwaysRun = true)
    public void configureMpxImportAccounts() {
        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Check that user can configure MPX  import accounts on roku " + brand);
        // Step 1
        mainRokuAdminPage = backEndLayer.openAdminPage();
        softAssert.assertTrue(mainRokuAdminPage.isAdminPanelPresent(), "User is not on admin",
                "Page title on " + webDriver.getCurrentUrl() + " is valid: "
                        + mainRokuAdminPage.getTitle("user", brand));

        // Step 2
        final ThePlatformMpxSettingsPage mpxSettingsPage = mainRokuAdminPage.openThePlatformMpxSettingsPage(brand);
        softAssert.assertContains(webDriver.getTitle(), ThePlatformMpxSettingsPage.PAGE_TITLE,
                "Page has unexpected title");
        softAssert.assertTrue(mpxSettingsPage.isConnectToMPXButtonPresent(),
                "Connect to MPX button is absent in MPX Login section",
                "Connect to MPX button is present in MPX Login section", webDriver);

        softAssert.assertTrue(mpxSettingsPage.isUsernameForNewAccountPresent(),
                "Username for new account didn't appeared after 'Add account' action", webDriver);
        softAssert.assertTrue(mpxSettingsPage.isPasswordForNewAccountPresent(),
                "Password for new account didn't appeared after 'Add account' action", webDriver);

        // Step 3
        final String mpxUsername = config.getRokuMPXUsername(brand);
        mpxSettingsPage.setCredentialsForNewAccount(mpxUsername, config.getRokuMPXPassword(brand));
        mpxSettingsPage.connectToMpx();
        softAssert.assertTrue(mpxSettingsPage.isStatusMessageShown(),
                "There is no status message after 'Connect to MPX' action");
        softAssert.assertContains(mpxSettingsPage.getStatusMessage(),
                String.format(MessageConstants.LOGIN_SUCCESSFUL_FOR_NEW_ACCOUNT_WITH_USERNAME_TEMPLATE, mpxUsername),
                "Unexpected status message");
        softAssert.assertTrue(mpxSettingsPage.isImportAccountSectionPresent(),
                "The Import Account section didn't appeared", "The Import Account section is present", webDriver);

        softAssert.assertFalse(mpxSettingsPage.isDefaultPlayerSelectActive(),
                "Default Player dropdown is active unexpectedly", "Default Player dropdown is inactive as expected");

        // Step 4
        mpxSettingsPage.setPlayerSettings(true, brand, Instance.STAGE);
        softAssert.assertTrue(mpxSettingsPage.isStatusMessageShown(),
                "The status message didn't appeared after 'Save Import Account settings' action");
        softAssert.assertContains(mpxSettingsPage.getStatusMessage(), MessageConstants.IMPORT_ACCOUNT_SETTINGS_UPDATED,
                "Unexpected status message", "Import Account settings updated successfully");

        softAssert.assertTrue(mpxSettingsPage.isDefaultPlayerSelectActive(),
                "Default Player dropdown is inactive unexpectedly after Import Account settings actions",
                "Default Player dropdown is active as expected");

        // Step 5
        mpxSettingsPage.selectDefaultPlayer(config.getRokuMPXDefaulPlayer(brand, Instance.STAGE));
        mpxSettingsPage.importPlayersFromThisAccount(true).importVideosFromThisAccount(true).saveImportAccountSettings();
        softAssert.assertTrue(mpxSettingsPage.isStatusMessageShown(),
                "The status message didn't appeared after 'Save Import Account settings' action");
        softAssert.assertContains(mpxSettingsPage.getStatusMessage(), MessageConstants.IMPORT_ACCOUNT_SETTINGS_UPDATED,
                "Unexpected status message", "Import Account settings updated successfully");
        softAssert.assertAll();
    }
}
