package com.nbcuni.test.cms.backend.chiller.pages.collections;

import com.nbcuni.test.cms.backend.chiller.pages.content.SelectInstancePage;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableCell;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.ReflectionUtils;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

public class CollectionsContentPage extends MainRokuAdminPage {

    public static final String TITLE = "Queues Listing";
    private static final String TABLE_XPATH = "//div[@class='view-content']//table";
    private static final String  CHECK_ITEM_CHECKBOX_XPATH = ".//*[contains(@id,'edit-views-bulk-operations-')]";
    //links
    private static final String EDIT_LINK = "Edit";
    private static final String DELETE_LINK = "Delete";
    private static final String OPERATIONS_LINK = "operations";
    //cells
    private static final String OPERATIONS = "Operations";
    @FindBy(xpath = ".//input[contains(@id,'edit-title')]")
    private TextField titleField;
    @FindBy(id = "edit-type")
    private DropDownList typeDropdownList;
    @FindBy(xpath = "//*[@id='edit-submit-queues-listing']|.//*[@id='edit-submit-collections']")
    private Button applyButton;
    @FindBy(id = "edit-reset")
    private Button resetButton;
    @FindBy(xpath = "//div[@class='view-content']//table")
    private Table table;
    @FindBy(id = "edit-operation")
    private DropDownList operations;
    @FindBy(xpath = ".//*[@id='edit-submit--2']")
    private Button execute;
    @FindBy(id = "edit-submit")
    private Button confirmExecute;

    public CollectionsContentPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public void enterTitle(String title) {
        titleField.enterText(title);
    }

    public void clickApplyButton() {
        applyButton.click();
    }

    public void clickResetButton() {
        resetButton.click();
    }

    public void findCollection(Collection collection) {
        titleField.enterText(collection.getTitle());
        typeDropdownList.selectFromDropDown(collection.getCollectionInfo().getType());
        applyButton.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public void findByTitle(Collection collection) {
        titleField.enterText(collection.getTitle());
        applyButton.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public boolean isCollectionPresent(Collection collection) {
        findCollection(collection);
        return WebDriverUtil.getInstance(webDriver).isElementPresent(TABLE_XPATH);
    }

    private void clickDeleteLink(TableCell cell) {
        cell.clickLinkByName(OPERATIONS_LINK);
        cell.clickLinkByName(DELETE_LINK);
    }

    private void clickEditLink(TableCell cell) {
        cell.clickLinkByName(EDIT_LINK);
    }

    public void deleteCollection(Collection collection) {
        if (isCollectionPresent(collection)) {
            clickDeleteLink(table.getRowByIndex(1).getCellByColumnTitle(OPERATIONS));
            confirmExecute.click();
        }
    }

    public CuratedCollectionPage openEditCuratedCollectionPage(Collection collection) {
        findCollection(collection);
        clickEditLink(table.getRowByIndex(1).getCellByColumnTitle(OPERATIONS));
        return new CuratedCollectionPage(webDriver, aid);
    }

    public CollectionGroupPage openEditCollectionGroupPage(Collection collection) {
        findCollection(collection);
        clickEditLink(table.getRowByIndex(1).getCellByColumnTitle(OPERATIONS));
        return new CollectionGroupPage(webDriver, aid);
    }

    public <T extends Page> T clickEditLink(Class<T> clazz, Collection collection) {
        findByTitle(collection);
        clickEditLink(table.getRowByIndex(1).getCellByColumnTitle(OPERATIONS));
        Utilities.logInfoMessage("Click on edit link for " + collection.getTitle());
        return ReflectionUtils.getInstance(clazz, webDriver, aid);
    }

    public String getNodePublishState(String title) {
        return table.getRowByTextInColumn(title, "TITLE").getCellByColumnTitle("STATUS").getCellInnerText();
    }

    public void checkAnItem(Collection collection) {
        findByTitle(collection);
        TableCell cell = table.getRowByTextInColumn(collection.getTitle(), "TITLE").getCellByIndex(1);
        CheckBox checkBox = new CheckBox(cell, CHECK_ITEM_CHECKBOX_XPATH);
        checkBox.check();
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

    @Override
    public boolean isPageOpened() {
        return getPageTitle().equalsIgnoreCase(TITLE);
    }
}
