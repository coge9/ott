package com.nbcuni.test.cms.backend.theplatform;

import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SikuliUtil;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.sikuli.script.Screen;

import java.util.List;

/*********************************************
 * publisher.nbcuni.com MPXLogin Library. Copyright
 *
 * @author Brandon Clark
 * @version 1.0 Date: January 16, 2014
 *********************************************/

public class MPXLoginControl extends Page {
    private final String LOGIN_FIELD_XPATH = ".//div[contains(@class,'tp-login-username')]//input[contains(@class,'tp-string-input-control-input')]";
    private final String PASSWORD_FIELD_XPATH = ".//div[contains(@class,'tp-login-password')]//input[contains(@class,'tp-string-input-control-input')]";
    private final String SUBMIT_BUTTON_XPATH = ".//button[contains(text(),'SIGN IN')]";
    private final String FORM_XPATH = ".//div[@class='modal-content']";

    private final SikuliUtil util;

    public MPXLoginControl(CustomWebDriver webDriver, AppLib aid, final Config config) {
        super(webDriver, aid);
        util = new SikuliUtil(config, new Screen());

    }

    @Override
    public List<String> verifyPage() {
        return null;
    }

    public void login(final String username, final String password) {
        try {
            WaitUtils.perform(webDriver).waitElementPresent(FORM_XPATH, 15);
            Utilities.logInfoMessage("Enter '" + username + "' in the 'Username' text box");
            webDriver.type(LOGIN_FIELD_XPATH, username);

            Utilities.logInfoMessage("Enter '" + password + "' in the 'Password' text box");
            webDriver.type(PASSWORD_FIELD_XPATH, password);

            Utilities.logInfoMessage("Click the 'Login' button");
            webDriver.click(SUBMIT_BUTTON_XPATH);
            util.waitForAllAssetsToLoad();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
            }
        } catch (Exception e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
        }

    }

    public void signOut() {
        Utilities.logInfoMessage("Click the 'Sign Out' button");

    }
}
