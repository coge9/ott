package com.nbcuni.test.cms.pageobjectutils.entities.rules;

public enum PremiumFilter {
    ANY("- Any -"), AUTH("Auth"), FREE("Free");

    private String filterType;

    private PremiumFilter(final String filterType) {
        this.filterType = filterType;
    }

    public static PremiumFilter get(final String value) {
        for (final PremiumFilter filterType : PremiumFilter.values()) {
            if (filterType.getPremiumValue().equals(value)) return filterType;
        }
        return null;
    }

    public String getPremiumValue() {
        return filterType;
    }
}