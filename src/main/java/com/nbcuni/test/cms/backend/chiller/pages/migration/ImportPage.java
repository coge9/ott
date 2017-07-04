package com.nbcuni.test.cms.backend.chiller.pages.migration;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableRow;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Aleksandra_Lishaeva on 5/17/16.
 */
public class ImportPage extends MainRokuAdminPage {

    private String CHECKBOX_XPATH = ".//*[contains(@id,'edit-migrations')]";

    @FindBy(xpath = "//table[contains(@class,'sticky-table')]")
    private Table table;

    @FindBy(xpath = ".//thead//th[1]")
    private CheckBox selectAll;

    @FindBy(id = "edit-operation")
    private DropDownList operations;

    @FindBy(id = "edit-submit")
    private Button execute;

    public ImportPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public void selectAllTasks() {
        selectAll.check();
    }

    private void checkTaskForImport(Task task) {
        TableRow row = table.getRowByTextInColumn(task.getTask(), Columns.TASK.toString());
        row.getCellByIndex(1).getInnerElementInCell(By.xpath(CHECKBOX_XPATH)).click();
    }

    public void performImport() {
        operations.selectFromDropDown("Import");
        execute.clickWithProgressBarWait();
    }

    public int getImportResults(Task task) {
        TableRow row = table.getRowByTextInColumn(task.getTask(), Columns.TASK.toString());
        return Integer.parseInt(row.getCellByColumnTitle(Columns.IMPORTED.toString()).getCellInnerText());
    }

    public void importTask(Task task) {
        checkTaskForImport(task);
        performImport();
    }

    private enum Columns {
        TASK, STATUS, ITEMS, IMPORTED, UNPROCESSED, MESSAGES, THROUGHPUT
    }

    public enum Task {
        ALL(""),
        ROLE("Role"),
        USER("User"),
        MEDIA_GALLERY_NODE("MediaGalleryNode");

        private String task;

        Task(String task) {
            this.task = task;
        }

        public String getTask() {
            return this.task;
        }
    }
}
