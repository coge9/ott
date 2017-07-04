package com.nbcuni.test.cms.backend;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.*;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * Created by alekca on 20.06.2016.
 */
public abstract class ContentAbstractPage extends MainRokuAdminPage {

    @FindBy(id = "edit-title")
    protected TextField title;

    @FindBy(id = "edit-mpx-video-id")
    protected TextField mpxId;

    @FindBy(id = "edit-type")
    protected DropDownList contentType;

    @FindBy(id = "edit-status")
    protected DropDownList publishStatus;

    @FindBy(id = "edit-field-mpx-series-title-value")
    protected TextField programName;

    @FindBy(xpath = ".//table[contains(@class,'views-table')]")
    protected Table table;

    @FindBy(xpath = "//table//input[contains(@class,'vbo-table-select-all-pages')]")
    private Button selectAllItems;

    @FindBy(xpath = ".//*[contains(@class,'sticky-enabled')]/thead//input")
    private CheckBox checkAllItems;

    @FindBy(xpath = ".//*[@id='console']/div")
    private Label statusMessage;


    public ContentAbstractPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public abstract boolean apply();

    /**
     * Search by title
     *
     * @param value - title to search
     * @return - page for Content
     */
    public ContentAbstractPage searchByTitle(final String value) {
        Utilities.logInfoMessage("Search By Title [" + value + "]");
        title.enterText(value);
        return this;
    }

    /**
     * Available options:<br>
     * - Any -
     * Character Profile<br>
     * Dynamic Lead Template<br>
     * FAQ<br>
     * Filtered Asset List Module<br>
     * Media Gallery<br>
     * Movie<br>
     * Person<br>
     * Promo<br>
     * TV Episode<br>
     * TV Season<br>
     * TV Show<br>
     *
     * Set isSearch type to type
     *
     * @param type - Content type entity for searching.
     * @return - page for content editing.
     */
    public ContentAbstractPage searchByType(final ContentType type) {
        contentType.selectFromDropDown(type.get());
        return this;
    }

    /**
     * Available options:<br>
     * - Any -<br>
     * Yes<br>
     * No<br>
     *
     * @param state - PublishState enum constnat to search.
     * @return - Content page.
     */
    public ContentAbstractPage searchByPublishedState(PublishState state) {
        publishStatus.selectFromDropDown(state.getStateValue());
        return this;
    }

    /**
     * Available for Video content only
     * Sort content By Program related value
     *
     * @param programName - name of program related to the content (videos only)
     * @return - instance of the page
     */
    public ContentAbstractPage searchByRelatedProgram(String programName) {
        this.programName.enterText(programName);
        return this;
    }

    public boolean isContentPresent() {
        return table.isPresent() && table.getNumberOfRows() > 0;
    }

    public void clickSelectAllItemsButton() {
        Utilities.logInfoMessage("Click on button select all items");
        selectAllItems.click();
    }

    public String getItemsNumber() {
        Utilities.logInfoMessage("Click on button select all items");

        return SimpleUtils.getNumbersFromString(selectAllItems.getValue());
    }

    public String getContentState(final String title) {
        return table.getRowByTextInColumn("Title", title).getCellByColumnTitle("STATUS").getCellInnerText();
    }

    public String getFullTitleOfFirstElement() {
        Utilities.logInfoMessage("Get Asset's title");
        return table.getCellTextByRowIndex(1, "TITLE");
    }

    public void checkAllItemsOnThePage() {
        Utilities.logInfoMessage("Select all items per the current content page");
        checkAllItems.check();
    }

    @Override
    public String getStatusMessage() {
        if (statusMessage.isVisible()) {
            return statusMessage.getText();
        }
        throw new TestRuntimeException("The status message is not displayed");
    }

    public String getNodeUpdatedDate(int rowIndex) {
        return table.getCellTextByRowIndex(rowIndex, 6);
    }

    public String getNodePublishState(int rowIndex) {
        return table.getCellTextByRowIndex(rowIndex, 5);
    }

    /**
     * Numbering starts at 1
     *
     * @param index - index of the element.
     * @return -  title of element.
     */
    public String getFullTitleOfElement(int index) {
        Utilities.logInfoMessage("Get element number: " + index);
        if (!table.getRows().isEmpty() && table.getRowByIndex(index).isEnable()) {
            WebElement element = table.getRowByIndex(index).getCellByColumnTitle("TITLE").geCellElement().findElement(By.xpath(".//a"));
            return WebDriverUtil.getInstance(webDriver).getText(element);
        } else {
            Utilities.logSevereMessageThenFail("Content type not found.");
            return null;
        }
    }

}
