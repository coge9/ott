package com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutimplementations;

import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.AndroidHomePageItems;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.Layout;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.LayoutImpl;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.LayoutItems;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 30-Jun-16.
 */
public class AndroidHomePageLayout implements Layout {

    private Layout layout;

    public AndroidHomePageLayout() {
        this.layout = new LayoutImpl(AndroidHomePageItems.values());
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
