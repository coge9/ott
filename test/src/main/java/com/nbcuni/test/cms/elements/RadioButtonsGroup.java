package com.nbcuni.test.cms.elements;

import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 02-Oct-15.
 */
public class RadioButtonsGroup extends AbstractElement {

    private static final String RADIO_BUTTON_GROUP = ".//div/input[@type='radio']";
    private static final String SELECTED_RADIO_BUTTON_LABEL = ".//*[@checked='checked']/following-sibling::label";

    public RadioButtonsGroup(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public RadioButtonsGroup(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public RadioButtonsGroup(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public RadioButtonsGroup(AbstractElement parentElement, String xPath) {
        super(parentElement, xPath);
    }

    public RadioButtonsGroup(AbstractElement parentElement, By byLocator) {
        super(parentElement, byLocator);
    }

    public RadioButtonsGroup(WebElement webElement) {
        super(webElement);
    }

    public List<RadioButton> getRadioButtonList() {
        List<RadioButton> radioButtons = new ArrayList<>();
        List<WebElement> radioButtonList = element().findElements(By.xpath(RADIO_BUTTON_GROUP));
        for (WebElement radioButton : radioButtonList) {
            radioButtons.add(new RadioButton(driver, radioButton));
        }
        return radioButtons;
    }

    public void selectRadioButtonByName(String name) {
        if (name == null) {
            return;
        }
        List<RadioButton> radioButtons = this.getRadioButtonList();
        for (RadioButton radioButton : radioButtons) {
            if (radioButton.getText().equalsIgnoreCase(name)) {
                radioButton.click();
            }
        }
    }

    public boolean getRadioButtonStatus(String name) {
        List<RadioButton> radioButtons = this.getRadioButtonList();
        for (RadioButton radioButton : radioButtons) {
            if (radioButton.getText().equalsIgnoreCase(name)) {
                return radioButton.isSelected();
            }
        }
        Utilities.logSevereMessageThenFail("No Radio Button with name " + name);
        return false;
    }

    public void selectRadioButtonByIndex(int index) {
        this.getRadioButtonList().get(index).click();
    }

    public List<String> getRadioButtonsNames() {
        List<String> radioButtonsName = new ArrayList<>();
        List<RadioButton> radioButtons = this.getRadioButtonList();
        for (RadioButton radioButton : radioButtons) {
            radioButtonsName.add(radioButton.getText());
        }
        return radioButtonsName;
    }

    public int getNumberOfTabs() {
        return this.getRadioButtonList().size();
    }

    public String getSelectedRadioButton() {
        return element().findElement(By.xpath(SELECTED_RADIO_BUTTON_LABEL)).getText().trim();
    }

    @Override
    public boolean isEnable() {
        List<RadioButton> radioButtonList = this.getRadioButtonList();
        for (RadioButton radioButton : radioButtonList) {
            if (!radioButton.isEnable()) {
                return false;
            }
        }
        return true;
    }
}
