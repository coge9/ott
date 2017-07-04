package com.nbcuni.test.cms.elements.table;

import com.nbcuni.test.cms.elements.AbstractElement;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TableRow extends AbstractElement {

    private static final String cellLocator = ".//td[%s]";
    private static final String headerCellLocator = ".//thead//th";
    private Table parentTable;

    public TableRow(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public TableRow(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public TableRow(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public TableRow(WebElement webElement) {
        super(webElement);
    }

    public TableRow(CustomWebDriver driver, WebElement element, Table table) {
        super(driver, element);
        parentTable = table;
    }

    public TableRow(AbstractElement parentElement, String xPath) {
        super(parentElement, xPath);
    }

    public TableRow(AbstractElement parentElement, By byLocator) {
        super(parentElement, byLocator);
    }

    public void setParentTable(Table table) {
        parentTable = table;
    }

    public TableCell getCellByIndex(int index) {
        WaitUtils.perform(driver).waitElementPresent(String.format(cellLocator, index));
        WebElement cellElement = element().findElement(
                By.xpath(String.format(cellLocator, index)));
        return new TableCell(driver, cellElement);
    }

    public void clickOnRow() {
        element().click();
    }

    public TableCell getCellByColumnTitle(String columnTitle) {
        if (parentTable == null) {
            throw new RuntimeException("Parent table is not defined for this row. Method can't be called.");
        }
        List<WebElement> headerCells = parentTable.getElement().findElements(By.xpath(headerCellLocator));
        for (int i = 1; i <= headerCells.size(); i++) {
            WebElement headerCell = headerCells.get(i - 1);
            if (headerCell.isDisplayed() && headerCell.getText().trim().toLowerCase().equals(columnTitle.trim().toLowerCase())) {
                return getCellByIndex(i);
            }
        }
        return null;
    }


}
