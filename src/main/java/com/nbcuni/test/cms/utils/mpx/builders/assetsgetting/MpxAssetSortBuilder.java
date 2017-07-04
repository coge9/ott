package com.nbcuni.test.cms.utils.mpx.builders.assetsgetting;

import com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata.api.get.Sort;

/**
 * Created by Ivan on 28.01.2016.
 */
public class MpxAssetSortBuilder {

    private final static String BODY_TEMPLATE = "&sort=%s";

    private final StringBuilder builder;

    public MpxAssetSortBuilder() {
        super();
        builder = new StringBuilder();
    }

    public MpxAssetSortBuilder sort(Sort sort, boolean descending) {
        builder.append(sort.getSort() + (descending ? "%7Cdesc" : "") + ",");
        return this;
    }

    public MpxAssetSortBuilder sortIncrease(Sort sort) {
        return this.sort(sort, false);
    }

    public MpxAssetSortBuilder sortDecrease(Sort sort) {
        return this.sort(sort, true);
    }

    public String build() {
        String body = "";

        if (builder.length() == 0) {
            return body;
        }

        builder.deleteCharAt(builder.length() - 1);
        body = String.format(BODY_TEMPLATE, builder.toString());
        return body;
    }
}
