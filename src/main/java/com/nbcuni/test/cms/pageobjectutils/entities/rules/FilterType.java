package com.nbcuni.test.cms.pageobjectutils.entities.rules;

public enum FilterType {
    FORMAT("Format"), CATEGORY("Category"), PROGRAMMING_TYPE("Programming type"), GENRE("Genre"),
    AVAILABLE_DATE("Available date"), PREMIUM("Premium"), TV_SHOW("TV Show"), TV_SEASON("TV Season");

    private String filterType;

    private FilterType(final String filterType) {
        this.filterType = filterType;
    }

    public static FilterType get(final String value) {
        for (final FilterType filterType : FilterType.values()) {
            if (filterType.getFilterName().equals(value)) return filterType;
        }
        return null;
    }

    public String getFilterName() {
        return filterType;
    }
}