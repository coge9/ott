package com.nbcuni.test.cms.backend.tvecms.block;

import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.ContentTabs;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 20-Jun-16.
 */
public abstract class BaseTabBlock extends AbstractContainer {

    protected ContentTabs contentTab;
    @FindBy(xpath = ".//a[@class='fieldset-title']")
    private Link link;

    private boolean isTabCollapsed() {
        return currentElement.element().getAttribute(HtmlAttributes.CLASS.get()).contains("collapsed");
    }

    public void collapseTab() {
        if (!isTabCollapsed()) {
            link.clickJS();
        }
    }

    public void expandTab() {
        if (isTabCollapsed()) {
            link.clickJS();
        }
    }

}
