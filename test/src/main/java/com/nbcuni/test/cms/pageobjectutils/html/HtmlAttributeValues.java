package com.nbcuni.test.cms.pageobjectutils.html;

public enum HtmlAttributeValues {
    ACTIVE("active"), DISABLED("disabled"), HIDDEN("hidden"), INACTIVE("inactive"), DEFAULT("default"), ENABLED(
            "enabled"), DISPLAY_BLOCK("display: block;"), CHECKED("checked"), DISPLAY_NONE("display: none;"), COLLAPSED(" collapsed"), SELECTED("selected"), REQUIRED("required");

    public String value;

    HtmlAttributeValues(final String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
}