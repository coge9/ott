package com.nbcuni.test.cms.pageobjectutils.mvpd;

/**
 * Created by Ivan_Karnilau on 11-Jun-15.
 */
public enum BoundingClientRect {
    LEFT("left"), TOP("top"), RIGHT("right"), BOTTOM("bottom"), HEIGHT("height"), WIDTH("width");

    private String name;

    private BoundingClientRect(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
