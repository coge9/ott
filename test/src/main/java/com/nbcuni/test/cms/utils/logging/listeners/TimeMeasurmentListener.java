package com.nbcuni.test.cms.utils.logging.listeners;

import com.nbcuni.test.webdriver.Utilities;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TimeMeasurmentListener extends TestListenerAdapter {

    /**
     *  This test listener allows to write time of each test running.
     *
     *  Time can be inaccurate. So it is better to use it as relative
     *  data, but not as absolute.
     *
     *  Also has functionality to right in log name of current test.
     *
     */

    private long start;

    @Override
    public void onTestStart(ITestResult result) {
        Utilities.logInfoMessage(" START TEST CASE [" + result.getTestClass().getName() + "->" + result.getName() + "]\n\n\n\n\n");
        start = System.currentTimeMillis();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        writeTiming(result, "SUCCESSFULL");
    }


    @Override
    public void onTestFailure(ITestResult result) {
        writeTiming(result, "FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        writeTiming(result, "SKIPPED");
    }


    private void writeTiming(ITestResult result, String testStatus) {
        long end = System.currentTimeMillis();
        Utilities.logInfoMessage("</br></br>");
        String timeLog = "\n\n\n[TIME OF TEST RUNNING IS] " + (end - start) / 1000 + " seconds \n";
        String statusLog = testStatus + " [" + result.getTestClass().getName() + "->" + result.getName() + "]\n\n\n\n";
        Utilities.logInfoMessage(timeLog + statusLog);
    }
}
