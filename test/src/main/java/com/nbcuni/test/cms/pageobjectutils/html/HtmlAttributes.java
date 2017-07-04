package com.nbcuni.test.cms.pageobjectutils.html;

public enum HtmlAttributes {
    HREF("href"), CLASS("class"), ID("id"), SRC("src"), TARGET("target"), STYLE("style"), VALUE("value"), DISABLED(
            "disabled"), TITLE("title"), DATA_TITLE("data-title"), OPTION("option"), CHECKED("checked"), COLOR("color"),
    PLACEHOLDER("placeholder"), DATA_PULSE_SPEED("data-pulse-speed"), DATA_PULSE_DURATION("data-pulse-duration"),
    LINK("link"), FOR("for"), LABEL("label");

    private String attribute;

    HtmlAttributes(final String attribute) {
        this.attribute = attribute;
    }

    public String get() {
        return attribute;
    }
}