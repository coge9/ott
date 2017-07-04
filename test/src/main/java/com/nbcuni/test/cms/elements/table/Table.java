package com.nbcuni.test.cms.elements.table;

import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Table extends AbstractContainer {

    //columns names
    public static final String COLUMN_TITLE = "Title";
    private static final String rowLocator = ".//tbody//tr[not(contains(@class,'views-table-row-select-all'))]";
    private static final String enabledRowLocator = ".//tbody//tr[not(contains(@class,'disabled'))]";
    private static final String cellLocator = ".//td[%s]";
    private static final String headerCellLocator = ".//thead//th";
    @FindBy(xpath = "//html")
    private Pager pager;
    @FindBy(xpath = ".//thead")
    private TableRow header;

    public Pager getPager() {
        return pager;
    }

    public TableRow elementHeader() {
        return header;
    }

    public int getNumberOfRows() {
        return getElement().findElements(By.xpath(rowLocator)).size();
    }

    /**
     * Numbering starts at 1
     * @param rowIndex - index of row
     * @return TableRow object.
     */
    public TableRow getRowByIndex(int rowIndex) {
        Utilities.logInfoMessage("Getting row " + rowIndex + " from table " + getName());
        List<WebElement> rows = getElement().findElements(By.xpath(rowLocator));
        TableRow row = null;
        try {
            row = new TableRow(getWebDriver(), rows.get(rowIndex - 1));
        } catch (Exception e) {
            Utilities.logSevereMessageThenFail("Unable to get row " + rowIndex + " " + Utilities.convertStackTraceToString(e));
        }
        row.setParentTable(this);
        return row;
    }

    public TableRow getRandomRow() {
        Utilities.logInfoMessage("Getting random row from table " + getName());
        List<WebElement> rows = getElement().findElements(By.xpath(rowLocator));
        int number = random.nextInt(rows.size());
        return getRowByIndex(number + 1);
    }

    public TableRow getRandomRowExcept(int rowIndex) {
        Utilities.logInfoMessage("Getting random row except " + rowIndex + " from table "
                + getName());
        List<WebElement> rows = getElement().findElements(By.xpath(rowLocator));
        if (rows.size() == 1) {
            return getRowByIndex(1);
        }
        int number = rowIndex - 1;
        while (number == rowIndex - 1) {
            number = random.nextInt(rows.size());
        }
        return getRowByIndex(number);
    }

    public TableRow getRowByTextInColumn(String text, int columnIndex) {
        Utilities.logInfoMessage("Getting row with text " + text + " in " + columnIndex
                + " column");
        List<WebElement> rows = getElement().findElements(By.xpath(rowLocator));
        for (WebElement row : rows) {
            WebElement cell = row.findElement(By.xpath(String.format(
                    cellLocator, columnIndex)));
            if (cell.getText().trim().equals(text)) {
                return new TableRow(getWebDriver(), row, this);
            }
        }
        Utilities.logSevereMessage("Row with element " + text + " in " + columnIndex
                + " is not found");
        return null;
    }

    public List<TableRow> getRowsByTextInColumn(String text, int columnIndex) {
        Utilities.logInfoMessage("Getting row with text " + text + " in " + columnIndex
                + " column");
        List<TableRow> tableRows = new ArrayList<>();
        List<WebElement> rows = getElement().findElements(By.xpath(rowLocator));
        for (WebElement row : rows) {
            WebElement cell = row.findElement(By.xpath(String.format(
                    cellLocator, columnIndex)));
            if (cell.getText().trim().equals(text)) {
                tableRows.add(new TableRow(getWebDriver(), row, this));
            }
        }
        if (tableRows.isEmpty()) {
            Utilities.logSevereMessage("Row with element " + text + " in " + columnIndex
                    + " column is not found");
        }
        return tableRows;
    }

    public TableRow getRowByTextInColumnContains(String text, int columnIndex) {
        Utilities.logInfoMessage("Getting row with text " + text + " in " + columnIndex
                + " column");
        List<WebElement> rows = getElement().findElements(By.xpath(rowLocator));
        for (WebElement row : rows) {
            WebElement cell = row.findElement(By.xpath(String.format(
                    cellLocator, columnIndex)));
            if (cell.getText().trim().contains(text)) {
                return new TableRow(getWebDriver(), row);
            }
        }
        Utilities.logSevereMessage("Row with element " + text + " in " + columnIndex
                + " is not found");
        return null;
    }

    public TableRow getRowByTextInColumn(String text, String columnTitle) {
        return getRowByTextInColumn(text,
                getColumnIndexByColumnTitle(columnTitle));
    }

    public List<TableRow> getRowsByTextInColumn(String text, String columnTitle) {
        return getRowsByTextInColumn(text,
                getColumnIndexByColumnTitle(columnTitle));
    }

    public TableRow getRowByTextInColumnContains(String text, String columnTitle) {
        return getRowByTextInColumnContains(text,
                getColumnIndexByColumnTitle(columnTitle));
    }

    public TableRow getRowByTextInColumnAllTable(String text, int columnIndex) {
        if (!pager.isPagingPresent()) {
            return getRowByTextInColumn(text, columnIndex);
        } else {
            pager.goToFirstPage();
            while (!pager.isLastPage()) {
                TableRow row = getRowByTextInColumn(text, columnIndex);
                if (row != null) {
                    return row;
                } else {
                    pager.clickNextLink();
                }
            }
        }
        return null;
    }

    public List<String> getValuesFromColumn(int columnIndex) {
        List<String> values = new ArrayList<String>();
        Utilities.logInfoMessage("Getting values from " + columnIndex + " column");
        List<WebElement> rows = getElement().findElements(By.xpath(rowLocator));
        for (WebElement row : rows) {
            WebElement cell = row.findElement(By.xpath(String.format(
                    cellLocator, columnIndex)));
            values.add(cell.getText().trim());
        }
        return values;
    }

    public List<String> getValuesFromColumn(String columnTitle) {
        return getValuesFromColumn(getColumnIndexByColumnTitle(columnTitle));
    }

    public List<String> getColumnValuesFromTable(List<TableRow> rows, String columnTitle) {
        List<String> results = new ArrayList<>();
        for (TableRow row : rows) {
            results.add(row.getCellByColumnTitle(columnTitle).getCellInnerText());
        }
        return results;
    }

    private int getColumnIndexByColumnTitle(String columnTitle) {
        List<WebElement> headerCells = getElement().findElements(
                By.xpath(headerCellLocator));
        for (int i = 1; i <= headerCells.size(); i++) {
            WebElement headerCell = headerCells.get(i - 1);
            if (headerCell.isDisplayed()
                    && headerCell.getText().trim().toLowerCase()
                    .equals(columnTitle.trim().toLowerCase())) {
                return i;
            }
        }
        throw new RuntimeException("Unble to get column index by title "
                + columnTitle);
    }

    public WebElement getCellByRowIndex(int rowIndex, int Column) {
        TableCell cell = getRowByIndex(rowIndex).getCellByIndex(Column);
        return cell.geCellElement();
    }

    public TableCell getCellByTextInRowAndColumnTitle(String text, String column) {
        return getRowByTextInColumn(text, column).getCellByColumnTitle(column);
    }

    public String getCellTextByRowIndex(int rowIndex, int Column) {
        TableCell cell = getRowByIndex(rowIndex).getCellByIndex(Column);
        return cell.getCellInnerText();
    }

    public String getCellTextByRowIndex(int rowIndex, String columnText) {
        TableCell cell = getRowByIndex(rowIndex).getCellByColumnTitle(columnText);
        return cell.getCellInnerText();
    }

    public List<TableRow> getRows() {
        Utilities.logInfoMessage("Getting all rows from table " + getName());
        return getTableAsTableRows(rowLocator);
    }

    public List<TableRow> getEnabledRows() {
        Utilities.logInfoMessage("Getting enabled rows from table [" + getName() + "] on current page");
        return getTableAsTableRows(enabledRowLocator);
    }

    public List<TableRow> sortByTextInColumnContains(List<TableRow> rows, String columnTitle, String textValue) {
        List<TableRow> sortedRows = new ArrayList<>();
        for (TableRow row : rows) {
            if (row.getCellByColumnTitle(columnTitle).getCellInnerText().contains(textValue)) {
                sortedRows.add(row);
            }
        }
        return sortedRows;
    }

    private List<TableRow> getTableAsTableRows(String locator) {
        List<TableRow> tableRows = new LinkedList<>();
        try {
            WaitUtils.perform(getWebDriver()).waitElementPresent(locator, 4);
            List<WebElement> rows = getElement().findElements(By.xpath(locator));
            for (WebElement row : rows) {
                tableRows.add(new TableRow(getWebDriver(), row, this));
            }
        } catch (TimeoutException e) {
            Utilities.logInfoMessage("There are 0 rows in the table.");
        }
        return tableRows;
    }


}
