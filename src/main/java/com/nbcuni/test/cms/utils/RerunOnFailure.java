package com.nbcuni.test.cms.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RerunOnFailure implements IRetryAnalyzer {
    Config config = Config.getInstance();
    private int retryCount = 0;

    @Override
    public boolean retry(final ITestResult result) {
        if (config.isReRunOnFailure()) {
            if (retryCount < config.getReRunOnFailureCount()) {
                retryCount++;
                return true;
            }
        }
        return false;
    }
}