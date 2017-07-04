package com.nbcuni.test.cms.pageobjectutils.chiller;


public enum TextFormatForTextArea {

    WYSIWYG_MINI("WYSIWYG Mini"),
    WYSIWYG_BASIC("WYSIWYG Basic"),
    MARKDOWN("Markdown"),
    RICH_TEXT("Rich text"),
    PLAIN_TEXT("Plain text"),
    DS_CODE("Display Suite code");

    private String formatName;


    TextFormatForTextArea(String format) {
        this.formatName = format;
    }

    public String get() {
        return formatName;
    }
}
