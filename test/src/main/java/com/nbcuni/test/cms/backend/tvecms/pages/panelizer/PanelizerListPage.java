package com.nbcuni.test.cms.backend.tvecms.pages.panelizer;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableCell;
import com.nbcuni.test.cms.elements.table.TableRow;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PanelizerListPage extends MainRokuAdminPage {

    private final String PANELIZE_CHECKBOX_XPATH = ".//*[@id='edit-entities-ott-page-ott-page-default-status']";
    private WebElement checkBoxElement;
    @FindBy(xpath = "//table[contains(@class,'sticky-table')][2]")
    private Table tablePage;
    private Label tableIdentificator = new Label(webDriver, By.xpath("//table[contains(@class,'sticky-table')]//td/strong[contains(text(),'Page')]"));
    private Button save = new Button(webDriver, By.id("edit-submit"));
    private CheckBox checkBox = new CheckBox(webDriver, checkBoxElement);

    public PanelizerListPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public void save() {
        Utilities.logInfoMessage("Click Save");
        save.click();
    }

    /***
     * Method to add Panelizer to the OTT Page list
     *
     * @param panelizerName - name of panelizer each action will be performed
     * @param status        - rather checkbox 'Panelize' should be checked(true) or unchecked(false) for OTT Pages
     *
     * @return PanelizerListPage
     */
    public PanelizerListPage checkPanelize(String panelizerName, boolean status) {
        Utilities.logInfoMessage("Check Panalize settings for OTT Page of panelizer: " + panelizerName);
        checkBoxElement = getPanelizeCell(panelizerName).geCellElement().findElement(By.xpath(PANELIZE_CHECKBOX_XPATH));
        if (status) {
            checkBox.check();
        } else checkBox.uncheck();
        return this;
    }

    /**
     * Method to get Panelize cell for particular panelizer name
     *
     * @param panelizerName - name of pre-setup panelizer of each action cell will be return
     * @return TableCell
     */
    private TableCell getPanelizeCell(String panelizerName) {
        Utilities.logInfoMessage("Get panelize Cell for panelizer: " + panelizerName + " with checkbox");
        TableRow row = tablePage.getRowByTextInColumn(panelizerName, Columns.OTT_PAGE_TYPE.toString());
        return row.getCellByColumnTitle(Columns.PANELIZE.toString());
    }

    public boolean isOTTPageTablePresent() {
        return tableIdentificator.isPresent();
    }

    public boolean isPanelizerPresentAtTheList(String panelizerName) {
        Utilities.logInfoMessage("Check rather panelizer: " + panelizerName + " is present");
        List<String> panelizers = tablePage.getValuesFromColumn(Columns.OTT_PAGE_TYPE.getValue());
        return panelizers.contains(panelizerName);
    }

    private enum Columns {
        OTT_PAGE_TYPE("OTT Page type"), PANELIZE("PANELIZE"), PROVIDE_DEFAULT_PANEL("Provide default panel"), ALLOW_PANEL_CHOICE("Allow panel choice"), OPERATIONS("OPERATIONS");
        private String value;

        Columns(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
