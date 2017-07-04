package com.nbcuni.test.cms.backend.chiller.block.contenttype.externallinks;

import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;


/**
 * Created by Aleksandra_Lishaeva on 5/4/16.
 */
public class LinkPairBlock extends AbstractContainer {

    @FindBy(xpath = ".//*[contains(@id,'-title')]")
    private TextField linkTitle;

    @FindBy(xpath = ".//input[contains(@id,'-url')]")
    private TextField linkUrl;

    @FindBy(xpath = ".//input[contains(@id,'-remove-button')]")
    private Button remove;

    public String getLinkUrl() {
        try {
            return linkUrl.getValue();
        } catch (Exception e) {
            Utilities.logInfoMessage("The link url value is empty");
        }
        return null;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl.enterText(linkUrl);
    }

    public String getLinkTitle() {
        try {
            return linkTitle.getValue();
        } catch (Exception e) {
            Utilities.logInfoMessage("The link title value is empty");
        }
        return null;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle.enterText(linkTitle);
    }

    public void setPairOfLink(String linkTitle, String linkUrl) {
        setLinkTitle(linkTitle);
        setLinkUrl(linkUrl);
    }

    public void remove() {
        remove.clickWithAjaxWait();
    }
}
