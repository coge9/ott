package com.nbcuni.test.cms.pageobjectutils.entities.promo;

public enum PromoMode {
    PRE_AIR("Pre-Air"), LIVE("Live"), PRE_AIR_LIVE("Pre-Air and Live"), DYNAMIC_LINEAR("Dynamic Linear ");

    private String mode;

    private PromoMode(final String value) {
        this.mode = value;
    }

    public static PromoMode get(final String value) {
        for (final PromoMode mode : PromoMode.values()) {
            if (mode.getModeValue().equals(value)) return mode;
        }
        return null;
    }

    public String getModeValue() {
        return mode;
    }
}