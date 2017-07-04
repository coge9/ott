package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 06-Apr-16.
 */
public class MultiSelect extends AbstractContainer {

    @FindBy(xpath = ".//input[@type='text']")
    private TextField input;

    @FindBy(xpath = ".//li[contains(@class,'active-result')]")
    private Link option;

    @FindBy(xpath = ".//li[contains(@class,'active-result')]")
    private List<WebElement> options;

    @FindBy(xpath = ".//ul/li[contains(@class,'search-choice')]")
    private List<WebElement> choiced;


    public void select(String valueToSelect) {
        if (valueToSelect == null) {
            return;
        }
        Utilities.logInfoMessage("Selecting value " + valueToSelect);
        if (!getSelected().contains(valueToSelect)) {
            if (currentElement.isEnable()) {
                WebDriverUtil.getInstance(webDriver).click(input.element());
                input.enterText(valueToSelect);
                if (option.getText().equalsIgnoreCase(valueToSelect)) {
                    option.clickWithAjaxWait();
                    return;
                }
            }
            Utilities.logSevereMessage("Value not found in multiselect dropdown");
        } else {
            Utilities.logInfoMessage("Value is already selected");
        }

    }

    public void select(List<String> valuesToSelect) {
        if (CollectionUtils.isEmpty(valuesToSelect)) {
            return;
        }
        for (String value : valuesToSelect) {
            this.select(value);
        }
    }

    public List<String> getSelected() {
        List<String> toReturn = new ArrayList<>();
        for (WebElement elem : choiced) {
            toReturn.add(elem.findElement(By.xpath(".//span")).getText());
        }
        return toReturn;
    }

    public void clearSelection() {
        if (!getSelected().isEmpty()) {
            for (WebElement elem : choiced) {
                WebDriverUtil.getInstance(webDriver).click(elem.findElement(By.xpath(".//a")));
            }
        }
    }

    public List<String> getListOfOptions() {
        WebDriverUtil.getInstance(webDriver).click(input.element());
        List<String> toReturn = new ArrayList<>();
        for (WebElement elem : options) {
            toReturn.add(elem.getText().trim());
        }
        return toReturn;
    }
}
