package com.nbcuni.test.cms.backend.tvecms.block.panelizer.contentblock;

import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutimplementations.RokuHomePageLayout;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.Layout;
import org.openqa.selenium.WebElement;

/**
 * Created by Ivan_Karnilau on 14-Jan-16.
 */
public class AppleTvHomepageBlock extends MainContentPanelizerBlock implements LayoutPanelizerBlock {

    public AppleTvHomepageBlock(WebElement element) {
        super(element);
    }

    @Override
    protected Layout getLayout() {
        return new RokuHomePageLayout();
    }

}
