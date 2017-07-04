package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;

/**
 * Created by Aleksandra_Lishaeva on 10/23/15.
 */
public enum RokuEndpointType {
    PAGE("/pages", "ott_page"),
    HEADER("/headers", "ott_page_header"),
    SHELF("/shelves", "ott_module"),
    FEATURE_CAROUSEL("/feature_carousels", "ott_module"),
    ASSET("/lists", "list"),
    PROGRAM("/programs", "node"),
    VIDEO("/videos", "node");

    private String endpointType;
    private String objectType;

    /**
     * @param endpointType - name of endpoint to be published, for Android and Roku it's path/
     *                     for Amazon it's name of the queue
     *                     refer to the field 'object_type' within Local Api Json
     * @param objectType   - type of object to be published to Api,
     *                     refer to the field 'object_type' within Local Api Json
     */
    RokuEndpointType(final String endpointType, String objectType) {
        this.endpointType = endpointType;
        this.objectType = objectType;
    }

    // Method allow to get endpoint pased on enum constant.
    public static RokuEndpointType getByPublishingType(SerialApiPublishingTypes publishingType) {
        switch (publishingType) {
            case PAGE:
                return PAGE;
            case HEADER:
                return HEADER;
            case MODULE:
                return null;
            case CUSTOM_MODULE:
                return SHELF;
            case FEATURE_CAROUSEL_MODULE:
                return FEATURE_CAROUSEL;
            case LIST:
                return ASSET;
            case PROGRAM:
                return PROGRAM;
            case VIDEO:
                return VIDEO;
            default:
                return null;
        }
    }

    public String getEndpointType() {
        return endpointType;
    }

    public String getObjectType() {
        return objectType;
    }

}
