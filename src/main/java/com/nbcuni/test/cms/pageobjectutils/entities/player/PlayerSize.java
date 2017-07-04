package com.nbcuni.test.cms.pageobjectutils.entities.player;

public enum PlayerSize {
    LIVE("Large player"), DEFAULT("Default");

    private String name;

    private PlayerSize(final String value) {
        this.name = value;
    }

    public static PlayerSize get(final String value) {
        for (final PlayerSize size : PlayerSize.values()) {
            if (size.get().equals(value)) return size;
        }
        return null;
    }

    public String get() {
        return name;
    }
}