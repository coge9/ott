package com.nbcuni.test.cms.pageobjectutils.entities.content;

public enum ContentFormat {
    SHORTFORM("Shortform content"), FULLEPISODIC("Full episodic content");

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