package com.nbcuni.test.cms.backend.tvecms.pages;

import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ContentFilesPage extends Page {

    private static final String CONTENT_TABLE_XPATH = ".//*[@id='views-form-content-files-page']//table";

    private static final String TABLE_XPATH = ".//*[@id='views-form-content-files-page']//table[contains(@class, 'views-table')]";
    private static final String ROWS_XPATH = "//tbody/tr";
    private static final String INPUT_NAME_XPATH = "//input[@id='edit-filename']";
    private static final String APPLY_BUTTON_XPATH = "//input[@id='edit-submit-content-files' or @id='edit-submit-broken-file-entities']";
    private static final String CHECKBOXS_XPATH = "//input[contains(@id,'edit-views-bulk-operations')]";
    private static final String SAVE_BUTTON = ".//*[@id='edit-submit']";

    private static final String EDIT_BUTTON_XPATH = "//div[@class='ctools-content']//a[text()='Edit']";
    private static final String DELETE_BUTTON_XPATH = "//div[@class='ctools-content']//a[text()='Delete']";
    private static final String OPTIONS_LINK_XPATH = "//a[@class='ctools-twisty ctools-text']";
    private static final String CONFIRMATION_DELETE_CONTENT_TYPE_XPATH = "//input[@id='edit-submit']";

    private static final String PUNLISHING_TAB = "//li/a/strong[text()='Publishing options']/..";
    private static final String PUBLISHING_CHECKBOS = ".//*[@id='edit-published']";


    public ContentFilesPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }


    public ContentFilesPage searchByTitle(final String title) {
        webDriver.type(INPUT_NAME_XPATH, title);
        return this;
    }

    public void clickDeleteButton() {
        webDriver.click(OPTIONS_LINK_XPATH);
        final WebElement deleteBtn = webDriver.findElement(By.xpath(DELETE_BUTTON_XPATH));
        WebDriverUtil.getInstance(webDriver).click(deleteBtn);
        webDriver.click(CONFIRMATION_DELETE_CONTENT_TYPE_XPATH);
    }

    public void clickSave() {
        webDriver.click(SAVE_BUTTON);
    }

    public boolean apply() {
        webDriver.click(APPLY_BUTTON_XPATH);
        return isContentPresent();
    }

    public boolean isContentPresent() {
        if (WebDriverUtil.getInstance(webDriver).isElementPresent(CONTENT_TABLE_XPATH))
            return true;
        return false;
    }

    public void openFirstElementsEditPage() {
        webDriver.click(OPTIONS_LINK_XPATH);
        WaitUtils.perform(webDriver).waitElementVisible(EDIT_BUTTON_XPATH);
        webDriver.click(EDIT_BUTTON_XPATH);
    }

    @Override
    public List<String> verifyPage() {

        return null;
    }


    public void uncheckPublishState() {
        WaitUtils.perform(webDriver).waitElementVisible(PUNLISHING_TAB);
        webDriver.click(PUNLISHING_TAB);
        if (webDriver.findElementByXPath(PUBLISHING_CHECKBOS).isSelected()) {
            webDriver.click(PUBLISHING_CHECKBOS);
        }
    }

}
