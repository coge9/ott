package com.nbcuni.test.cms.backend.tvecms.pages;

import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.MultiSelect;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableRow;
import com.nbcuni.test.cms.pageobjectutils.tvecms.HeaderStyle;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OTTHeaderImageGenerationPage extends MainRokuAdminPage {

    private static final String TABLE_XPATH = "//table";

    @FindBy(xpath = "//select[contains(@id,'edit-app')]")
    private DropDownList selectApplication;

    @FindBy(xpath = "//select[contains(@id,'edit-page')]")
    private DropDownList selectPage;

    @FindBy(xpath = "//select[contains(@id,'edit-template')]")
    private DropDownList selectHeaderStyle;

    @FindBy(id = "edit_mvpd_chosen")
    private MultiSelect selectMvpd;

    @FindBy(id = "edit-submit")
    private Button submit;

    @FindBy(xpath = "//table")
    private Table headersTable;

    public OTTHeaderImageGenerationPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public DropDownList elementSelectApplication() {
        return selectApplication;
    }

    public DropDownList elementSelectPage() {
        return selectPage;
    }

    public Table elemenHeadersTable() {
        return headersTable;
    }

    public DropDownList elementSelectHeaderStyle() {
        return selectHeaderStyle;
    }

    public MultiSelect elementSelectMvpd() {
        selectMvpd.clearSelection();
        return selectMvpd;
    }

    public List<String> getListOfMVPDs() {
        return selectMvpd.getListOfOptions();
    }

    public Button elementSubmit() {
        return submit;
    }

    public String getOttPageHeaderByIndex(int index, String separator) {
        TableRow row = headersTable.getRowByIndex(index);
        String name = row.getCellByIndex(1).getCellInnerText();
        String url = row.getCellByIndex(1).getCellInnerText();
        return name + separator + url;
    }

    public Map<String, String> getOttPageHeaders() {
        Map<String, String> headers = new HashMap<>();
        WaitUtils.perform(webDriver).waitElementPresent(TABLE_XPATH);
        int headersNumber = headersTable.getNumberOfRows();
        for (int i = 1; i <= headersNumber; i++) {
            TableRow row = headersTable.getRowByIndex(i);
            String id = row.getCellByIndex(1).getCellInnerText();
            String url = row.getCellByIndex(3).getCellInnerText();
            if (!id.equals("") && !url.equals("")) {
                id = id.trim();
                id = id.replace(":", "");
                url = url.trim();
                headers.put(id, url);
            }
        }
        return headers;
    }

    public boolean areAnyDuplicatesInHeaders() {
        boolean areDupclicates = false;
        Map<String, String> headers = new HashMap<>();
        WaitUtils.perform(webDriver).waitElementPresent(TABLE_XPATH);
        int headersNumber = headersTable.getNumberOfRows();
        for (int i = 1; i <= headersNumber; i++) {
            TableRow row = headersTable.getRowByIndex(i);
            String name = row.getCellByIndex(1).getCellInnerText();
            String url = row.getCellByIndex(1).getCellInnerText();
            if (!name.equals("") && !url.equals("")) {
                if (headers.containsKey(name)) {
                    if (headers.get(name).equals(url)) {
                        areDupclicates = true;
                    }
                } else {
                    headers.put(name, url);
                }
            }
        }
        return areDupclicates;
    }


    public Map<String, String> getOttPageHeaders(String pageTitle) {
        selectPage.selectFromDropDown(pageTitle);
        WaitUtils.perform(webDriver).waitForThrobberNotPresent(10);
        return getOttPageHeaders();
    }

    public List<String> getOttPageHeaders(String pageTitle, List<String> ids) {
        selectPage.selectFromDropDown(pageTitle);
        WaitUtils.perform(webDriver).waitForThrobberNotPresent(10);
        Map<String, String> headers = getOttPageHeaders();
        List<String> headerUrl = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            headerUrl.add(headers.get(ids.get(i)));
        }
        return headerUrl;
    }

    public void generateOttPageHeaders(String pageTitle, HeaderStyle headerStyle, String mvpdName) {
        selectPage.selectFromDropDown(pageTitle);
        WaitUtils.perform(webDriver).waitForThrobberNotPresent(10);
        selectHeaderStyle.selectFromDropDown(headerStyle.get());
        WaitUtils.perform(webDriver).waitForThrobberNotPresent(10);
        this.elementSelectMvpd().select(mvpdName);
        submit.clickWithProgressBarWait(500);
    }

    public void generateOttPageHeaders(String pageTitle, HeaderStyle headerStyle, List<String> mvpdName) {
        selectPage.selectFromDropDown(pageTitle);
        WaitUtils.perform(webDriver).waitForThrobberNotPresent(10);
        selectHeaderStyle.selectFromDropDown(headerStyle.get());
        WaitUtils.perform(webDriver).waitForThrobberNotPresent(10);
        this.elementSelectMvpd().select(mvpdName);
        submit.clickWithProgressBarWait(500);
    }
}
