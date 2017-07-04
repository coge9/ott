package com.nbcuni.test.cms.pageobjectutils.entities.widgets;

public enum WidgetType {
    DEFAULT_DL_WIDGET("Dynamic Lead [default]"), DL_WIDGET("DL widget"), LIVE_DEFAULT("Live Player [default]"),
    ASSET_LIST_DEFAULT("Asset List [default]"), SEE_MORE_EPISODES("SEE MORE EPISODES"), WATCH_LATEST_EPISODES("WATCH THE LATEST EPISODES:"), ORIGINALS_SERIES_DEFAULT("Original Series [default]"),
    A_SPOT_DEFAULT("A-Spot [default]"), CHARACTER_CAROUSEL("Character Carousel [default]");

    private String widget;

    private WidgetType(final String widget) {
        this.widget = widget;
    }

    public static WidgetType get(final String value) {
        for (final WidgetType widget : WidgetType.values()) {
            if (widget.getWidgetValue().equals(value)) return widget;
        }
        return null;
    }

    public String getWidgetValue() {
        return widget;
    }
}