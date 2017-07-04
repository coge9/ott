package com.nbcuni.test.cms.elements;

import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TextFieldWithSimpleAutocompleteGroup extends AbstractElement {

    TextFieldWithSimpleAutocomplete tempField = new TextFieldWithSimpleAutocomplete(driver, "");

    public TextFieldWithSimpleAutocompleteGroup(CustomWebDriver driver, WebElement webElement) {
        super(driver, webElement);
    }

    public TextFieldWithSimpleAutocompleteGroup(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public TextFieldWithSimpleAutocompleteGroup(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public TextFieldWithSimpleAutocompleteGroup(AbstractElement parentElement, By by) {
        super(parentElement, by);
    }

    public TextFieldWithSimpleAutocompleteGroup(AbstractElement parentElement, String xpath) {
        super(parentElement, xpath);
    }

    public TextFieldWithSimpleAutocompleteGroup(WebElement webElement) {
        super(webElement);
    }

    public int getNumberOfElements() {
        return elements().size();
    }

    public void enterTextByFieldIndex(int index, String value) {
        List<WebElement> list = elements();
        if (list.size() < index) {
            throw new RuntimeException("There is no field with index " + index);
        } else {
            tempField.setName("field with " + index + " index");
            tempField.setElement(list.get(index - 1));
            tempField.enterText(value);
        }
    }

    public void enterRandomTextByFieldIndex(int index) {
        List<WebElement> list = elements();
        if (list.size() < index) {
            throw new RuntimeException("There is no field with index " + index);
        } else {
            tempField.setName("field with " + index + " index");
            tempField.setElement(list.get(index - 1));
            tempField.enterRandonValueFromAutoComplete();
        }
    }

    public String getValueByFieldIndex(int index) {
        List<WebElement> list = elements();
        if (list.size() < index) {
            throw new RuntimeException("There is no field with index " + index);
        } else {
            tempField.setName("field with " + index + " index");
            return tempField.getValue();
        }
    }

    public void clearFieldByIndex(int index) {
        List<WebElement> list = elements();
        if (list.size() < index) {
            throw new RuntimeException("There is no field with index " + index);
        } else {
            tempField.setName("field with " + index + " index");
            tempField.setElement(list.get(index - 1));
            tempField.clearText();
        }
    }

    public void fillAllFieldsWithRandomText() {
        int numberOfElements = getNumberOfElements();
        for (int i = 1; i <= numberOfElements; i++) {
            enterRandomTextByFieldIndex(i);
        }
    }

    public void fillEmptyFieldsWithRandomText() {
        int numberOfElements = getNumberOfElements();
        for (int i = 1; i <= numberOfElements; i++) {
            if (getValueByFieldIndex(i).equals("")) {
                enterRandomTextByFieldIndex(i);
            }
        }
    }

    public void clearAllFields() {
        int numberOfElements = getNumberOfElements();
        for (int i = 1; i <= numberOfElements; i++) {
            clearFieldByIndex(i);
        }
    }

    public List<String> getFieldsValues() {
        List<String> values = new ArrayList<String>();
        int numberOfElements = getNumberOfElements();
        for (int i = 1; i <= numberOfElements; i++) {
            String value = getValueByFieldIndex(i);
            if (!value.equals("")) {
                values.add(value);
            }
        }
        return values;
    }

    public List<TextFieldWithSimpleAutocomplete> getTextFieldWithSimpleAutocompleteList() {
        List<WebElement> elementList = this.elements();
        List<TextFieldWithSimpleAutocomplete> fieldWithSimpleAutocompleteList = new LinkedList<>();
        for (WebElement element : elementList) {
            fieldWithSimpleAutocompleteList.add(new TextFieldWithSimpleAutocomplete(driver, element));
        }
        return fieldWithSimpleAutocompleteList;
    }

    public TextFieldWithSimpleAutocomplete getTextFieldWithSimpleAutocompleteByIndex(int index) {
        return this.getTextFieldWithSimpleAutocompleteList().get(index);
    }

    public int getNumberOfTextFieldWithSimpleAutocomplete() {
        return this.getTextFieldWithSimpleAutocompleteList().size();
    }


}
