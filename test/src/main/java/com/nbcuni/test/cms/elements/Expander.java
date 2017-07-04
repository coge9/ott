package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Expander extends AbstractElement {

    public Expander(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public Expander(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }


    public Expander(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public Expander(WebElement webElement) {
        super(webElement);
    }

    /**
     * Only for Module's content on Page.
     * For current moment if block expand tag contains "collapsed"
     *
     * @return return state of expander (collapsed or not)
     */
    public boolean isCollapsed() {
        return !element().getAttribute(HtmlAttributes.CLASS.get()).contains("collapsed");
    }

    public void collapse() {
        if (!isCollapsed()) {
            element().click();
        }
    }

    public void expand() {
        if (isCollapsed()) {
            element().click();
        }
    }
}
