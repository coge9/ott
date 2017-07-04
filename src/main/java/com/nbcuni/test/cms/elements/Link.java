package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Link extends AbstractElement {

    public Link(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public Link(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }


    public Link(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public Link(AbstractElement parentElement, String xPath) {
        super(parentElement, xPath);
    }

    public Link(AbstractElement parentElement, By byLocator) {
        super(parentElement, byLocator);
    }

    public Link(WebElement webElement) {
        super(webElement);
    }

    public String getText() {
        return element().getText();
    }

    public String getLinkUrl() {
        return element().getAttribute(HtmlAttributes.HREF.get());
    }

    public void click() {
        element().click();
    }

    public void clickWithAjaxWait() {
        Utilities.logInfoMessage("Click " + getName() + " button");
        element().click();
        waitFor().waitForThrobberNotPresent(10);
    }

    public void clickJS() {
        WebDriverUtil.getInstance(driver).click(element());
    }
}
