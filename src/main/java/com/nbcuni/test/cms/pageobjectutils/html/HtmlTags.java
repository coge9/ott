package com.nbcuni.test.cms.pageobjectutils.html;

public enum HtmlTags {
    TD("td"),
    TR("tr"),
    TBODY("tbody"),
    A("a"),
    H2("h2"),
    IMG("img"),
    DIV("div"),
    P("p"),
    OBJECT("object"),
    SPAN("span"),
    IFRAME("iframe");

    private String tag;

    HtmlTags(final String tag) {
        this.tag = tag;
    }

    public String get() {
        return this.tag;
    }
}