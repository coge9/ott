package com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata;

/**
 * Created by Alena_Aukhukova on 8/24/2016.
 */
public enum MpxInfoFields {
    TITLE("Title"),
    SHORT_DESCRIPTION("Short Description"),
    ADVERTISING_GENRE("Advertising genre"),
    EPISODE_NUMBER("Episode #"),
    SEASON_NUMBER("Season #"),
    TUNE_IN_TIME("TuneInTime"),
    ENTITLEMENT("Entitlement"),
    EXTERNAL_ID("ExternalID"),
    FULL_EPISODE("FullEpisode"),
    PROGRAMMING_TYPE("ProgrammingType"),
    DAYPART("Day Part"),
    VIDEOLENGTH("VideoLength"),
    SERIES("Series"),
    CATEGORIES("Categories"),
    ID("ID"),
    GUID("GUID"),
    DESCRIPTION("Description"),
    AIRDATE("Airdate"),
    AVAILABLE_DATE("Available date"),
    EXPIRATION_DATE("Expiration date"),
    SERIES_TYPE("Series Type"),
    TMS_ID("Tms ID"),
    EVENT("Event");

    private String name;

    MpxInfoFields(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}