package com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout;

import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.LayoutItems;

/**
 * Created by Ivan_Karnilau on 30-Jun-16.
 */
public enum AndroidHomePageItems implements LayoutItems {
    FEATURE_CAROUSEL("Feature Carousel", true),
    FIRST_COLUMN("First Column", false),
    SECOND_COLUMN("Second Column", false),
    THIRD_COLUMN("Third Column", false),
    FOURTH_COLUMN("Fourth Column", false),
    FIFTH_COLUMN("Fifth Column", false),
    SIXTH_COLUMN("Sixth Column", false);

    private final String label;
    private final boolean isRow;

    AndroidHomePageItems(String label, boolean isRow) {
        this.label = label;
        this.isRow = isRow;
    }

    @Override
    public boolean isRow() {
        return isRow;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
