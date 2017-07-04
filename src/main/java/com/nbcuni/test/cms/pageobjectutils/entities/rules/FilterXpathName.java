package com.nbcuni.test.cms.pageobjectutils.entities.rules;

public enum FilterXpathName {
    FORMAT("content-format-value"), CATEGORY("media-categories-tid"), PROGRAMMING_TYPE("mpx-programming-type-value"), GENRE("advertising-genre-tid"),
    AVAILABLE_DATE("mpx-available-date-value"), PREMIUM("mpx-entitlement-value"), TV_SHOW("mpx-relation-tv-show-target-id"), TV_SEASON("mpx-relation-tv-season-target-id");

    private String filterType;

    private FilterXpathName(final String filterType) {
        this.filterType = filterType;
    }

    public static FilterXpathName get(final String value) {
        for (final FilterXpathName filterType : FilterXpathName.values()) {
            if (filterType.getFilterName().equals(value)) return filterType;
        }
        return null;
    }

    public String getFilterName() {
        return filterType;
    }
}