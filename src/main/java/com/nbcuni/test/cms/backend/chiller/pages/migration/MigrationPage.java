package com.nbcuni.test.cms.backend.chiller.pages.migration;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableCell;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Aleksandra_Lishaeva on 5/17/16.
 */
public class MigrationPage extends MainRokuAdminPage {

    @FindBy(xpath = "//table[contains(@class,'sticky-table')]")
    protected Table table;

    public MigrationPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public ImportPage selectGroupToImport(Groups groups) {
        if (table.getValuesFromColumn(Columns.GROUP.toString()).contains(groups.getGroup())) {
            TableCell cell = table.getCellByTextInRowAndColumnTitle(groups.getGroup(), Columns.GROUP.toString());
            cell.clickLinkByName(groups.getGroup());
            WaitUtils.perform(webDriver).waitForPageLoad();
        }
        return new ImportPage(webDriver, aid);
    }

    private enum Columns {
        GROUP, STATUS
    }

    public enum Groups {
        USER("User migrations"),
        NODE("Node migrations"),
        TAXONOMY("Taxonomy Term migrations");

        private String group;

        Groups(String group) {
            this.group = group;
        }

        public String getGroup() {
            return this.group;
        }
    }
}
