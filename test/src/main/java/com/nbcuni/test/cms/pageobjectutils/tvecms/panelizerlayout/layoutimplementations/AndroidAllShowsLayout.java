package com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutimplementations;

import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.AndroidAllShowsItems;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.Layout;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.LayoutImpl;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.LayoutItems;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 30-Jun-16.
 */
public class AndroidAllShowsLayout implements Layout {

    private Layout layout;

    public AndroidAllShowsLayout() {
        this.layout = new LayoutImpl(AndroidAllShowsItems.values());
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
