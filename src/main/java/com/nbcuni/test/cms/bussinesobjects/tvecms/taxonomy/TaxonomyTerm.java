package com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy;

import com.google.common.base.MoreObjects;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.TaxonomyType;

import java.util.ArrayList;
import java.util.List;

public class TaxonomyTerm extends AbstractEntity {

    protected TaxonomyType taxonomyType = TaxonomyType.CATEGORIES;
    private String title;
    private String description;
    private List<String> parents = new ArrayList<>();
    private int weight;
    private String uuid;

    public TaxonomyType getTaxonomyType() {
        return taxonomyType;
    }

    public TaxonomyTerm setTaxonomyType(TaxonomyType taxonomyType) {
        this.taxonomyType = taxonomyType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tag) {
        this.title = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getParents() {
        return parents;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("description", description)
                .add("parents", parents)
                .add("weight", weight)
                .add("taxonomyType", taxonomyType)
                .add("uuid", uuid)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TaxonomyTerm other = (TaxonomyTerm) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ItemTypes getType() {
        return ItemTypes.TAXONOMY_TERM;
    }
}
