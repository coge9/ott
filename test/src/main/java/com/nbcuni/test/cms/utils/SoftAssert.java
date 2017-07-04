package com.nbcuni.test.cms.utils;

import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import junit.framework.AssertionFailedError;
import org.testng.Assert;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author Aliaksei_Dzmitrenka
 *         Custom soft assert class
 */

public class SoftAssert {

    private static final String EQUALS_MESSAGE_PATTERN = "%s:<br>&nbsp;&nbsp;Expected: [%s],<br>&nbsp;&nbsp;Accctual: [%s]";
    private static final String EQUALS_LIST_SIZE_MESSAGE_PATTERN = "%s : expected list size is [%s] actual list size is [%s]. </br>";
    private static final String EQUALS_MAP_SIZE_MESSAGE_PATTERN = "%s : expected map size is [%s] actual map size is [%s].";
    private static final String BOOLEAN_MESSAGE_PATTERN = "%s : condition evaluates to %s";
    private static final String CONTAINS_MESSAGE_PATTERN = "%s : full string is: [%s], part of string is: [%s]";
    private static final String CONTAINS_REGEX_MESSAGE_PATTERN = "%s : string is: [%s], regex is: [%s]";
    private static final String CONTAINS_LIST_ELEMENT_MESSAGE_PATTERN = "<br>&nbsp;&nbsp;Element: [%s] dosen't present in expected list";
    private static final String CONTAINS_MAP_ELEMENT_MESSAGE_PATTERN = "<br>&nbsp;&nbsp;Elements dosent equals: {key=[%s],value=[%s] ; key=[%s],value=[%s]}";
    private static final String CONTAINS_MAP_KEY_MESSAGE_PATTERN = "<br>&nbsp;&nbsp;Expected map dosent contains key: [%s]";
    private static final String PRINT_TWO_MAPS_MESSAGE_PATTERN = "<br>&nbsp;&nbsp;Expected map is: %s.<br>&nbsp;&nbsp;Accctual map is: %s.";
    private static final String STRING_CONTAINS_LIST_PATTERN = "<br>&nbsp;&nbsp;Actual string is: %s";
    private static final String LIST_ELEMENT_PATTERN = "<br>&nbsp;&nbsp;Element: [%s] dosen't present in expected actual";

    private static final int logObjectLimit = 800;
    private boolean NO_ANY_ERRORS = true;
    private boolean TEMP_STATUS = true;


    //TRUE
    public SoftAssert assertTrue(Boolean condition, String errorDescription, CustomWebDriver... webDriver) {
        assertTrue(condition, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertTrue(Boolean condition, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        if (!condition) {
            addFail(String.format(BOOLEAN_MESSAGE_PATTERN, errorDescription, condition), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    //FALSE
    public SoftAssert assertFalse(Boolean condition, String errorDescription, CustomWebDriver... webDriver) {
        assertFalse(condition, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertFalse(Boolean condition, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        if (condition) {
            addFail(String.format(BOOLEAN_MESSAGE_PATTERN, errorDescription, condition), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    //CONTAINS STRING
    public SoftAssert assertContains(String bigString, String smallString, String errorDescription, CustomWebDriver... webDriver) {
        assertContains(bigString, smallString, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertContains(String bigString, String smallString, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        if (!(bigString.contains(smallString))) {
            addFail(String.format(CONTAINS_MESSAGE_PATTERN, errorDescription, bigString, smallString), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    public SoftAssert assertNotContains(String bigString, String smallString, String errorDescription, CustomWebDriver... webDriver) {
        assertNotContains(bigString, smallString, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertNotContains(String bigString, String smallString, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        if (bigString.contains(smallString)) {
            addFail(String.format(CONTAINS_MESSAGE_PATTERN, errorDescription, bigString, smallString), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    public SoftAssert assertContainsRegex(String bigString, String regex, String errorDescription, CustomWebDriver... webDriver) {
        assertContainsRegex(bigString, regex, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertContainsRegex(String bigString, String regex, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        if (RegexUtil.getFirstSubstringByRegex(bigString, regex) == null) {
            addFail(String.format(CONTAINS_REGEX_MESSAGE_PATTERN, errorDescription, bigString, regex), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    public SoftAssert assertNull(Object object, String errorDescription, CustomWebDriver... webDriver) {
        assertNull(object, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertNull(Object object, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        if (object != null) {
            addFail(errorDescription, webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    public SoftAssert assertNotNull(Object object, String errorDescription, CustomWebDriver... webDriver) {
        assertNotNull(object, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertNotNull(Object object, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        if (object == null) {
            addFail(errorDescription, webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    //EQUALS IGNORE CASE
    public SoftAssert assertEqualsIgnoreCase(String expected, String actual, String errorDescription, CustomWebDriver... webDriver) {
        assertEqualsIgnoreCase(expected, actual, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertEqualsIgnoreCase(String expected, String actual, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        if (!expected.equalsIgnoreCase(actual)) {
            addFail(String.format(EQUALS_MESSAGE_PATTERN, errorDescription + "\n", expected + "\n", actual), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    //EQUALS OBJECT
    public SoftAssert assertEquals(Object expected, Object actual, String errorDescription, CustomWebDriver... webDriver) {
        assertEquals(expected, actual, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertEquals(Object expected, Object actual, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        if (!(Objects.equals(expected, actual))) {
            String expectedToString = expected != null ? expected.toString() : "null";
            expectedToString = expectedToString.length() > logObjectLimit ? expectedToString.substring(0, logObjectLimit) : expectedToString;
            String actualToString = actual != null ? actual.toString() : "null";
            actualToString = actualToString.length() > logObjectLimit ? actualToString.substring(0, logObjectLimit) : actualToString;
            addFail(String.format(EQUALS_MESSAGE_PATTERN, errorDescription, expectedToString, actualToString), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    public SoftAssert assertEquals(Object expected, Object actual, String errorDescription, String successDescription, Verificator verificator, CustomWebDriver... webDriver) {
        if (!verificator.verify(expected, actual)) {
            String expectedToString = expected.toString();
            expectedToString = expectedToString.length() > logObjectLimit ? expectedToString.substring(0, logObjectLimit) : expectedToString;
            String actualToString = actual.toString();
            actualToString = actualToString.length() > logObjectLimit ? actualToString.substring(0, logObjectLimit) : actualToString;
            addFail(String.format(EQUALS_MESSAGE_PATTERN, errorDescription, expectedToString, actualToString), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    //EQUALS OBJECT
    public SoftAssert assertNotEquals(Object expected, Object actual, String errorDescription, CustomWebDriver... webDriver) {
        assertNotEquals(expected, actual, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertNotEquals(Object expected, Object actual, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        if (Objects.equals(expected, actual)) {
            addFail(String.format(EQUALS_MESSAGE_PATTERN, errorDescription, expected, actual), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    //EQUALS LIST
    public SoftAssert assertEquals(List<?> expected, List<?> actual, String errorDescription, CustomWebDriver... webDriver) {
        assertEquals(expected, actual, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertEquals(List<?> expected, List<?> actual, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        StringBuilder errorMessage = new StringBuilder();
        if (expected.size() != actual.size()) {
            errorMessage.append(String.format(EQUALS_LIST_SIZE_MESSAGE_PATTERN, errorDescription, expected.size(), actual.size()));
        }
        for (Object item : expected) {
            if (!actual.contains(item)) {
                errorMessage.append("Item [" + item + "] is MISSED in actual list </br>");
            }
        }

        for (Object item : actual) {
            if (!expected.contains(item)) {
                errorMessage.append("Item [" + item + "] is EXTRA in actual list </br>");
            }
        }
        if (!errorMessage.toString().isEmpty()) {
            addFail(errorMessage.toString(), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    public SoftAssert assertReflectEquals(List<?> expected, List<?> actual, String errorDescription, String successDescription) {
        StringBuilder errorMessage = new StringBuilder();
        if (expected.size() != actual.size()) {
            errorMessage.append(String.format(EQUALS_LIST_SIZE_MESSAGE_PATTERN, errorDescription, expected.size(), actual.size()));
        }
        try {

            ReflectionAssert.assertReflectionEquals(errorDescription, expected, actual, ReflectionComparatorMode.LENIENT_ORDER);
        } catch (AssertionFailedError e) {
            addFail("<pre>" + e.getMessage().toString() + "</pre>");
        }

        Utilities.logInfoMessage(successDescription);
        return this;
    }

    public SoftAssert assertReflectEquals(Object expected, Object actual, String errorDescription, String successDescription) {
        try {
            ReflectionAssert.assertReflectionEquals(errorDescription, expected, actual, ReflectionComparatorMode.LENIENT_ORDER);
        } catch (AssertionFailedError e) {
            addFail("<pre>" + e.getMessage().toString() + "</pre>");
        }

        Utilities.logInfoMessage(successDescription);
        return this;
    }

    //CONTAINS EXPECTED COLLECTIONS
    public SoftAssert assertContains(List<String> bigList, List<String> smallList, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        int count = 0;
        for (String longValue : bigList) {
            for (String shortValue : smallList) {
                if (longValue.toUpperCase().contains(shortValue.toUpperCase())) {
                    count++;
                    Utilities.logInfoMessage("String " + longValue + " from big List contains string " + shortValue + " from small List");
                }
            }
        }
        assertTrue(count == smallList.size(), errorDescription, successDescription, webDriver);
        return this;
    }

    //CONTAINS ALL COLLECTIONS
    public SoftAssert assertContainsAll(String bigString, Collection<String> list, String errorDescription, CustomWebDriver... webDriver) {
        assertContainsAll(bigString, list, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertContainsAll(String bigString, Collection<String> list, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        StringBuilder errorMessage = new StringBuilder();
        boolean status = true;
        for (String stringExpected : list) {
            if (!bigString.contains(stringExpected)) {
                if (status) {
                    errorMessage.append(errorDescription + String.format(STRING_CONTAINS_LIST_PATTERN, bigString));
                    status = false;
                }
                errorMessage.append(String.format(LIST_ELEMENT_PATTERN, stringExpected));
            }
        }

        if (!errorMessage.toString().isEmpty()) {
            addFail(errorMessage.toString(), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }


    public SoftAssert assertContainsAll(Collection<?> bigList, Collection<?> smallList, String errorDescription, CustomWebDriver... webDriver) {
        assertContainsAll(bigList, smallList, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertContainsAll(Collection<?> bigList, Collection<?> smallList, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        StringBuilder errorMessage = new StringBuilder();
        boolean status = true;
        for (Object objExpected : smallList) {
            if (!bigList.contains(objExpected)) {
                if (status) {
                    //errorMessage.append(errorDescription + String.format(PRINT_TWO_LISTS_MESSAGE_PATTERN, bigList, smallList));
                    status = false;
                }
                errorMessage.append(String.format(CONTAINS_LIST_ELEMENT_MESSAGE_PATTERN, objExpected));
            }
        }
        if (!errorMessage.toString().isEmpty()) {
            addFail(errorMessage.toString(), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    @SuppressWarnings({"rawtypes"})
    public SoftAssert assertContainsAll(Collection<?> bigList, Collection<?> smallList, String errorDescription, String successDescription, Verificator verificator, CustomWebDriver... webDriver) {
        StringBuilder errorMessage = new StringBuilder();
        boolean status = true;
        for (Object expected : smallList) {
            if (!contains(expected, bigList, verificator)) {
                if (status) {
                    //errorMessage.append(errorDescription + String.format(PRINT_TWO_LISTS_MESSAGE_PATTERN, bigList, smallList));
                    status = false;
                }
                errorMessage.append(String.format(CONTAINS_LIST_ELEMENT_MESSAGE_PATTERN, expected));
            }
        }
        if (!errorMessage.toString().isEmpty()) {
            addFail(errorMessage.toString(), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private boolean contains(Object expected, Collection<?> list, Verificator verificator) {
        for (Object actual : list) {
            boolean isVerified = false;
            if (verificator.verify(expected, actual)) {
                return true;
            }
            if (isVerified) {
                break;
            }
        }
        Utilities.logInfoMessage("VERIFICATION FALSE");
        return false;
    }

    //EQUALS MAP
    public SoftAssert assertEquals(Map<?, ?> expected, Map<?, ?> actual, String errorDescription, CustomWebDriver... webDriver) {
        assertEquals(expected, actual, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertEquals(Map<?, ?> expected, Map<?, ?> actual, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        if (expected.size() != actual.size()) {
            addFail(String.format(EQUALS_MAP_SIZE_MESSAGE_PATTERN, errorDescription, expected.size(), actual.size()), webDriver);
        } else {
            assertContainsAll(expected, actual, errorDescription, successDescription, webDriver);
        }

        return this;
    }

    //CONTAINS MAP
    public SoftAssert assertContainsAll(Map<?, ?> bigMap, Map<?, ?> smallMap, String errorDescription, CustomWebDriver... webDriver) {
        assertContainsAll(bigMap, smallMap, errorDescription, null, webDriver);
        return this;
    }

    public SoftAssert assertContainsAll(Map<?, ?> bigMap, Map<?, ?> smallMap, String errorDescription, String successDescription, CustomWebDriver... webDriver) {
        StringBuilder errorMessage = new StringBuilder();
        boolean status = true;
        for (final Object key : smallMap.keySet()) {
            if (bigMap.containsKey(key)) {
                if (!smallMap.get(key).equals(bigMap.get(key))) {
                    if (status) {
                        errorMessage.append(errorDescription + String.format(PRINT_TWO_MAPS_MESSAGE_PATTERN, bigMap, smallMap));
                        status = false;
                    }
                    errorMessage.append(String.format(CONTAINS_MAP_ELEMENT_MESSAGE_PATTERN, key, bigMap.get(key), key, smallMap.get(key)));
                }

            } else {
                if (status) {
                    errorMessage.append(errorDescription + String.format(PRINT_TWO_MAPS_MESSAGE_PATTERN, bigMap, smallMap));
                    status = false;
                }
                errorMessage.append(String.format(CONTAINS_MAP_KEY_MESSAGE_PATTERN, key));
            }
        }
        if (!errorMessage.toString().isEmpty()) {
            addFail(errorMessage.toString(), webDriver);
        } else if (successDescription != null) {
            Utilities.logInfoMessage(successDescription);
        }
        return this;
    }

    public void addFail(String message, CustomWebDriver... webDriver) {
        Utilities.logSevereMessage(message);
        if (webDriver.length > 0) {
            WebDriverUtil.getInstance(webDriver[0]).attachScreenshot();
        }
        NO_ANY_ERRORS = false;
        TEMP_STATUS = false;
    }

    public void assertAll(String message) {
        if (!NO_ANY_ERRORS) {
            Assert.fail(message);
        }
    }

    public void assertAll() {
        if (!NO_ANY_ERRORS) {
            NO_ANY_ERRORS = true;
            Assert.fail("Test Faild.");
        }
    }

    public boolean isNotAnyError() {
        return NO_ANY_ERRORS;
    }

    public boolean getTempStatus() {
        return TEMP_STATUS;
    }

    public void resetTempStatus() {
        TEMP_STATUS = true;
    }
}
