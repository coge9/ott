package com.nbcuni.test.cms.pageobjectutils.entities.player;

public enum CompanionSize {
    LARGE_TOP("728x90"), SMALL_PLAYER("300x60"), MIDDLE_PLAYER("300x250"), ZERO("0");

    private String name;

    private CompanionSize(final String value) {
        this.name = value;
    }

    public static CompanionSize get(final String value) {
        for (final CompanionSize size : CompanionSize.values()) {
            if (size.get().equals(value)) return size;
        }
        return null;
    }

    public String get() {
        return name;
    }

}