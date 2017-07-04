package com.nbcuni.test.cms.backend.tvecms.pages.panelizer;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableCell;
import com.nbcuni.test.cms.elements.table.TableRow;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PanelizerManagerPage extends MainRokuAdminPage {

    private final String ACTION_LINK = ".//*[@class='ctools-link']//a";
    private Link addLink = new Link(webDriver, By.linkText("Add"));
    private Link importLink = new Link(webDriver, By.linkText("Import"));
    private DropDownList storage = new DropDownList(webDriver, By.id("edit-storage"));
    private DropDownList enabling = new DropDownList(webDriver, By.id("edit-disabled"));
    private DropDownList sorting = new DropDownList(webDriver, By.id("edit-order"));
    private DropDownList ordering = new DropDownList(webDriver, By.id("edit-sort"));
    private TextField search = new TextField(webDriver, By.id("edit-search"));
    private Button apply = new Button(webDriver, By.xpath("//input[@value='Apply']"));
    private Button delteConfirmation = new Button(webDriver, By.xpath(".//*[@id='edit-submit']"));
    private Button reset = new Button(webDriver, By.xpath("//input[@value='Reset']"));

    @FindBy(xpath = "//table[@id='ctools-export-ui-list-items']")
    private Table table;

    public PanelizerManagerPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public AddPanelizerPage clickAddPanelizer() {
        Utilities.logInfoMessage("Click on link 'Add' to add panelizer");
        addLink.click();
        return new AddPanelizerPage(webDriver, aid);
    }

    public ImportPanelizerPage clickImportPanelizer() {
        Utilities.logInfoMessage("Click on link 'Import' to import panelizer");
        importLink.click();
        return new ImportPanelizerPage(webDriver, aid);
    }

    public void selectStorage(Storage value) {
        Utilities.logInfoMessage("Select storage: " + value);
        storage.selectFromDropDown(value.toString());
    }

    public void selectEnable(Enable value) {
        Utilities.logInfoMessage("Select enabling: " + value);
        enabling.selectFromDropDown(value.toString());
    }

    public void selectSorting(Sort value) {
        Utilities.logInfoMessage("Select sorting by: " + value);
        sorting.selectFromDropDown(value.getValue());
    }

    public void selectOrdering(Order value) {
        Utilities.logInfoMessage("Select ordering by: " + value);
        ordering.selectFromDropDown(value.toString());
    }

    public void apply() {
        Utilities.logInfoMessage("Click Apply");
        apply.click();
    }

    public void reset() {
        Utilities.logInfoMessage("Click Reset");
        reset.click();
    }

    /**
     * Method to show how possible to Open any configuration panelizer page
     * by clicking on action link next to panelizer Name
     *
     * @param panelizerName - name of pre-setup panelizer on each action will be performed
     */
    public void clickContentForPanelizer(String panelizerName) {
        Utilities.logInfoMessage("Open Content Page for panelizer: " + panelizerName);
        getActionsCell(panelizerName).element().findElement(By.xpath(ACTION_LINK)).click();
        getActionsCell(panelizerName).clickLinkByName(Operations.CONTENT.getValue());
    }

    /**
     * Method to show how possible to Open any configuration panelizer page
     * by clicking on action link next to panelizer Name
     *
     * @param panelizerName - name of pre-setup panelizer on each action will be performed
     * @return SettingsPanelizerPage
     */
    public SettingsPanelizerPage clickSettingForPanelizer(String panelizerName) {
        Utilities.logInfoMessage("Open Settings Page for panelizer: " + panelizerName);
        getActionsCell(panelizerName).clickLinkByName(Operations.SETTINGS.getValue());
        return new SettingsPanelizerPage(webDriver, aid);
    }

    /**
     * Method to show how possible to Open any configuration panelizer page
     * by clicking on action link next to panelizer Name
     *
     * @param panelizerName - name of pre-setup panelizer on each action will be performed
     * @return LayoutPanelizerPage
     */
    public LayoutPanelizerPage clickLayoutForPanelizer(String panelizerName) {
        Utilities.logInfoMessage("Open Layout Page for panelizer: " + panelizerName);
        getActionsCell(panelizerName).element().findElement(By.xpath(ACTION_LINK)).click();
        getActionsCell(panelizerName).clickLinkByName(Operations.LAYOUT.getValue());
        return new LayoutPanelizerPage(webDriver, aid);
    }

    /**
     * Method to delete panelizer
     *
     * @param panelizerName - name of pre-setup panelizer on each action will be performed
     */
    public void deletePanelizer(String panelizerName) {
        Utilities.logInfoMessage("Delete panelizer: " + panelizerName);
        getActionsCell(panelizerName).element().findElement(By.xpath(ACTION_LINK)).click();
        getActionsCell(panelizerName).clickLinkByName(Operations.DELETE.getValue());
        delteConfirmation.click();
    }

    /**
     * Method to clone panelizer
     *
     * @param panelizerName - name of pre-setup panelizer on each action will be performed
     * @return LayoutPanelizerPage
     */
    public AddPanelizerPage clonePanelizer(String panelizerName) {
        Utilities.logInfoMessage("Delete panelizer: " + panelizerName);
        getActionsCell(panelizerName).element().findElement(By.xpath(ACTION_LINK)).click();
        getActionsCell(panelizerName).clickLinkByName(Operations.CLONE.getValue());
        return new AddPanelizerPage(webDriver, aid);
    }

    /**
     * Method to disable panelizer
     *
     * @param panelizerName - name of pre-setup panelizer on each action will be performed
     */
    public void disablePanelizer(String panelizerName) {
        Utilities.logInfoMessage("Delete panelizer: " + panelizerName);
        getActionsCell(panelizerName).element().findElement(By.xpath(ACTION_LINK)).click();
        getActionsCell(panelizerName).clickLinkByName(Operations.DISABLE.getValue());
    }

    /**
     * Method to enable panelizer
     *
     * @param panelizerName - name of pre-setup panelizer on each action will be performed
     */
    public void enablePanelizer(String panelizerName) {
        Utilities.logInfoMessage("Delete panelizer: " + panelizerName);
        getActionsCell(panelizerName).element().findElement(By.xpath(ACTION_LINK)).click();
        getActionsCell(panelizerName).clickLinkByName(Operations.ENABLE.getValue());
    }

    /**
     * Method to get Action cell for particular panelizer name
     *
     * @param panelizerName - name of pre-setup panelizer of each action cell will be return
     * @return
     */
    private TableCell getActionsCell(String panelizerName) {
        Utilities.logInfoMessage("Get Cell for panelizer: " + panelizerName + " with action links");
        TableRow row = table.getRowByTextInColumn(panelizerName, Columns.TITLE.toString());
        return row.getCellByColumnTitle(Columns.OPERATIONS.toString());
    }

    public void inputSearch(String searchCriteria) {
        Utilities.logInfoMessage("Search panelizer by criteria: " + searchCriteria);
        search.enterText(searchCriteria);
    }

    public boolean isPanelizerExist(String panelizerName) {
        Utilities.logInfoMessage("Check rather panelizer :" + panelizerName + " is present at the list");
        List<String> panelizers = table.getValuesFromColumn(Columns.TITLE.toString());
        return panelizers.contains(panelizerName);
    }

    public boolean isAddPanelizerLinkExist() {
        Utilities.logInfoMessage("Check rather Add panelizer link is present");
        return addLink.isPresent() && addLink.isEnable();
    }

    public boolean isImportPanelizerLinkExist() {
        Utilities.logInfoMessage("Check rather Import panelizer link is present");
        return importLink.isPresent() && importLink.isEnable();
    }

    private enum Columns {
        TITLE, NAME, STORAGE, OPERATIONS
    }

    private enum Operations {
        SETTINGS("Settings"), CONTEXT("Context"), LAYOUT("Layout"),
        CONTENT("Content"), ACCESS("Access"), DISABLE("Disable"),
        ENABLE("Enable"), CLONE("Clone"), DELETE("Delete"), EXPORT("Export");

        private String value;

        Operations(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    private enum Storage {
        NORMAL, DEFAULT, OVERRIDDEN
    }

    private enum Enable {
        ENABLED, DISABLED
    }

    private enum Order {
        DOWN, UP
    }

    private enum Sort {
        ENABLE_TITLE("Enabled, title"), TITLE("Title"), NAME("Name"), STORAGE("Storage");

        private String value;

        Sort(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
