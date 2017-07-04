package com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutimplementations;

import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.RokuAllShowsItems;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.Layout;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.LayoutImpl;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.LayoutItems;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 30-Jun-16.
 */
public class RokuAllShowsLayout implements Layout {

    private Layout layout;

    public RokuAllShowsLayout() {
        this.layout = new LayoutImpl(RokuAllShowsItems.values());
    }

    @Override
    public List<LayoutItems> getRows() {
        return layout.getRows();
    }

    @Override
    public List<LayoutItems> getColumns() {
        return layout.getColumns();
    }
}
