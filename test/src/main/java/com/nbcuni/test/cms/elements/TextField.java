package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TextField extends AbstractElement {

    public TextField(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public TextField(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public TextField(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public TextField(AbstractElement parentElement, By by) {
        super(parentElement, by);
    }

    public TextField(AbstractElement parentElement, String xpath) {
        super(parentElement, xpath);
    }

    public TextField(WebElement webElement) {
        super(webElement);
    }

    public void enterText(String value) {
        if (value == null) {
            return;
        }
        Utilities.logInfoMessage("Enter " + value + " into " + getName() + " field");
        try {
            clearText();
        } catch (Exception e) {
            Utilities.logSevereMessage("Field was not clear");
        }
        if (isVisible()) {
            element().sendKeys(value);
        } else {
            Assertion.assertTrue(false, "The element: " + getName() + " is not displayed at the page ", driver);
        }
    }

    public boolean verifyValue(String expectedValue) {
        Utilities.logInfoMessage("Verification value in " + getName() + " field");
        String actualValue = getValue();
        if (!expectedValue.equals(actualValue)) {
            Utilities.logInfoMessage("Actual value: " + actualValue
                    + " is differ from expected: " + expectedValue);
            return false;
        } else {
            Utilities.logInfoMessage("Value is correct");
            return true;
        }
    }

    public boolean isEmpty() {
        return getValue().isEmpty();
    }

    public boolean isEditable() {
        return element().isEnabled();
    }

    public String getValue() {
        return element().getAttribute(HtmlAttributes.VALUE.get());
    }

    public void clearText() {
        element().clear();
    }

    public void updateValue(String newValue) {
        if (newValue != null && !getValue().equals(newValue)) {
            enterText(newValue);
        }
    }

    @Override
    public String toString() {
        return "TextField{" +
                byLocator + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
