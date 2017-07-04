package com.nbcuni.test.cms.backend.tvecms.block.panelizer.contentblock;

import com.nbcuni.test.cms.utils.SoftAssert;

/**
 * Created by Ivan_Karnilau on 14-Jan-16.
 */
public interface LayoutPanelizerBlock {

    SoftAssert checkLayout(SoftAssert softAssert);

    SoftAssert checkMoveBlock(SoftAssert softAssert);
}
