package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Ivan_Karnilau on 28-Sep-15.
 */
public class Tab extends AbstractElement {

    private static final String TAB_NAME = ".//strong";
    private static final String TAB_LINK = ".//a";

    public Tab(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public Tab(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public Tab(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public Tab(WebElement webElement) {
        super(webElement);
    }

    public boolean isSelected() {
        return element().getAttribute(HtmlAttributes.CLASS.get()).contains("selected");
    }

    @Override
    public String getName() {
        try {
            return element().findElement(By.xpath(TAB_NAME)).getText();
        } catch (Exception e) {
            return element().findElement(By.xpath(TAB_LINK)).getText();
        }
    }

    public void clickByTab() {
        WebDriverUtil.getInstance(driver).scrollPageUp();
        element().findElement(By.xpath(TAB_LINK)).click();
    }

    public void clickByTabJS() {
        WebDriverUtil.getInstance(driver).click(element().findElement(By.xpath(TAB_LINK)));
    }

    public void open() {
        if (!this.isSelected()) {
            this.clickByTabJS();
        }
    }
}
