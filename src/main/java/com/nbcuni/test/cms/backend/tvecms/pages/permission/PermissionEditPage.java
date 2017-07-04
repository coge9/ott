package com.nbcuni.test.cms.backend.tvecms.pages.permission;

import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;

import java.util.List;

/**
 * Created by Alena_Aukhukova on 9/8/2015.
 */
public class PermissionEditPage extends Page {

    public PermissionEditPage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    public List<String> verifyPage() {
        Utilities.logSevereMessage("Skip checking of page");
        return null;
    }

}
