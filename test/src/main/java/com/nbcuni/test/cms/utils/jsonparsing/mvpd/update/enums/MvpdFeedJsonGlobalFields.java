package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums;

public enum MvpdFeedJsonGlobalFields {

    BRAND("brand"),
    GLOBAL_SETTINGS("globalSettings"),
    FILE_PATH("file_path"),
    ADOBE_PASS_END_POINT("adobePassEndPoint"),
    ADOBE_PASS_ERROR_MAPPING("adobePassErrorMappings"),
    MVPD_WHITE_LIST("mvpdWhitelist");

    String nameInJson;

    MvpdFeedJsonGlobalFields(String nameInJson) {
        this.nameInJson = nameInJson;
    }

    public String getFieldNameInJson() {
        return nameInJson;
    }
}
