package com.nbcuni.test.cms.backend.tvecms.pages.people;

import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.CheckBoxGroup;
import com.nbcuni.test.cms.elements.RadioButtonsGroup;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.entities.User;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by Alena_Aukhukova on 9/9/2015.
 */
public class PeopleEditRolePage extends Page {

    @FindBy(id = "edit-name")
    private TextField username;

    @FindBy(id = "edit-mail")
    private TextField email;

    @FindBy(id = "edit-status")
    private RadioButtonsGroup status;

    @FindBy(id = "edit-roles")
    private CheckBoxGroup roles;

    @FindBy(id = "edit-submit")
    private Button save;

    public PeopleEditRolePage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    public List<String> verifyPage() {
        Utilities.logSevereMessage("Skip checking of page");
        return null;
    }

    public String getUserName() {
        return username.getValue();
    }

    public String getUserMail() {
        return email.getValue();
    }

    public String getStatus() {
        return status.getSelectedRadioButton();
    }

    public List<String> getCheckedRoles() {
        return roles.getListOfChecked();
    }

    public void clickSave() {
        save.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public User getUserObject() {
        User user = new User();
        user.setUsername(getUserName());
        user.setEmail(getUserMail());
        return user;
    }
}
