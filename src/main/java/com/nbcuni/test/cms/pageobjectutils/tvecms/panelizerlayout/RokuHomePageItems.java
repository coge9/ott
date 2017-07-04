package com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout;

import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.LayoutItems;

/**
 * Created by Ivan_Karnilau on 30-Jun-16.
 */
public enum RokuHomePageItems implements LayoutItems {
    FEATURE_CAROUSEL("Feature carousel", true),
    FIRST_ROW("First row", true),
    SECOND_ROW("Second row", true),
    THIRD_ROW("Third row", true),
    FOURTH_ROW("Fourth row", true),
    FIFTH_ROW("Fifth row", true),
    SIXTH_ROW("Sixth row", true),
    SEVENTH_ROW("Seventh row", true),
    EIGHTH_ROW("Eighth row", true),
    NINTH_ROW("Ninth row", true),
    TENTH_ROW("Tenth row", true),
    ELEVENTH_ROW("Eleventh row", true),
    TWELFTH_ROW("Twelfth row", true),
    THIRTEENTH_ROW("Thirteenth row", true),
    FOURTEENTH_ROW("Fourteenth row", true),
    FIFTEENTH_ROW("Fifteenth row", true),
    SIXTEENTH_ROW("Sixteenth row", true),
    SEVENTEENTH_ROW("Seventeenth row", true),
    EIGHTEENTH_ROW("Eighteenth row", true),
    NINETEENTH_ROW("Nineteenth row", true),
    TWENTIETH_ROW("Twentieth row", true);

    private final String label;
    private final boolean isRow;

    RokuHomePageItems(String label, boolean isRow) {
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
