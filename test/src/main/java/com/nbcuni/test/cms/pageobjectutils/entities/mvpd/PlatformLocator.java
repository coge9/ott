package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;


public enum PlatformLocator {
    APPLETV("31"), MOBILE("16"), WIN8("26"), XBOXONE("21"), DESKTOP("11");


    private String type;

    PlatformLocator(final String type) {
        this.type = type;
    }

    public String get() {
        return type;
    }
}
