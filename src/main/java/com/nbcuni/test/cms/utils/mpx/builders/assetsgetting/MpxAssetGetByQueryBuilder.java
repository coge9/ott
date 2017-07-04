package com.nbcuni.test.cms.utils.mpx.builders.assetsgetting;

import com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata.api.get.Query;

/**
 * Created by Ivan on 28.01.2016.
 */
public class MpxAssetGetByQueryBuilder {

    private final StringBuilder builder;

    public MpxAssetGetByQueryBuilder() {
        super();
        builder = new StringBuilder();
    }

    public MpxAssetGetByQueryBuilder by(Query query, String value) {
        String bodyTemplate = "&%s=%s";
        builder.append(String.format(bodyTemplate, query.getQuery(), value));
        return this;
    }

    public String build() {
        return builder.length() != 0 ? builder.toString() : "";
    }
}
