package com.nbcuni.test.cms.backend.tvecms.pages.module.tabs;

import com.nbcuni.test.cms.backend.tvecms.pages.module.MainModulePage;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ViewPublishTab extends MainModulePage {

    private Label title = new Label(webDriver, "//div[contains(@class,'form-type-item')][1]");
    private Label tile = new Label(webDriver, "//div[contains(@class,'field-type-list-text')]//div[contains(@class,'field-item even')]");
    private Label listItems = new Label(webDriver, "//div[contains(@class,'field-name-field-ott-module-list')]//div[@class='item-list']//li");
    private Label type = new Label(webDriver, "//div[contains(@class,'form-type-item')][2]");

    @FindBy(xpath = ".//*[@id='block-system-main']//table[2]")
    private Table table;

    public ViewPublishTab(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public String getTitle() {
        Utilities.logInfoMessage("Get Shelf title text");
        return title.getText();
    }

    public String getTile() {
        Utilities.logInfoMessage("Get tile text");
        return tile.getText();
    }

    public List<String> getListItems() {
        Utilities.logInfoMessage("Get Names of assets in the list");
        List<String> assets = new ArrayList<>();
        List<WebElement> items = listItems.elements();
        for (WebElement item : items) {
            assets.add(item.getText());
        }
        return assets;
    }

    public String getShelfType() {
        Utilities.logInfoMessage("Get Type of shelf");
        return type.getText();
    }

    public List<String> getAssets() {
        Utilities.logInfoMessage("Get List Of assets to be displayed on View Page");
        return table.getValuesFromColumn(Columns.TITLE.getValue());
    }

    @Override
    public List<String> verifyPage() {
        Utilities.logInfoMessage("Verify page " + getPageUrl() + " for missed elements");
        final ArrayList<String> missedElements = new ArrayList<>();
        final Class<?> thisClass = this.getClass();
        final Field[] fields = thisClass.getDeclaredFields();
        for (final Field field : fields) {
            try {
                table.isPresent();
                missedElements.add("Element: table is missed");
                WebDriverUtil.getInstance(webDriver).attachScreenshot();
                missedElements.trimToSize();
            } catch (final NullPointerException e) {
                Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
            } catch (final Exception e) {
                Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
            }
        }
        return missedElements;
    }

    private enum Columns {
        TITLE("TITLE"), SERIES("SERIES"), SHOW_LATEST("SHOW LATEST");
        private String value;

        Columns(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
