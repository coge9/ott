package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums;

public enum ErrorMessageFields {

    USE_ADOBE_DESC("useAdobeDesc"), TITLE("title"), BODY("body");

    String nameInJson;

    ErrorMessageFields(String nameInJson) {
        this.nameInJson = nameInJson;
    }

    public String getFieldNameInJson() {
        return nameInJson;
    }

}
