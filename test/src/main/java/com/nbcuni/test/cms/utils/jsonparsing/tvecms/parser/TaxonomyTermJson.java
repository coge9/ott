package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTerm;

import java.util.List;

public class TaxonomyTermJson extends AbstractEntity {

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("itemType")
    private String itemType;

    @SerializedName("title")
    private String title;

    @SerializedName("vocabularyType")
    private String vocabularyType;

    @SerializedName("parentUuids")
    private List<String> parentUuids;

    @SerializedName("description")
    private String description;

    @SerializedName("weight")
    private int weight;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getVocabularyType() {
        return vocabularyType;
    }

    public void setVocabularyType(String vocabularyType) {
        this.vocabularyType = vocabularyType;
    }

    public List<String> getParentUuids() {
        return parentUuids;
    }

    public void setParentUuids(List<String> parentUuids) {
        this.parentUuids = parentUuids;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public TaxonomyTermJson getObject(TaxonomyTerm term) {
        this.itemType = "taxonomyTerm";
        this.title = term.getTitle();
        this.description = term.getDescription() != null ? term.getDescription() : "";
        this.weight = term.getWeight();
        this.vocabularyType = term.getTaxonomyType().getVocabularyType();
        this.parentUuids = term.getParents();

        return this;
    }

}
