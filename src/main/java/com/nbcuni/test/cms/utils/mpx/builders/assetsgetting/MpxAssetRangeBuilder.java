package com.nbcuni.test.cms.utils.mpx.builders.assetsgetting;

/**
 * Created by Ivan on 28.01.2016.
 */
public class MpxAssetRangeBuilder {

    private String builder = "";
    private String bodyByRangeTemplate = "&range=%s-%s";
    private String bodyByNumberTemplate = "&range=%s-%s";

    public MpxAssetRangeBuilder() {
        super();
    }

    public MpxAssetRangeBuilder getRange(Integer lowerLimit, Integer upperLimit) {
        builder = String.format(bodyByRangeTemplate, lowerLimit, upperLimit);
        return this;
    }

    public MpxAssetRangeBuilder getRangeFrom(Integer lowerLimit) {
        builder = String.format(bodyByRangeTemplate, lowerLimit, "");
        return this;
    }

    public MpxAssetRangeBuilder getRangeTo(Integer upperLimit) {
        builder = String.format(bodyByRangeTemplate, "", upperLimit);
        return this;
    }

    public MpxAssetRangeBuilder getByNumber(Integer number) {
        builder = String.format(bodyByNumberTemplate, number);
        return this;
    }

    public String build() {
        return builder;
    }
}
