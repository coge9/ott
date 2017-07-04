package com.nbcuni.test.cms.backend.tvecms.pages;

import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.DataUtil;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CronPage extends MainRokuAdminPage {
    public static final String PAGE_TITLE = "Cron";
    public static final String STATUS_XPATH = "//div[@class='messages status']";
    private static final String RUN_CRON_XPATH = ".//*[@id = 'edit-run']";
    private static final String RUN_CRON_EVERY_XPATH = ".//*[@id='edit-cron-safe-threshold']";
    private static final String RUN_CRON_EVERY_OPTIONS_XPATH = ".//*[@id='edit-cron-safe-threshold']//option";
    private static final String SAVE_CONFIGURATION_XPATH = ".//*[@id='edit-submit']";
    private static final String TIME_XPATH = ".//form[@id='system-cron-settings']//*[@class='placeholder']";

    public CronPage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
    }

    /*
     * Options: Never, 1 hour, 3 hours, 6 hours, 12 hours, 1 day, 1 week
     */
    public void selectRunCronEvery(final String... periodic) {
        final List<WebElement> options = webDriver.findElements(By.xpath(RUN_CRON_EVERY_OPTIONS_XPATH));
        if (null != periodic && periodic.length != 0) {
            for (final WebElement option : options) {
                if (!option.getText().toLowerCase().contains(periodic[0].toLowerCase())) {
                    webDriver.selectFromDropdown(RUN_CRON_EVERY_XPATH, option.getText());
                    break;
                }
            }
        } else {

            for (final WebElement option : options) {
                if (!option.getText().toLowerCase().contains("select")) {
                    Utilities.logInfoMessage("Select first option from Run Cron Every dropdown: " + option.getText());
                    webDriver.selectFromDropdown(RUN_CRON_EVERY_XPATH, option.getText());
                    break;
                }
            }
        }
    }

    public void saveConfiguration() {
        Utilities.logInfoMessage("Save configuration");
        webDriver.click(SAVE_CONFIGURATION_XPATH);
    }

    public void runCron(final int launchCount, final long... pageLoadTimeoutMin) {

        for (int i = 0; i < launchCount; i++) {
            if (pageLoadTimeoutMin != null && pageLoadTimeoutMin.length > 0) {
                webDriver.manage().timeouts().pageLoadTimeout(pageLoadTimeoutMin[0], TimeUnit.MINUTES);
            } else {
                webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
            }
            Utilities.logInfoMessage("Run Cron manually");
            final String currentUrl = webDriver.getCurrentUrl();
            if (WebDriverUtil.getInstance(webDriver).isElementPresent(RUN_CRON_XPATH)) {
                webDriver.click(RUN_CRON_XPATH);
                WaitUtils.perform(webDriver).waitForPageLoad();
            }
            if (!isStatusMessageShown()) {
                webDriver.get(currentUrl);
                if (WebDriverUtil.getInstance(webDriver).isElementPresent(RUN_CRON_XPATH)) {
                    // do {
                    webDriver.click(RUN_CRON_XPATH);
                    i++;
                    //} while (i == 5 || isStatusMessageShown());

                } else {
                    i = launchCount;
                }
            }
        }
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        webDriver.manage().timeouts().pageLoadTimeout(Config.getInstance().getPageLoadWaitTime(), TimeUnit.SECONDS);
    }

    public int getTimeCronRun() {
        String[] timesString = DataUtil.splitByWhiteSpace(webDriver.getText(TIME_XPATH));
        return Integer.parseInt(timesString[0]);

    }
}
