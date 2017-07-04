package com.nbcuni.test.cms.pageobjectutils.entities.rules;

public enum SortingType {
    AIR_DATE("Air date"), TITLE("Title"), AVAILABLE_DATE("Available Date");

    private String filterType;

    SortingType(final String filterType) {
        this.filterType = filterType;
    }

    public static SortingType get(final String value) {
        for (final SortingType filterType : SortingType.values()) {
            if (filterType.getSortingValue().equals(value)) return filterType;
        }
        return null;
    }

    public String getSortingValue() {
        return filterType;
    }
}