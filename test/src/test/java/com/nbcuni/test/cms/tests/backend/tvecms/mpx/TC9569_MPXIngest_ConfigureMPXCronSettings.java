package com.nbcuni.test.cms.tests.backend.tvecms.mpx;

import com.nbcuni.test.cms.backend.tvecms.pages.LoginPage;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ThePlatformMpxSettingsPage;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 07-Sep-15.
 */

/**
 * TC9569
 * P7: MPX Ingest. Configure MPX Cron settings<br>
 * Check that user can configure MPX Cron settings<br>
 *
 * Pre-Conditions: <br>
 * Go to brand site as Admin. Kill all content (/admin/config/system/tve_mpx_debug/kill_all_content) <br>
 *
 * Steps: 1.Go to brand site as admin<br>
 * Verify: Admin menu is appeared<br>
 *
 * 2.Go to Configuration -> Media -> Media: thePlatform mpx settings<br>
 * Verify: Media: thePlatform mpx settings page is opened with:<br>
 * - "Add Account" button,<br>
 * - "Connect to MPX" button <br>
 *
 * 3. Click on "Add Account" button<br>
 * Verify: "MPX LOGIN" form is opened with controls:<br>
 * - Username for New Account (required),<br>
 * - Password for New Account (required)<br>
 *
 * 4.Provide: - Username for New Account, - Password for New Account. Click on "Connect to MPX"
 * button<br>
 * Verify: "Login successful for new account with username mpx/Pub7Sprout." message is appeared.<br>
 * "CRON SETTINGS" menu with "Save Configuration" button is appeared<br>
 *
 * 5. Go to "CRON SETTINGS" menu. Provide:<br>
 * - Sync mpxPlayers on Cron = checked,<br>
 * - Sync mpxMedia on Cron = checked,<br>
 * - Media Processing Limit = 50,<br>
 * - Media Feed Request Timeout = 240,<br>
 * - Cron Queue Processing Time = 240,<br>
 * - Display MPX Debug Message Level = Basic,<br>
 * - Authentication Token TTL = 10,<br>
 * Click on "Save Configuration"<br>
 * Verify: "The configuration options have been saved." message is appeared<br>
 *
 */
public class TC9569_MPXIngest_ConfigureMPXCronSettings extends BaseIngestTest {

    @Test(groups = {"roku_ingest"}, alwaysRun = true)
    public void configureMpxCronSettings() {
        SoftAssert softAssert = new SoftAssert();

        Utilities.logInfoMessage("Check that user can configure MPX Cron settings on Roku " + brand);

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

        // Step 3
        softAssert.assertTrue(mpxSettingsPage.isUsernameForNewAccountPresent(),
                "Username for new account didn't appeared after 'Add account' action", webDriver);
        softAssert.assertTrue(mpxSettingsPage.isPasswordForNewAccountPresent(),
                "Password for new account didn't appeared after 'Add account' action", webDriver);

        // Step 4
        final String mpxUsername = config.getRokuMPXUsername(brand);
        mpxSettingsPage.setCredentialsForNewAccount(mpxUsername, config.getRokuMPXPassword(brand));
        mpxSettingsPage.connectToMpx();
        softAssert.assertTrue(mpxSettingsPage.isStatusMessageShown(),
                "There is no status message after 'Connect to MPX' action");
        softAssert.assertContains(mpxSettingsPage.getStatusMessage(),
                String.format(MessageConstants.LOGIN_SUCCESSFUL_FOR_NEW_ACCOUNT_WITH_USERNAME_TEMPLATE, mpxUsername),
                "Unexpected status message");
        softAssert.assertTrue(mpxSettingsPage.isCronSettingsSectionPresent(),
                "The Cron Settings section/Save configuration button didn't appeared",
                "The Cron Settings section and Save configuration button are present", webDriver);

        // Step 5
        mpxSettingsPage.expandCronSettings().turnOnSyncMpxPlayersOnCron().turnOnSyncMpxMediaOnCron()
                .setMediaProcessingLimit(50).setMediaFeedRequestTimeout(240).setCronQueueProcessingTime(240)
                .selectDisplayMpxDebugMessageLevel("Basic").selectMpxDebugLoggingLevel("Advanced")
                .setAuthentificationTokenTTL(10).saveConfiguration();
        softAssert.assertTrue(mpxSettingsPage.isStatusMessageShown(),
                "The status message didn't appear after Save configuration action");
        softAssert.assertContains(mpxSettingsPage.getStatusMessage(),
                MessageConstants.THE_CONFIGURATION_OPTIONS_HAVE_BEEN_SAVED,
                "Unexpected status message after Save configuration action", "Configuration was saved successfully");

        softAssert.assertAll();
    }

}
