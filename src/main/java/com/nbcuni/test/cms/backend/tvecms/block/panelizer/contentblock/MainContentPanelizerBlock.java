package com.nbcuni.test.cms.backend.tvecms.block.panelizer.contentblock;

import com.nbcuni.test.cms.backend.tvecms.block.PanelizerContentBlock;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizerlayout.layoutservises.Layout;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 15-Jan-16.
 */
public abstract class MainContentPanelizerBlock extends AbstractContainer implements LayoutPanelizerBlock {

    protected static final String grabbers = ".//div[contains(@id,'panel-pane')]/div[contains(@class,'grabber')]";
    @FindBy(xpath = ".//div[contains(@class,'panel-panel') and not(contains(@class,'panel-col'))]/div")
    protected List<PanelizerContentBlock> rowList;
    @FindBy(xpath = ".//div[contains(@class,'panel-panel panel-col')]")
    protected List<PanelizerContentBlock> columnList;
    protected Layout layout = getLayout();

    public MainContentPanelizerBlock(WebElement element) {
        super(element);
    }

    @Override
    public SoftAssert checkLayout(SoftAssert softAssert) {

        softAssert.assertEquals(getLayout().getRows().size(), rowList.size(), "Rows number is not correct",
                "Rows number is correct");
        softAssert.assertEquals(getLayout().getColumns().size(), columnList.size(), "Columns number is not correct",
                "Columns number is correct");

        if (layout.getRows().size() != 0 && getLayout().getRows().size() == rowList.size()) {
            for (int i = 0; i < getLayout().getRows().size(); i++) {
                softAssert.assertEquals(getLayout().getRows().get(i).getLabel(), rowList.get(i).getPanelLabel(), "Panel label is not correct");
            }
        }

        if (layout.getColumns().size() != 0 && layout.getColumns().size() == columnList.size()) {
            for (int i = 0; i < layout.getColumns().size(); i++) {
                softAssert.assertEquals(layout.getColumns().get(i).getLabel(), columnList.get(i).getPanelLabel(), "Panel label is not correct");
            }
        }
        return softAssert;
    }

    protected abstract Layout getLayout();

    @Override
    public SoftAssert checkMoveBlock(SoftAssert softAssert) {
        return null;
    }
}
