package com.nbcuni.test.cms.backend.tvecms.pages;

import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * ******************************************************************
 * Brand Admin. Configuation - Development - Performance page
 *
 * @version 1.0
 *
 * Date: Juny 10, 2014
 *
 * Revision Log---------------------------------------------------
 * Date- , Author- , Change Description-
 * *********************************************************************
 */

public class PerformanceSettingsPage extends Page {

    //Caching
    private static final String REPORT_CACHE_PURGES_CHECKBOX = "//input[@id='edit-acquia-purge-reportpurges']";
    private static final String CACHE_PAGES_FOR_ANONYM_CHECKBOX = "//input[@id='edit-cache']";
    private static final String CACHE_BLOCKS_CHECKBOX = "//input[@id='edit-block-cache']";
    //Bandwidth optimization
    private static final String AGGREGATE_CSS_CHECKBOX = "//input[@id='edit-preprocess-css']";
    private static final String AGGREGATE_JS_CHECKBOX = "//input[@id='edit-preprocess-js']";
    //X Autoload
    private static final String NO_CACHE_RADIOBUTTON = "//input[@id='edit-xautoload-cache-mode-default']";
    private static final String USE_APC_CACHE_RADIOBUTTON = "//input[@id='edit-xautoload-cache-mode-apc']";
    private static final String USE_LAZY_APC_CACHE_RADIOBUTTON = "//input[@id='edit-xautoload-cache-mode-apc-lazy']";
    // Actions buttons
    private static final String SAVE_CONFIGURATION_BUTTON = "//input[@id='edit-submit']";
    private static final String CLEAR_ALL_CACHES_BUTTON = "//input[@id='edit-clear']";

    public PerformanceSettingsPage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
    }

    public void selectReportCachePurgesCheckbox(boolean isChecked) {
        if (isChecked) {
            webDriver.setCheckOn(REPORT_CACHE_PURGES_CHECKBOX);
            Utilities.logInfoMessage("Checkbox 'Report cache purges' is checked");
        } else {
            webDriver.setCheckOff(REPORT_CACHE_PURGES_CHECKBOX);
            Utilities.logInfoMessage("Checkbox 'Report cache purges' is unchecked");
        }
    }

    public void selectCachePagesForAnonymCheckbox(boolean isChecked) {
        if (isChecked) {
            webDriver.setCheckOn(CACHE_PAGES_FOR_ANONYM_CHECKBOX);
            Utilities.logInfoMessage("Checkbox 'Cache pages for Anonymous' is checked");
        } else {
            webDriver.setCheckOff(CACHE_PAGES_FOR_ANONYM_CHECKBOX);
            Utilities.logInfoMessage("Checkbox 'Cache pages for Anonymous' is unchecked");
        }
    }

    public void selectCacheBlocksCheckbox(boolean isChecked) {
        if (isChecked) {
            webDriver.setCheckOn(CACHE_BLOCKS_CHECKBOX);
            Utilities.logInfoMessage("Checkbox 'Cache blocks' is checked");
        } else {
            webDriver.setCheckOff(CACHE_BLOCKS_CHECKBOX);
            Utilities.logInfoMessage("Checkbox 'Cache blocks' is unchecked");
        }
    }

    public void selectAggregateCssCheckbox(boolean isChecked) {
        if (isChecked) {
            webDriver.setCheckOn(AGGREGATE_CSS_CHECKBOX);
            Utilities.logInfoMessage("Checkbox 'Aggregate CSS' is checked");
        } else {
            webDriver.setCheckOff(AGGREGATE_CSS_CHECKBOX);
            Utilities.logInfoMessage("Checkbox 'Aggregate CSS' is unchecked");
        }
    }

    public void selectAggregateJsCheckbox(boolean isChecked) {
        if (isChecked) {
            webDriver.setCheckOn(AGGREGATE_JS_CHECKBOX);
            Utilities.logInfoMessage("Checkbox 'Aggregate JavaScript files.' is checked");
        } else {
            webDriver.setCheckOff(AGGREGATE_JS_CHECKBOX);
            Utilities.logInfoMessage("Checkbox 'Aggregate JavaScript files.' is unchecked");
        }
    }

    public void clickNoCacheRadiobutton() {
        webDriver.click(NO_CACHE_RADIOBUTTON);
    }

    public void clickUseApcCacheRadiobutton() {
        webDriver.click(USE_APC_CACHE_RADIOBUTTON);
    }

    public void clickUseLazyApcCacheRadiobutton() {
        webDriver.click(USE_LAZY_APC_CACHE_RADIOBUTTON);
    }

    public void clickSaveConfigurationButton() {
        webDriver.click(SAVE_CONFIGURATION_BUTTON);
    }

    public void clickClearAllCachesButton() {
        webDriver.click(CLEAR_ALL_CACHES_BUTTON);
    }

    public WebElement getAggregateJsCheckbox() {
        return webDriver.findElement(By.xpath(AGGREGATE_JS_CHECKBOX));
    }

    public boolean isAggregateJsCheckboxSelected() {
        return webDriver.isChecked(AGGREGATE_JS_CHECKBOX);
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