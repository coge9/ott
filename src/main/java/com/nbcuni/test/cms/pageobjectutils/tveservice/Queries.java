package com.nbcuni.test.cms.pageobjectutils.tveservice;

public enum Queries {
    VIDEO("tve_video.sql"),
    FILTERED_ASSET_LIST("filtered_asset_list.sql"),
    QUEUE("queue.sql"),
    QUEUE_TYPES("queue_types.sql"),
    ASSET_ON_QUEUE("asset_queue.sql"),
    OLD_SERIES("oldseries.sql"),
    COLLECTION("collection.sql"),
    PROMO("promo.sql"),
    IMAGE("nbcSports"),
    MEDIA_GALLERY("media_gallery.sql"),
    USERS("users.sql"),
    ROLES("roles.sql");

    private String query;

    Queries(final String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;

    }


}
