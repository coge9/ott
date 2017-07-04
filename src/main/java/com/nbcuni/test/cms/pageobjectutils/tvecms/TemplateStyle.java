package com.nbcuni.test.cms.pageobjectutils.tvecms;

/**
 * Created by Ivan_Karnilau on 13-Nov-15.
 */
public enum TemplateStyle {
    DARK("dark"), LIGHT("light");

    private String style;

    TemplateStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }
}
