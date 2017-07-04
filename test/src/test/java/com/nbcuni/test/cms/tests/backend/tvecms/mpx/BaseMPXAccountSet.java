package com.nbcuni.test.cms.tests.backend.tvecms.mpx;

import com.nbcuni.test.cms.backend.tvecms.pages.ThePlatformMpxSettingsPage;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SoftAssert;
import org.testng.annotations.BeforeMethod;

/**
 * Created by aleksandra_lishaeva on 9/7/15.
 */
public class BaseMPXAccountSet extends BaseIngestTest {

    protected ThePlatformMpxSettingsPage mpxSettingsPage;
    protected SoftAssert softAssert;

    /**
     * Set MPX account and import settings
     */

    @BeforeMethod(alwaysRun = true)
    public void setMPXAccountSettings() {
        //Log to admin site
        softAssert = new SoftAssert();
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Go to Configuration -> Media -> Media: thePlatform mpx settings
        mpxSettingsPage = mainRokuAdminPage.openThePlatformMpxSettingsPage(brand);
        softAssert.assertContains(webDriver.getTitle(), ThePlatformMpxSettingsPage.PAGE_TITLE,
                "Page has unexpected title");
        softAssert.assertTrue(mpxSettingsPage.isConnectToMPXButtonPresent(),
                "Connect to MPX button is absent in MPX Login section",
                "Connect to MPX button is present in MPX Login section", webDriver);

        softAssert.assertTrue(mpxSettingsPage.isUsernameForNewAccountPresent(),
                "Username for new account didn't appeared after 'Add account' action", webDriver);
        softAssert.assertTrue(mpxSettingsPage.isPasswordForNewAccountPresent(),
                "Password for new account didn't appeared after 'Add account' action", webDriver);


        //Provide:- Username for New Account, - Password for New Account.Click on "Connect to MPX" button
        final String mpxUserName = config.getRokuMPXUsername(brand);
        final String mpxUserPassword = config.getRokuMPXPassword(brand);
        mpxSettingsPage.setCredentialsForNewAccount(mpxUserName, mpxUserPassword);
        mpxSettingsPage.connectToMpx();
        Assertion.assertTrue(mpxSettingsPage.isStatusMessageShown(),
                "There is no status message after 'Connect to MPX' action");
        softAssert.assertContains(mpxSettingsPage.getStatusMessage(),
                String.format(MessageConstants.LOGIN_SUCCESSFUL_FOR_NEW_ACCOUNT_WITH_USERNAME_TEMPLATE, mpxUserName),
                "Unexpected status message");
        softAssert.assertTrue(mpxSettingsPage.isCronSettingsSectionPresent(),
                "The Cron Settings section/Save configuration button didn't appeared",
                "The Cron Settings section/Save configuration button is appeared", webDriver);
        softAssert.assertTrue(mpxSettingsPage.isImportAccountSectionPresent(),
                "The Import Account section didn't appeared", "The Import Account section is appeared", webDriver);
        softAssert.assertFalse(mpxSettingsPage.isDefaultPlayerSelectActive(),
                "Default Player dropdown is active unexpectedly", "Default Player dropdown is inactive as expected", webDriver);

        //Provide: - Select Import Account for account = [any value from dropdown],
        // - Import players from this account = checked,
        // - Import videos from this account = checked,
        // Click on "Save Import Account settings"

        mpxSettingsPage.setPlayerSettings(true, brand, Instance.STAGE);
        Assertion.assertTrue(mpxSettingsPage.isStatusMessageShown(),
                "The status message didn't appeared after 'Save Import Account settings' action");
        softAssert.assertContains(mpxSettingsPage.getStatusMessage(), MessageConstants.IMPORT_ACCOUNT_SETTINGS_UPDATED,
                "Unexpected status message", "Status message contains " + MessageConstants.IMPORT_ACCOUNT_SETTINGS_UPDATED);
        softAssert.assertTrue(mpxSettingsPage.isDefaultPlayerSelectActive(),
                "Default Player dropdown is inactive unexpectedly after Import Account settings actions",
                "Default Player dropdown is active as expected", webDriver);

        //Set Default player = "[Brand][Env] VOD Player".
        // Click on "Save Import Account settings"
        mpxSettingsPage.selectDefaultPlayer(config.getRokuMPXDefaulPlayer(brand, Instance.STAGE));
        mpxSettingsPage.importPlayersFromThisAccount(true).importVideosFromThisAccount(true).saveImportAccountSettings();
        Assertion.assertTrue(mpxSettingsPage.isStatusMessageShown(),
                "The status message didn't appeared after 'Save Import Account settings' action");
        softAssert.assertContains(mpxSettingsPage.getStatusMessage(), MessageConstants.IMPORT_ACCOUNT_SETTINGS_UPDATED,
                "Unexpected status message", "Status message contains " + MessageConstants.IMPORT_ACCOUNT_SETTINGS_UPDATED);

        /**Go to "CRON SETTINGS" menu.
         *Provide:- Sync mpxPlayers on Cron = checked,
         * - Sync mpxMedia on Cron = checked,
         * - Media Processing Limit = 50 - Media Feed Request Timeout = 240 - Cron Queue Processing Time = 240,
         * - Display MPX Debug Message Level = Basic,
         * - Authentication Token TTL = 30,
         * Click on "Save Configuration"
         * */
        mpxSettingsPage.expandCronSettings().turnOnSyncMpxPlayersOnCron().turnOnSyncMpxMediaOnCron()
                .setMediaProcessingLimit(50).setMediaFeedRequestTimeout(240).setCronQueueProcessingTime(240)
                .selectDisplayMpxDebugMessageLevel("Basic").selectMpxDebugLoggingLevel("Basic")
                .setAuthentificationTokenTTL(30).saveConfiguration();
        Assertion.assertTrue(mpxSettingsPage.isStatusMessageShown(),
                "The status message didn't appear after Save configuration action");
        softAssert.assertContains(mpxSettingsPage.getStatusMessage(),
                MessageConstants.THE_CONFIGURATION_OPTIONS_HAVE_BEEN_SAVED,
                "Unexpected status message after Save configuration action",
                "Configuration was saved successfully, message contains " + MessageConstants.THE_CONFIGURATION_OPTIONS_HAVE_BEEN_SAVED);

    }
}
