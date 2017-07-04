package com.nbcuni.test.cms.pageobjectutils.entities.rules;

public enum OrderType {
    ASK("Ask"), DESC("Desc");

    private String filterType;

    OrderType(final String filterType) {
        this.filterType = filterType;
    }

    public static OrderType get(final String value) {
        for (final OrderType filterType : OrderType.values()) {
            if (filterType.getOrderValue().equals(value)) return filterType;
        }
        return null;
    }

    public String getOrderValue() {
        return filterType;
    }
}