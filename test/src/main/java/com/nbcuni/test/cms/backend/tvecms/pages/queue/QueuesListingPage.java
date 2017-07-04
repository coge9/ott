package com.nbcuni.test.cms.backend.tvecms.pages.queue;

import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class QueuesListingPage extends Page {

    public static final String PAGE_TITLE = "Queues Listing";

    private static final String TITLE_FIELD = ".//input[@id='edit-title-1']";
    private static final String TYPE_DDL = ".//input[@id='edit-type']";
    private static final String CREATION_LINKS = ".//ul[@class='action-links']//li/a";

    private static final String TABLE_XPATH = ".//*[@id='block-system-main']//table[contains(@class, 'views-table')]";
    private static final String ROWS_XPATH = "//tbody/tr";
    private static final String CONTENT_TABLE_XPATH = ".//*[@id='block-system-main']";
    private static final String TITLE_FIELD_XPATH = "//input[@id='edit-title-1']";

    private static final String APPLY_BUTTON_XPATH = "//input[@id='edit-submit-queues-listing']";
    private static final String RESET_BUTTON_XPATH = ".//input[@id='edit-reset']";
    private static final String EDIT_BUTTON_XPATH = "//div[@class='ctools-content']//a[text()='Edit']";
    private static final String DELETE_BUTTON_XPATH = "//div[@class='ctools-content']//a[text()='Delete']";
    private static final String DELETE_EDIT_XPATH = "//div[@class='ctools-content']//a[text()='Edit']";
    private static final String OPTIONS_LINK_XPATH = "//a[@class='ctools-twisty ctools-text']";
    private static final String CONFIRMATION_DELETE_CONTENT_TYPE_XPATH = "//input[@id='edit-submit']";

    private static final String TITLE_XPATH = "//td[contains(@class,'views-field-title')]";
    private static final String TYPE_XPATH = "//td[contains(@class,'views-field-type')]";
    private static final String STATUS_XPATH = "//td[contains(@class,'views-field-published')]";
    private static final String STATUS_MESSAGE_XPATH = "//div[@class='messages status']";

    private static final String ROWS_XPATH_GENERAL = "(.//div[@class='view-content']/table/tbody/tr)[%s]";
    private static final String ALL_ROWS_XPATH = ".//div[@class='view-content']/table/tbody/tr";
    private static final String EDIT_XPATH = "(.//div[@class='view-content']/table/tbody/tr)[%s]/td[contains(@class,'views-field-edit-link')]//li/a";

    public QueuesListingPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public QueuesListingPage searchByTitle(final String title) {
        Utilities.logInfoMessage("Search queue by title: " + title);
        if (WebDriverUtil.getInstance(webDriver).isElementNotPresent(
                TITLE_FIELD_XPATH)) {
            Assertion.fail("Filter fields isn't present");
        }
        webDriver.type(TITLE_FIELD_XPATH, title);
        return this;
    }

    public boolean apply() {
        webDriver.click(APPLY_BUTTON_XPATH);
        pause(3);
        return isContentPresent();
    }

    public boolean reset() {
        webDriver.click(RESET_BUTTON_XPATH);
        return isContentPresent();
    }

    public boolean isContentPresent() {
        Utilities.logInfoMessage("Check rather content present at the queue page");
        if (WebDriverUtil.getInstance(webDriver).isElementPresent(
                CONTENT_TABLE_XPATH))
            return WebDriverUtil.getInstance(webDriver).isElementPresent(
                    TABLE_XPATH);
        return false;
    }

    public List<String> getQueue() {
        return webDriver.getSelectValues(TYPE_DDL);
    }

    public void selectQueueType(final String valueToSelect) {
        webDriver.selectFromDropdown(TYPE_DDL, valueToSelect);
    }

    public AddQueuePage clickAddQueueByType(String type) {
        List<WebElement> creationLinks = webDriver
                .findElementsByXPath(CREATION_LINKS);
        for (WebElement link : creationLinks) {
            if (link.getText().equals(type)) {
                link.click();
                return new AddQueuePage(webDriver, aid);
            }
        }
        Utilities.logSevereMessageThenFail("No link with name " + type + " were found");
        return null;
    }


    public int getNumberOfAssetsbyPartName(String titlePart) {
        final boolean isPresent = searchByTitle(titlePart).apply();
        if (!isPresent) {
            Utilities.logSevereMessageThenFail(String.format(
                    "Content type with partial title '%s' not found.",
                    titlePart));
            return 0;
        }
        final WebElement contentTable = webDriver.findElement(By
                .xpath(CONTENT_TABLE_XPATH));
        final List<WebElement> rows = contentTable.findElements(By
                .xpath(ROWS_XPATH));
        return rows.size();
    }

    public void deleteAssetWithConfirmation(final String title) {
        Utilities.logInfoMessage("Delete the asset with confirmation");
        searchByTitle(title).apply();
        webDriver.click(OPTIONS_LINK_XPATH);
        final WebElement deleteBtn = webDriver.findElement(By
                .xpath(DELETE_BUTTON_XPATH));
        WebDriverUtil.getInstance(webDriver).click(deleteBtn);
        webDriver.click(CONFIRMATION_DELETE_CONTENT_TYPE_XPATH);
    }

    public void clickDeleteOption() {
        Utilities.logInfoMessage("Click delete option on selected asset");
        final WebElement deleteBtn = webDriver.findElement(By
                .xpath(DELETE_BUTTON_XPATH));
        WaitUtils.perform(webDriver).waitElementVisible(DELETE_BUTTON_XPATH, 2);
        WebDriverUtil.getInstance(webDriver).click(deleteBtn);
    }

    public void clickEditOption() {
        Utilities.logInfoMessage("Click edit option on selected asset");
        final WebElement editBtn = webDriver.findElement(By
                .xpath(DELETE_EDIT_XPATH));
        WebDriverUtil.getInstance(webDriver).click(editBtn);
    }

    public AddQueuePage openEditQueuePage(final String queueName) {
        Utilities.logInfoMessage("Open edit " + queueName + " queue page");
        searchByTitle(queueName).apply();
        if (isContentPresent()) {
            WaitUtils.perform(webDriver).waitElementPresent(OPTIONS_LINK_XPATH,
                    5);
            WaitUtils.perform(webDriver).waitElementVisible(OPTIONS_LINK_XPATH,
                    5);
            webDriver.click(OPTIONS_LINK_XPATH);
            if (webDriver.getObjectCount(EDIT_BUTTON_XPATH) < 1) {
                Utilities.logSevereMessageThenFail(String.format("Queue with title '%s' not found.",
                        queueName));
            }
            final WebElement editBtn = webDriver.findElement(By
                    .xpath(EDIT_BUTTON_XPATH));
            WebDriverUtil.getInstance(webDriver).click(editBtn);
            return new AddQueuePage(webDriver, aid);
        } else {
            Utilities.logSevereMessageThenFail("Queue with title '%s' not found." + queueName);
            new TestRuntimeException(String.format(
                    "Queue with title '%s' not found.", queueName));
            return null;
        }
    }

    public void clickOnOptions() {
        webDriver.click(OPTIONS_LINK_XPATH);
    }

    public void editQueueByName(String title) {
        List<WebElement> rows = webDriver.findElements(By.xpath(ALL_ROWS_XPATH));
        for (int i = 1; i <= rows.size(); i++) {
            if (webDriver.getText(String.format(ROWS_XPATH_GENERAL, i)).contains(title)) {
                webDriver.click(String.format(EDIT_XPATH, i));
                break;
            }
        }
    }

    public boolean isOptionsDeleteDisplayed() {
        Utilities.logInfoMessage("Chcek rather option delete present");
        return WebDriverUtil.getInstance(webDriver).isElementPresent(
                DELETE_BUTTON_XPATH);
    }

    private List<List<WebElement>> getRows() {
        final List<List<WebElement>> rows = new ArrayList<List<WebElement>>();
        final WebElement contentTable = webDriver.findElement(By
                .xpath(CONTENT_TABLE_XPATH));
        final List<WebElement> lines = contentTable.findElements(By
                .xpath(ROWS_XPATH));
        for (final WebElement webElement : lines) {
            final List<WebElement> row = new ArrayList<WebElement>();
            final WebElement title = webElement.findElement(By
                    .xpath(TITLE_XPATH));
            row.add(title);
            final WebElement typeField = webElement.findElement(By
                    .xpath(TYPE_XPATH));
            row.add(typeField);
            final WebElement statusField = webElement.findElement(By
                    .xpath(STATUS_XPATH));
            row.add(statusField);
            final WebElement editButton = webElement.findElement(By
                    .xpath(EDIT_BUTTON_XPATH));
            row.add(editButton);
            rows.add(row);
        }
        return rows;
    }

    public boolean isQueueExist(String queueName) {
        Utilities.logInfoMessage("Check rather queue: " + queueName + " exist");
        List<WebElement> titles = webDriver.findElements(By.xpath(TITLE_XPATH));
        for (WebElement title : titles) {
            if (title.getText().equalsIgnoreCase(queueName)) {
                return true;

            }
        }
        return false;
    }

    public void clickConfirmation() {
        WaitUtils.perform(webDriver).waitElementVisible(
                CONFIRMATION_DELETE_CONTENT_TYPE_XPATH);
        webDriver.click(CONFIRMATION_DELETE_CONTENT_TYPE_XPATH);
    }

    public boolean isItemExist(String queueName) {
        Utilities.logInfoMessage("Check rather queue with part name: " + queueName
                + " exist");
        List<WebElement> titles = webDriver.findElements(By.xpath(TITLE_XPATH));
        for (WebElement title : titles) {
            if (title.getText().contains(queueName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getErrorMessage() {
        WaitUtils.perform(webDriver).waitElementPresent(STATUS_MESSAGE_XPATH);
        WaitUtils.perform(webDriver).waitElementVisible(STATUS_MESSAGE_XPATH);
        return webDriver.findElementByXPath(STATUS_MESSAGE_XPATH).getText();

    }

    @Override
    public List<String> verifyPage() {
        return null;
    }

    public void fillTitle(String textToEnter) {
        webDriver.type(TITLE_FIELD, textToEnter);
    }

    public String getTitle() {
        return webDriver.getText(TITLE_FIELD);
    }

}
