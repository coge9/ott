package com.nbcuni.test.cms.utils.logging.listeners;

import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.lang.reflect.Field;

/*
*  TesNG listener define to take screenshots upon test failure of test skipped.
*  This screenshot is added to the automation report for the specific test script.
*
*
* */

public class ScreenshotTaker extends TestListenerAdapter {

    // declare field of webDriver name in test class.
    public static final String WEB_DRIVER_FIELD_NAME = "webDriver";

    @Override
    public void onTestFailure(ITestResult result) {
        takeScreenShot(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        takeScreenShot(result);
    }


    private void takeScreenShot(ITestResult result) {
        try {
            // get test class of the current test with java Reflection API.
            Class testClass = result.getTestClass().getRealClass();
            // check if test class is inherited from BaseAuthFlowTest.class.
            if (BaseAuthFlowTest.class.isAssignableFrom(testClass)) {
                // take custom webDriver file with Reflection API.
                Field driverField = BaseAuthFlowTest.class.getDeclaredField(WEB_DRIVER_FIELD_NAME);
                // make filed to be accessible.
                driverField.setAccessible(true);
                // get CustomWebDriver instance from test class by java Reflection API
                CustomWebDriver driver = (CustomWebDriver) driverField.get(result.getInstance());
                // capture screenshot of browser which is managed by CustomWebDriver instance
                Utilities.logSevereMessage("Final state screenshot: ");
                WebDriverUtil.getInstance(driver).attachScreenshot();
            }

        } catch (ReflectiveOperationException e) {
            // log error in case of any failure
            Utilities.logSevereMessage("Unable to take final Screenshot " + Utilities.convertStackTraceToString(e));
        }

    }
}
