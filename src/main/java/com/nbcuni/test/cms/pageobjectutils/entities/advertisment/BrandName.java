package com.nbcuni.test.cms.pageobjectutils.entities.advertisment;

public enum BrandName {
    SYFY("syfy", ""), BRAVO("bravo", "black"), TELEMUNDO("telemundo", "black2x"), CNBC("cnbc", "white"), OXYGEN(
            "oxygen", "white2x"), SPROUT("White Logo Alt", "whiteAlt"), MUN2("Color Logo", "color"), EONLINE(
            "Color Logo 2x", "color2x"), ESQUIRE(
            "Color Logo 2x", "color2x");

    private String brand;
    private String tagValue;

    BrandName(final String brand, final String tagValue) {
        this.brand = brand;
        this.tagValue = tagValue;
    }

    public String get() {
        return this.brand;
    }

    public String getBulkUploadName() {
        return this.tagValue;
    }
}