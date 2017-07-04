package com.nbcuni.test.cms.utils;

import com.nbcuni.test.lib.Util;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.lang3.StringUtils;
import org.testng.Reporter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**************************************************************************
 * . NBC.com Application Library. Copyright
 *
 * @author Jeremy Ocampo
 * @version 1.0 Date: September 1, 2013
 ***************************************************************************/

public class AppLib {
    private static final int FOUR_ZERO_FOUR = 404;
    public static final String PAGE_URL = "PAGE_URL: ";
    private final Util utility;
    private final Config config;
    private CustomWebDriver webDriver = null;
    private String environment = "";
    private String browser = "";

    public AppLib(final CustomWebDriver driver, final String environment, final String... browser) {
        webDriver = driver;
        utility = new Util(webDriver);
        config = Config.getInstance();
        manageWebDriverSettings();
        setEnvironmentInfo(environment);
    }

    public String getEnvironment() {
        return environment;
    }

    private void manageWebDriverSettings() {
        webDriver.manage().timeouts().implicitlyWait(config.getImplicitWaitTime(),
                TimeUnit.SECONDS);
        if (browser != null && !("chrome".equalsIgnoreCase(browser) && System.getProperty("os.name").toLowerCase()
                .contains("mac"))) {
            webDriver.manage().window().maximize();
        }
    }

    private void setEnvironmentInfo(final String sEnv) {
        Reporter.log("Setting Environment to: " + sEnv);
        this.environment = sEnv;
        config.setEnvironment(sEnv);
    }

    public void showErrors(final List<String> err) {
        String tempErr = StringUtils.EMPTY;
        if (!err.isEmpty()) {
            for (final String str : err) {
                tempErr = tempErr + "\n" + str;
                Reporter.log(str);
            }
            Reporter.log(tempErr);
        }
    }

    public void validatePageTitle(final String sPagename) {
        final String sPage = webDriver.getTitle();
        utility.compareTextValues(sPage, sPagename);
    }

    /**
     * This method will attain all links on the page and perform a HTTP response verification on the
     * page ensuring that it does not return a error
     *
     * @throws Exception - error
     */
    public void verifyHttpResponseForAllLinksOnPage() throws Exception {
        final ArrayList<String> rawLinks = webDriver.getAllLinksOnPage();
        for (final String link : rawLinks) {
            if (link.startsWith("http:")) {
                final URL url = new URL(link.trim());
                Reporter.log(PAGE_URL + url);
                final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                final int code = connection.getResponseCode();
                if (code == FOUR_ZERO_FOUR) {
                    Reporter.log("Response Code incorrect");
                }
            }
            if (link.startsWith("/")) {
                final String baseLocation = webDriver.getLocation();
                final String baseURL = baseLocation.replace("com/", "com");
                final String rawLinks2 = baseURL + link;
                final URL url = new URL(rawLinks2.trim());
                Reporter.log(PAGE_URL + url);
                final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                final int code = connection.getResponseCode();
                if (code == FOUR_ZERO_FOUR) {
                    Reporter.log("Response Code incorrect");
                }
            }
        }
    }


    public int verifyHttpResponseForLink(String link) throws IOException {
        Utilities.logInfoMessage(link);
        final URL url = new URL(link.trim());
        Reporter.log(PAGE_URL + url);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        return connection.getResponseCode();
    }


}