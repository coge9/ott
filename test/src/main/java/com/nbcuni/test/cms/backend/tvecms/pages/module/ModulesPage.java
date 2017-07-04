package com.nbcuni.test.cms.backend.tvecms.pages.module;

import com.nbcuni.test.cms.backend.chiller.pages.content.SelectInstancePage;
import com.nbcuni.test.cms.backend.tvecms.block.Input;
import com.nbcuni.test.cms.backend.tvecms.pages.ConfirmationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.elements.*;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableCell;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.ReflectionUtils;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ModulesPage extends MainRokuAdminPage {

    private static final String ADD_SHELF_LINK = ".//*[@class='action-links']//a[text()='Add Shelf']";
    private static final String EDIT_LINK_BY_NAME = ".//a[text()='%s']/ancestor::tr//a[text()='edit']";
    private static final String DELETE_LINK_BY_NAME = ".//a[text()='%s']/ancestor::tr//a[text()='delete']";
    private static final String CONFIRM_DELETE_NAME = ".//*[@id='edit-submit']";
    private static final String LINK_BY_NAME = ".//a[text()='%s']";
    private static final String LAST_PAGE_LINK = ".//a[contains(text(), 'last')]";
    private static final String NEXT_PAGE_LINK = ".//a[contains(text(), 'next')]";
    private static final String ROWS_XPATH = "//tbody/tr";
    private static final String TITLE_SEARCH_INPUT_XPATH = ".//input[@id='edit-title']";
    private static final String TITLE_XPATH = ".//td[2]/a";
    private static final String EDIT_XPATH = ".//a[contains(text(),'edit')]";
    private static final String DELETE_XPATH = ".//a[contains(text(),'delete')]";
    private static final String EXPAND_BTN_XPATH = ".//*[contains(@id,'ctools-button')]//a";
    private static final String CHECK_ITEM_CHECKBOX_XPATH = ".//*[contains(@id,'edit-views-bulk-operations-')]";
    @FindBy(xpath = ".//input[contains(@id,'edit-alias')]")
    private TextField aliasFilter;
    @FindBy(xpath = ".//*[contains(@id,'edit-submit-ott-modules-page')]")
    private Button apply;
    @FindBy(xpath = ".//td[contains(@class,'views-field-title')]/a")
    private Link title;
    @FindBy(xpath = ".//*[contains(@id,'edit-operation')]")
    private DropDownList operations;
    @FindBy(xpath = ".//*[@id='edit-submit--2']")
    private Button execute;
    @FindBy(id = "edit-submit")
    private Button confirmExecute;
    @FindBy(id = "block-system-main")
    private Table table;
    private Link addFeatureCarousel = new Link(webDriver, ".//*[@class='action-links']//a[text()='Add Feature Carousel']");
    //private Button apply = new Button(webDriver, ".//input[@id='edit-submit-ott-modules-page']");

    public ModulesPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public void clickAddShelfLink() {
        Utilities.logInfoMessage("Click add shelf link");
        webDriver.click(ADD_SHELF_LINK);
    }

    public DraftModuleTab clickAddFeatureCarouselLink() {
        Utilities.logInfoMessage("Click add shelf link");
        addFeatureCarousel.click();
        return new DraftModuleTab(webDriver, aid);
    }

    public DraftModuleTab clickEditLink(String shelfTitle) {
        Utilities.logInfoMessage("Click edit for page: " + shelfTitle);
        if (isShelfExist(shelfTitle)) {
            TableCell operationsCell = table.getRowByTextInColumn(shelfTitle, "TITLE").getCellByColumnTitle("OPERATIONS");
            operationsCell.getInnerElementInCell(By.xpath(EDIT_XPATH)).click();
            return new DraftModuleTab(webDriver, aid);
        }
        throw new TestRuntimeException("The row with shelf title: " + shelfTitle + " is not found");
    }

    public <T extends MainModulePage> T editModule(String shelfTitle, Class<T> tClass) {
        Utilities.logInfoMessage("Click edit for page: " + shelfTitle);
        searchByTitle(shelfTitle);
        if (isShelfExist(shelfTitle)) {
            TableCell operationsCell = table.getRowByTextInColumn(shelfTitle, "TITLE").getCellByColumnTitle("OPERATIONS");
            operationsCell.getInnerElementInCell(By.xpath(EDIT_XPATH)).click();
            return ReflectionUtils.getInstance(tClass, webDriver, aid);
        }
        throw new TestRuntimeException("The row with shelf title: " + shelfTitle + " is not found");
    }

    public ConfirmationPage clickDeleteLink(String shelfTitle) {
        Utilities.logInfoMessage("Click edit for shelf: " + shelfTitle);
        if (isShelfExist(shelfTitle)) {
            TableCell operationsCell = table.getRowByTextInColumn(shelfTitle, "TITLE").getCellByColumnTitle("OPERATIONS");
            operationsCell.getInnerElementInCell(By.xpath(EXPAND_BTN_XPATH)).click();
            WaitUtils.perform(webDriver).waitForElementClickable(DELETE_XPATH);
            operationsCell.getInnerElementInCell(By.xpath(DELETE_XPATH)).click();
            return new ConfirmationPage(webDriver, aid);
        }
        throw new TestRuntimeException("The row with page title: " + shelfTitle + " is not found");
    }

    public void confirmDelete() {
        Utilities.logInfoMessage("Confirm delete ");
        webDriver.click(CONFIRM_DELETE_NAME);
    }

    public SelectInstancePage executePublishToServices() {
        operations.selectFromDropDown("Publish to Services");
        execute.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new SelectInstancePage(webDriver, aid);
    }

    public ModulesPage deleteModule(String title) {
        clickDeleteLink(title);
        confirmDelete();
        return this;
    }

    public String getNodeShelfId(String shelfName) {
        Utilities.logInfoMessage("Get Node Id of the Shelf: " + shelfName);
        while (true) {
            if (!WebDriverUtil.getInstance(webDriver).isElementPresent(String.format(EDIT_LINK_BY_NAME, shelfName))) {
                if (!isNextLinkPresent()) {
                    Utilities.logInfoMessage("Shelf reference" + shelfName + " does not exist!");
                    return "";
                }
                clickNextLink();
            } else {
                String href = webDriver.getAttribute(String.format(EDIT_LINK_BY_NAME, shelfName), HtmlAttributes.HREF.get());
                return SimpleUtils.getNumbersFromString(href);
            }
        }

    }

    public void clickShelfLink(String shelfName) {
        Utilities.logInfoMessage("Click shelf " + shelfName + " link");
        webDriver.click(String.format(LINK_BY_NAME, shelfName));
    }

    public void clickLastLink() {
        table.getPager().goToLastPage();
    }

    public void clickNextLink() {
        table.getPager().clickNextLink();
    }

    public void apply() {
        apply.setName("Search");
        apply.click();
    }

    public String getTitle() {
        Utilities.logInfoMessage("Get Module title");
        return title.getText();
    }

    private boolean isLastLinkPresent() {
        return WebDriverUtil.getInstance(webDriver).isElementPresent(LAST_PAGE_LINK);
    }

    private boolean isNextLinkPresent() {
        return WebDriverUtil.getInstance(webDriver).isElementPresent(NEXT_PAGE_LINK);
    }

    public boolean isShelfExist(String shelfName) {
        searchByTitle(shelfName);
        if (!WebDriverUtil.getInstance(webDriver).isElementPresent(String.format(LINK_BY_NAME, shelfName))) {
            if (!isLastLinkPresent()) {
                return false;
            }
            clickLastLink();
            return isShelfExist(shelfName);
        } else {
            return true;
        }
    }

    public Table getTable() {
        return table;
    }

    @Override
    public List<String> verifyPage() {

        return null;
    }

    public void searchByTitle(String shelfTitle) {
        Utilities.logInfoMessage("Search shelf by title: " + shelfTitle);
        Input search = new Input(webDriver, By.xpath(TITLE_SEARCH_INPUT_XPATH));
        search.clearAndType(shelfTitle);
        apply();
    }

    public ModulesPage searchByAlias(String alias) {
        Utilities.logInfoMessage("Search shelf by alias: " + alias);
        aliasFilter.enterText(alias);
        apply();
        return this;
    }

    public void checkAnItem(String title) {
        searchByTitle(title);
        TableCell cell = table.getRowByTextInColumn(title, "TITLE").getCellByIndex(1);
        CheckBox checkBox = new CheckBox(cell, CHECK_ITEM_CHECKBOX_XPATH);
        checkBox.check();
    }

    public void executeDelete() {
        operations.selectFromDropDown("Delete item");
        execute.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        confirmExecute.click();

    }
}
