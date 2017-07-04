package com.nbcuni.test.cms.pageobjectutils.tvecms;

/**
 * Created by Ivan_Karnilau on 19-Jan-16.
 */
public enum ContentFormat {

    SHORT_EPISODE("0"), FULL_EPISODE("1");

    private String state;

    private ContentFormat(final String state) {
        this.state = state;
    }

    public static ContentFormat get(final String value) {
        for (final ContentFormat filter : ContentFormat.values()) {
            if (filter.get().equals(value)) return filter;
        }
        return null;
    }

    public String get() {
        return state;
    }
}
