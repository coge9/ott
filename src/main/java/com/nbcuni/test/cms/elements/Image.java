package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;

/**
 * Created by Ivan on 16.03.2016.
 */
public class Image extends AbstractElement {
    public Image(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public Image(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public Image(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public Image(AbstractElement parentElement, String xPath) {
        super(parentElement, xPath);
    }

    public Image(AbstractElement parentElement, By byLocator) {
        super(parentElement, byLocator);
    }

    public Image(WebElement webElement) {
        super(webElement);
    }

    public String getSource() {
        return element().getAttribute(HtmlAttributes.SRC.get());
    }

    public int getHeight() {
        return element().getSize().getHeight();
    }

    public int getWidth() {
        return element().getSize().getWidth();
    }

    @Override
    public String getName() {
        File file = new File(this.getSource());
        String name = file.getName().split("\\?")[0];
        return name;
    }
}
