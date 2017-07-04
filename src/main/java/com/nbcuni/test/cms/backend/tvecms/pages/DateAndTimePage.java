package com.nbcuni.test.cms.backend.tvecms.pages;

import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateAndTimePage extends Page {

    private static String ISO_8601_FORMAT_DROP_DOWN = "//select[@id='edit-date-format-html5-tools-iso8601']";
    private DateFormat isoFormat = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss");

    public DateAndTimePage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public String getIsoDateStringValue() {
        return webDriver.getSelectedValueFromDropdown(ISO_8601_FORMAT_DROP_DOWN);
    }

    public Date getDateFromIsoString() {
        Date date = null;
        try {
            date = isoFormat.parse(getIsoDateStringValue());
        } catch (ParseException e) {
            Utilities.logSevereMessageThenFail("There were an error during parsing date");
        }
        return date;
    }

    @Override
    public List<String> verifyPage() {

        return null;
    }

}
