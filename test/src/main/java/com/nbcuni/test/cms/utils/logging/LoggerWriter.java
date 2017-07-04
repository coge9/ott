package com.nbcuni.test.cms.utils.logging;

import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;

public class LoggerWriter {

    public static boolean writeInfoIntoLogger(boolean b, String warning, String info) {
        if (b) {
            Utilities.logInfoMessage(info);
            return true;
        } else {
            Utilities.logWarningMessage(warning);
            return false;
        }
    }

    public static boolean writeInfoIntoLogger(boolean b, String warning, String info, CustomWebDriver webDriver) {
        if (b) {
            Utilities.logInfoMessage(info);
            return true;
        } else {
            Utilities.logWarningMessage(warning);
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            return false;
        }
    }

    public static boolean writeWarningIntoLogger(boolean b, String warning) {
        if (!b) {
            Utilities.logWarningMessage(warning);
            return false;
        }
        return true;
    }

    public static boolean writeWarningIntoLogger(boolean b, String warning, CustomWebDriver webDriver) {
        if (!b) {
            Utilities.logWarningMessage(warning);
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            return false;
        }
        return true;
    }


    public static boolean writeSuccessIntoLogger(boolean b, String warning, String success) {
        if (b) {
            Utilities.logInfoMessage(success);
            return true;
        } else {
            Utilities.logWarningMessage(warning);
            return false;
        }
    }

    public static boolean writeSuccessIntoLogger(boolean b, String warning, String success, CustomWebDriver webDriver) {
        if (b) {
            Utilities.logInfoMessage(success);
            return true;
        } else {
            Utilities.logWarningMessage(warning);
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            return false;
        }
    }

    public static boolean statusCheck(boolean b, String fail, String success) {
        if (b) {
            Utilities.logInfoMessage(success);
            return true;
        } else {
            Utilities.logSevereMessage(fail);
            return false;
        }
    }

}
