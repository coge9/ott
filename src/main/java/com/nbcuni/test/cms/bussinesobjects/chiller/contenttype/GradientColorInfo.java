package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype;

import com.nbcuni.test.cms.pageobjectutils.tvecms.TemplateStyle;

/**
 * Created by aleksandra_lishaeva on 6/16/17.
 */
public class GradientColorInfo {

    private TemplateStyle gradient = null;
    private String color = null;

    public TemplateStyle getGradient() {
        return gradient;
    }

    public GradientColorInfo setGradient(TemplateStyle gradient) {
        this.gradient = gradient;
        return this;
    }

    public String getColor() {
        return color;
    }

    public GradientColorInfo setColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GradientColorInfo that = (GradientColorInfo) o;

        if (gradient != that.gradient) return false;
        return color.equals(that.color);
    }

    @Override
    public int hashCode() {
        int result = gradient.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GradientColorInfo{" +
                "gradient=" + gradient +
                ", color='" + color + '\'' +
                '}';
    }
}
