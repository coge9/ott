package com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata;

import java.util.Random;

/**
 * Created by Ivan_Karnilau on 16-Dec-15.
 */
public enum SeriesType {

    SPECIAL("Special"), MOVIES("Movies"), SHOW("Show");

    private String type;

    SeriesType(String type) {
        this.type = type;
    }

    public static SeriesType getRandomType() {
        SeriesType[] values = SeriesType.values();
        int randomValueNumber = new Random().nextInt(values.length);
        return values[randomValueNumber];
    }

    public String getType() {
        return type;
    }
}
