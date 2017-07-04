package com.nbcuni.test.cms.backend.tvecms.pages.module.tabs;

import com.nbcuni.test.cms.backend.tvecms.pages.module.MainModulePage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableCell;
import com.nbcuni.test.cms.elements.table.TableRow;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;


public class ManageRevisionsTab extends MainModulePage {

    private static final String VIEW_XPATH = ".//a[text()='view']";
    private static final String EDIT_XPATH = ".//a[text()='edit']";
    private static final String REVERT_XPATH = ".//a[text()='revert']";
    private static final String DELETE_XPATH = ".//a[text()='delete']";
    private static final String CURRENT_REVISION = "current revision";
    @FindBy(xpath = "//table[contains(@class,'tableheader-processed')]")
    private Table table;
    private Link view = new Link(webDriver, VIEW_XPATH);
    private Link edit = new Link(webDriver, EDIT_XPATH);
    private Link revert = new Link(webDriver, REVERT_XPATH);
    private Link delete = new Link(webDriver, DELETE_XPATH);

    public ManageRevisionsTab(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public ViewPublishTab clickViewButton(Shelf shelf) {
        Utilities.logInfoMessage("Click View revision button for shelf: " + shelf.getTitle());
        getCellOfActions(shelf).element().findElement(By.xpath(VIEW_XPATH)).click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new ViewPublishTab(webDriver, aid);
    }

    public DraftModuleTab clickEditButton(Shelf shelf) {
        Utilities.logInfoMessage("Click Edit revision button for shelf: " + shelf.getTitle());
        getCellOfActions(shelf).element().findElement(By.xpath(EDIT_XPATH)).click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new DraftModuleTab(webDriver, aid);
    }

    public void clickRevertButton(Shelf shelf) {
        Utilities.logInfoMessage("Click Revert revision button for shelf: " + shelf.getTitle());
        getCellOfActions(shelf).element().findElement(By.xpath(REVERT_XPATH)).click();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public void clickDeleteButton(Shelf shelf) {
        Utilities.logInfoMessage("Click Delete revision button for shelf: " + shelf.getTitle());
        getCellOfActions(shelf).element().findElement(By.xpath(DELETE_XPATH)).click();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public boolean isViewButtonVisible(Shelf shelf) {
        Utilities.logInfoMessage("Check Rather View button is displayed for shelf: " + shelf.getTitle());
        return !getCellOfActions(shelf).element().findElements(By.xpath(VIEW_XPATH)).isEmpty();
    }

    public boolean isEditButtonVisible(Shelf shelf) {
        Utilities.logInfoMessage("Check Rather Edit button is displayed for shelf: " + shelf.getTitle());
        return !getCellOfActions(shelf).element().findElements(By.xpath(VIEW_XPATH)).isEmpty();
    }

    public boolean isRevertButtonVisible(Shelf shelf) {
        Utilities.logInfoMessage("Check Rather Revert button is displayed for shelf: " + shelf.getTitle());
        return !getCellOfActions(shelf).element().findElements(By.xpath(REVERT_XPATH)).isEmpty();
    }

    public boolean isDeleteButtonVisible(Shelf shelf) {
        Utilities.logInfoMessage("Check Rather delete button is displayed for shelf: " + shelf.getTitle());
        return !getCellOfActions(shelf).element().findElements(By.xpath(DELETE_XPATH)).isEmpty();
    }

    public String getIdRevision(Shelf shelf) {
        Utilities.logInfoMessage("Get ID of Row for revision of shelf name: " + shelf.getTitle());
        TableRow row = table.getRowByTextInColumn(shelf.getTitle(), Columns.TITLE.toString());
        return row.getCellByColumnTitle(Columns.ID.toString()).getCellInnerText();
    }

    public boolean isShelfRevisionPresentByID(String id, Shelf shelf) {
        Utilities.logInfoMessage("Check rather shelf revision is present by ID: " + id);
        TableRow row = table.getRowByTextInColumn(shelf.getTitle(), Columns.TITLE.toString());
        return row.getCellByColumnTitle(Columns.ID.toString()) != null;
    }

    public boolean isRevisionCurrent(String id) {
        Utilities.logInfoMessage("Check rather selected by ID: " + id + " revision is current");
        TableRow row = table.getRowByTextInColumn(id, Columns.ID.toString());
        return row.getCellByColumnTitle(Columns.INFO.toString()).getCellInnerText().contains(CURRENT_REVISION);
    }

    private TableCell getCellOfActions(Shelf shelf) {
        Utilities.logInfoMessage("Get cell of actions within current Row for shelf: " + shelf.getTitle());
        TableRow row = table.getRowByTextInColumn(shelf.getTitle(), Columns.TITLE.toString());
        return row.getCellByColumnTitle(Columns.ACTIONS.toString());
    }

    private enum Columns {
        ID, TITLE, DATE, LOG, ACTIONS, INFO;
    }


}
