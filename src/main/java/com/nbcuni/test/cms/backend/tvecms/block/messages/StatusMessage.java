package com.nbcuni.test.cms.backend.tvecms.block.messages;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class StatusMessage {
    public static final String XPATH = "//div[@class='messages status']";
    private final CustomWebDriver webDriver;
    @FindBy(how = How.XPATH, using = XPATH)
    private WebElement statusMessage;
    @FindBy(how = How.XPATH, using = "(//div[@class='messages status']//ul/li)")
    private List<WebElement> statusMessageItems;

    public StatusMessage(final CustomWebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public boolean isShown() {
        if (WebDriverUtil.getInstance(webDriver).isElementNotPresent(XPATH))
            return false;
        return statusMessage.isDisplayed();
    }

    public void waitForStatusMessageShown(final int timeOutSec) {
        WaitUtils.perform(webDriver).waitElementPresent(XPATH, timeOutSec);
    }

    public String getMessage() {
        if (isShown())
            return statusMessage.getText();
        return "";
    }

    public Integer getStatusMessagesNumber() {
        return statusMessageItems.size();
    }

    public String getStatusMessage(final int index) {
        if (statusMessageItems.size() >= index)
            return statusMessageItems.get(index).getText();
        throw new TestRuntimeException("Invalid index was passed: " + index
                + ". There possible valuea are [0, "
                + (statusMessageItems.size() - 1) + "]");
    }

    public List<String> getAllStatusMessages() {
        final List<String> listOfMessages = new ArrayList<String>(
                statusMessageItems.size());
        for (final WebElement item : statusMessageItems) {
            listOfMessages.add(item.getText());
        }
        return listOfMessages;
    }
}