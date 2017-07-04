package com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata.api.get;

/**
 * Created by Ivan on 28.01.2016.
 */
public enum Fields {

    ID("id"),
    GUID("guid"),
    TITLE("title"),
    AUTHOR("author"),
    DESCRIPTION("description"),
    ADDED("added"),
    OWNER_ID("ownerId"),
    ADDED_BY_USER_ID("addedByUserId"),
    UPDATED("updated"),
    UPDATED_BY_USER_ID("updatedByUserId"),
    VERSION("version"),
    LOCKED("locked"),
    ALLOWED_ACCOUNT_IDS("allowedAccountIds"),
    AVAILABLE_DATE("availableDate"),
    AIR_DATE("pubDate");

    private String field;

    Fields(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
