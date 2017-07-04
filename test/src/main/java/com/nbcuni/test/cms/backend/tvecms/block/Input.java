package com.nbcuni.test.cms.backend.tvecms.block;

import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Alena_Aukhukova on 9/15/2015.
 */
public class Input {

    private final String AUTOCOMPLETE_XPATH = "//div[@id='autocomplete']";
    private final String AUTOCOMPLETE_LIST_XPATH = "//div[text()='%s']";
    private final String AUTOCOMPLETE_LIST_CONTAINS_XPATH = "//div[contains(text(),'%s')]";
    private WebElement input;
    private CustomWebDriver webDriver;


    public Input(CustomWebDriver webDriver, WebElement input) {
        this.input = input;
        this.webDriver = webDriver;
    }

    public Input(CustomWebDriver webDriver, By by) {
        input = webDriver.findElement(by);
        this.webDriver = webDriver;
    }

    public void clearAndType(String text) {
        input.clear();
        input.sendKeys(text);
    }

    public void clear() {
        input.clear();
    }

    public String getText() {
        return input.getText();
    }

    public void fillAutocomplete(String text) {
        clearAndType(text);
        WaitUtils.perform(webDriver).waitElementPresent(AUTOCOMPLETE_XPATH, 10);
        webDriver.findElementByXPath(String.format(AUTOCOMPLETE_LIST_XPATH, text)).click();
        WaitUtils.perform(webDriver).waitElementInvisible(AUTOCOMPLETE_LIST_XPATH, 10);
        WaitUtils.perform(webDriver).waitElementInvisible(AUTOCOMPLETE_LIST_CONTAINS_XPATH, 3);
    }

    public void fillAutocompletePartName(String text) {
        clearAndType(text);
        WaitUtils.perform(webDriver).waitElementPresent(AUTOCOMPLETE_XPATH, 10);
        webDriver.findElementByXPath(String.format(AUTOCOMPLETE_LIST_CONTAINS_XPATH, text)).click();
        WaitUtils.perform(webDriver).waitElementInvisible(AUTOCOMPLETE_LIST_CONTAINS_XPATH, 3);
    }

    public boolean isDisplayed() {
        return input.isDisplayed();
    }

    public boolean isEnabled() {
        return input.isEnabled();
    }
}
