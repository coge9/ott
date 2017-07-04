package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class TextFieldWithSimpleAutocomplete extends TextField {

    private static final String SEARCH_AUTOCOMPLETE_LIST = "//div[@id='autocomplete']";
    private static final String AUTOCOMPLETE_VALUE = ".//div[@id='autocomplete']//td|.//div[@id='autocomplete']//li";
    private static final String AUTOCOMPLETE_VALUE_GENERALIZED = "(.//div[@id='autocomplete']//td)[%s]|(.//div[@id='autocomplete']//li)[%s]";
    private static final String AUTOCOMPLETE_VALUE_LIST_GENERALIZED = "(.//div[@id='autocomplete']//li)[%s]";

    public TextFieldWithSimpleAutocomplete(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public TextFieldWithSimpleAutocomplete(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public TextFieldWithSimpleAutocomplete(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public TextFieldWithSimpleAutocomplete(AbstractElement parentElement, By by) {
        super(parentElement, by);
    }

    public TextFieldWithSimpleAutocomplete(AbstractElement parentElement, String xpath) {
        super(parentElement, xpath);
    }

    public TextFieldWithSimpleAutocomplete(WebElement webElement) {
        super(webElement);
    }

    public int getNumberOfAutocompleteValues() {
        if (!isAutocompletePresent()) {
            Utilities.logSevereMessage("Autocomplete list is not shown");
            return 0;
        } else
            return driver.getObjectCount(AUTOCOMPLETE_VALUE);

    }

    public boolean isAutocompletePresent() {
        return util().isElementPresent(SEARCH_AUTOCOMPLETE_LIST);
    }

    public void clickValueInAutocompleteTable(final String fullName) {
        element().clear();
        element().sendKeys(fullName);
        waitFor().waitElementPresent(SEARCH_AUTOCOMPLETE_LIST, 7);
        final int numberOfAutocompleteValues = getNumberOfAutocompleteValues();
        for (int i = 1; i <= numberOfAutocompleteValues; i++) {
            final String value = driver.getText(String.format(AUTOCOMPLETE_VALUE_GENERALIZED, i, i));
            if (value.equalsIgnoreCase(fullName)) {
                driver.click(String.format(AUTOCOMPLETE_VALUE_GENERALIZED, i, i));
                Utilities.logInfoMessage("Clicking value" + value + " in autocomplete list");
                break;
            }
        }
        waitFor().waitElementNotPresent(SEARCH_AUTOCOMPLETE_LIST, 7);
    }

    public void clickFirstValueInAutocompleteTable(final String partOfName) {
        element().clear();
        element().sendKeys(partOfName);
        waitFor().waitElementPresent(SEARCH_AUTOCOMPLETE_LIST, 7);
        final int numberOfAutocompleteValues = getNumberOfAutocompleteValues();
        for (int i = 1; i <= numberOfAutocompleteValues; i++) {
            final String value = driver.getText(String.format(AUTOCOMPLETE_VALUE_GENERALIZED, i, i));
            if (value.toLowerCase().contains(partOfName.toLowerCase())) {
                //WebDriverUtil.getInstance(driver).click(String.format(AUTOCOMPLETE_VALUE_GENERALIZED, i, i));
                driver.click(String.format(AUTOCOMPLETE_VALUE_GENERALIZED, i, i));
                Utilities.logInfoMessage("Clicking value" + value + " in autocomplete list");
                break;
            }
        }
        waitFor().waitElementNotPresent(SEARCH_AUTOCOMPLETE_LIST, 7);
    }

    public void clickValueInAutocompleteList(final String fullName) {
        element().clear();
        element().sendKeys(fullName);
        waitFor().waitElementPresent(SEARCH_AUTOCOMPLETE_LIST, 7);
        final int numberOfAutocompleteValues = getNumberOfAutocompleteValues();
        for (int i = 1; i <= numberOfAutocompleteValues; i++) {
            final String value = driver.getText(String.format(AUTOCOMPLETE_VALUE_LIST_GENERALIZED, i));
            if (value.equalsIgnoreCase(fullName)) {
                driver.click(String.format(AUTOCOMPLETE_VALUE_LIST_GENERALIZED, i));
                Utilities.logInfoMessage("Clicking value" + value + " in autocomplete list");
                waitFor().waitElementNotPresent(SEARCH_AUTOCOMPLETE_LIST, 7);
                break;
            }
        }
        //    waitFor().waitElementInvisible(SEARCH_AUTOCOMPLETE_LIST, 7);
    }

    public void clickRandomValueInAutocompleteList(PublishState state, String stateXpath) {
        waitFor().waitElementPresent(SEARCH_AUTOCOMPLETE_LIST, 7);
        List<WebElement> autocompleteValues = driver.findElementsByXPath(AUTOCOMPLETE_VALUE);
        int index = new Random().nextInt(autocompleteValues.size());
        if (index == 0) {
            index = 1;
        }
        WebElement value = autocompleteValues.get(index);
        String gettedText = value.getText();
        String publishState = value.findElement(By.xpath(stateXpath)).getText();
        if (publishState.equalsIgnoreCase(state.toString())) {
            value.click();
            waitFor().waitElementNotPresent(SEARCH_AUTOCOMPLETE_LIST);
            Utilities.logInfoMessage("Clicking value" + gettedText + " in autocomplete list");
        }
    }

    public boolean isValuePresentInAutocompleteList(final String fullName) {
        element().clear();
        element().sendKeys(fullName);
        waitFor().waitElementPresent(SEARCH_AUTOCOMPLETE_LIST, 7);
        final int numberOfAutocompleteValues = getNumberOfAutocompleteValues();
        for (int i = 1; i <= numberOfAutocompleteValues; i++) {
            final String value = driver.getText(String.format(AUTOCOMPLETE_VALUE_GENERALIZED, i, i));
            if (value.equals(fullName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValuePresentInAutocompleteList(final String valueToType, final String valueToSearch) {
        element().clear();
        element().sendKeys(valueToType);
        waitFor().waitElementPresent(SEARCH_AUTOCOMPLETE_LIST, 7);
        final int numberOfAutocompleteValues = getNumberOfAutocompleteValues();
        for (int i = 1; i <= numberOfAutocompleteValues; i++) {
            final String value = driver.getText(String.format(AUTOCOMPLETE_VALUE_GENERALIZED, i, i));
            if (value.equals(valueToSearch)) {
                return true;
            }
        }
        return false;
    }

    public String clickValueInAutocompleteListByNumber(final int number) {
        return clickValueInAutocompleteListByNumber(number, "a");
    }

    public String clickValueInAutocompleteListByNumber(final int number, String name) {
        element().sendKeys(name);
        waitFor().waitElementPresent(SEARCH_AUTOCOMPLETE_LIST, 10);
        final int numberOfAutocompleteValues = getNumberOfAutocompleteValues();
        if (numberOfAutocompleteValues > 0) {
            String title = getItemTitle(number);
            driver.click(String.format(AUTOCOMPLETE_VALUE_GENERALIZED, number, number));
            waitFor().waitElementNotPresent(SEARCH_AUTOCOMPLETE_LIST);
            Utilities.logInfoMessage("Clicking value in autocomplete list");
            return title;
        } else {
            Utilities.logSevereMessage("No more than " + numberOfAutocompleteValues + " values in the autocomplete table exist");
            return null;
        }
    }

    public void clickRandomValueInAutocompleteList() {
        waitFor().waitElementPresent(SEARCH_AUTOCOMPLETE_LIST, 7);
        List<WebElement> autocompleteValues = driver.findElementsByXPath(AUTOCOMPLETE_VALUE);
        int index = new Random().nextInt(autocompleteValues.size());
        if (index == 0) {
            index = 1;
        }
        WebElement value = autocompleteValues.get(index);
        String gettedText = value.getText();
        value.click();
        waitFor().waitElementNotPresent(SEARCH_AUTOCOMPLETE_LIST);
        Utilities.logInfoMessage("Clicking value" + gettedText + " in autocomplete list");
    }

    public void enterRandonValueFromAutoComplete() {
        element().sendKeys("a");
        clickRandomValueInAutocompleteList();
    }

    private String getItemTitle(int lineNumber) {
        return driver.getText(String.format(AUTOCOMPLETE_VALUE_GENERALIZED, lineNumber, lineNumber));
    }

}
