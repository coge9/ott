package com.nbcuni.test.cms.pageobjectutils;

import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.nbcuni.test.cms.utils.webdriver.WebDriverUtil.getInstance;

/**
 * Created by Alena_Aukhukova on 9/9/2015.
 */
public class Table {

    private static final String PAGES_XPATH = "//ul[@class='pager']";
    private static final String CURRENT_PAGE_XPATH = "//ul[@class='pager']//li[contains(@class,'pager-current')]";
    private static final String ROW_XPATH = "./ancestor::tr";
    private static final String ROW_GENERALIZED = "(.//tbody/tr)[%s]";
    private static final String NEXT_PAGE_XPATH = "//ul[@class='pager']//li[contains(text(),'%s')]";


    private CustomWebDriver webDriver;
    private AppLib aid;

    public Table(final CustomWebDriver webDriver, final AppLib aid) {
        this.webDriver = webDriver;
        this.aid = aid;
    }

    public WebElement getRowWithElement(String elementXpath) {
        while (true) {
            if (getInstance(webDriver).isElementPresent(elementXpath, 2)) {
                WebElement searchingElement = webDriver.findElementByXPath(elementXpath);
                Utilities.logInfoMessage("Find row in sheet by xpath: " + elementXpath);
                return searchingElement.findElement(By.xpath(ROW_XPATH));
            } else {
                if (isNextPagePresent()) {
                    clickNextPage();
                } else {
                    Utilities.logSevereMessageThenFail("Element with xaph: " + elementXpath + " is not found");
                    return null;
                }
            }
        }
    }

    public List<WebElement> getListOfRows() {
        List<WebElement> rows = webDriver.findElements(By.xpath(ROW_XPATH));
        return rows;
    }

    public WebElement getRowByIndex(WebElement table, int index) {
        if (table != null) {
            WebElement row = table.findElement(By.xpath(String.format(ROW_GENERALIZED, index)));
            return row;
        }
        throw new TestRuntimeException("The table element is null, couldn't detect a row");
    }

    public boolean isNextPagePresent() {
        int nextPageNumber = getCurrentPageNumber() + 1;
        return getInstance(webDriver).isElementPresent(String.format(NEXT_PAGE_XPATH, nextPageNumber, 2));
    }

    public int getCurrentPageNumber() {
        String currentPageNumber = webDriver.findElementByXPath(CURRENT_PAGE_XPATH).getText();
        return Integer.valueOf(currentPageNumber);
    }

    public void clickNextPage() {
        String nextPageNumberXpath = String.format(NEXT_PAGE_XPATH, getCurrentPageNumber() + 1);
        webDriver.findElementByXPath(nextPageNumberXpath).click();
        WaitUtils.perform(webDriver).waitTextOfElementContains(CURRENT_PAGE_XPATH, String.valueOf(getCurrentPageNumber() + 1), 15);
        Utilities.logInfoMessage("Go to next page");
    }
}
