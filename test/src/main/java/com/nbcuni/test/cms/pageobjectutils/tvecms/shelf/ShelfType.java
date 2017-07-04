package com.nbcuni.test.cms.pageobjectutils.tvecms.shelf;

/**
 * Created by Aleksandra_Lishaeva on 1/20/16.
 */
public enum ShelfType {

    SHELF("Shelf"), FEATURE_CAROUSEL("Feature Carousel "), FEATURE_SHOWS("Shows"), PLACEHOLDER("Placeholder ");

    private String name;

    ShelfType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
