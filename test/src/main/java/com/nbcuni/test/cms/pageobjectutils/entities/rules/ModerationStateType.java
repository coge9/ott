package com.nbcuni.test.cms.pageobjectutils.entities.rules;

public enum ModerationStateType {
    DRAFT("Draft"), Review("Review"), PUBLISHED("Published"), PUBLISH("Publish");

    private String filterType;

    private ModerationStateType(final String filterType) {
        this.filterType = filterType;
    }

    public static ModerationStateType get(final String value) {
        for (final ModerationStateType filterType : ModerationStateType.values()) {
            if (filterType.getFilterName().equals(value)) return filterType;
        }
        return null;
    }

    public String getFilterName() {
        return filterType;
    }
}