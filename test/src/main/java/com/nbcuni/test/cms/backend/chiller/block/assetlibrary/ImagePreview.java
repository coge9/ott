package com.nbcuni.test.cms.backend.chiller.block.assetlibrary;

import com.nbcuni.test.cms.elements.Image;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Aleksandra_Lishaeva on 4/25/16.
 */
public class ImagePreview extends AbstractContainer {

    @FindBy(tagName = "img")
    private Image image;

    @FindBy(xpath = ".//preceding-sibling::h2[1]")
    private Label imageName;

    public ImagePreview() {
    }

    public ImagePreview(WebElement element) {
        super(element);
        init(element);
    }

    @Override
    public boolean isPresent() {
        return image.isPresent();
    }

    public String getImageSrc() {
        return image.getSource();
    }

    public String getImageName() {
        return imageName.getText();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getWidth() {
        return image.getWidth();
    }
}
