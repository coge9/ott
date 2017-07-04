package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes;

/**
 * Interface design to represent any type which can be published to the API.
 * Created by Dzianis_Kulesh on 1/23/2017.
 */
public interface PublishingContentType {

    // return java Class object (mapping for JSON parsing)
    Class getBelongObjectClass();

    // return object label. Field which allow to identify content type in API.
    String getObjectLabel();
}

