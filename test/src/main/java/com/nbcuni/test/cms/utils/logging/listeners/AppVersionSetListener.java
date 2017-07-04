package com.nbcuni.test.cms.utils.logging.listeners;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class AppVersionSetListener extends TestListenerAdapter {


    @Override
    public void onTestStart(ITestResult result) {
        String appVersion = System.getenv("BUILD_TAG");
        if (appVersion != null && !appVersion.isEmpty()) {
            result.setAttribute("APP_VERSION", appVersion);
        }
    }
}
