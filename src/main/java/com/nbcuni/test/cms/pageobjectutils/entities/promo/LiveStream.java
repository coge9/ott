package com.nbcuni.test.cms.pageobjectutils.entities.promo;

public enum LiveStream {
    STANDART_LIVE("Standard Live Page"), CUSTOM_URL("Custom URL");

    private String stream;

    private LiveStream(final String value) {
        this.stream = value;
    }

    public static LiveStream get(final String value) {
        for (final LiveStream stream : LiveStream.values()) {
            if (stream.getStreamLocationValue().equals(value)) return stream;
        }
        return null;
    }

    public String getStreamLocationValue() {
        return stream;
    }
}