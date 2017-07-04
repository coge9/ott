package com.nbcuni.test.cms.backend.tvecms.pages;

import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ConfirmationPage extends Page {

    @FindBy(id = "edit-cancel")
    private Button cancel;

    @FindBy(id = "edit-submit")
    private Button submit;


    public ConfirmationPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }


    public TVEPage clickSubmit() {
        submit.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new TVEPage(webDriver, aid);
    }

    public TVEPage clickCancel() {
        cancel.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new TVEPage(webDriver, aid);
    }

    @Override
    public List<String> verifyPage() {
        return null;
    }
}
