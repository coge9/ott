package com.nbcuni.test.cms.backend.tvecms.pages.people;

import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableRow;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by Alena_Aukhukova on 9/9/2015.
 */


public class PeoplePage extends Page {

    private static final String USER_NAME = "USERNAME";
    private static final String USER_ROLE = "ROLES";
    private static final String USER_STATUS = "STATUS";
    private static final String OPERATIONS = "OPERATIONS";
    private static final String EDIT_LINK = "edit";

    @FindBy(xpath = "//table[contains(@class,'sticky-table')]")
    private Table peopleTable;

    public PeoplePage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    public List<String> verifyPage() {
        Utilities.logSevereMessage("Skip checking of page");
        return null;
    }

    public List<String> getUserRoles(String userName) {
        TableRow row = peopleTable.getRowByTextInColumn(userName, USER_NAME);
        return row.getCellByColumnTitle(USER_ROLE).getCellListOfInnerText();
    }

    public String getUserStatus(String userName) {
        TableRow row = peopleTable.getRowByTextInColumn(userName, USER_NAME);
        return row.getCellByColumnTitle(USER_STATUS).getCellInnerText();
    }

    public PeopleEditRolePage openEditUserRolePage(String userName) {
        TableRow row = peopleTable.getRowByTextInColumn(userName, USER_NAME);
        row.getCellByColumnTitle(OPERATIONS).clickLinkByName(EDIT_LINK);
        return new PeopleEditRolePage(webDriver, aid);

    }

    public boolean isUserPresent(String userName) {
        return peopleTable.getValuesFromColumn(USER_NAME).contains(userName);
    }


}
