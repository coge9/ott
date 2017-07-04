package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;


public enum ServiceVersionLocator {

    V1("36"), V2("41");


    private String type;

    ServiceVersionLocator(final String type) {
        this.type = type;
    }

    public String get() {
        return type;
    }
}
