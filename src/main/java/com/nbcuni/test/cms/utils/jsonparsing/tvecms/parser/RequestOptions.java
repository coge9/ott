package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alekca on 24.05.2016.
 */
public class RequestOptions {

    @SerializedName("attributes")
    private AttributesJson attributes;

    @SerializedName("data")
    private String data;

    public AttributesJson getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesJson attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("attributes", attributes)
                .add("data", data)
                .toString();
    }
}
