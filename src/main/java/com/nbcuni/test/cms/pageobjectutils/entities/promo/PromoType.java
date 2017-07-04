package com.nbcuni.test.cms.pageobjectutils.entities.promo;

public enum PromoType {
    EPISODE("Upcoming Episode"), SEASON("Upcoming Season");

    private String promo;

    private PromoType(final String value) {
        this.promo = value;
    }

    public static PromoType get(final String value) {
        for (final PromoType type : PromoType.values()) {
            if (type.getTypeValue().equals(value)) return type;
        }
        return null;
    }

    public String getTypeValue() {
        return promo;
    }
}