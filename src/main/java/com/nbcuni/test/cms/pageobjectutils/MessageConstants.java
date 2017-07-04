package com.nbcuni.test.cms.pageobjectutils;

public class MessageConstants {

    // -----------------------------------  Brand sites -------------------------------------------
    public static final String LOGIN_SUCCESSFUL_FOR_NEW_ACCOUNT_WITH_USERNAME_TEMPLATE = "Login successful for new account with username %s";
    public static final String PASSWORD_FOR_NEW_ACCOUNT_FIELD_IS_REQUIRED = "Password for New Account field is required.";
    public static final String USERNAME_FOR_NEW_ACCOUNT_FIELD_IS_REQUIRED = "Username for New Account field is required.";
    public static final String CHANGES_HAVE_BEEN_SAVED = "The changes have been saved.";
    public static final String WELCOME_MESSAGE_TO_THE_NEW_USER_TEMPLATE = "A welcome message with further instructions has been e-mailed to the new user %s";
    public static final String IMPORT_ACCOUNT_SETTINGS_UPDATED = "Import Account settings updated.";
    public static final String THE_CONFIGURATION_OPTIONS_HAVE_BEEN_SAVED = "The configuration options have been saved.";
    public static final String AUTOMATIC_CONTENT_CREATION_SETTINGS_WERE_UPDATED = "Automatic content creation settings were updated.";
    public static final String CRON_RUN_SUCCESSFULLY = "Cron run successfully.";
    public static final String WARNING_WIDGET_DRAFT = "Draft is different from published state.";
    public static final String WARNING_PAGE_DRAGDROP_SHELF = "Changes made in this table will not be saved until the form is submitted.";

    /**
     * Roku CMS
     */

    //ERRORS MESSAGES
    public static final String EXPECTED_ERROR_PLATFORM_ALL_FIELDS_ARE_REQUIRED =
            "Error message\n" +
                    "Name field is required.\n" +
                    "Machine Name field is required.\n" +
                    "Viewports field is required.";
    public static final String EXPECTED_ERROR_FEATURE_SHOW_ALL_FIELDS_ARE_REQUIRED =
            "Error message\n" +
                    "Title field is required.";
    public static final String EXPECTED_ERROR_PLATFORM_WRONG_MVPD_ENTITLEMENT_SERVICE_URL = "Error message\nPlease enter the valid MVPD entitlement service url";
    public static final String EXPECTED_ERROR_MAXCOUNT_FOR_SHELF = "\"Max count\" must be a positive number.";
    public static final String EXPECTED_ERROR_TITLE_IS_REQUIRED = "Error message\nTitle field is required.";
    public static final String ERROR_PAGE_LOCKING = "The page you are editing could not be locked automatically. Please lock the page to make sure other people cannot accidentally overwrite your changes.";
    public static final String ERROR_NOTIOS_PAGE_PUBLISHING = "could not prepare data or item doesn't support publishing to";
    public static final String ERROR_REQUIRED_FIELDS_FOR_POST_CONTENT_TYPE = "Title field is required.\nA valid date is required for Date Line.";

    //Validation Messages
    public static final String MEDIA_USAGE_VALIDATION = "Usage value for media items is required.";
    //STATUS MESSAGES
    public static final String EXPECTED_STATUS_ON_PROGRAM_PUBLISHING = "Status message\n" +
            "Changes for TVE Program %s are published to app.\n" +
            "Open full publishing report";

    public static final String SERIES_REQUIRED_FIELDS = "Title field is required.\n" +
            "Status field is required.\n" +
            "Type field is required.";

    public static final String SEASON_REQUIRED_FIELDS = "Title field is required.\n" +
            "Short Description field is required.\n" +
            "Production Number field is required.\n" +
            "Season Number field is required.\n" +
            "Subhead field is required.";

    //PAGE TITLES
    public static final String TITLE_EDIT_PAGE = "Edit %s";
    public static final String TITLE_PUBLISH_CONFIRM_PAGE = "Please choose the API Service instance | Chiller TVE CMS";

    //STATUS MESSAGES
    public static final String STATUS_ON_SHOW_DELETING = "Status message\nDeleted Module %s (Shows).";

    //PAGE TITLES
    public static final String TITLE_ADD_FEATURE_SHOW = "Add shows module";
    public static final String TITLE_EDIT_OTT_MODULE = "Edit %s (%s)";

    //MODULES
    //FEATURE SHOW MODULE
    public static final String FEATURE_SHOW_DEFAULT_TITLE = "Feature Shows";
    public static final String FEATURE_SHOW_DEFAULT_ALIAS = "allShows";

    //SEARCH MESSAGES
    public static final String NO_RESULTS_FIND = "No assets found";

    //PERSON MESSAGES
    public static final String MISS_REQUIRED_FIELDS = "First Name field is required.";

    //REGEXP
    public static final String CHILLER_IMAGE_STYLE_FOR_PUBLISHING = ".+itok=.{8}";

    //Content type message
    public static final String TITLE_IS_ALREADY_USED_FOR_NODE = "Title is already used in %s node.";
    public static final String TITLE_IS_ALREADY_USED_FOR_CURATED_COLLECTION = "Title is already used in %s curated collection.";
    public static final String TITLE_IS_ALREADY_USED_FOR_COLLECTION_GROUP = "Title is already used in %s collection group.";

}
