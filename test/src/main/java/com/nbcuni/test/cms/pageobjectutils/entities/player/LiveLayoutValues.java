package com.nbcuni.test.cms.pageobjectutils.entities.player;

public enum LiveLayoutValues {
    CENTERED_LAGRE("Centered Large Player"), CENTERED_WITH_INFO("Centered Large Player with Info Block");

    private String name;

    private LiveLayoutValues(final String value) {
        this.name = value;
    }

    public static LiveLayoutValues get(final String value) {
        for (final LiveLayoutValues size : LiveLayoutValues.values()) {
            if (size.get().equals(value)) return size;
        }
        return null;
    }

    public String get() {
        return name;
    }
}