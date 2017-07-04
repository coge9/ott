package com.nbcuni.test.cms.backend.chiller.block;

import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by Alena_Aukhukova on 5/13/2016.
 */
public class Publish extends AbstractContainer {

    private static final String SUBMIT_ID = "edit-submit";
    private static final String INSTANCE_LIST_ID = "edit-instance";
    private static final String CANCEL_BUTTON_ID = "edit-cancel";
    String confirmTitle = MessageConstants.TITLE_PUBLISH_CONFIRM_PAGE;
    @FindBy(xpath = ".")
    private Button publishButton;

    // For checking confirmation

    public DropDownList elementInstanceDropDown() {
        return new DropDownList(getWebDriver(), By.id(INSTANCE_LIST_ID));
    }

    private Button getCancelButton() {
        return new Button(getWebDriver(), By.id(CANCEL_BUTTON_ID));
    }

    public Button elementSubmitConfirm() {
        return new Button(getWebDriver(), By.id(SUBMIT_ID));
    }

    private String selectRandomApiInstance() {
        String selectedValue = null;
        if (webDriver.getTitle().equals(confirmTitle)) {
            elementInstanceDropDown().selectRandomValueFromDropDown();
            selectedValue = elementInstanceDropDown().getSelectedValue();
            submit();
        }
        return selectedValue;
    }

    private void selectApiInstance(String instanceName) {
        if (webDriver.getTitle().equals(confirmTitle)) {
            elementInstanceDropDown().selectFromDropDown(instanceName);
            submit();
        }
    }

    public void click() {
        publishButton.clickWithJS();
        WaitUtils.perform(webDriver).waitForPageLoad();
        selectRandomApiInstance();
    }

    public void click(String instanceName) {
        publishButton.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        selectApiInstance(instanceName);
    }

    public boolean clickPublishAndCheckConfirmation(String expectedName, List<String> expectedNames) {
        publishButton.click();
        boolean resultForAll = elementInstanceDropDown().isAllValuesPresentInDropDown(expectedNames);
        boolean resultForContains = elementInstanceDropDown().isValuePresentInDropDown(expectedName);
        return resultForAll && resultForContains;
    }

    public void submit() {
        elementSubmitConfirm().click();
    }

    public void cancelPublishing() {
        publishButton.click();
        waitFor().waitForPageLoad();
        getCancelButton().click();
        waitFor().waitForPageLoad();
    }

}
