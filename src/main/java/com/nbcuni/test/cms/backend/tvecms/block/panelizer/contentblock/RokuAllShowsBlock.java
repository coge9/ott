package com.nbcuni.test.cms.backend.tvecms.block.panelizer.contentblock;

import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutimplementations.RokuAllShowsLayout;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.Layout;
import org.openqa.selenium.WebElement;

/**
 * Created by Ivan_Karnilau on 11-Feb-16.
 */
public class RokuAllShowsBlock extends MainContentPanelizerBlock implements LayoutPanelizerBlock {

    public RokuAllShowsBlock(WebElement element) {
        super(element);
    }

    @Override
    protected Layout getLayout() {
        return new RokuAllShowsLayout();
    }

}
