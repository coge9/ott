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

public class ErrorMessage {
    public static final String XPATH = "//div[contains(@class,'messages error')]";
    private final CustomWebDriver webDriver;
    @FindBy(how = How.XPATH, using = XPATH)
    private WebElement errorMessage;
    @FindBy(how = How.XPATH, using = "(//div[contains(@class,'error')]//ul/li)")
    private List<WebElement> errorMessageItems;

    public ErrorMessage(final CustomWebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public boolean isPresent() {
        if (!WebDriverUtil.getInstance(webDriver).isElementPresent(XPATH, 2))
            return false;
        return errorMessage.isDisplayed();
    }

    public String getMessage() {
        if (isPresent())
            return errorMessage.getText();
        return "";
    }

    public String getMessage(int index) {
        if (isPresent()) {
            if (errorMessageItems.size() >= index)
                return errorMessageItems.get(index).getText();
            throw new TestRuntimeException("Invalid index was passed: " + index
                    + ". There possible valuea are [0, "
                    + (errorMessageItems.size() - 1) + "]");
        }
        return "";
    }

    public Integer getErrorMessagesNumber() {
        return errorMessageItems.size();
    }

    public String getStatusMessage(final int index) {
        if (errorMessageItems.size() >= index)
            return errorMessageItems.get(index).getText();
        throw new TestRuntimeException("Invalid index was passed: " + index
                + ". There possible valuea are [0, "
                + (errorMessageItems.size() - 1) + "]");
    }

    public List<String> getAllStatusMessages() {
        final List<String> listOfMessages = new ArrayList<String>(
                errorMessageItems.size());
        for (final WebElement item : errorMessageItems) {
            listOfMessages.add(item.getText());
        }
        return listOfMessages;
    }
}