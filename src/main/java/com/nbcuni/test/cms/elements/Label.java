package com.nbcuni.test.cms.elements;

import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Label extends AbstractElement {

    public Label(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public Label(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }


    public Label(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public Label(AbstractElement parentElement, String xPath) {
        super(parentElement, xPath);
    }

    public Label(AbstractElement parentElement, By byLocator) {
        super(parentElement, byLocator);
    }

    public Label(WebElement webElement) {
        super(webElement);
    }

    public String getText() {
        return element().getText();
    }

    public void click() {
        element().click();
    }

    public boolean isLink() {
        return !element().findElements(By.xpath(".//a")).isEmpty();
    }
}
