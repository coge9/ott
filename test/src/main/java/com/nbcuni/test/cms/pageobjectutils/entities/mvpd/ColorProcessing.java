package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

/**
 * Created by Dzianis_Kulesh on 6/10/2016.
 */
public enum ColorProcessing {

    KEEP_ORIGINAL("Keep Original"),
    COLORIZE("Colorize");


    private final String name;

    ColorProcessing(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }

}


