package com.nbcuni.test.cms.backend.chiller.block.contenttype.post;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.media.MediaBlock;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.TabsGroup;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BlurbMediaFrame extends Page {

    private static final String submitButtonXpath = "//*[text()='Submit']";
    @FindBy(xpath = ".//div[@id='media-tabs-wrapper']")
    private TabsGroup tabs;
    @FindBy(xpath = ".//*[@id='edit-embed-code']")
    private TextField youTubeURL;
    @FindBy(xpath = ".//div[contains(@style,'display: block')]//ul[contains(@id, 'media-browser')]//li")
    private List<WebElement> mediaItems;
    @FindBy(xpath = ".//div[contains(@style,'display: block')]//*[@id='edit-submit' or @id='edit-next' or text()='Submit']")
    private Button selectMedia;
    @FindBy(xpath = "//iframe[@id='mediaStyleSelector']")
    private WebElement styleSelectorFrame;

    public BlurbMediaFrame(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }


    public void addMediaItem(BlurbMedia blurbMedia) {
        tabs.openTabByName(blurbMedia.getTabName());
        if (blurbMedia.getValue().isEmpty()) {
            mediaItems.get(0).click();
        } else {
            youTubeURL.enterText(blurbMedia.getValue());
        }
        selectMedia.clickWithAjaxWait();
        webDriver.switchTo().defaultContent();
    }

    public void uploadMediaItem() {
        new MediaBlock().uploadImages(1);
    }

    @Override
    public List<String> verifyPage() {
        // TODO Auto-generated method stub
        return null;
    }

}
