package com.nbcuni.test.cms.backend.tvecms.pages.content;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleksandra_lishaeva on 9/8/15.
 */
public class EditFilePage extends MainRokuAdminPage {

    protected static final String TAB_XPATH_TEMPLATE = "//li/a/strong[text()='%s']/..";
    private static final String TITLE_XPATH = ".//*[@id='edit-filename']";
    private static final String SHORT_DESCRIPTION_XPATH = ".//*[@id='edit-field-mpx-short-description-und-0-value']";
    private static final String ADVERTISING_GENRE_XPATH = ".//*[@id='edit-field-mpx-advertising-genre-und-0-value']";
    private static final String EPISODE_NUMBER_XPATH = ".//*[@id='edit-field-mpx-episode-number-und-0-value']";
    private static final String DEFAULT_PUBLIC_ID_XPATH = ".//*[@id='edit-field-mpx-main-released-file-pid-und-0-value']";
    private static final String SEASON_NUMBER_XPATH = ".//*[@id='edit-field-mpx-season-number-und-0-value']";
    private static final String MEDIA_TITLE_XPATH = ".//*[@id='edit-field-mpx-title-und-0-value']";
    private static final String TUNE_IN_TIME_XPATH = ".//*[@id='edit-field-mpx-tunein-time-und-0-value']";
    private static final String ENTITLEMENT_XPATH = ".//*[@id='edit-field-mpx-entitlement-und-0-value']";
    private static final String MPX_ID_XPATH = ".//*[@id='edit-field-mpx-id-und-0-value']";
    private static final String GUID_ID_XPATH = ".//*[@id='edit-field-mpx-guid-und-0-value']";
    private static final String EXTERNAL_ADVERTISER_ID_XPATH = ".//*[@id='edit-field-mpx-external-advertiser-id-und-0-value']";
    private static final String MPX_MEDIA_DESCRIPTION_XPATH = ".//*[@id='edit-field-mpx-description-und-0-value']";
    private static final String FULL_EPISODE_XPATH = ".//*[@id='edit-field-mpx-full-episode-und-0-value']";
    private static final String MPX_MEDIA_AUTHOR_XPATH = ".//*[@id='edit-field-mpx-author-und-0-value']";
    private static final String PROGRAMMING_TYPE_XPATH = ".//*[@id='edit-field-mpx-programming-type-und-0-value']";
    private static final String MPX_AIR_DATE_XPATH = ".//*[@id='edit-field-mpx-programming-type-und-0-value']";
    private static final String MPX_AIR_TIME_XPATH = ".//*[@id='edit-field-mpx-airdate-und-0-value-timeEntry-popup-1']";
    private static final String DAY_PART_XPATH = ".//*[@id='edit-field-mpx-day-part-und-0-value']";
    private static final String VIDEO_LENGTH_XPATH = ".//*[@id='edit-field-mpx-video-length-und-0-value']";
    private static final String MPX_MEDIA_AVAILABLE_DATE_XPATH = ".//*[@id='edit-field-mpx-available-date-und-0-value-datepicker-popup-0']";
    private static final String MPX_MEDIA_AVAILABLE_TIME_XPATH = ".//*[@id='edit-field-mpx-available-date-und-0-value-timeEntry-popup-1']";
    private static final String MPX_MEDIA_EXPIRATION_DATE_XPATH = ".//*[@id='edit-field-mpx-expiration-date-und-0-value-datepicker-popup-0']";
    private static final String MPX_MEDIA_EXPIRATION_TIME_XPATH = ".//*[@id='edit-field-mpx-expiration-date-und-0-value-timeEntry-popup-1']";
    private static final String SERIES_TITLE_XPATH = ".//*[@id='edit-field-mpx-series-title-und-0-value']";
    private static final String CLOSED_CAPTION_NOT_AVAILABLE_XPATH = ".//*[@id='edit-field-mpx-cc-available-und']";
    private static final String MPX_MEDIA_KEYWORDS_XPATH = ".//*[@id='edit-field-mpx-keywords-und-0-value']";
    private static final String MPX_THUMBNAILS_SECTION_XPATH = ".//*[@id='edit-field-mpx-thumbnails-und-0']/legend/span/a";
    private static final String MPX_MEDIA_COPYRIGHT_XPATH = ".//*[@id='edit-field-mpx-copyright-und-0-value']";
    private static final String MPX_MEDIA_RELATED_LINK_XPATH = ".//*[@id='edit-field-mpx-related-link-und-0-value']";
    private static final String MPX_MEDIA_BOARD_RATING_XPATH = ".//*[@id='edit-field-mpx-fab-rating-und-0-value']";
    private static final String VIDEO_PLAYER_DDL_XPATH = ".//*[@id='edit-pub-mpx-player-pid']";
    private static final String PUBLISHED_CHECKBOX_XPATH = ".//*[@id='edit-published']";
    private static final String USER_INFORMATION = "User information";
    private static final String SAVE_XPATH = ".//*[@id='edit-submit']";
    private static final String DELETE_XPATH = ".//*[@id='edit-delete']";
    private static final String CANCEL_XPATH = ".//*[@id='edit-cancel']";
    private final String PUBLISHING_TAB = "Publishing options";
    private final String PUBLISHER7_SETTINGS = "Publisher7 Settings";

    public EditFilePage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }


    public void openPublisher7Tab() {
        WaitUtils.perform(webDriver).waitElementVisible(String.format(TAB_XPATH_TEMPLATE, PUBLISHER7_SETTINGS));
        webDriver.click(String.format(TAB_XPATH_TEMPLATE, PUBLISHER7_SETTINGS));
    }

    public void openUserInformationTab() {
        WaitUtils.perform(webDriver).waitElementVisible(String.format(TAB_XPATH_TEMPLATE, USER_INFORMATION));
        webDriver.click(String.format(TAB_XPATH_TEMPLATE, USER_INFORMATION));
    }

    public void openPublishingTab() {
        WaitUtils.perform(webDriver).waitElementVisible(String.format(TAB_XPATH_TEMPLATE, PUBLISHING_TAB));
        webDriver.click(String.format(TAB_XPATH_TEMPLATE, PUBLISHING_TAB));
    }

    public void clickDelete() {
        webDriver.click(DELETE_XPATH);
    }

    public void clickSave() {
        webDriver.click(SAVE_XPATH);
    }

    public void clickCancel() {
        webDriver.click(CANCEL_XPATH);
    }

    @Override
    public List<String> verifyPage() {
        Utilities.logInfoMessage("Verify page " + getPageUrl() + " for missed elements");
        final ArrayList<String> missedElements = new ArrayList<>();
        final Class<?> thisClass = this.getClass();
        final Field[] fields = thisClass.getDeclaredFields();
        for (final Field field : fields) {
            try {
                if (field.getType().equals(String.class) && field.getName().contains("_XPATH")) {
                    final String fieldLocator = field.get(this).toString();
                    if (!webDriver.isVisible(fieldLocator)) {
                        missedElements.add("Element: " + field.getName() + ", Locator: " + fieldLocator);
                        WebDriverUtil.getInstance(webDriver).attachScreenshot();
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
