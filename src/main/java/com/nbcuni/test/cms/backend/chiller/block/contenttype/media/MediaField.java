package com.nbcuni.test.cms.backend.chiller.block.contenttype.media;

import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.Image;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public class MediaField extends AbstractContainer {

    @FindBy(xpath = ".//img")
    private Image imagesItem;

    @FindBy(xpath = ".//label[@class='media-filename']")
    private WebElement fileName;

    @FindBy(xpath = ".//select[contains(@class,'media-usage')]")
    private DropDownList usageSelect;

    @FindBy(xpath = ".//*[@value='Remove']")
    private Button removeButton;

    @FindBy(xpath = ".//*[text()='Edit']")
    private Button editButton;


    public void edit() {
        editButton.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public void delete() {
        removeButton.clickWithAjaxWait();
    }

    public String getUrl() {
        return imagesItem.getSource();
    }

    @Override
    public String getName() {
        return fileName.getText();
    }

    public String getUsage() {
        return usageSelect.getSelectedValue();
    }

    public void selectUsage(String name) {
        usageSelect.selectFromDropDown(name);
    }
}
