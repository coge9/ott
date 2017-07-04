package com.nbcuni.test.cms.pageobjectutils.entities.template;

public enum TemplateTypeJson {
    PRE_AIR("pre_air"),
    POST_AIR_SHORT("post_air_shortform"), POST_AIR_FULLEPISODIC("post_air_full_episode"),
    LIVE("live"), DYNAMIC_LINEAR("Dynamic Linear (Promo)");

    private String template;

    private TemplateTypeJson(final String value) {
        this.template = value;
    }

    public static TemplateTypeJson get(final String value) {
        for (final TemplateTypeJson template : TemplateTypeJson.values()) {
            if (template.getTemplateValue().equals(value)) return template;
        }
        return null;
    }

    public String getTemplateValue() {
        return template;
    }
}