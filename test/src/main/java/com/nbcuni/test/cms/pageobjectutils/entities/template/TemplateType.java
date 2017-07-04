package com.nbcuni.test.cms.pageobjectutils.entities.template;

public enum TemplateType {
    SELECT_VALUE("- Select a value -"), GLOBAL_SETTINGS("[ use global settings ]"), PRE_AIR("Pre Air (Promo)"),
    POST_AIR_SHORT("Post Air Short Form (TV Episode)"), POST_AIR_FULLEPISODIC("Post Air (TV Episode)"),
    LIVE("Live (Promo)"), DYNAMIC_LINEAR("Dynamic Linear (Promo)"), DEFAULT("- Select a value -");

    private String template;

    private TemplateType(final String value) {
        this.template = value;
    }

    public static TemplateType get(final String value) {
        for (final TemplateType template : TemplateType.values()) {
            if (template.getTemplateValue().equals(value)) return template;
        }
        return null;
    }

    public String getTemplateValue() {
        return template;
    }
}