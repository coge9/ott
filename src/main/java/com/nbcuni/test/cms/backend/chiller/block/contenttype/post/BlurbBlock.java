package com.nbcuni.test.cms.backend.chiller.block.contenttype.post;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.Blurb;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.pageobjectutils.chiller.TextFormatForTextArea;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan on 21.04.2016.
 */
public class BlurbBlock extends AbstractContainer {

    private static final String ADDING_FORMAT = "Rich text";
    @FindBy(xpath = ".//input[contains(@id,'field-blurb-title')]")
    private TextField title;
    @FindBy(xpath = ".//textarea[contains(@id,'field-blurb-text')]")
    private TextField text;
    @FindBy(xpath = ".//div[contains(@id, 'cke_edit-field-blurb-und-')]//*[@title='Add media']")
    private Button addMediaButton;
    @FindBy(xpath = ".//select[contains(@id,'field-blurb-text-und-')]")
    private DropDownList textFormat;
    @FindBy(xpath = "//iframe[@id='mediaBrowser']")
    private WebElement iframe;

    @FindBy(xpath = ".//div[contains(@style,'display: block')]//*[@id='edit-submit' or @id='edit-next' or text()='Submit']")
    private Button selectMedia;

    @FindBy(xpath = "//*[ text()='Submit']")
    private Button submit;

    @FindBy(xpath = ".//input[contains(@id,'remove-button')]")
    private Button delete;

    public void enterBlurbData(Blurb blurb) {
        this.enterTitle(blurb.getTitle());
        this.enterBody(blurb.getBlurbMedia(), blurb.getText());
    }

    public Blurb getBlurbData() {
        Blurb blurb = new Blurb();
        blurb.setTitle(this.getTitle());
        blurb.setText(this.getText());
        return blurb;
    }

    private void enterTitle(String title) {
        if (title == null) {
            return;
        }
        this.title.enterText(title);
    }

    private void enterBody(BlurbMedia blurbMedia, String textValue) {
        if (textValue != null && textValue != "") {
            textFormat.selectFromDropDown(TextFormatForTextArea.PLAIN_TEXT.get());
            text.enterText(textValue);
        }
        if (blurbMedia == null) {
            return;
        }
        textFormat.selectFromDropDown(ADDING_FORMAT);
        addMediaButton.click();
        webDriver.switchTo().defaultContent();
        webDriver.switchTo().frame(iframe);
        BlurbMediaFrame bmf = new BlurbMediaFrame(webDriver, null);
        bmf.addMediaItem(blurbMedia);
        webDriver.switchTo().defaultContent();
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getText() {
        return text.getValue();
    }


    public void delete() {
        this.delete.click();
    }
}
