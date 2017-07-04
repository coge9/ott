package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

public class LogoTypeObject {

    private String name;
    private String width;
    private String height;
    private String colorProcessing;
    private String hexValue;

    public LogoTypeObject() {
        super();
    }

    public LogoTypeObject(String name, String width, String height,
                          String colorProcessing) {
        super();
        this.name = name;
        this.width = width;
        this.height = height;
        this.colorProcessing = colorProcessing;
    }

    public LogoTypeObject(String name, String width, String height,
                          String colorProcessing, String hexValue) {
        this(name, width, height, colorProcessing);
        if (hexValue != null && !hexValue.equals("")) {
            this.hexValue = hexValue;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = String.valueOf(width);
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = String.valueOf(height);
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getColorProcessing() {
        return colorProcessing;
    }

    public void setColorProcessing(String colorProcessing) {
        this.colorProcessing = colorProcessing;
    }

    public String getHexValue() {
        return hexValue;
    }

    public void setHexValue(String hexValue) {
        this.hexValue = hexValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogoTypeObject that = (LogoTypeObject) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (width != null ? !width.equals(that.width) : that.width != null) return false;
        if (height != null ? !height.equals(that.height) : that.height != null) return false;
        if (colorProcessing != null ? !colorProcessing.equals(that.colorProcessing) : that.colorProcessing != null)
            return false;
        return hexValue != null ? hexValue.equals(that.hexValue) : that.hexValue == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (colorProcessing != null ? colorProcessing.hashCode() : 0);
        result = 31 * result + (hexValue != null ? hexValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LogoTypeObject{" +
                "name='" + name + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", colorProcessing='" + colorProcessing + '\'' +
                ", hexValue='" + hexValue + '\'' +
                '}';
    }
}
