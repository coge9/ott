package com.nbcuni.test.cms.backend.tvecms.block.content.ios.promo;

import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.elements.TextFieldWithSimpleAutocomplete;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.promo.LinkUsage;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 8/26/2016.
 */
public class LinkBlock extends AbstractContainer {

    @FindBy(xpath = ".//input[contains(@id,'field-promo-links-display-text')]")
    private TextField displayText;

    @FindBy(xpath = ".//div[contains(@id,'field-promo-url')]//input[contains(@id,'field-promo-url-content')]")
    private TextField url;

    @FindBy(xpath = ".//input[contains(@id,'field-promo-url')]")
    private TextFieldWithSimpleAutocomplete contentItem;

    @FindBy(xpath = ".//select[contains(@id,'field-promo-usage')]")
    private DropDownList usage;

    @FindBy(xpath = ".//input[contains(@id,'remove-button')]")
    private Button remove;

    public void enterDisplayText(String displayText) {
        this.displayText.enterText(displayText);
    }

    public void enterUrl(String url) {
        this.url.enterText(url);
    }

    public void selectContentItem(String contentItem) {
        this.contentItem.clickValueInAutocompleteTable(contentItem);
    }

    public void selectUsage(LinkUsage usage) {
        this.usage.selectFromDropDown(usage.getUsage());
    }

    public void removeLink() {
        this.remove.click();
    }

    public String getDisplayText() {
        return displayText.getValue();
    }

    public String getUrl() {
        return url.getValue();
    }

    public String getContentItem() {
        return contentItem.getValue();
    }

    public LinkUsage getUsage() {
        return LinkUsage.getUsageByValue(usage.getSelectedValue());
    }

    public Button getRemove() {
        return remove;
    }
}
