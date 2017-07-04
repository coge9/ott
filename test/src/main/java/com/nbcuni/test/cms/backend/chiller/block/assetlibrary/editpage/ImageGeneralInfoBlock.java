package com.nbcuni.test.cms.backend.chiller.block.assetlibrary.editpage;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.ImageGeneralInfo;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 07-Jun-16.
 */
public class ImageGeneralInfoBlock extends BaseTabBlock {

    @FindBy(xpath = ".//div[@id='edit-focal-point']//a")
    private Link imagePreview;

    @FindBy(id = "edit-filename")
    private TextField title;

    @FindBy(xpath = ".//input[contains(@id,'alt-text')]")
    private TextField altText;

    @FindBy(xpath = ".//input[contains(@id,'source')]")
    private TextField source;

    @FindBy(xpath = ".//input[contains(@id,'credit')]")
    private TextField credit;

    @FindBy(xpath = ".//input[contains(@id,'copyright')]")
    private TextField copyright;

    @FindBy(id = "edit-field-image-high-resolution-und")
    private CheckBox highResolution;

    @FindBy(xpath = ".//textarea[contains(@id,'description')]")
    private TextField description;

    @FindBy(xpath = ".//textarea[contains(@id,'caption')]")
    private TextField caption;

    private void enterTitle(String title) {
        this.title.enterText(title);
    }

    private void enterAltText(String altText) {
        this.altText.enterText(altText);
    }

    private void enterSource(String source) {
        this.source.enterText(source);
    }

    private void enterCredit(String credit) {
        this.credit.enterText(credit);
    }

    private void enterCopyright(String copyright) {
        this.copyright.enterText(copyright);
    }

    private void enterDescription(String description) {
        this.description.enterText(description);
    }

    private void enterCaption(String caption) {
        this.caption.enterText(caption);
    }

    private void selectHighResolution(Boolean highResolution) {
        this.highResolution.selectStatus(highResolution);
    }

    public String getTitle() {
        return title.getValue();
    }

    public void clickImagePreview() {
        imagePreview.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public void enterImageGeneralInfo(ImageGeneralInfo imageGeneralInfo) {
        this.enterTitle(imageGeneralInfo.getTitle());
        this.enterAltText(imageGeneralInfo.getAltText());
        this.enterSource(imageGeneralInfo.getSource());
        this.enterCredit(imageGeneralInfo.getCredit());
        this.enterCopyright(imageGeneralInfo.getCopyright());
        this.enterDescription(imageGeneralInfo.getDescription());
        this.enterCaption(imageGeneralInfo.getCaption());
        this.selectHighResolution(imageGeneralInfo.getHighResolution());
    }

    public ImageGeneralInfo getImageGeneralInfo() {
        ImageGeneralInfo imageGeneralInfo = new ImageGeneralInfo();
        imageGeneralInfo
                .setTitle(getTitle())
                .setAltText(altText.getValue())
                .setSource(source.getValue())
                .setCredit(credit.getValue())
                .setCopyright(copyright.getValue())
                .setDescription(description.getValue())
                .setCaption(caption.getValue())
                .setHighResolution(highResolution.isSelected());
        return imageGeneralInfo;
    }
}
