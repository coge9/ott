package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes;

import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.*;

/**
 * Enum represent items types which can be published to Serial API.
 * <p>
 * Created by Dzianis_Kulesh on 1/23/2017.
 */
public enum SerialApiPublishingTypes implements PublishingContentType {

    VIDEO("Node (TVE Video)", RokuVideoJson.class),
    PROGRAM("Node (TVE Program)", RokuProgramJson.class),
    PAGE("TVE Page", RokuPageJson.class),
    MODULE("Module", RokuShelfJson.class),
    CUSTOM_MODULE("Module (Custom)", RokuShelfJson.class),
    FEATURE_CAROUSEL_MODULE("Module (Feature)", RokuFeatureCarouselJson.class),
    HEADER("Header", RokuHeaderJson.class),
    LIST("list", RokuQueueJson.class);


    private final String objectLabel;
    private final Class clazz;
    SerialApiPublishingTypes(String objectLabel, Class clazz) {
        this.objectLabel = objectLabel;
        this.clazz = clazz;
    }

    @Override
    public Class getBelongObjectClass() {
        return clazz;
    }

    @Override
    public String getObjectLabel() {
        return objectLabel;
    }
}
