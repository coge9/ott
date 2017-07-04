package com.nbcuni.test.cms.pageobjectutils.entities.blocks;

public enum VisibilityValues {

    ONLY_LISTED_BELOW("1"), ALL_EXCEPT_LISTED_BELOW("0");

    private String optionName;

    private VisibilityValues(final String optionName) {
        this.optionName = optionName;
    }

    public static VisibilityValues get(final String value) {
        for (final VisibilityValues optionType : VisibilityValues.values()) {
            if (optionType.getOption().equals(value)) return optionType;
        }
        return null;
    }

    public String getOption() {
        return optionName;
    }
}