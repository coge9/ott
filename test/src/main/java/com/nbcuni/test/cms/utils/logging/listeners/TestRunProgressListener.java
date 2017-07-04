package com.nbcuni.test.cms.utils.logging.listeners;

import com.nbcuni.test.webdriver.Utilities;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRunProgressListener extends TestListenerAdapter {

    private volatile static int currentIndex = 0;
    private volatile static int allTestNumber = 0;
    /**
     * This test listener allows track test RUN progress.
     * <p>
     * In the beginning of the run it calculate total number of
     * tests to run (included data driven tests).
     * <p>
     * On start of each test it increase current index of test (int variable)
     * and display information about current test in console e.g.
     * <p>
     * Running test (20 from 124)
     */

    private long start;

    @Override
    public void onStart(ITestContext testContext) {
        calculateTotalNumberOfTestScripts(testContext);
        super.onStart(testContext);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ++currentIndex;
        String percentage = String.format("%.2f", (double) currentIndex / allTestNumber * 100);
        Utilities.logInfoMessage("\n\n\n\n " + "Running test (" + (currentIndex) + " from " + allTestNumber + "  what is " + percentage + "% )");
        Utilities.logInfoMessage(result.getTestClass().getRealClass().getName() + "\n");
        Utilities.logInfoMessage("Till this moment:");
        Utilities.logInfoMessage(" PASSED: " + getPassedTests().size());
        Utilities.logInfoMessage(" FAILED: " + getFailedTests().size());
        Utilities.logInfoMessage(" SKIPPED: " + getSkippedTests().size() + "\n");
        super.onTestStart(result);
    }


    private void calculateTotalNumberOfTestScripts(ITestContext testContext) {
        // get all test methods;
        ITestNGMethod[] methods = testContext.getAllTestMethods();
        for (int i = 0; i < methods.length; i++) {
            // for each method identify number of runs.
            try {
                ITestNGMethod testMethod = methods[i];
                // get real class of test method.
                Class classOfTest = testMethod.getRealClass();
                // get @Test annotation and identify if it has data provider parameters.
                Test testAnnotation = testMethod.getConstructorOrMethod().getMethod().getAnnotation(Test.class);
                Class<?> dataProviderclass = testAnnotation.dataProviderClass();
                // if provider name is empty string it means test doesnt have dataprovider.
                String providerName = testAnnotation.dataProvider();
                if (!providerName.isEmpty()) {
                    // if data provider class is Object.class this mean that data provider is in the same class with test method.
                    if (dataProviderclass.equals(Object.class)) {
                        dataProviderclass = classOfTest;
                        allTestNumber += calculateResultsInDataProvider(dataProviderclass, providerName, dataProviderclass.newInstance());
                        // This mean data provider is in the separate class in static method.
                    } else {
                        allTestNumber += calculateResultsInDataProvider(dataProviderclass, providerName, null);
                    }
                } else {
                    allTestNumber += 1;
                }
            } catch (Exception throwable) {
                // in case of any exception consider that method will run only once.
                allTestNumber += 1;
            }
        }
    }


    /**
     * Method will calculate number of run test with dataProvider.
     * It invoke data provider method and take number of results it return
     *
     * @param dataProviderclass    - clss object of data provider
     * @param dataProviderName     - name of data provider
     * @param objectToInvokeMethod - object of dataProvider class or NULL in case of static method
     */


    private int calculateResultsInDataProvider(Class dataProviderclass, String dataProviderName, Object objectToInvokeMethod) {
        try {
            // get all methods of data provider class
            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(dataProviderclass.getDeclaredMethods()));
            Method providerMethod = null;
            // identify which method is related to required data provider
            for (final Method method : allMethods) {
                // check if method has @DataProvider annotation
                if (method.isAnnotationPresent(DataProvider.class)) {
                    // if name() parameter of annotation is equal to dataProviderName consider we found correct method of dataProvider
                    DataProvider annotInstance = method.getAnnotation(DataProvider.class);
                    if (annotInstance.name().equals(dataProviderName)) {
                        providerMethod = method;
                        break;
                    }
                }
            }
            providerMethod.setAccessible(true);
            // invoke dataProvider method and return number of data in data proivder
            Object[][] dataProviderResult = (Object[][]) providerMethod.invoke(objectToInvokeMethod);
            return getNumberOfNotNullInArray(dataProviderResult);
        } catch (Throwable e) {
            // in case of any exception consider data provider return only one value.
            return 1;
        }
    }


    /**
     * Calculate only not NULL values in array.
     *
     * @param array  - array under test.
     *
     * */

    private int getNumberOfNotNullInArray(Object[][] array) {
        int number = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] != null) {
                    number++;
                    break;
                }
            }
        }
        return number;
    }
}
