package com.nbcuni.test.cms.utils.mpx.builders.assetsgetting;

import com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata.api.get.Fields;

/**
 * Created by Ivan on 28.01.2016.
 */
public class MpxAssetGetByFieldsBuilder {

    private final static String BODY_TEMPLATE = "&fields=%s";

    private final StringBuilder builder;

    public MpxAssetGetByFieldsBuilder() {
        super();
        builder = new StringBuilder();
    }

    public MpxAssetGetByFieldsBuilder get(Fields field) {
        builder.append(field.getField() + ",");
        return this;
    }

    public String build() {
        // create tempBuilder object not to break original one.
        StringBuilder tempBuilder = new StringBuilder(builder);
        String body = "";
        if (builder.length() == 0) {
            return body;
        }
        tempBuilder.deleteCharAt(tempBuilder.length() - 1);
        body = String.format(BODY_TEMPLATE, tempBuilder.toString());
        return body;
    }
}
