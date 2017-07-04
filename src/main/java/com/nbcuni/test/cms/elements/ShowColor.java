package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by aleksandra_lishaeva on 6/16/17.
 */
public class ShowColor extends AbstractContainer {

    @FindBy(xpath = ".//div[@class='sp-dd']")
    private Button colorArrow;

    @FindBy(xpath = "//input[@class='sp-input']")
    private TextField colorInput;

    @FindBy(xpath = ".//input[contains(@class,'sp-thumb-active')]")
    private WebElement colorValue;


    /**
     * Method for getting set color value
     * @return string value of the selected color (e.g #9d2424)
     */
    public String getColor() {
        colorArrow.click();
        return colorValue.getAttribute(HtmlAttributes.TITLE.get());
    }

    /**
     * Method is used for setting show color to the node
     * @param color of the Series/Video
     */
    public void setColor(String color) {
        if (color == null) {
            return;
        }
        colorArrow.click();
        colorInput.enterText(color);
        colorArrow.click();
    }
}
