package com.nbcuni.test.cms.utils.jsonparsing.services.mvpd;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum MVPDServicesFields {
    FIELD_MVPD_IDS("field_mvpd_ids", "mvpdIds", "ALTERNATE MVPD IDS"),
    FIELD_MVPD_K2_ID("field_mvpd_k2_id", "k2Id", "MVPD K2 ID"),
    FIELD_MVPD_URL("field_mvpd_url", "url", "MVPD URL"),
    FIELD_MVPD_FREEWHEEL_HASH("field_mvpd_freewheel_hash", "freewheelHash", "MVPD Freewal Hash"),
    FIELD_MVPD_PROXY("field_mvpd_proxy", "proxy", "MVPD Proxy"),
    FIELD_IOS_APP_URL("field_ios_app_url", "iosAppUrl", ""),
    FIELD_ANDROID_APP_URL("field_android_app_url", "androidAppUrl", ""),
    FIELD_AMAZON_URL("field_amazon_app_url", "amazonAppUrl", ""),
    FIELD_USERNAME_PW_RECOVERY_URL("field_username_pw_recovery_url", "usernamePwRecoveryUrl", ""),
    FIELD_ACCOUNT_REGISTRATION_URL("field_account_registration_url", "accountRegistrationUrl", ""),
    FIELD_CONTACT_PHONE_NUMBER("field_contact_phone_number", "contactPhoneNumber", ""),
    FIELD_CONTACT_CHAT_URL("field_contact_chat_url", "contactChatUrl", ""),
    FIELD_CONTACT_MAILTO_LINK("field_contact_mailto_link", "contactMailtoLink", ""),
    FIELD_CONTACT_TWITTER_URL("field_contact_twitter_url", "contactTwitterUrl", ""),
    FIELD_CONTACT_FAQ_LINK("field_contact_faq_link", "contactFaqLink", ""),
    FIELD_MVPD_COMPANY_URL("field_mvpd_company_url", "companyUrl", ""),
    FIELD_MVPD_FORGOT_USERNAME_URL("field_mvpd_forgot_username_url", "forgotUsernameUrl", ""),
    TITLE("title", "title", "Display Name");

    private static final List<MVPDServicesFields> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final MVPDServicesFields[] FIELDS_FOR_BASIC_FILTER = {
            FIELD_MVPD_IDS, FIELD_MVPD_K2_ID, FIELD_MVPD_URL,
            FIELD_MVPD_FREEWHEEL_HASH, FIELD_MVPD_PROXY, FIELD_IOS_APP_URL, FIELD_ANDROID_APP_URL, FIELD_AMAZON_URL};
    private static final MVPDServicesFields[] FIELDS_FOR_SUPPORT_FILTER = {
            FIELD_USERNAME_PW_RECOVERY_URL, FIELD_ACCOUNT_REGISTRATION_URL,
            FIELD_CONTACT_PHONE_NUMBER, FIELD_CONTACT_CHAT_URL,
            FIELD_CONTACT_MAILTO_LINK, FIELD_CONTACT_TWITTER_URL,
            FIELD_CONTACT_FAQ_LINK, FIELD_MVPD_COMPANY_URL,
            FIELD_MVPD_FORGOT_USERNAME_URL};
    private static final String[] ALL_FILDS_NAMES = {"field_mvpd_ids",
            "field_mvpd_k2_id", "field_mvpd_url", "field_mvpd_freewheel_hash",
            "field_mvpd_proxy", "field_ios_app_url", "field_android_app_url", "field_amazon_app_url", "field_username_pw_recovery_url",
            "field_account_registration_url", "field_contact_phone_number",
            "field_contact_chat_url", "field_contact_mailto_link",
            "field_contact_twitter_url", "field_contact_faq_link",
            "field_mvpd_company_url", "field_mvpd_forgot_username_url"
    };
    private String fieldName;
    private String javaName;
    private String mvpdLogName;

    MVPDServicesFields(final String fieldname, final String javaName, final String mvpdLogName) {
        this.fieldName = fieldname;
        this.javaName = javaName;
        this.mvpdLogName = mvpdLogName;

    }

    public static MVPDServicesFields[] getFieldsForBasicFilter() {
        return FIELDS_FOR_BASIC_FILTER;
    }

    public static MVPDServicesFields[] getFieldsForSupportFilter() {
        return FIELDS_FOR_SUPPORT_FILTER;
    }

    public static List<MVPDServicesFields> getValues() {
        return VALUES;
    }

    public static boolean isStringPresentInEnum(String element) {
        for (MVPDServicesFields field : values()) {
            if (field.getFieldName().equals(element)) {
                return true;
            }
        }
        return false;
    }

    public static MVPDServicesFields getFieldByName(String filterName) {
        MVPDServicesFields field = null;
        for (MVPDServicesFields currentField : MVPDServicesFields.values()) {
            if (currentField.getFieldName().equals(filterName)) {
                field = currentField;
            }
        }
        return field;
    }

    public static int getFieldNameIndex(String fieldName) {
        int index = 0;
        for (String field : ALL_FILDS_NAMES) {
            if (field.equals(fieldName)) {
                return index;
            }
            index++;
        }
        return 1000;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getJavaName() {
        return this.javaName;
    }

    public String getMvpdLogName() {
        return this.mvpdLogName;
    }
}
