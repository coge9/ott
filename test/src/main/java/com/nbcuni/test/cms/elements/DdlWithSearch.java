package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.support.FindBy;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dzianis on 20.05.2016.
 */
public class DdlWithSearch extends AbstractContainer {

    private static String LINK_LOCATOR = ".//a";

    @FindBy(tagName = "a")
    private Link link;

    @FindBy(xpath = ".//input")
    private TextField textField;

    @FindBy(tagName = "li")
    private List<Label> options;

    public void selectValue(String value) {
        if (value == null) {
            return;
        }
        link.click();
        textField.enterText(value);
        clickOption(value);
    }

    public List<String> getListOfAvailableOptions() {
        link.click();
        List<String> textOptions = new LinkedList<String>();
        for (Label item : options) {
            textOptions.add(item.getText().trim());
        }
        link.click();
        return textOptions;
    }

    public List<String> getListOfAvailableOptionsAfterEnteringValue(String value) {
        link.click();
        textField.enterText(value);
        List<String> textOptions = new LinkedList<String>();
        for (Label item : options) {
            textOptions.add(item.getText().trim());
        }
        return textOptions;
    }

    private void clickOption(String value) {
        for (Label item : options) {
            if (item.getText().trim().equals(value)) {
                item.click();
            }
        }
    }

    public String getSelectedValue() {
        return link.getText();
    }
}
