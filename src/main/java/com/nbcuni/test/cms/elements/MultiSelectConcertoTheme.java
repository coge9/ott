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
 * Created by Ivan_Karnilau on 22-Jun-16.
 */
public class MultiSelectConcertoTheme extends AbstractContainer {

    @FindBy(xpath = ".//input[@type='search']")
    private TextField input;

    @FindBy(xpath = "//ul[@class='select2-results__options']/li")
    private List<Link> options;

    @FindBy(xpath = ".//ul/li[contains(@class,'selection__choice')]")
    private List<WebElement> choiced;


    public void select(String valueToSelect) {
        if (valueToSelect == null) {
            return;
        }
        Utilities.logInfoMessage("Selecting value " + valueToSelect);
        if (!getSelected().contains(valueToSelect)) {
            if (currentElement.isEnable()) {
                if (input.isPresent()) {
                    WebDriverUtil.getInstance(webDriver).click(input.element());
                    input.enterText(valueToSelect);
                }
                for (Link option : options) {
                    if (option.getText().equalsIgnoreCase(valueToSelect)) {
                        option.clickWithAjaxWait();
                        return;
                    }
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

            toReturn.add(WebDriverUtil.getInstance(webDriver).getTextFirstNode(elem));
        }
        return toReturn;
    }

    public void clearSelection() {
        if (!getSelected().isEmpty()) {
            for (int i = choiced.size() - 1; i >= 0; i--) {
                choiced.get(i).findElement(By.xpath(".//span")).click();
            }
        }
    }
}
