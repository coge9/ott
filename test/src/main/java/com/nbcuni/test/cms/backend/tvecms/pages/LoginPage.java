package com.nbcuni.test.cms.backend.tvecms.pages;

import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LoginPage extends Page {
    public static final String PAGE_TITLE = " User account";
    private static final String ID_ADMIN_MENU_WRAPPER = ".//*[@id='admin-menu-wrapper']";
    private static final String HEADER_TITLE = ".//*[@id='site-name']//a";
    private static final String EMAIL_FIELD = "//*[@id='edit-name']";
    private static final String PASSWORD_FIELD = "//*[@id='edit-pass']";
    private static final String LOGIN_BUTTON = "//*[@id='edit-submit']";
    private static final String LOG_OUT_LINK = "user/logout";
    private final String brand;

    public LoginPage(final CustomWebDriver webDriver, final AppLib aid, final String brand) {
        super(webDriver, aid);
        this.brand = brand;
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + "user/");
        if (webDriver.findElements(By.xpath(HEADER_TITLE)).isEmpty()) {
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessageThenFail("Login page was not opened successfully");
            new TestRuntimeException("Login page was not opened successfully");
        }
        Utilities.logInfoMessage("User on login page: " + getPageUrl());
    }

    public LoginPage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
        Utilities.logInfoMessage("User on login page: " + getPageUrl());
        brand = null;
    }


    public void enterEmail(final String email) {
        webDriver.type(EMAIL_FIELD, email);
    }

    public void enterPassword(final String password) {
        webDriver.type(PASSWORD_FIELD, password);
    }

    public void clickLoginButton() {
        webDriver.click(LOGIN_BUTTON);
    }

    public MainRokuAdminPage loginToBrand(final String email, final String password) {
        Utilities.logInfoMessage("User login with email: " + email);
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
        return new MainRokuAdminPage(webDriver, aid);
    }

    private MainRokuAdminPage login(String userName, String password, String message, final String... brandName) {
        Utilities.logInfoMessage(message);
        if (!WebDriverUtil.getInstance(webDriver).isElementPresent(ID_ADMIN_MENU_WRAPPER, 3)) {
            if (brand != null) {
                webDriver.get(Config.getInstance().getRokuHomePage(brand) + LOG_OUT_LINK);
                webDriver.get(Config.getInstance().getRokuHomePage(brand) + "user/");
            } else {
                webDriver.get(Config.getInstance().getRokuHomePage(brandName[0]) + LOG_OUT_LINK);
                webDriver.get(Config.getInstance().getRokuHomePage(brandName[0]) + "user/");
            }
            return loginToBrand(userName, password);
        }
        return new MainRokuAdminPage(webDriver, aid);
    }

    public MainRokuAdminPage loginAsSpecifiedUser(String userName, String password, final String... brandName) {
        String message = brand != null ? "Log in as " + userName + " to " + brand + " site" : "Log in as Admin to " + brandName[0] + " site";
        return login(userName, password, message, brandName);
    }

    public MainRokuAdminPage loginAsAdmin(final String... brandName) {
        String message = brand != null ? "Log in as Admin to " + brand + " site" : "Log in as Admin to " + brandName[0] + " site";
        return login(Config.getInstance().getProperty("roku.user.login"), Config.getInstance()
                .getProperty("roku.user.password"), message, brandName);
    }

    public MainRokuAdminPage loginAsSuperUser(final String... brandName) {
        String message = brand != null ? "Log in as SuperUser to " + brand + " site" : "Log in as SuperUser to " + brandName[0] + " site";
        return login(Config.getInstance().getProperty("roku.superuser.login"), Config.getInstance()
                .getProperty("roku.superuser.password"), message, brandName);
    }

    public MainRokuAdminPage loginAsEditor(final String... brandName) {
        if (!WebDriverUtil.getInstance(webDriver).isElementPresent(ID_ADMIN_MENU_WRAPPER, 3)) {
            openCMSSite(brandName);
            return loginToBrand(Config.getInstance().getProperty("roku.editor.login"), Config.getInstance()
                    .getProperty("roku.editor.password"));
        }
        return new MainRokuAdminPage(webDriver, aid);
    }

    public MainRokuAdminPage loginAsSeniorEditor(final String... brandName) {
        if (!WebDriverUtil.getInstance(webDriver).isElementPresent(ID_ADMIN_MENU_WRAPPER, 3)) {
            openCMSSite(brandName);
            return loginToBrand(Config.getInstance().getProperty("roku.senior.editor.login"), Config.getInstance()
                    .getProperty("roku.senior.editor.password"));
        }
        return new MainRokuAdminPage(webDriver, aid);
    }

    private void openCMSSite(final String... brandName) {
        String currentBrand = brand != null ? brand : brandName[0];
        webDriver.get(Config.getInstance().getRokuHomePage(currentBrand) + LOG_OUT_LINK);
        webDriver.get(Config.getInstance().getRokuHomePage(currentBrand) + "user/");
        Utilities.logInfoMessage("Log in as User to " + currentBrand + " site");
    }

    @Override
    public List<String> verifyPage() {
        Utilities.logInfoMessage("Verify page " + getPageUrl() + " for missed elements");
        final ArrayList<String> missedElements = new ArrayList<String>();
        final Class<?> thisClass = this.getClass();
        final Field[] fields = thisClass.getDeclaredFields();
        for (final Field field : fields) {
            try {
                if (field.getType().equals(String.class) && !field.getName().equals("PAGE_TITLE")
                        && !field.getName().equals("brand")) {
                    final String fieldLocator = field.get(this).toString();
                    if (!webDriver.isVisible(String.format(fieldLocator))) {
                        missedElements.add("Element:  " + field.getName() + " , Locator: " + fieldLocator);
                        missedElements.trimToSize();
                    }
                }
            } catch (final IllegalArgumentException e) {
                Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
            } catch (final IllegalAccessException e) {
                Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
            }
        }
        return missedElements;
    }
}