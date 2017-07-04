package com.nbcuni.test.cms.pageobjectutils.html;

import com.nbcuni.test.cms.utils.fielddecorator.ExtendedFieldDecorator;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public abstract class Element {
    protected CustomWebDriver webDriver;
    protected WebDriverUtil driverUtil;

    public Element(final CustomWebDriver webDriver) {
        this.webDriver = webDriver;
        init(this.webDriver);
    }

    public void init(final WebDriver driver) {
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public void pause(int seconds) {
        pauseInMiliseconds(seconds * 1000);
    }

    public void pauseInMiliseconds(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract List<String> verifyElement();
}