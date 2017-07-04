package com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 30-Jun-16.
 */
public class LayoutImpl implements Layout {

    List<LayoutItems> layoutItems;

    public LayoutImpl(LayoutItems[] layoutItems) {
        this.layoutItems = Arrays.asList(layoutItems);
    }

    @Override
    public List<LayoutItems> getRows() {
        List<LayoutItems> rows = new LinkedList<>();
        for (LayoutItems layoutItem : layoutItems) {
            if (layoutItem.isRow()) {
                rows.add(layoutItem);
            }
        }
        return rows;
    }

    @Override
    public List<LayoutItems> getColumns() {
        List<LayoutItems> columns = new LinkedList<>();
        for (LayoutItems layoutItem : layoutItems) {
            if (!layoutItem.isRow()) {
                columns.add(layoutItem);
            }
        }
        return columns;
    }
}
