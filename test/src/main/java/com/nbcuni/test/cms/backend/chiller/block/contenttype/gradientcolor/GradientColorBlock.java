package com.nbcuni.test.cms.backend.chiller.block.contenttype.gradientcolor;

import com.nbcuni.test.cms.elements.RadioButtonsGroup;
import com.nbcuni.test.cms.elements.ShowColor;
import com.nbcuni.test.cms.pageobjectutils.tvecms.TemplateStyle;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import org.openqa.selenium.support.FindBy;

/**
 * Created by aleksandra_lishaeva on 6/16/17.
 */
public class GradientColorBlock extends AbstractContainer {

    @FindBy(id = "edit-field-program-template-und")
    private RadioButtonsGroup gradient;

    @FindBy(id = "field-show-color-add-more-wrapper")
    private ShowColor showColor;

    public GradientColorBlock setGradient(TemplateStyle style) {
        if (style == null) {
            return this;
        }
        gradient.selectRadioButtonByName(style.getStyle());
        return this;
    }

    public TemplateStyle getGradient() {

        if (gradient.getSelectedRadioButton().equalsIgnoreCase(TemplateStyle.LIGHT.getStyle())) {
            return TemplateStyle.LIGHT;
        }
        if (gradient.getSelectedRadioButton().equalsIgnoreCase(TemplateStyle.DARK.getStyle())) {
            return TemplateStyle.DARK;
        }

        WebDriverUtil.getInstance(webDriver).attachScreenshot();
        throw new TestRuntimeException("The Gradient either not selected or do not matched any Values from TemplateStyle ");
    }

    public GradientColorBlock setShowColor(String color) {
        showColor.setColor(color);
        return this;
    }

    public String getShowColor() {
        return showColor.getColor();
    }
}
