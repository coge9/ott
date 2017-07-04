package com.nbcuni.test.cms.backend.tvecms.pages;

import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;

import java.util.List;

public class SiteInformationPage extends Page {

    // SITE DETAILS
    private static String SITE_NAME_FIELD = "//input[@id='edit-site-name']";
    private static String BRAND_NAME_FIELD = "//input[@id='edit-site-slogan']";

    public SiteInformationPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public String getSiteName() {
        return webDriver.getAttribute(SITE_NAME_FIELD,
                HtmlAttributes.VALUE.get());
    }

    public String getBrandName() {
        return webDriver.getAttribute(BRAND_NAME_FIELD,
                HtmlAttributes.VALUE.get());
    }

    @Override
    public List<String> verifyPage() {
        return null;
    }

}
