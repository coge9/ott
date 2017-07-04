package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ivan_Karnilau on 20-Apr-16.
 */
public class BaseGeneralInfoBlock extends BaseTabBlock {

    private static final String ENABLE_TEXT_LONG_DESCRIPTION_XPATH = ".//*[contains(@id, 'wysiwyg-toggle-edit-field-long-description-')]";
    private static final String ENABLE_TEXT_MEDIUM_DESCRIPTION_XPATH = ".//*[contains(@id, 'wysiwyg-toggle-edit-field-long-description-')]";
    private static final String LINK_TEXT = "Disable rich-text";
    @FindBy(id = "edit-title")
    private TextField title;
    @FindBy(xpath = ".//textarea[contains(@id,'edit-field-description')]")
    private TextField shortDescription;
    @FindBy(xpath = ".//textarea[contains(@id,'edit-field-medium-description')]")
    private TextField mediumDescription;
    @FindBy(xpath = ".//textarea[contains(@id,'edit-field-long-description')]")
    private TextField longDescription;
    @FindBy(xpath = ENABLE_TEXT_LONG_DESCRIPTION_XPATH)
    private Link enableRichTextLongDescription;

    @FindBy(xpath = ENABLE_TEXT_MEDIUM_DESCRIPTION_XPATH)
    private Link enableRichTextMediumDescription;

    public BaseGeneralInfoBlock enterTitle(String title) {
        this.title.enterText(title);
        return this;
    }

    public String getTitle() {
        return this.title.getValue();
    }

    public BaseGeneralInfoBlock enterShortDescription(String shortDescription) {
        this.shortDescription.enterText(shortDescription);
        return this;
    }

    public String getShortDescription() {
        return this.shortDescription.getValue();
    }

    public BaseGeneralInfoBlock enterMediumDescription(String mediumDescription) {
        this.mediumDescription.enterText(mediumDescription);
        return this;
    }

    public String getMediumDescription() {
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if (WebDriverUtil.getInstance(webDriver).isElementPresent(ENABLE_TEXT_MEDIUM_DESCRIPTION_XPATH) && enableRichTextMediumDescription.getText().equals(LINK_TEXT)) {
            WebDriverUtil.getInstance(webDriver).scrollPageUp();
            enableRichTextMediumDescription.click();
        }
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return this.mediumDescription.getValue();
    }

    public BaseGeneralInfoBlock enterLongDescription(String longDescription) {
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if (WebDriverUtil.getInstance(webDriver).isElementPresent(ENABLE_TEXT_LONG_DESCRIPTION_XPATH) && enableRichTextLongDescription.getText().equals(LINK_TEXT)) {
            enableRichTextLongDescription.click();
        }
        this.longDescription.enterText(longDescription);
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return this;
    }

    public String getLongDescription() {
        return this.longDescription.getValue();
    }
}
