package com.nbcuni.test.cms.utils;

import com.google.gson.JsonElement;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Assertion {

    public static final String ACTUAL = "; actual [";
    public static final String EXPECTED = "], expected [";
    public static final String BR_TAG = "</br>";

    private Assertion(){
        super();
    }

    public static void assertTrue(final boolean condition, final String message) {
        if (!condition) {
            Utilities.logSevereMessage(message);
            Assert.fail(message);
        }
    }

    public static void assertTrue(final boolean condition,
                                  final String message, final CustomWebDriver webDriver) {
        if (!condition) {
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessage(message);
            Assert.fail(message);

        }
    }

    public static void assertFalse(final boolean condition, final String message) {
        if (condition) {
            Utilities.logSevereMessage(message);
            Assert.fail(message);
        }
    }

    public static void assertFalse(boolean condition, String message,
                                   CustomWebDriver webDriver) {
        if (condition) {
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessage(message);
            Assert.fail(message);
        }

    }

    public static void assertEquals(final String actual, final String expected,
                                    final String message) {
        if (!actual.equals(expected)) {
            final String errorMessage = message + ACTUAL + actual
                    + EXPECTED + expected + "]";
            Utilities.logSevereMessage(errorMessage);
            Assert.fail(errorMessage);
        }
    }

    public static void assertEqualsIgnoreCase(final String actual, final String expected,
                                              final String message) {
        if (!actual.equalsIgnoreCase(expected)) {
            final String errorMessage = message + ACTUAL + actual
                    + EXPECTED + expected + "]";
            Utilities.logSevereMessage(errorMessage);
            Assert.fail(errorMessage);
        }
    }

    public static void assertEquals(final Double actual, final Double expected,
                                    final String message) {
        if (!actual.equals(expected)) {
            final String errorMessage = message + ACTUAL + actual
                    + EXPECTED + expected + "]";
            Utilities.logSevereMessage(errorMessage);
            Assert.fail(errorMessage);
        }
    }


    public static void assertEquals(final Boolean actual,
                                    final Boolean expected, final String message) {
        if (actual != expected) {
            if (!(actual == null && expected == null)) {
                if (!actual.equals(expected)) {
                    final String errorMessage = message + ACTUAL + actual
                            + EXPECTED + expected + "]";
                    Utilities.logSevereMessage(errorMessage);
                    Assert.fail(errorMessage);
                }
            }
        }
    }

    // TODO: [DK for AD] rename method to be obvious that Lists are compared
    // If there are much objects in list and most of them has errors you will get finaly large String which is hard to read
    // Create List<String> which contains errors and log them at the end of the method.
    public static void assertEqualsObject(final List<?> actual,
                                          final List<?> expected, final String message) {
        final StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(BR_TAG);
        // TODO: to if can be combined together because the do exactly the same thing except log message
        if (actual.size() > expected.size()) {
            errorMessage.append("The number of actual assets: ")
                    .append(actual.size())
                    .append(" more than number of expected: ")
                    .append(expected.size())
                    .append(BR_TAG);
        }
        if (expected.size() > actual.size()) {
            errorMessage.append("The number of expected assets: ")
                    .append(expected.size())
                    .append(" more than number of actual: ")
                    .append(actual.size())
                    .append(BR_TAG);
        }
        List<Object> copy = new ArrayList<Object>(actual);
        for (final Object value : expected) {
            if (!actual.contains(value)) {
                errorMessage.append(" Expected value: '").append(value)
                        .append("' not contains in Actual list.</br> ");
            } else {
                copy.remove(value);
            }
        }
        for (final Object value : copy) {
            errorMessage.append(" Actual value: '").append(value)
                    .append("' is extra and not expected to be present.</br> ");
        }

        if (errorMessage.length() > 5) {
            Utilities.logSevereMessage(message + "\n" + errorMessage.toString());
            Assert.fail(message + "\n" + errorMessage.toString());
        }

    }

    public static void assertEquals(final String actual, final String expected,
                                    final String message, final CustomWebDriver webDriver) {
        if (!actual.equals(expected)) {
            final String errorMessage = message + ACTUAL + actual
                    + EXPECTED + expected + "]";
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessage(errorMessage);
            Assert.fail(errorMessage);
        }
    }

    public static void assertEquals(final int actual, final int expected,
                                    final String message) {
        if (actual != expected) {
            final String errorMessage = message + ACTUAL + actual
                    + EXPECTED + expected + "]";
            Utilities.logSevereMessage(errorMessage);
            Assert.fail(errorMessage);
        }
    }

    public static void assertEquals(final int actual, final int expected,
                                    final String message, final CustomWebDriver webDriver) {
        if (actual != expected) {
            final String errorMessage = message + ACTUAL + actual
                    + EXPECTED + expected + "]";
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessage(errorMessage);
            Assert.fail(errorMessage);
        }
    }

    public static void fail(final String message) {
        Utilities.logSevereMessage(message);
        Assert.fail(message);
    }

    public static void assertContains(final String full, final String part,
                                      final String message) {
        if (!full.contains(part)) {
            final String errorMessage = message + "; full string [" + full
                    + "] does not contain [" + part + "] as expected";
            Utilities.logSevereMessage(errorMessage);
            Assert.fail(errorMessage);
        }
    }

    public static void assertContains(final String full, final String part,
                                      final String message, final CustomWebDriver webDriver) {
        if (!full.contains(part)) {
            final String errorMessage = message + "; full string [" + full
                    + "] does not contain [" + part + "] as expected";
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessage(errorMessage);
            Assert.fail(errorMessage);
        }
    }

    public static void assertMapEquals(final Map<String, String> actual,
                                       final Map<String, String> expected, final String message) {
        final StringBuilder errorMessage = new StringBuilder();
        for (final String key : expected.keySet()) {
            if (actual.containsKey(key)) {
                if (!actual.get(key).equalsIgnoreCase(expected.get(key))) {
                    errorMessage.append(key).append(" not equals: actual ")
                            .append(actual.get(key)).append(", expected ")
                            .append(expected.get(key)).append("; ");
                }
            } else {
                errorMessage.append(
                        "Actual data does not contain pair with key: ").append(
                        key);
            }
        }

        if (errorMessage.length() > 0) {
            Utilities.logSevereMessage(message + "\n" + errorMessage.toString());
            Assert.fail(message + "\n" + errorMessage.toString());
        }
    }

    public static void assertMapContains(final Map<String, String> actual,
                                         final Map<String, String> expected, final String message) {
        final StringBuilder errorMessage = new StringBuilder();
        for (final String key : expected.keySet()) {
            if (actual.containsKey(key)) {
                if (!expected.get(key).contains(actual.get(key))) {
                    errorMessage.append(key).append(" not equals: actual ")
                            .append(actual.get(key)).append(", expected ")
                            .append(expected.get(key)).append("; ");
                }
            } else {
                errorMessage.append(
                        "Actual data does not contain pair with key: ").append(
                        key);
            }
        }

        if (errorMessage.length() > 0) {
            Utilities.logSevereMessage(message + "\n" + errorMessage.toString());
            Assert.fail(message + "\n" + errorMessage.toString());
        }
    }

    public static void assertListEquals(final List<String> actual,
                                        final List<String> expected, final String message) {
        final StringBuilder errorMessage = new StringBuilder();
        if (actual.size() > expected.size()) {
            errorMessage.append("The number of actual assets: ")
                    .append(actual.size())
                    .append(" more than number of expected: ")
                    .append(expected.size());
        }
        if (expected.size() > actual.size()) {
            errorMessage.append("The number of expected assets: ")
                    .append(expected.size())
                    .append(" more than number of actual: ")
                    .append(actual.size());
        }
        for (final String value : expected) {
            if (!actual.contains(value)) {

                errorMessage.append(" Expected value: ").append(value)
                        .append(" not contains in actual list: ")
                        .append(actual).append(", expected list")
                        .append(expected).append("; ");
            }
        }

        if (errorMessage.length() > 0) {
            Utilities.logSevereMessage(message + "\n" + errorMessage.toString());
            Assert.fail(message + "\n" + errorMessage.toString());
        }
    }

    public static void assertListContains(final List<String> actual,
                                          final List<String> expected, final String message) {
        final StringBuilder errorMessage = new StringBuilder();
        for (final String value : actual) {
            if (!expected.contains(value)) {

                errorMessage.append(" Actual value: ").append(value)
                        .append(" not contains in expected list: ")
                        .append(expected).append(", expected list")
                        .append(expected).append("; ");
            }
        }

        if (errorMessage.length() > 0) {
            Utilities.logSevereMessage(message + "\n" + errorMessage.toString());
            Assert.fail(message + "\n" + errorMessage.toString());
        }
    }

    public static void assertJSONObject(JsonElement actual, JsonElement expected,
                                        boolean strictValidation) {
        try {
            String act = actual.toString();

            String exp = expected.toString();
            JSONAssert.assertEquals(exp, act, strictValidation);
        } catch (JSONException e) {
            Utilities.logSevereMessage(e.getMessage().toString());

        }
    }
}
