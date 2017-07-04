package com.nbcuni.test.cms.backend.tvecms.block.panelizer.contentblock;

import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutimplementations.AndroidHomePageLayout;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.Layout;
import com.nbcuni.test.cms.utils.SoftAssert;
import org.openqa.selenium.WebElement;

/**
 * Created by Ivan_Karnilau on 14-Jan-16.
 */
public class AndroidHomePageBlock extends MainContentPanelizerBlock implements LayoutPanelizerBlock {

    public AndroidHomePageBlock(WebElement element) {
        super(element);
    }

    @Override
    protected Layout getLayout() {
        return new AndroidHomePageLayout();
    }

    @Override
    public SoftAssert checkMoveBlock(SoftAssert softAssert) {
        return softAssert;
    }
}
