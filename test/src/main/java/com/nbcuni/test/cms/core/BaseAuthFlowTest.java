package com.nbcuni.test.cms.core;

import com.nbcuni.test.annotation.base.test.CoreBaseTest;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.pageobjectutils.entities.Providers;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.TestFrameworkInitializationUtil;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.RequestHelper;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.*;

import java.util.List;
import java.util.Random;

public class BaseAuthFlowTest extends CoreBaseTest {

    @Autowired
    public ConfigProperties configProperties;
    protected String brand;
    protected Content content;
    protected String environment;
    protected CustomWebDriver webDriver;
    protected String browser;
    protected AppLib aid;
    protected Config config;
    protected List<Providers> data;
    protected MainRokuAdminPage mainRokuAdminPage;
    protected RokuBackEndLayer rokuBackEndLayer;
    protected SoftAssert softAssert;
    protected Random random = new Random();
    protected RequestHelper requestHelper = new RequestHelper();

    @BeforeSuite(alwaysRun = true)
    public void mainInitialization() {
        TestFrameworkInitializationUtil.initDatabase();
        TestFrameworkInitializationUtil.setTempFolder();
        try {
            TestFrameworkInitializationUtil.createBuildInfoFile();
        } catch (Exception e) {
            Utilities.logInfoMessage("Error during get build number.");
        }
    }

    @BeforeClass(alwaysRun = true)
    @Parameters({"brand", "environment", "browser"})
    public void initDatabase(@Optional("syfy") final String brand, @Optional("QA") final String environment, @Optional("firefox") final String browser) {
        config = Config.getInstance();
        this.brand = brand;
        this.browser = browser;
        this.environment = environment;
        config.setEnvironment(environment);
        softAssert = new SoftAssert();
    }

    @BeforeMethod(alwaysRun = true)
    public void initWebDriver() {
        try {
            /**
             * do not delete please, this is example for sauce lab cloud running
             * */
            webDriver = cs;
            cs.setFileDetector(new LocalFileDetector());
            aid = new AppLib(cs, environment);
        } catch (final Exception e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
    }

    /**
     * Instantiate the TestNG After Class Method.
     *
     */

    @AfterClass(alwaysRun = true)
    public void releaseWebDriver() {
        try {
            if (cs != null) {
                if ("firefox".equalsIgnoreCase(cs.getCapabilities().getBrowserName())
                        && !System.getProperty("os.name").toLowerCase().contains("mac")) {
                    Runtime.getRuntime().exec("taskkill /F /IM plugin-container.exe");
                    Thread.sleep(5000);
                    cs.quit();
                } else {
                    cs.quit();
                }
            }
        } catch (final Exception e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
    }

}
