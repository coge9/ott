package com.nbcuni.test.cms.elements.table;

import com.nbcuni.test.cms.elements.AbstractElement;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Pager extends AbstractElement {

    public static final String DEFAULT_PAGER_LOCATOR = "//ul[@class='pager']";
    public static final String FIRST_PAGE_LINK = ".//li[contains(@class,'pager-first')]/a";
    public static final String LAST_PAGE_LINK = ".//li[contains(@class,'pager-last')]/a";
    public static final String PREVIOUS_PAGE_LINK = ".//li[contains(@class,'pager-previous')]/a";
    public static final String NEXT_PAGE_LINK = ".//li[contains(@class,'pager-next')]/a";
    public static final String ITEM_PAGE_LINK = ".//li[contains(@class,'pager-item')]/a[text()='%s']";
    public static final String CURRENT_PAGE = ".//li[contains(@class,'pager-current')]";

    public Pager(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public Pager(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public Pager(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public Pager(CustomWebDriver driver) {
        super(driver, DEFAULT_PAGER_LOCATOR);
    }

    public Pager(WebElement webElement) {
        super(webElement);
    }

    public boolean isPagingPresent() {
        return isVisible();
    }

    public boolean isLastPage() {
        return !util().isElementPresent(NEXT_PAGE_LINK);
    }

    public boolean isNextPagePresent() {
        return util().isElementPresent(NEXT_PAGE_LINK);
    }

    public boolean isFirstPage() {
        return !util().isElementPresent(PREVIOUS_PAGE_LINK);
    }

    public void clickNextLink() {
        Link nextPage = new Link(driver, NEXT_PAGE_LINK);
        nextPage.clickWithAjaxWait();
    }

    public void clickPreviousLink() {
        Link previousPage = new Link(driver, PREVIOUS_PAGE_LINK);
        previousPage.clickWithAjaxWait();
    }

    public void goToFirstPage() {
        if (isPagingPresent()) {
            if (util().isElementPresent(FIRST_PAGE_LINK, 2)) {
                element().findElement(By.xpath(FIRST_PAGE_LINK)).click();
            }
        }
    }

    public void goToLastPage() {
        if (isPagingPresent()) {
            if (util().isElementPresent(LAST_PAGE_LINK, 2)) {
                element().findElement(By.xpath(LAST_PAGE_LINK)).click();
                waitFor().waitForPageLoad();
            }
        }
    }

    public int currentPageNumber() {
        if (!isPagingPresent()) {
            return 1;
        } else {
            String text = element().findElement(By.xpath(CURRENT_PAGE))
                    .getText();
            try {
                return Integer.parseInt(text);
            } catch (Exception e) {
                Utilities.logSevereMessageThenFail("Unable to get current page number");
                return -1;
            }
        }
    }

    public void goTyPageNumber(int pageNumber) {
        goToFirstPage();
        String url = getDriver().getCurrentUrl();
        if (url.contains("?")) {
            url = url + "&page=" + (pageNumber - 1);
        } else {
            url = url + "?page=" + (pageNumber - 1);
        }
        getDriver().get(url);
    }
}
