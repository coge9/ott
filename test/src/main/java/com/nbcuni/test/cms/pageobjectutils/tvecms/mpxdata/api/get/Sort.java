package com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata.api.get;

/**
 * Created by Ivan on 28.01.2016.
 */
public enum Sort {

    ID("id"),
    GUID("guid"),
    TITLE("title"),
    ADDED("added"),
    OWNER_ID("ownerId"),
    ADDED_BY_USER_ID("addedByUserId"),
    UPDATED("updated"),
    UPDATED_BY_USER_ID("updatedByUserId"),
    VERSION("version"),
    LOCKED("locked"),
    AVAILABLE_DATE("availableDate"),
    AIR_DATE("pubDate"),
    EPISODE_NUMBER("pl1$episodeNumber"),
    SEASON_NUMBER("pl1$seasonNumber");

    private String sortField;

    Sort(String sortField) {
        this.sortField = sortField;
    }

    public String getSort() {
        return sortField;
    }
}
