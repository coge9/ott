package com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout;

import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.LayoutItems;

/**
 * Created by Ivan_Karnilau on 30-Jun-16.
 */
public enum RokuAllShowsItems implements LayoutItems {
    ALL_SHOWS("All shows", true);

    private final String label;
    private final boolean isRow;

    RokuAllShowsItems(String label, boolean isRow) {
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
