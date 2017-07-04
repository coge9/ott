package com.nbcuni.test.cms.backend.tvecms.block.messages;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class WarningMessage {
    public static final String XPATH = "//div[contains(@class,'messages warning')]";
    private final CustomWebDriver webDriver;
    @FindBy(how = How.XPATH, using = XPATH)
    private WebElement warningMessage;
    @FindBy(how = How.XPATH, using = "(" + XPATH + "//ul/li)")
    private List<WebElement> warningMessageItems;

    public WarningMessage(final CustomWebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }


    public boolean isPresent() {
        if (!WebDriverUtil.getInstance(webDriver).isElementPresent(XPATH))
            return false;
        return warningMessage.isDisplayed();
    }

    public String getMessage() {
        if (isPresent())
            return warningMessage.getText();
        return "";
    }

    public String getMessage(int index) {
        if (isPresent()) {
            if (warningMessageItems.size() >= index)
                return warningMessageItems.get(index).getText();
            throw new TestRuntimeException("Invalid index was passed: " + index
                    + ". There possible valuea are [0, "
                    + (warningMessageItems.size() - 1) + "]");
        }
        return "";
    }

    public Integer getWarningMessagesNumber() {
        return warningMessageItems.size();
    }

    public String getStatusMessage(final int index) {
        if (warningMessageItems.size() >= index)
            return warningMessageItems.get(index).getText();
        throw new TestRuntimeException("Invalid index was passed: " + index
                + ". There possible valuea are [0, "
                + (warningMessageItems.size() - 1) + "]");
    }

    public List<String> getAllWarningMessages() {
        final List<String> listOfMessages = new ArrayList<String>(
                warningMessageItems.size());
        for (final WebElement item : warningMessageItems) {
            listOfMessages.add(item.getText());
        }
        return listOfMessages;
    }
}