package com.nbcuni.test.cms.elements;

import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CustomDropDownListWithSearch extends AbstractElement {

    private String searchFieldLocator = ".//input[@type='text']";
    private String optionLocator = ".//ul/li";
    private String clickableElement = ".//a";
    private String chosenOption = ".//a/span";

    public CustomDropDownListWithSearch(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public CustomDropDownListWithSearch(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public CustomDropDownListWithSearch(WebElement webElement) {
        super(webElement);
    }

    public void selectFromDropDown(String valueToSelect) {
        Utilities.logInfoMessage("Selecting value " + valueToSelect + " from " + getName()
                + " drop down list");
        element().findElement(By.xpath(clickableElement)).click();
        element().findElement(By.xpath(searchFieldLocator)).sendKeys(
                valueToSelect);
        boolean isFound = false;
        for (WebElement value : element().findElements(By.xpath(optionLocator))) {
            if (value.getText().equals(valueToSelect)) {
                value.click();
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            Utilities.logSevereMessageThenFail("Value " + valueToSelect + " is not found in "
                    + getName() + " drop down list");
        }
    }

    public void selectFromDropDown(int index) {
        Utilities.logInfoMessage("Selecting value by index " + index + " from " + getName()
                + " drop down list");
        element().findElement(By.xpath(clickableElement)).click();
        List<WebElement> options = element().findElements(
                By.xpath(optionLocator));
        options.get(index).click();
    }

    /**
     * Select random value from 2 item to last item
     */
    public void selectRandomValueFromDropDown() {
        Utilities.logInfoMessage("Selecting random value from " + getName()
                + " drop down list");
        element().findElement(By.xpath(clickableElement)).click();
        List<WebElement> options = element().findElements(
                By.xpath(optionLocator));
        options.get(random.nextInt(options.size())).click();
    }

    public void selectFromDropDown(DdlSelectable toSelect) {
        selectFromDropDown(toSelect.getValueToSelect());
    }

    public boolean isValuePresentInDropDown(String expectedValue) {
        element().findElement(By.xpath(clickableElement)).click();
        boolean isFound = false;
        for (WebElement value : element().findElements(By.xpath(optionLocator))) {
            if (value.getText().equals(expectedValue)) {
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            Utilities.logSevereMessageThenFail("Value " + expectedValue + " is not found in "
                    + getName() + " drop down list");
        }
        element().findElement(By.xpath(clickableElement)).click();
        return isFound;
    }

    public boolean isValuePresentInDropDown(DdlSelectable expectedValue) {
        return isValuePresentInDropDown(expectedValue.getValueToSelect());
    }

    public List<String> getValuesToSelect() {
        Utilities.logInfoMessage("Getting values from " + getName() + " drop down list");
        List<String> values = new ArrayList<String>();
        element().findElement(By.xpath(clickableElement)).click();
        for (WebElement value : element().findElements(By.xpath(optionLocator))) {
            values.add(value.getText());
        }
        element().findElement(By.xpath(clickableElement)).click();
        return values;
    }

    public String getSelectedValue() {
        return element.findElement(By.xpath(chosenOption)).getText();
    }

    @SuppressWarnings("unchecked")
    public <T extends DdlSelectable> T getSelectedObject(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        String value = getSelectedValue();
        T object = null;
        try {
            if (clazz.getEnumConstants() != null
                    && clazz.getEnumConstants().length > 0) {
                object = clazz.getEnumConstants()[0];
            } else {
                object = clazz.newInstance();
            }
        } catch (InstantiationException e) {
            Utilities.logSevereMessageThenFail("Error during getting object from drop down "
                    + e.getMessage());
        } catch (IllegalAccessException e) {
            Utilities.logSevereMessageThenFail("Error during getting object from drop down "
                    + e.getMessage());
        }
        object = (T) object.getItemByText(value);
        return object;
    }
}
