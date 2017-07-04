package com.nbcuni.test.cms.backend.tvecms.pages.permission;

import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.tvecms.Role;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Alena_Aukhukova on 9/7/2015.
 */
public class PermissionRolesPage extends Page {

    private static final String TABLE_LINE_XPATH = ".//table[@id='user-roles']//tr[contains(@name,'draggable')]";
    private static final String LINE_NAME_XPATH = ".//td[1]";
    private static final String LINE_OPERATION_XPATH = ".//td//a[text()='edit permissions']";

    public PermissionRolesPage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    public List<String> verifyPage() {
        Utilities.logSevereMessage("Skip checking of page");
        return null;
    }

    public PermissionEditPage clickRoleEditPermissions(Role role) {
        String roleName = role.getName();
        Utilities.logInfoMessage("Click edit for role: " + roleName);
        List<WebElement> rows = webDriver.findElements(By.xpath(TABLE_LINE_XPATH));
        for (WebElement row : rows) {
            if (row.findElement(By.xpath(LINE_NAME_XPATH)).getText().equalsIgnoreCase(roleName)) {
                row.findElement(By.xpath(LINE_OPERATION_XPATH)).click();
                return new PermissionEditPage(webDriver, aid);
            }
        }
        throw new TestRuntimeException("The row with role: " + roleName + " is not found");
    }

}
