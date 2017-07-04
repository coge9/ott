package com.nbcuni.test.cms.pageobjectutils.entities.player;

public enum DartVisibilityRule {
    VISIBLE_FOR_VISITORS_ONLY("Visible only for not authenticated visitors"), VISIBLE_FOR_ALL("Visible for all");

    private String name;

    private DartVisibilityRule(final String value) {
        this.name = value;
    }

    public static DartVisibilityRule get(final String value) {
        for (final DartVisibilityRule rule : DartVisibilityRule.values()) {
            if (rule.get().equals(value)) return rule;
        }
        return null;
    }

    public String get() {
        return name;
    }
}