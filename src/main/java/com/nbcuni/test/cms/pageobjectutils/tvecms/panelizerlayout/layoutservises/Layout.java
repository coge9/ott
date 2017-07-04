package com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 30-Jun-16.
 */
public interface Layout {
    List<LayoutItems> getRows();

    List<LayoutItems> getColumns();
}
