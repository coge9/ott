package com.nbcuni.test.cms.pageobjectutils.entities.rules;

public enum FormatFilter {
    FULL_EPISODE("Full episodic content"), SHORT_FORM("Shortform content"), ANY("Any"),;

    private String filterType;

    private FormatFilter(final String filterType) {
        this.filterType = filterType;
    }

    public static FormatFilter get(final String value) {
        for (final FormatFilter filterType : FormatFilter.values()) {
            if (filterType.getFormatValue().equals(value)) return filterType;
        }
        return null;
    }

    public String getFormatValue() {
        return filterType;
    }
}