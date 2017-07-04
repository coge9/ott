package com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata.api.get;

/**
 * Created by Ivan on 28.01.2016.
 */
public enum Query {

    BY_ADDED("byAdded"),
    BY_ADDED_BY_USER_ID("byAddedByUserId"),
    BY_ADMIN_TAGS("byAdminTags"),
    BY_APPROVED("byApproved"),
    BY_AVAILABILITY("byAvailability"),
    BY_AVAILABILITY_STATE("byAvailabilityState"),
    BY_AVAILABLE_DATE("byAvailableDate"),
    BY_CATEGORIES("byCategories"),
    BY_CONTENT("byContent"),
    BY_OWNER_ID("byOwnerId");


    private String query;

    Query(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
