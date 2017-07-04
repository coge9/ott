package com.nbcuni.test.cms.backend.chiller.block.assetlibrary;

import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.Image;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 18-Apr-16.
 */
public class FileBlock extends AbstractContainer {

    @FindBy(tagName = "label")
    private Label label;

    @FindBy(tagName = "input")
    private CheckBox checkbox;

    @FindBy(tagName = "img")
    private Image image;

    @FindBy(xpath = ".//div[contains(@class, 'media-item')]")
    private WebElement divForSelect;

    @FindBy(tagName = "a")
    private Link link;

    public void check() {
        this.checkbox.check();
    }

    public void uncheck() {
        this.checkbox.uncheck();
    }

    public void divSelect() {
        this.divForSelect.click();
    }

    public boolean isSelected() {
        return checkbox.isSelected();
    }

    @Override
    public String getName() {
        return this.label.getText();
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public void click() {
        link.clickJS();
    }
}
