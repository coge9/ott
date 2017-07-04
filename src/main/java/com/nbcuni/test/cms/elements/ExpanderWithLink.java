package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 21-Mar-16.
 */
public class ExpanderWithLink extends AbstractContainer {

    @FindBy(className = "fieldset-title")
    private Link expandLink;

    public boolean isCollapsed() {
        return currentElement.element().getAttribute(HtmlAttributes.CLASS.get()).contains("collapsed");
    }

    public void collapse() {
        if (!isCollapsed()) {
            expandLink.click();
        }
    }

    public void expand() {
        if (isCollapsed()) {
            expandLink.click();
            waitFor().waitAttributeNotPresent(currentElement.element(), "collapsed", 5);
        }
    }
}
