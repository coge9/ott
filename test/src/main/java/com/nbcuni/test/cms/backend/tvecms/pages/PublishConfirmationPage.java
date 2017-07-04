package com.nbcuni.test.cms.backend.tvecms.pages;

import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PublishConfirmationPage extends ConfirmationPage {

    String confirmTitle = MessageConstants.TITLE_PUBLISH_CONFIRM_PAGE;
    @FindBy(xpath = ".//*[id='edit-instance']")
    private DropDownList selectInstance;

    public PublishConfirmationPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public String selectApiInstance() {
        String selectedValue = null;
        if (webDriver.getTitle().equals(confirmTitle)) {
            selectInstance.selectRandomValueFromDropDown();
            selectedValue = selectInstance.getSelectedValue();
            clickSubmit();
        }
        return selectedValue;
    }

    public boolean isAllInstancePresent(List<String> expectedValues) {
        List<String> actualNames = selectInstance.getValuesToSelect();
        return actualNames.equals(expectedValues);
    }

    public boolean isInstanceListContains(String instanceName) {
        List<String> actualNames = selectInstance.getValuesToSelect();
        return actualNames.contains(instanceName);
    }

}
