package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Ivan_Karnilau on 02-Oct-15.
 */
public class CheckBox extends AbstractElement {

    private static final String LABEL = "./following-sibling::label";

    public CheckBox(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public CheckBox(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public CheckBox(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public CheckBox(AbstractElement parentElement, By by) {
        super(parentElement, by);
    }

    public CheckBox(AbstractElement parentElement, String xpath) {
        super(parentElement, xpath);
    }

    public CheckBox(WebElement webElement) {
        super(webElement);
    }

    public void click() {
        Utilities.logInfoMessage("Select " + getName() + " radio button");
        element().click();
    }

    public void clickWithAjaxWait() {
        Utilities.logInfoMessage("Click " + getName() + " button");
        element().click();
        waitFor().waitForThrobberNotPresent(10);
    }

    public void check() {
        if (!element().isSelected()) {
            element().click();
        }
    }

    public void uncheck() {
        if (element().isSelected()) {
            element().click();
        }
    }

    public void selectStatus(Boolean status) {
        if (status == null) {
            return;
        }
        if (element().isSelected() != status) {
            clickWithJS();
        }
    }

    public void clickWithJS() {
        Utilities.logInfoMessage("Click with JS " + getName() + " button");
        WebDriverUtil.getInstance(driver).click(element());
    }

    public boolean isSelected() {
        return element().isSelected();
    }

    public Label getLabel() {
        return new Label(driver, element().findElement(By.xpath(LABEL)));
    }

    public String getText() {
        return this.getLabel().getText();
    }

    @Override
    public String toString() {
        return "CheckBox{" +
                byLocator + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
