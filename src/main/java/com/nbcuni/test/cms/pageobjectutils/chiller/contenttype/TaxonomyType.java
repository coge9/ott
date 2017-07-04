package com.nbcuni.test.cms.pageobjectutils.chiller.contenttype;

/**
 * Created by Ivan_Karnilau on 19-Aug-16.
 */
public enum TaxonomyType {
    CATEGORIES("Categories", "Categories"),
    TAGS("Tags", "tags"),
    EPISODE_TYPE("Episode type", "episodeType");

    private String vocabularyType;

    private String name;

    TaxonomyType(String name, String vocabularyType) {
        this.name = name;
        this.vocabularyType = vocabularyType;
    }

    public String getName() {
        return name;
    }

    public String getVocabularyType() {
        return vocabularyType;
    }
}
