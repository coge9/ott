package com.nbcuni.test.cms.backend.tvecms.pages.ottpage;

import com.nbcuni.test.cms.backend.chiller.pages.content.SelectInstancePage;
import com.nbcuni.test.cms.backend.tvecms.pages.ConfirmationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.elements.*;
import com.nbcuni.test.cms.elements.table.Pager;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableCell;
import com.nbcuni.test.cms.elements.table.TableRow;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.*;

public class TVEPage extends Page {

    private static final String TITLE_XPATH = ".//td[2]/a";
    private static final String EDIT_XPATH = ".//a[contains(text(),'edit')]";
    private static final String DELETE_XPATH = ".//a[contains(text(),'delete')]";
    private static final String EXPAND_BTN_XPATH = ".//*[@class='ctools-link']//a";
    private static final String CHECK_ITEM_CHECKBOX_XPATH = ".//*[contains(@id,'edit-views-bulk-operations-')]";
    public static final String TITLE = "TITLE";
    public static final String CLICK_EDIT_FOR_PAGE = "Click edit for page: ";
    private Pager pager;
    //Page Elements
    @FindBy(xpath = ".//*[@class='views-table cols-8']")
    private Table table;
    private DropDownList platform = new DropDownList(webDriver, By.id("edit-field-ott-page-add-app-target-id"));
    private Link addOttPage = new Link(webDriver, ".//*[@id='content']//ul[@class='action-links']//a");
    private TextField title = new TextField(webDriver, By.id("edit-title"));

    @FindBy(id = "edit-operation")
    private DropDownList operations;

    @FindBy(xpath = ".//*[@id='edit-submit--2']")
    private Button execute;

    @FindBy(id = "edit-submit")
    private Button confirmExecute;

    @FindBy(id = "edit-submit-ott-pages")
    private Button apply;

    public TVEPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
        pager = new Pager(webDriver);
    }

    public AddNewPage clickAddNewPage() {
        addOttPage.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new AddNewPage(webDriver, aid);
    }

    public boolean isTablePresent() {
        return table.isPresent();
    }

    public boolean isPageExist(String pageTitle) {
        Utilities.logInfoMessage("Check rather page title exist: " + pageTitle);
        searchByTitle(pageTitle);
        List<WebElement> titles = webDriver.findElements(By.xpath(TITLE_XPATH));
        for (WebElement singleTitle : titles) {
            if (singleTitle.getText().equalsIgnoreCase(pageTitle)) {
                return true;
            }
        }
        if (pager.isNextPagePresent()) {
            pager.clickNextLink();
            return isPageExist(pageTitle);
        }
        return false;
    }

    public List<String> getAllPagesTitles() {
        Utilities.logInfoMessage("Getting all pages");
        List<String> titles = getAllPageTitlesOnPage();
        while (pager.isNextPagePresent()) {
            pager.clickNextLink();
            titles.addAll(getAllPageTitlesOnPage());
        }
        return titles;
    }

    public List<String> getAllPageTitlesOnPage() {
        List<String> titles = new ArrayList<>();
        List<WebElement> titlesElements = webDriver.findElements(By.xpath(TITLE_XPATH));
        for (WebElement singleTitle : titlesElements) {
            titles.add(singleTitle.getText().trim());
        }
        return titles;
    }

    public void checkAnItem(String title) {
        searchByTitle(title);
        TableCell cell = table.getRowByTextInColumn(title, TITLE).getCellByIndex(1);
        CheckBox checkBox = new CheckBox(cell, CHECK_ITEM_CHECKBOX_XPATH);
        checkBox.check();
    }

    public Table getTable() {
        return table;
    }

    public EditPageWithPanelizer clickEdit(String pageTitle) {
        Utilities.logInfoMessage(CLICK_EDIT_FOR_PAGE + pageTitle);
        WaitUtils.perform(webDriver).waitForPageLoad();
        if (isPageExist(pageTitle)) {
            TableCell operationsCell = table.getRowByTextInColumn(pageTitle, TITLE).getCellByColumnTitle("OPERATIONS");
            if (operationsCell.isInnerElementInCellPresent(By.xpath(EXPAND_BTN_XPATH))) {
                operationsCell.getInnerElementInCell(By.xpath(EXPAND_BTN_XPATH)).click();
            }
            operationsCell.getInnerElementInCell(By.xpath(EDIT_XPATH)).click();
            WaitUtils.perform(webDriver).waitForPageLoad();
            return new EditPageWithPanelizer(webDriver, aid);
        }
        throw new TestRuntimeException("The row with page title: " + pageTitle + " is not found");
    }

    public ConfirmationPage clickDelete(String pageTitle) {
        Utilities.logInfoMessage(CLICK_EDIT_FOR_PAGE + pageTitle);
        searchByTitle(pageTitle);
        if (isPageExist(pageTitle)) {
            TableCell operationsCell = table.getRowByTextInColumn(pageTitle, TITLE).getCellByColumnTitle("OPERATIONS");
            operationsCell.getInnerElementInCell(By.xpath(EXPAND_BTN_XPATH)).click();
            WaitUtils.perform(webDriver).waitForElementClickable(DELETE_XPATH);
            operationsCell.getInnerElementInCell(By.xpath(DELETE_XPATH)).click();
            return new ConfirmationPage(webDriver, aid);
        }
        throw new TestRuntimeException("The row with page title: " + pageTitle + " is not found");
    }

    public void executeDelete() {
        operations.selectFromDropDown("Delete item");
        execute.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        confirmExecute.click();
    }

    public void executePublishToServices() {
        operations.selectFromDropDown("Publish to Services");
        execute.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        new SelectInstancePage(webDriver, aid).publishToAllInstances();
    }

    public EditPageWithPanelizer openRandomPage() {
        List<String> titlesOnFirstPage = getAllPageTitlesOnPage();
        int index = new Random().nextInt(titlesOnFirstPage.size());
        String randomTitle = titlesOnFirstPage.get(index);
        Utilities.logInfoMessage(CLICK_EDIT_FOR_PAGE + randomTitle);
        return clickEdit(randomTitle);
    }

    public String getRandomPageTitle() {
        TableRow randomPage = table.getRandomRow();
        String randomPageTitle = randomPage.getCellByColumnTitle(Table.COLUMN_TITLE).getCellInnerText();
        Utilities.logInfoMessage("Random page title is [" + randomPageTitle + "]");
        return randomPageTitle;
    }

    @Override
    public List<String> verifyPage() {
        return null;
    }

    public void searchByTitle(String pageTitle) {
        Utilities.logInfoMessage("Search page by title: " + pageTitle);
        title.enterText(pageTitle);
        this.apply();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public TVEPage searchByPlatform(String platform) {
        Utilities.logInfoMessage("Search page by Platform: " + platform);
        this.platform.selectFromDropDown(platform);
        return this;
    }

    public TVEPage apply() {
        apply.setName("Search");
        apply.click();
        return this;
    }

    public Map<String, Set<String>> getAttachedPageForPlatforms(List<String> platforms) {
        Map<String, Set<String>> platformsMap = new HashMap<>();
        for (String singlePlatform : platforms) {
            Set<String> pagesNames = new HashSet<>();
            searchByPlatform(singlePlatform).apply();
            pagesNames.addAll(this.getAllPagesTitles());
            platformsMap.put(singlePlatform, pagesNames);
        }
        return platformsMap;
    }
}
