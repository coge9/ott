package com.nbcuni.test.cms.backend.tvecms.pages;

import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributeValues;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 28-Aug-15.
 */
public class ThePlatformMpxSettingsPage extends MainRokuAdminPage {

    public static final String PAGE_TITLE = "Media: thePlatform mpx settings | ";

    // MPX LOGIN
    private static final String MPX_LOGIN_LINK_XPATH = ".//*[@id='edit-accounts']//*[@class='fieldset-title']";
    private static final String MPX_LOGIN_BLOCK_XPATH = ".//*[@id='edit-accounts']";
    private static final String CONNECT_TO_MPX_XPATH = ".//*[contains(@id, 'edit-accounts-actions-submit')]";
    private static final String USERNAME_FOR_NEW_ACCOUNT_XPATH = ".//*[contains(@id, 'edit-accounts-new-') and contains(@id, '-theplatform-username')]";
    private static final String PASSWORD_FOR_NEW_ACCOUNT_XPATH = ".//*[contains(@id, 'edit-accounts-new-') and contains(@id, '-theplatform-password')]";
    private static final String SAVE_ACCOUNT_XPATH = ".//*[@id='edit-accounts-actions-submit']";

    // ACCOUNT List
    private static final String ACCOUNT_LIST_BLOCK_XPATH = ".//*[@id='edit-account-list']";
    private static final String ACCOUNT_LIST_LINK_XPATH = ".//*[@id='edit-account-list']//a";
    private static final String USERNAME_THEPLATFORM_GENERALIZED = "(.//*[contains(@id, 'edit-account-list') and contains(@id, '-theplatform-username')])[%s]";
    private static final String PASSWORD_THEPLATFORM_GENERALIZED = "(.//*[contains(@id, 'edit-account-list') and contains(@id, '-theplatform-password')])[%s]";
    private static final String UPDATE_ACCOUNT_GENERALIZED = "(.//*[contains(@id,'update-account')])[%s]";
    private static final String DELETE_ACCOUNT_XPATH = ".//*[@id='edit-account-list']//input[contains(@id,'delete-account')]";
    private static final String DELETE_ACCOUNT_GENERALIZED = "(.//*[@id='edit-account-list']//input[contains(@id,'delete-account')])[%s]";
    private static final String CONFIRM_DELETE_ACCOUNT_XPATH = ".//*[@id='edit-submit']";

    // IMPORT ACCOUNT
    private static final String IMPORT_ACCOUNT_SECTION_XPATH = ".//*[@id='edit-import-accounts']";
    private static final String DEFAULT_PLAYER_XPATH = ".//*[contains(@id, 'edit-import-accounts-') and contains(@id, '-default-player')]";
    private static final String DEFAULT_PLAYER_GENERALIZED = "(.//*[contains(@id, 'edit-import-accounts-') and contains(@id, '-default-player')])[%s]";
    private static final String DEFAULT_PLAYER_OPTIONS_XPATH = ".//*[contains(@id, 'edit-import-accounts-') and contains(@id, '-default-player')]//option";
    private static final String DEFAULT_PLAYER_OPTIONS_GENERALIZED = "(.//*[contains(@id, 'edit-import-accounts-') and contains(@id, '-default-player')])[%s]//option";
    private static final String SELECT_IMPORT_ACCOUNT_XPATH = ".//*[contains(@name, 'import_accounts[') and contains(@name, '][import_account]')]";
    private static final String SELECT_IMPORT_ACCOUNT_GENERALIZED = "(.//*[contains(@name, 'import_accounts[') and contains(@name, '][import_account]')])[%s]";
    private static final String SELECT_IMPORT_ACCOUNT_OPTIONS_XPATH = ".//*[contains(@name, 'import_accounts[') and contains(@name, '][import_account]')]//option";
    private static final String SELECT_IMPORT_ACCOUNT_OPTIONS_GENERALIZED = "(.//*[contains(@name, 'import_accounts[') and contains(@name, '][import_account]')])[%s]//option";
    private static final String IMPORT_PLAYERS_FROM_THIS_ACCOUNT_XPATH = ".//*[contains(@id, 'edit-import-accounts-') and contains(@id, '-import-players')]";
    private static final String IMPORT_PLAYERS_FROM_THIS_ACCOUNT_GENERALIZED = "(.//*[contains(@id, 'edit-import-accounts-') and contains(@id, '-import-players')])[%s]";
    private static final String IMPORT_VIDEOS_FROM_THIS_ACCOUNT_XPATH = ".//*[contains(@id, 'edit-import-accounts-') and contains(@id, '-import-videos')]";
    private static final String IMPORT_VIDEOS_FROM_THIS_ACCOUNT_GENERALIZED = ".(//*[contains(@id, 'edit-import-accounts-') and contains(@id, '-import-videos')])[%s]";
    private static final String SAVE_IMPORT_ACCOUNT_SETTINGS_XPATH = ".//*[@id='edit-import-accounts-actions-submit']";

    // CRON SETTINGS SECTION
    private static final String CRON_SETTINGS_SECTION_XPATH = ".//*[@id='edit-cron']";
    private static final String CRON_SETTINGS_LINK_XPATH = ".//*[@id='edit-cron']//a[@class='fieldset-title']";
    private static final String CRON_SETTINGS_BLOCK_XPATH = ".//*[@id='edit-cron']//*[@class='fieldset-wrapper']";
    private static final String SYNC_MPX_PLAYERS_ON_CRON_XPATH = ".//*[@id='edit-media-theplatform-mpx-cron-players']";
    private static final String SYNC_MPX_MEDIA_ON_CRON_XPATH = ".//*[@id='edit-media-theplatform-mpx-cron-videos']";
    private static final String GZIP_COMPRESSION_XPATH = ".//*[@id='edit-media-theplatform-mpx-use-gzip']";
    private static final String MEDIA_PROCESSING_LIMIT_XPATH = ".//*[@id='edit-media-theplatform-mpx-cron-videos-per-run']";
    private static final String MEDIA_FEED_REQUEST_TIMEOUT_XPATH = ".//*[@id='edit-media-theplatform-mpx-cron-videos-timeout']";
    private static final String CRON_QUEUE_PROCESSING_TIME_XPATH = ".//*[@id='edit-media-theplatform-mpx-cron-queue-processing-time']";
    private static final String ACCOUNT_DELETION_LIMIT_XPATH = ".//*[@id='edit-media-theplatform-mpx-account-deletion-item-limit']";
    private static final String DISPLAY_MPX_DEBUG_MESSAGE_LEVEL_XPATH = ".//*[@id='edit-media-theplatform-mpx-output-message-watchdog-severity']";
    private static final String MPX_DEBUG_LOGGING_LEVEL_XPATH = ".//*[@id='edit-media-theplatform-mpx-watchdog-severity']";
    private static final String AUTHENTIFICATION_TOKEN_TTL_XPATH = ".//*[@id='edit-media-theplatform-mpx-token-ttl']";

    private static final String SAVE_CONFIGURATION_XPATH = ".//*[@id='edit-submit']";

    private final WebDriverUtil util;

    public ThePlatformMpxSettingsPage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
        util = WebDriverUtil.getInstance(webDriver);
    }

    public boolean isConnectToMPXButtonPresent() {
        return util.isElementPresent(CONNECT_TO_MPX_XPATH);
    }

    public void connectToMpx() {
        Utilities.logInfoMessage("Click Connect to MPX");
        this.expandMxpLogin();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webDriver.click(CONNECT_TO_MPX_XPATH);
    }

    /**
     * MPX LOGIN SECTION
     */
    public void expandMxpLogin() {
        final String attribute = webDriver.getAttribute(MPX_LOGIN_BLOCK_XPATH, HtmlAttributes.CLASS.get());
        if (attribute.contains("collapsed")) {
            util.click(webDriver.findElement(By.xpath(MPX_LOGIN_LINK_XPATH)));
        }
    }

    public boolean isUsernameForNewAccountPresent() {
        return util.isElementPresent(USERNAME_FOR_NEW_ACCOUNT_XPATH);
    }

    public boolean isPasswordForNewAccountPresent() {
        return util.isElementPresent(PASSWORD_FOR_NEW_ACCOUNT_XPATH);
    }

    public void setCredentialsForNewAccount(final String username, final String password) {
        Utilities.logInfoMessage("Set credentials for new account: " + username);
        webDriver.type(USERNAME_FOR_NEW_ACCOUNT_XPATH, username);
        webDriver.type(PASSWORD_FOR_NEW_ACCOUNT_XPATH, password);
    }

    public void saveNewAccount() {
        Utilities.logInfoMessage("Save new additional account");
        webDriver.click(SAVE_ACCOUNT_XPATH);
    }

    // ------------ IMPORT ACCOUNT LIST ----------------

   /* public void killContent(){
        Utilities.logInfoMessage("Delete Account");
        if(WebDriverUtil.getInstance(webDriver).isElementPresent(ACCOUNT_LIST_BLOCK_XPATH)){
            if(!util.isElementVisible(DELETE_ACCOUNT_XPATH,5)){
                util.click(webDriver.findElement(By.xpath(ACCOUNT_LIST_LINK_XPATH)));
            }
            WaitUtils.perform(webDriver).waitElementVisible(DELETE_ACCOUNT_XPATH,10);
            List<WebElement> deletes = webDriver.findElements(By.xpath(DELETE_ACCOUNT_XPATH));
            for(int i=1; i<=deletes.size(); i++){
                webDriver.click(String.format(DELETE_ACCOUNT_GENERALIZED,i));
                webDriver.click(CONFIRM_DELETE_ACCOUNT_XPATH);
                wailtTillProgressBarFinished();
                Assertion.assertTrue(isStatusMessageShown(),
                        "There is no status message after 'Kill MPX content' action");
            }}
        else {Utilities.logInfoMessage("Account already has deleted");}
    }*/

    public void killContent(String brand) {
        Utilities.logInfoMessage("Delete Account");
        if (WebDriverUtil.getInstance(webDriver).isElementPresent(ACCOUNT_LIST_BLOCK_XPATH)) {
            if (!util.isElementVisible(DELETE_ACCOUNT_XPATH, 5)) {
                util.click(webDriver.findElement(By.xpath(ACCOUNT_LIST_LINK_XPATH)));
            }
            WaitUtils.perform(webDriver).waitElementVisible(DELETE_ACCOUNT_XPATH, 10);
            List<WebElement> deletes = webDriver.findElements(By.xpath(DELETE_ACCOUNT_XPATH));
            for (int i = 1; i <= deletes.size(); i++) {
                final String mpxUserName = Config.getInstance().getRokuMPXUsername(brand);
                final String mpxUserPassword = Config.getInstance().getRokuMPXPassword(brand);
                setTHePlatformUserName(mpxUserName, i);
                setTHePlatformPassword(mpxUserPassword, i);
                webDriver.click(String.format(DELETE_ACCOUNT_GENERALIZED, i));
                WaitUtils.perform(webDriver).waitElementPresent(CONFIRM_DELETE_ACCOUNT_XPATH, 10);
                webDriver.click(CONFIRM_DELETE_ACCOUNT_XPATH);
                WaitUtils.perform(webDriver).waitForProgressNotPresent(999999999);
                Assertion.assertTrue(isStatusMessageShown(),
                        "There is no status message after 'Kill MPX content' action");
            }
        } else {
            Utilities.logInfoMessage("Account already has deleted");
        }
    }

    public void setTHePlatformUserName(String username, int index) {
        Utilities.logInfoMessage("Set account list");
        webDriver.type(String.format(USERNAME_THEPLATFORM_GENERALIZED, index), username);

    }

    public void setTHePlatformPassword(String password, int index) {
        Utilities.logInfoMessage("Set account list");
        webDriver.type(String.format(PASSWORD_THEPLATFORM_GENERALIZED, index), password);
    }

    public void updateAccount(int index) {
        webDriver.click(String.format(UPDATE_ACCOUNT_GENERALIZED, index));
    }

    public void setNewThePlatformAccount(String username, String password, int index) {
        Utilities.logInfoMessage("Set new MPX account list");
        setTHePlatformUserName(username, index);
        setTHePlatformPassword(password, index);
        updateAccount(index);
    }

    public void expandAccountList() {
        final String attribute = webDriver.getAttribute(ACCOUNT_LIST_BLOCK_XPATH, HtmlAttributes.CLASS.get());
        if (attribute.contains("collapsed")) {
            util.click(webDriver.findElement(By.xpath(ACCOUNT_LIST_LINK_XPATH)));
        }
    }

    // ---------- IMPORT ACCOUNTS ----
    public boolean isImportAccountSectionPresent() {
        return util.isElementPresent(IMPORT_ACCOUNT_SECTION_XPATH)
                && util.isElementPresent(SAVE_IMPORT_ACCOUNT_SETTINGS_XPATH);
    }

    public boolean isDefaultPlayerSelectActive() {
        if (util.isElementPresent(DEFAULT_PLAYER_XPATH)) {
            final String attribute = webDriver.getAttribute(DEFAULT_PLAYER_XPATH, HtmlAttributes.DISABLED.get());
            if (attribute != null
                    && !attribute.isEmpty()
                    && (attribute.equalsIgnoreCase(HtmlAttributeValues.DISABLED.get()) || attribute
                    .equalsIgnoreCase("true"))) return false;
            return true;
        }
        return false;
    }

    public ThePlatformMpxSettingsPage selectImportAccount(final String... select) {
        Utilities.logInfoMessage("Import account");
        final List<WebElement> options = webDriver.findElements(By.xpath(SELECT_IMPORT_ACCOUNT_OPTIONS_XPATH));
        if (null != select && select.length != 0) {
            for (final WebElement option : options) {
                if (option.getText().toLowerCase().contains(select[0].toLowerCase())) {
                    Utilities.logInfoMessage("Select '" + option.getText() + "' option from Select Import Account dropdown");
                    webDriver.selectFromDropdown(SELECT_IMPORT_ACCOUNT_XPATH, option.getText());
                    break;
                }
            }
        } else {
            for (final WebElement option : options) {
                if (!option.getText().toLowerCase().contains("select")
                        && option.getText().toLowerCase().contains("stage")) {
                    Utilities.logInfoMessage("Select first option from Select Import Account dropdown: " + option.getText());
                    webDriver.selectFromDropdown(SELECT_IMPORT_ACCOUNT_XPATH, option.getText());
                    break;
                }
            }
        }
        return this;
    }

    public ThePlatformMpxSettingsPage selectImportAccount(int number, final String... select) {
        Utilities.logInfoMessage("Import account");
        final List<WebElement> options = webDriver.findElements(By.xpath(String.format(SELECT_IMPORT_ACCOUNT_OPTIONS_GENERALIZED, number)));
        if (null != select && select.length != 0) {
            for (final WebElement option : options) {
                if (option.getText().toLowerCase().contains(select[0].toLowerCase())) {
                    Utilities.logInfoMessage("Select '" + option.getText() + "' option from Select Import Account dropdown");
                    webDriver.selectFromDropdown(String.format(SELECT_IMPORT_ACCOUNT_GENERALIZED, number), option.getText());
                    break;
                }
            }
        } else {
            for (final WebElement option : options) {
                if (!option.getText().toLowerCase().contains("select")
                        && option.getText().toLowerCase().contains("stage")) {
                    Utilities.logInfoMessage("Select first option from Select Import Account dropdown: " + option.getText());
                    webDriver.selectFromDropdown(String.format(SELECT_IMPORT_ACCOUNT_GENERALIZED, number), option.getText());
                    break;
                }
            }
        }
        return this;
    }

    public ThePlatformMpxSettingsPage selectDefaultPlayer(final String... select) {
        util.click(webDriver.findElement(By.xpath(DEFAULT_PLAYER_XPATH)));
        final List<WebElement> options = webDriver.findElements(By.xpath(DEFAULT_PLAYER_OPTIONS_XPATH));
        if (null != select && select.length != 0) {
            for (final WebElement option : options) {
                if (option.getText().toLowerCase().contains(select[0].toLowerCase())) {
                    Utilities.logInfoMessage("Select '" + option.getText() + "' option from Default Player dropdown");
                    webDriver.selectFromDropdown(DEFAULT_PLAYER_XPATH, option.getText());
                    break;
                }
            }
        } else {
            for (final WebElement option : options) {
                if (!option.getText().toLowerCase().contains("select")) {
                    Utilities.logInfoMessage("Select first option from Default Player dropdown: " + option.getText());
                    webDriver.selectFromDropdown(DEFAULT_PLAYER_XPATH, option.getText());
                    break;
                }
            }
        }
        return this;
    }

    public ThePlatformMpxSettingsPage selectDefaultPlayer(int number, final String... select) {
        util.click(webDriver.findElement(By.xpath(String.format(DEFAULT_PLAYER_GENERALIZED, number))));
        final List<WebElement> options = webDriver.findElements(By.xpath(String.format(DEFAULT_PLAYER_OPTIONS_GENERALIZED, number)));
        if (null != select && select.length != 0) {
            for (final WebElement option : options) {
                if (option.getText().toLowerCase().contains(select[0].toLowerCase())) {
                    Utilities.logInfoMessage("Select '" + option.getText() + "' option from Default Player dropdown");
                    webDriver.selectFromDropdown(String.format(DEFAULT_PLAYER_GENERALIZED, number), option.getText());
                    break;
                }
            }
        } else {
            for (final WebElement option : options) {
                if (!option.getText().toLowerCase().contains("select")) {
                    Utilities.logInfoMessage("Select first option from Default Player dropdown: " + option.getText());
                    webDriver.selectFromDropdown(String.format(DEFAULT_PLAYER_GENERALIZED, number), option.getText());
                    break;
                }
            }
        }
        return this;
    }

    public ThePlatformMpxSettingsPage setPlayerSettings(final boolean doImport, String brand, Instance instance) {
        selectImportAccount(Config.getInstance().getRokuMPXAccount(brand, instance)).saveImportAccountSettings();
        WebDriverUtil.getInstance(webDriver).scrollPageDown();
        importPlayersFromThisAccount(doImport).importVideosFromThisAccount(doImport).selectDefaultPlayer(Config.getInstance().getRokuMPXDefaulPlayer(brand, instance));
        saveImportAccountSettings();
        return this;
    }

    public ThePlatformMpxSettingsPage importPlayersFromThisAccount(final boolean doImport, final int... number) {
        Utilities.logInfoMessage("Set player settings");
        if (doImport) {
            Utilities.logInfoMessage("Import Players from this account");
            if (number != null && number.length != 0) {
                webDriver.setCheckOn(String.format(IMPORT_PLAYERS_FROM_THIS_ACCOUNT_GENERALIZED, number[0]));
            } else {
                webDriver.setCheckOn(IMPORT_PLAYERS_FROM_THIS_ACCOUNT_XPATH);
            }
        } else {
            Utilities.logInfoMessage("Do not import Players from this account");
            if (number != null && number.length != 0) {
                webDriver.setCheckOff(String.format(IMPORT_PLAYERS_FROM_THIS_ACCOUNT_GENERALIZED, number[0]));
            } else {
                webDriver.setCheckOff(IMPORT_PLAYERS_FROM_THIS_ACCOUNT_XPATH);
            }
        }
        return this;
    }

    public ThePlatformMpxSettingsPage importVideosFromThisAccount(final boolean doImport, final int... number) {
        if (doImport) {
            Utilities.logInfoMessage("Import Videos from this account");
            if (number != null && number.length != 0) {
                webDriver.setCheckOn(String.format(IMPORT_VIDEOS_FROM_THIS_ACCOUNT_GENERALIZED, number[0]));
            } else {
                webDriver.setCheckOn(IMPORT_VIDEOS_FROM_THIS_ACCOUNT_XPATH);
            }
        } else {
            Utilities.logInfoMessage("Do not import Videos from this account");
            if (number != null && number.length != 0) {
                webDriver.setCheckOff(String.format(IMPORT_VIDEOS_FROM_THIS_ACCOUNT_GENERALIZED, number[0]));
            } else {
                webDriver.setCheckOff(IMPORT_VIDEOS_FROM_THIS_ACCOUNT_XPATH);
            }
        }
        return this;
    }

    public void saveImportAccountSettings() {
        WebDriverUtil.getInstance(webDriver).click(SAVE_IMPORT_ACCOUNT_SETTINGS_XPATH);
    }

    // ---------- CRON SETTINGS ----------------
    public boolean isCronSettingsSectionPresent() {
        return util.isElementPresent(CRON_SETTINGS_SECTION_XPATH) && util.isElementPresent(SAVE_CONFIGURATION_XPATH);
    }

    public void setDefaultCronSettings() {
        Utilities.logInfoMessage("Set default values for Cron setting section");
        expandCronSettings();
        turnOffSyncMpxPlayersOnCron();
        turnOnSyncMpxMediaOnCron();
        setMediaProcessingLimit(50);
        setMediaFeedRequestTimeout(240);
        setCronQueueProcessingTime(240);
        selectDisplayMpxDebugMessageLevel("Advanced");
        selectMpxDebugLoggingLevel("Advanced");
        setAuthentificationTokenTTL(30);
    }

    public ThePlatformMpxSettingsPage expandCronSettings() {
        final String attribute = webDriver.getAttribute(CRON_SETTINGS_BLOCK_XPATH, HtmlAttributes.STYLE.get());
        if (attribute.equalsIgnoreCase(HtmlAttributeValues.DISPLAY_NONE.get())) {
            util.click(webDriver.findElement(By.xpath(CRON_SETTINGS_LINK_XPATH)));
        }
        return this;
    }

    public ThePlatformMpxSettingsPage setAuthentificationTokenTTL(final int timeInSec) {
        Utilities.logInfoMessage("Set Authentification Token TTL to " + timeInSec);
        webDriver.type(AUTHENTIFICATION_TOKEN_TTL_XPATH, String.valueOf(timeInSec));
        return this;
    }

    /**
     * Basic <br>
     * Advanced <br>
     * Extremely Verbose
     *
     * @param level - debug logging level
     * @return - mpx settings page
     */
    public ThePlatformMpxSettingsPage selectMpxDebugLoggingLevel(final String level) {
        Utilities.logInfoMessage("Set Mpx Debug Logging Level to " + level);
        webDriver.selectFromDropdown(MPX_DEBUG_LOGGING_LEVEL_XPATH, level);
        return this;
    }

    /*
     * Basic <br>
     * Advanced <br>
     * Extremely Verbose
     */
    public ThePlatformMpxSettingsPage selectDisplayMpxDebugMessageLevel(final String level) {
        Utilities.logInfoMessage("Set Display Mpx Debug Message Level to " + level);
        webDriver.selectFromDropdown(DISPLAY_MPX_DEBUG_MESSAGE_LEVEL_XPATH, level);
        return this;
    }

    public ThePlatformMpxSettingsPage setCronQueueProcessingTime(final int timeInSec) {
        Utilities.logInfoMessage("Set Cron Queue Processing Time to " + timeInSec);
        webDriver.type(CRON_QUEUE_PROCESSING_TIME_XPATH, String.valueOf(timeInSec));
        return this;
    }

    public ThePlatformMpxSettingsPage setAccountDeletionLimit(final String limit) {
        Utilities.logInfoMessage("Set Account deletion limit to " + limit);
        webDriver.type(ACCOUNT_DELETION_LIMIT_XPATH, String.valueOf(limit));
        return this;
    }

    public ThePlatformMpxSettingsPage setMediaFeedRequestTimeout(final int timeoutInSec) {
        Utilities.logInfoMessage("Set Media Feed Request Timeout to " + timeoutInSec);
        webDriver.type(MEDIA_FEED_REQUEST_TIMEOUT_XPATH, String.valueOf(timeoutInSec));
        return this;
    }

    public ThePlatformMpxSettingsPage setMediaProcessingLimit(final int limit) {
        Utilities.logInfoMessage("Set Media Processing Limit to " + limit);
        webDriver.type(MEDIA_PROCESSING_LIMIT_XPATH, String.valueOf(limit));
        return this;
    }

    public ThePlatformMpxSettingsPage turnOnSyncMpxMediaOnCron() {
        Utilities.logInfoMessage("Check on Sync Mpx Media on Cron");
        webDriver.setCheckOn(SYNC_MPX_MEDIA_ON_CRON_XPATH);
        return this;
    }

    public ThePlatformMpxSettingsPage turnOffSyncMpxMediaOnCron() {
        Utilities.logInfoMessage("Check off Sync Mpx Media on Cron");
        webDriver.setCheckOff(SYNC_MPX_MEDIA_ON_CRON_XPATH);
        return this;
    }

    public ThePlatformMpxSettingsPage turnOnSyncMpxPlayersOnCron() {
        Utilities.logInfoMessage("Check on Sync Mpx Players on Cron");
        webDriver.setCheckOn(SYNC_MPX_PLAYERS_ON_CRON_XPATH);
        return this;
    }

    public ThePlatformMpxSettingsPage turnOffSyncMpxPlayersOnCron() {
        Utilities.logInfoMessage("Check off Sync Mpx Players on Cron");
        webDriver.setCheckOff(SYNC_MPX_PLAYERS_ON_CRON_XPATH);
        return this;
    }


    public ThePlatformMpxSettingsPage turnOnGzipCompression() {
        Utilities.logInfoMessage("Check on Gzip compression");
        webDriver.setCheckOn(GZIP_COMPRESSION_XPATH);
        return this;
    }

    public ThePlatformMpxSettingsPage turnOffGzipCompression() {
        Utilities.logInfoMessage("Check off Gzip compression");
        webDriver.setCheckOff(GZIP_COMPRESSION_XPATH);
        return this;
    }

    public ThePlatformMpxSettingsPage setSyncMpxMediaOnCron(boolean status) {
        if (status) {
            turnOnSyncMpxMediaOnCron();
        } else {
            turnOffSyncMpxMediaOnCron();
        }
        return this;
    }

    public ThePlatformMpxSettingsPage setSyncMpxPlayersOnCron(boolean status) {
        if (status) {
            turnOnSyncMpxPlayersOnCron();
        } else {
            turnOffSyncMpxPlayersOnCron();
        }
        return this;
    }

    public ThePlatformMpxSettingsPage setGzipCompression(boolean status) {
        if (status) {
            turnOnGzipCompression();
        } else {
            turnOffGzipCompression();
        }
        return this;
    }

    // ----- PUBLISHER7 SETTINGS ----
    public void saveConfiguration() {
        Utilities.logInfoMessage("Save configuration");
        util.click(webDriver.findElement(By.xpath(SAVE_CONFIGURATION_XPATH)));
    }
}
