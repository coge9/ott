package com.nbcuni.test.cms.pageobjectutils.chiller;

/**
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */

import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;

/**
 *  * The Item Types used for Chiller brand only to test publishing content types
 *  with fields within Body(item type) and Header(entityType)
 */
public enum ItemTypes {

    EVENT("event", "event", true, ContentType.TVE_EVENT, "event"),
    POST("post", "posts", false, ContentType.TVE_POST, "post"),
    IMAGE("image", "images", false, null, "image"),
    ROLE("role", "roles", false, ContentType.TVE_ROLE, "role"),
    PERSON("person", "persons", false, ContentType.TVE_PERSON, "person"),
    MEDIA_GALLERY("mediaGallery", "mediaGallery", false, ContentType.TVE_MEDIA_GALLERY, "media-gallery"),
    VIDEO("video", "videos", false, ContentType.TVE_VIDEO, "video"),
    EPISODE("episode", "episodes", true, ContentType.TVE_EPISODE, "episode"),
    SEASON("season", "seasons", true, ContentType.TVE_SEASON, "season"),
    SERIES("series", "series", true, ContentType.TVE_SERIES, "series"),
    CAST_CREDIT("castCredit", "castCredit", false, null, "castCredit"),
    COLLECTIONS("list", "lists", false, null, "list"),
    HEADER("header", "header", false, null, "headers"),
    SCHEDULE("schedule", "schedule", false, null, "schedule"),
    PLATFORM("platform", "platforms", false, null, "platforms"),
    TAXONOMY_TERM("taxonomyTerm", "taxonomyTerms", false, null, "taxonomy-terms"),
    PROMO("promo", "promos", false, ContentType.TVE_PROMO, "promo");

    private String itemType;
    private String entityType;
    private boolean isRegistryService;
    private ContentType contentType;
    // will determine relative path to Apiary scheme.
    private String apiaryEndpoint;

    ItemTypes(final String itemType, final String entityType, boolean isRegistryService, ContentType contentType, String apiaryEndpoint) {
        this.itemType = itemType;
        this.entityType = entityType;
        this.isRegistryService = isRegistryService;
        this.contentType = contentType;
        this.apiaryEndpoint = apiaryEndpoint;
    }

    public String getItemType() {
        return itemType;
    }

    public String getEntityType() {
        return entityType;
    }

    public boolean isHasRegistryService() {
        return isRegistryService;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getApiaryEndpoint() {
        return apiaryEndpoint;
    }
}
