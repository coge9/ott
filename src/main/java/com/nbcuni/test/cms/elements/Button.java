package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;

public class Button extends AbstractElement {

    public Button(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public Button(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public Button(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public Button(AbstractElement parentElement, By by) {
        super(parentElement, by);
    }

    public Button(AbstractElement parentElement, String xpath) {
        super(parentElement, xpath);
    }

    public Button(WebElement webElement) {
        super(webElement);
    }


    public void click() {
        clickWithoutWait();
        waitFor().waitForPageLoad();
    }

    public void clickWithoutWait() {
        Utilities.logInfoMessage("Click " + getName() + " button");
        try {
            element().click();
        } catch (ElementNotVisibleException e) {
            util().click(element());
        }
    }

    public void clickWithAjaxWait() {
        Utilities.logInfoMessage("Click " + getName() + " button");
        element().click();
        waitFor().waitForThrobberNotPresent(15);
    }

    public void clickWithProgressBarWait() {
        clickWithProgressBarWait(100);
    }

    public void clickWithProgressBarWait(int seconds) {
        Utilities.logInfoMessage("Click " + getName() + " button");
        element().click();
        waitFor().waitForProgressNotPresent(seconds);
    }

    public void clickWithDisabledWait() {
        clickWithAjaxWait();
        waitAttributeNotPresent(HtmlAttributes.DISABLED.get(), 5);
    }

    public void clickWithJS() {
        Utilities.logInfoMessage("Click with JS " + getName() + " button");
        WebDriverUtil.getInstance(driver).click(element());
    }

    public String getValue() {
        return element().getAttribute(HtmlAttributes.VALUE.get());
    }

}
