package com.nbcuni.test.cms.backend.chiller.block.contenttype.post;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.BaseGeneralInfoBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.PostInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.WysiwigDescription;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.pageobjectutils.chiller.TextFormatForTextArea;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 20-Apr-16.
 */
public class PostGeneralInfoBlock extends BaseGeneralInfoBlock {

    @FindBy(xpath = ".//input[contains(@id,'edit-field-byline')]")
    private TextField byline;

    @FindBy(xpath = "..//input[contains(@id, 'edit-field-post-date-line')]")
    private TextField dateLine;

    @FindBy(xpath = ".//select[contains(@id,'edit-field-medium-description')]")
    private DropDownList textFormatMediumDescription;

    @FindBy(xpath = ".//div[contains(@id, 'cke_edit-field-long-description-')]//*[@title='Add media']")
    private Button addMediaLongDescButton;

    @FindBy(xpath = ".//div[contains(@id, 'cke_edit-field-medium-description-')]//*[@title='Add media']")
    private Button addMediaMediumDescButton;

    @FindBy(xpath = "//iframe[@id='mediaBrowser']")
    private WebElement iframe;

    @FindBy(xpath = ".//select[contains(@id,'edit-field-long-description')]")
    private DropDownList textFormatLongDescription;


    public void enterByline(String byline) {
        if (byline == null) {
            return;
        }
        this.byline.enterText(byline);
    }

    public void enterDateLine(String dateLine) {
        if (dateLine == null) {
            return;
        }
        this.dateLine.enterText(dateLine);
    }

    public String getByline() {
        return this.byline.getValue();
    }

    public void enterGeneralInfoData(GeneralInfo generalInfo) {
        this.enterTitle(generalInfo.getTitle());
        this.enterByline(generalInfo.getByline());
        this.enterDateLine(generalInfo.getDateLine());
        this.enterShortDescription(generalInfo.getShortDescription());
    }

    public void enterLongMediumDescriptionData(PostInfo descriptionsInfo) {
        this.enterMediumDescription(descriptionsInfo.getMediumDescription());
        this.enterLongDescription(descriptionsInfo.getLongDescription());
    }

    public GeneralInfo getPostGeneralInfoData() {
        GeneralInfo generalInfo = new GeneralInfo();
        generalInfo.setTitle(this.getTitle());
        generalInfo.setByline(this.getByline());
        generalInfo.setShortDescription(this.getShortDescription());
        generalInfo.setMediumDescription(this.getMediumDescription());
        generalInfo.setLongDescription(this.getLongDescription());
        return generalInfo;
    }

    private void selectTextFormatMediumDescription() {
        if (textFormatMediumDescription.isVisible()) {
            this.textFormatMediumDescription.selectFromDropDown(TextFormatForTextArea.PLAIN_TEXT.get());
        }
    }

    private void selectTextFormatLongDescription() {
        if (textFormatLongDescription.isVisible()) {
            this.textFormatLongDescription.selectFromDropDown(TextFormatForTextArea.PLAIN_TEXT.get());
        }
    }

    private void selectRichTextFormatLongDescription() {
        if (textFormatLongDescription.isVisible()) {
            this.textFormatLongDescription.selectFromDropDown(TextFormatForTextArea.RICH_TEXT.get());
        }
    }

    private void selectRichTextFormatMediumDescription() {
        if (textFormatMediumDescription.isVisible()) {
            this.textFormatMediumDescription.selectFromDropDown(TextFormatForTextArea.RICH_TEXT.get());
        }
    }

    private void enterLongDescription(WysiwigDescription description) {
        if (description.getBlurb().getText() != null && description.getBlurb().getText() != "") {
            selectTextFormatLongDescription();
            this.enterLongDescription(description.getBlurb().getText());
        }
        if (description.getBlurb().getBlurbMedia() == null) {
            return;
        }
        selectRichTextFormatLongDescription();
        addMediaLongDescButton.click();
        webDriver.switchTo().defaultContent();
        webDriver.switchTo().frame(iframe);
        BlurbMediaFrame bmf = new BlurbMediaFrame(webDriver, null);
        if (description.getBlurb().getBlurbMedia().equals(BlurbMedia.IMAGE)) {
            bmf.addMediaItem(description.getBlurb().getBlurbMedia());
        } else if (description.getBlurb().getBlurbMedia().equals(BlurbMedia.UPLOAD)) {
            bmf.uploadMediaItem();
        }
        webDriver.switchTo().defaultContent();
    }

    private void enterMediumDescription(WysiwigDescription description) {
        if (description.getBlurb().getText() != null && description.getBlurb().getText() != "") {
            selectTextFormatMediumDescription();
            this.enterMediumDescription(description.getBlurb().getText());
        }
        if (description.getBlurb().getBlurbMedia() == null) {
            return;
        }
        selectRichTextFormatMediumDescription();
        addMediaMediumDescButton.click();
        webDriver.switchTo().defaultContent();
        webDriver.switchTo().frame(iframe);
        BlurbMediaFrame bmf = new BlurbMediaFrame(webDriver, null);
        if (description.getBlurb().getBlurbMedia().equals(BlurbMedia.IMAGE)) {
            bmf.addMediaItem(description.getBlurb().getBlurbMedia());
        } else if (description.getBlurb().getBlurbMedia().equals(BlurbMedia.UPLOAD)) {
            bmf.uploadMediaItem();
        }
        webDriver.switchTo().defaultContent();
    }
}


