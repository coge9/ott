package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class DropDownList extends AbstractElement {

    private static final String OPTGROUP = ".//optgroup[translate(@label, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='%s']";

    public DropDownList(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public DropDownList(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public DropDownList(WebElement webElement) {
        super(webElement);
    }

    private CustomSelect getSelect(String... optgroupLabel) {
        if (optgroupLabel != null && optgroupLabel.length > 0) {
            String optgroupLocator = String.format(OPTGROUP, optgroupLabel[0].toLowerCase());
            return new CustomSelect(element().findElement(By.xpath(optgroupLocator)));
        }
        return new CustomSelect(element());
    }

    public void selectFromDropDown(String valueToSelect, String... optgroupLabel) {
        if (valueToSelect == null) {
            return;
        }
        Utilities.logInfoMessage("Selecting value " + valueToSelect + " from " + getName() + " drop down list");
        if (element().isEnabled()) {
            getSelect(optgroupLabel).selectByVisibleText(valueToSelect);
        }
    }

    public String getOptgroup(String value) {
        List<WebElement> groups = element().findElements(By.tagName("optgroup"));
        for (WebElement group : groups) {
            String groupName = group.getAttribute(HtmlAttributes.LABEL.get());
            String optgroupLocator = String.format(OPTGROUP, groupName).toLowerCase();
            CustomSelect select = new CustomSelect(group);
            if (select.getAvailableOptions().contains(value)) {
                return groupName;
            }
        }
        return null;
    }

    public void selectIgnoreCaseFromDropDown(String valueToSelect) {
        if (valueToSelect == null) {
            return;
        }
        Utilities.logInfoMessage("Selecting value " + valueToSelect + " from " + getName() + " drop down list");
        if (element().isEnabled()) {
            for (String value : getValuesToSelect()) {
                if (value.equalsIgnoreCase(valueToSelect)) {
                    CustomSelect select = new CustomSelect(element());
                    select.selectByVisibleText(value);
                }
            }
        }
    }

    public void selectWithAjaxWaiting(String valueToSelect) {
        if (valueToSelect == null) {
            return;
        }
        Utilities.logInfoMessage("Selecting value " + valueToSelect + " from " + getName() + " drop down list and AJAX waiting");
        selectFromDropDown(valueToSelect);
        waitFor().waitForThrobberNotPresent(15);
    }

    public void selectFromDropDown(int index) {
        Utilities.logInfoMessage("Selecting value by index " + index + " from " + getName() + " drop down list");
        getSelect().selectByIndex(index);
    }

    /**
     * Select random value from 2 item to last item
     */
    public void selectRandomValueFromDropDown() {
        Utilities.logInfoMessage("Selecting random value from " + getName() + " drop down list");
        int numberOfElements = getSelect().getOptions().size();
        int index = 0;
        if (numberOfElements > 1) {
            index = SimpleUtils.randomNumber(1, numberOfElements - 1);
        }
        getSelect().selectByIndex(index);
        Utilities.logInfoMessage("Value " + getSelectedValue() + " is selected.");
    }

    /**
     * Select random value from dropdown except one provided as parameter
     *
     * @param exceptValue - value you don't want to be selected.
     *
     */
    public void selectRandomValueFromDropDownExcept(String exceptValue) {
        Utilities.logInfoMessage("Selecting random value from " + getName() + " drop down list");
        int numberOfElements = getSelect().getOptions().size();
        if (numberOfElements == 1) {
            throw new TestRuntimeException("There is only one option available for select");
        }
        int i = 0;
        while (i <= 15) {
            int index = SimpleUtils.randomNumber(0, numberOfElements - 1);
            getSelect().selectByIndex(index);
            String value = getSelect().getFirstSelectedOption().getText();
            if (!value.equals(exceptValue)) {
                break;
            }
            i++;
        }
        if (i > 15) {
            throw new TestRuntimeException("Was not able to select value from " + getName() + " except " + exceptValue);
        }
        Utilities.logInfoMessage("Value " + getSelectedValue() + " is selected.");
    }

    public void selectRandomValueFromDropDownNotContains(String partText) {
        Utilities.logInfoMessage("Selecting random value from " + getName() + " drop down list");
        int numberOfElements = getSelect().getOptions().size();
        if (numberOfElements == 1) {
            throw new TestRuntimeException("There is only one option available for select");
        }
        int i = 0;
        while (i <= 15) {
            int index = SimpleUtils.randomNumber(0, numberOfElements - 1);
            getSelect().selectByIndex(index);
            String value = getSelect().getFirstSelectedOption().getText();
            if (!value.contains(partText)) {
                break;
            }
            i++;
        }
        if (i > 15) {
            throw new TestRuntimeException("Was not able to select value from " + getName() + " which not contains " + partText);
        }
        Utilities.logInfoMessage("Value " + getSelectedValue() + " is selected.");
    }

    public void selectFromDropDown(DdlSelectable toSelect) {
        Utilities.logInfoMessage("Selecting value " + toSelect.getValueToSelect() + " from " + getName() + " drop down list");
        getSelect().selectByVisibleText(toSelect.getValueToSelect());
    }

    public boolean isValuePresentInDropDown(String expectedValue) {
        Utilities.logInfoMessage("Verification of presence value " + expectedValue + " in " + getName() + " drop down list");
        List<WebElement> options = getSelect().getOptions();
        for (WebElement option : options) {
            if (option.getText().equals(expectedValue)) {
                Utilities.logInfoMessage("Value is present");
                return true;
            }
        }
        Utilities.logInfoMessage("Value is absent");
        return false;
    }

    public boolean isAllValuesPresentInDropDown(List<String> expectedValues) {
        Utilities.logInfoMessage("Verification of presence values " + expectedValues + " in " + getName() + " drop down list");
        List<String> values = getValuesToSelect();
        return CollectionUtils.isEqualCollection(values, expectedValues);
    }

    public boolean isValuePresentInDropDown(DdlSelectable expectedValue) {
        return isValuePresentInDropDown(expectedValue.getValueToSelect());
    }

    public List<String> getValuesToSelect() {
        Utilities.logInfoMessage("Getting values from " + getName() + " drop down list");
        List<String> values = new ArrayList<String>();
        List<WebElement> options = getSelect().getOptions();
        for (WebElement option : options) {
            values.add(option.getText());
        }
        return values;
    }

    public String getSelectedValue() {
        return getSelect().getFirstSelectedOption().getText();
    }


    @SuppressWarnings("unchecked")
    public <T extends DdlSelectable> T getSelectedObject(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        String value = getSelect().getFirstSelectedOption().getText();
        T object = null;
        try {
            if (clazz.getEnumConstants() != null && clazz.getEnumConstants().length > 0) {
                object = clazz.getEnumConstants()[0];
            } else {
                object = clazz.newInstance();
            }
        } catch (InstantiationException e) {
            Utilities.logSevereMessageThenFail("Error during getting object from drop down " + Utilities.convertStackTraceToString(e));
        } catch (IllegalAccessException e) {
            Utilities.logSevereMessageThenFail("Error during getting object from drop down " + Utilities.convertStackTraceToString(e));
        }
        object = (T) object.getItemByText(value);
        return object;
    }

}
