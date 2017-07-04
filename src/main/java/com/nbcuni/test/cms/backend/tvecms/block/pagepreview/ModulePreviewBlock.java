package com.nbcuni.test.cms.backend.tvecms.block.pagepreview;

import com.nbcuni.test.cms.elements.Image;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 26.01.2016.
 */
public class ModulePreviewBlock extends AbstractContainer {

    @FindBy(xpath = ".//div[contains(@class,'field-name-field-program')]")
    private Image programImageBlock;

    @FindBy(xpath = ".//div[contains(@class,'field-name-field-video')]")
    private Image videoImageBlock;

    @FindBy(xpath = ".//h4")
    private Label title;

    @FindBy(xpath = ".//img')]")
    private Image image;

    public String getLabel() {
        return WebDriverUtil.getInstance(webDriver).getTextFirstNode(title.element());
    }

    public List<String> getImagesSrc() {
        List<String> imagesSrc = new ArrayList<>();
        List<Image> images = image.customElements();
        for (Image image : images) {
            imagesSrc.add(image.getSource());
        }
        return imagesSrc;
    }

    public List<String> getImagesNames() {
        List<String> imagesNames = new ArrayList<>();
        List<String> imagesSrc = this.getImagesSrc();
        for (String imageSrc : imagesSrc) {
            imagesNames.add(imageSrc.split("/")[imageSrc.split("/").length - 1]);
        }
        return imagesNames;
    }

    /**
     * Method to get program image source within module block
     * @return program image source
     */
    public String getProgramImageSource() {
        return programImageBlock.getSource();
    }

    /**
     * ethod to get video image source within module block
     * @return video image source
     */
    public String getVideoImageSource() {
        return videoImageBlock.getSource();
    }
}
