package com.nbcuni.test.cms.pageobjectutils.entities.content;

public enum PublishState {
    NO("No", 0), YES("Yes", 1), PUBLISHED("Published", 1), UNPUBLISHED("Unpublished", 0), NOT_PUBSLISHED("Not published", 0);

    private String state;
    private Integer nodeApiValue;

    /**
     *
     * @param state - string value for Content Filtering and Edit page of the content
     * @param nodeApiValue - publish state value for NodeApi
     */
    private PublishState(final String state, final int nodeApiValue) {
        this.state = state;
        this.nodeApiValue = nodeApiValue;
    }

    public static PublishState get(final String value) {
        for (final PublishState mode : PublishState.values()) {
            if (mode.getStateValue().equals(value)) return mode;
        }
        return null;
    }

    public String getStateValue() {
        return state;
    }

    public Integer getNodeApiValue() {
        return nodeApiValue;
    }
}