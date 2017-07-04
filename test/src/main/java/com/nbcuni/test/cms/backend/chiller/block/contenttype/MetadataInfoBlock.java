package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.MetadataInfo;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Aleksandra_Lishaeva on 4/15/16.
 */
public class MetadataInfoBlock extends BaseTabBlock {

    private static final String ENABLE_TEXT_XPATH = ".//*[contains(@id, 'wysiwyg-toggle-edit-field-bio-')]";
    private static final String LINK_TEXT = "Disable rich-text";
    @FindBy(className = "page-title")
    private Label pageTitle;
    @FindBy(id = "edit-field-prefix-und-0-value")
    private TextField prefix;
    @FindBy(id = "edit-field-first-name-und-0-value")
    private TextField firstName;
    @FindBy(id = "edit-field-middle-name-und-0-value")
    private TextField middleName;
    @FindBy(id = "edit-field-last-name-und-0-value")
    private TextField lastName;
    @FindBy(id = "edit-field-suffix-und-0-value")
    private TextField suffix;
    @FindBy(id = "edit-field-bio-und-0-value")
    private TextField bio;
    @FindBy(xpath = ENABLE_TEXT_XPATH)
    private Link enableRichText;

    public String getPrefix() {
        return prefix.getValue();
    }

    public void setPrefix(String value) {
        prefix.enterText(value);
    }

    public String getFirstName() {
        return firstName.getValue();
    }

    public void setFirstName(String value) {
        firstName.enterText(value);
    }

    public String getMiddleName() {
        return middleName.getValue();
    }

    public void setMiddleName(String value) {
        middleName.enterText(value);
    }

    public String getLastName() {
        return lastName.getValue();
    }

    public void setLastName(String value) {
        lastName.enterText(value);
    }

    public String getSuffix() {
        return suffix.getValue();
    }

    public void setSuffix(String value) {
        suffix.enterText(value);
    }

    public String getBio() {
        return bio.getValue();
    }

    public void setBio(String value) {
        if (WebDriverUtil.getInstance(webDriver).isElementPresent(ENABLE_TEXT_XPATH) && enableRichText.getText().equals(LINK_TEXT)) {
            enableRichText.click();
        }
        bio.enterText(value);
    }

    public void enterMetadataInfo(MetadataInfo metadataInfo) {
        Utilities.logInfoMessage("Fill metadata fields by values: " + metadataInfo);
        setPrefix(metadataInfo.getPrefix());
        setFirstName(metadataInfo.getFirstName());
        setMiddleName(metadataInfo.getMiddleName());
        setLastName(metadataInfo.getLastName());
        setSuffix(metadataInfo.getSuffix());
        setBio(metadataInfo.getBio());
    }

    public MetadataInfo getMetadataInfo() {
        Utilities.logInfoMessage("Fill metadata fields by values: ");
        MetadataInfo actualBlockInfo = new MetadataInfo();
        actualBlockInfo
                .setPrefix(getPrefix())
                .setFirstName(getFirstName())
                .setMiddleName(getMiddleName())
                .setLastName(getLastName())
                .setSuffix(getSuffix())
                .setBio(getBio());

        return actualBlockInfo;
    }
}
