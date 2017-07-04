package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Ivan_Karnilau on 02-Oct-15.
 */
public class RadioButton extends AbstractElement {

    private static final String LABEL = "./following-sibling::label";

    public RadioButton(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public RadioButton(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public RadioButton(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public RadioButton(WebElement webElement) {
        super(webElement);
    }

    public void click() {
        Utilities.logInfoMessage("Select " + getName() + " radio button");
        if (this.getLabel().element().getAttribute(HtmlAttributes.FOR.get())
                .equals(this.element().getAttribute(HtmlAttributes.ID.get())) &&
                !this.getLabel().isLink()) {
            this.getLabel().click();
        } else {
            this.element().click();
        }
    }

    public boolean isSelected() {
        return this.element().isSelected();
    }

    private Label getLabel() {
        return new Label(driver, element().findElement(By.xpath(LABEL)));
    }

    public String getText() {
        return this.getLabel().getText();
    }
}
