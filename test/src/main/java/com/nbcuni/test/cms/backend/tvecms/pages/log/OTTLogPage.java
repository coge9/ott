package com.nbcuni.test.cms.backend.tvecms.pages.log;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableCell;
import com.nbcuni.test.cms.elements.table.TableRow;
import com.nbcuni.test.cms.pageobjectutils.tvecms.LogTableColumns;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleksandra_lishaeva on 10/21/15.
 */
public class OTTLogPage extends MainRokuAdminPage {

    private static final String SESSION_ID_XPATH = ".//*[@id='edit-session-id']";
    private static final String APPLY_XPATH = ".//*[@id='edit-submit-ott-logs']";
    @FindBy(xpath = ".//table[contains(@class,'views-table')]")
    private Table table;

    public OTTLogPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public OTTLogPage setSessionId(String id) {
        webDriver.type(SESSION_ID_XPATH, id);
        return this;
    }

    public void apply() {
        webDriver.click(APPLY_XPATH);
    }

    public List<String> getListIdsPerSession(String sessionID) {
        setSessionId(sessionID).apply();
        return table.getValuesFromColumn(LogTableColumns.ID.getName());
    }

    public List<String> getIdsByAssetName(List<String> mpxIds) {
        List<String> assetId = new ArrayList<>();
        for (String id : mpxIds) {
            TableRow row = table.getRowByTextInColumnContains(id, LogTableColumns.RESPONSE_DATA.getName());
            TableCell cell = row.getCellByColumnTitle(LogTableColumns.ID.getName());
            assetId.add(cell.getCellInnerText());
        }
        return assetId;
    }

    public String getLatestSessionId() {
        return table.getRowByIndex(1).getCellByColumnTitle("Session Id").getCellInnerText();
    }
}
