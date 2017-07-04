package com.nbcuni.test.cms.utils.jsonparsing.services.mvpd;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum MvpdServiceFilters {

    BASIC_FILTER("basic"),
    SUPPORT_FILTER("support"),
    MVPD_IDS_FILTER("mvpdID"),
    K2_ID_FILTER("field_mvpd_k2_id"),
    MVPD_URL_FILTER("field_mvpd_url"),
    FREEWHEEL_HASH_FILTER("field_mvpd_freewheel_hash"),
    PROXY_FILTER("field_mvpd_proxy"),
    IOS_APP_URL_FILTER("field_ios_app_url"),
    ANDROID_APP_URL_FILTER("field_android_app_url"),
    AMAZON_APP_URL_FILTER("field_amazon_app_url"),
    PW_RECOVERY_URL_FILTER("field_username_pw_recovery_url"),
    ACCOUNT_REGISTRATION_URL_FILTER("field_account_registration_url"),
    CONTACT_PHONE_NUMBER_FILTER("field_contact_phone_number"),
    CONTACT_CHAT_URL_FILTER("field_contact_chat_url"),
    CONTACT_MAILTO_LINK_FILTER("field_contact_mailto_link"),
    CONTACT_TWITTER_URL_FILTER("field_contact_twitter_url"),
    CONTACT_FAQ_LINK_FILTER("field_contact_faq_link"),
    COMPANY_URL_FILTER("field_mvpd_company_url"),
    FORGOT_USERNAME_URL_FILTER("field_mvpd_forgot_username_url"),
    NOT_EXISTED_FILTER("wrong_filter");

    private static final MvpdServiceFilters[] BASIC_FILTERS = {
            MVPD_IDS_FILTER, K2_ID_FILTER, MVPD_URL_FILTER,
            FREEWHEEL_HASH_FILTER, PROXY_FILTER, IOS_APP_URL_FILTER, ANDROID_APP_URL_FILTER, AMAZON_APP_URL_FILTER};
    private static final MvpdServiceFilters[] SUPPORT_FILTERS = {
            PW_RECOVERY_URL_FILTER, ACCOUNT_REGISTRATION_URL_FILTER,
            CONTACT_CHAT_URL_FILTER, CONTACT_MAILTO_LINK_FILTER,
            CONTACT_TWITTER_URL_FILTER, CONTACT_FAQ_LINK_FILTER,
            COMPANY_URL_FILTER, FORGOT_USERNAME_URL_FILTER,};
    private static final MvpdServiceFilters[] CUSTOM_FILTERS = {
            MVPD_IDS_FILTER, K2_ID_FILTER, MVPD_URL_FILTER,
            FREEWHEEL_HASH_FILTER, PROXY_FILTER, IOS_APP_URL_FILTER, ANDROID_APP_URL_FILTER, AMAZON_APP_URL_FILTER, PW_RECOVERY_URL_FILTER,
            ACCOUNT_REGISTRATION_URL_FILTER, CONTACT_PHONE_NUMBER_FILTER,
            CONTACT_CHAT_URL_FILTER, CONTACT_MAILTO_LINK_FILTER,
            CONTACT_TWITTER_URL_FILTER, CONTACT_FAQ_LINK_FILTER,
            COMPANY_URL_FILTER, FORGOT_USERNAME_URL_FILTER};
    private String filterName;


    MvpdServiceFilters(final String filterName) {
        this.filterName = filterName;
    }

    public static MvpdServiceFilters[] getBasicFilters() {
        return BASIC_FILTERS;
    }

    public static MvpdServiceFilters[] getSupportFilters() {
        return SUPPORT_FILTERS;
    }

    public static MvpdServiceFilters[] getCustomFilters() {
        return CUSTOM_FILTERS;
    }

    public static MvpdServiceFilters[] getFiltersExcept(MvpdServiceFilters[] filters) {
        List<MvpdServiceFilters> listAll = new LinkedList<MvpdServiceFilters>();
        for (MvpdServiceFilters filter : MvpdServiceFilters.values()) {
            listAll.add(filter);
        }
        List<MvpdServiceFilters> list = Arrays.asList(filters);
        listAll.removeAll(list);
        MvpdServiceFilters[] array = new MvpdServiceFilters[listAll.size()];
        int index = 0;
        for (MvpdServiceFilters filter : listAll) {
            array[index] = filter;
            index++;
        }
        return array;
    }

    public String get() {
        return filterName;
    }

}
