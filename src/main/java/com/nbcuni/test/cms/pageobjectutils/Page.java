package com.nbcuni.test.cms.pageobjectutils;

import com.nbcuni.test.cms.backend.tvecms.block.messages.ErrorMessage;
import com.nbcuni.test.cms.backend.tvecms.block.messages.StatusMessage;
import com.nbcuni.test.cms.backend.tvecms.block.messages.WarningMessage;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.fielddecorator.ExtendedFieldDecorator;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

public abstract class Page {

    protected CustomWebDriver webDriver;
    protected AppLib aid;
    protected StatusMessage messageStatus;
    protected ErrorMessage errorMessage;
    protected WarningMessage warningMessage;
    protected Random random = new Random();

    // designed for admin pages to get their title on page.
    @FindBy(xpath = ".//h1[@class='page-title']")
    protected Label adminPageTitle;

    public Page(final CustomWebDriver webDriver, final AppLib aid) {
        this.webDriver = webDriver;
        this.aid = aid;
        messageStatus = new StatusMessage(webDriver);
        errorMessage = new ErrorMessage(webDriver);
        warningMessage = new WarningMessage(webDriver);
        init(webDriver);
    }

    // return titles for Admin pages.
    public String getAdminPageTitle() {
        return adminPageTitle.getText().trim();
    }

    public String getPageUrl() {
        return webDriver.getCurrentUrl();
    }

    public boolean isStatusMessageShown() {
        return messageStatus.isShown();
    }

    public void waitForStatusMessage(final int timeoutSec) {
        messageStatus.waitForStatusMessageShown(timeoutSec);
    }

    public String getStatusMessage() {
        return messageStatus.getMessage();
    }

    public String getStatusMessage(final int index) {
        return messageStatus.getStatusMessage(index);
    }

    public List<String> getAllStatusMessages() {
        return messageStatus.getAllStatusMessages();
    }

    public boolean isErrorMessagePresent() {
        if (errorMessage.isPresent()) {
            return !(errorMessage.getMessage().contains(MessageConstants.ERROR_PAGE_LOCKING) || errorMessage.getMessage().contains(MessageConstants.ERROR_NOTIOS_PAGE_PUBLISHING));
        } else {
            return false;
        }
    }

    public String getErrorMessage() {
        return errorMessage.getMessage();
    }

    public String getErrorMessage(final int index) {
        return errorMessage.getMessage(index);
    }

    public boolean isWarningMessagePresent() {
        return warningMessage.isPresent();
    }

    public String getWarningMessage() {
        return warningMessage.getMessage();
    }

    public List<String> getAllWarningMessages() {
        return warningMessage.getAllWarningMessages();
    }

    public void pause(int seconds) {
        pauseInMilliseconds(seconds * 1000);
    }

    public void pauseInMilliseconds(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getExpectedPageTitle(String pageTitle) {
        return String.format(MessageConstants.TITLE_EDIT_PAGE, pageTitle);
    }

    public abstract List<String> verifyPage();

    public void init(final WebDriver driver) {
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public boolean isPageOpened() {
        return true;
    }
}