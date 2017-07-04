package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;

import java.util.ArrayList;
import java.util.List;

public class Associations {

    private List<Tag> tags = new ArrayList<>();
    private ChannelReferenceAssociations channelReferenceAssociations = new ChannelReferenceAssociations();
    private List<String> categories = new ArrayList<>();
    private List<String> collections = new ArrayList<>();
    private List<String> collectionIds = new ArrayList<>();
    private Content season;

    public Associations() {
        tags = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public List<String> getCollections() {
        return collections;
    }

    public void setCollections(List<String> collections) {
        this.collections = collections;
    }

    public List<String> getCollectionIds() {
        return collectionIds;
    }

    public void setCollectionIds(List<String> collectionIds) {
        this.collectionIds = collectionIds;
    }

    public Associations addTag(Tag tag) {
        tags.add(tag);
        return this;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Associations setTags(List<Tag> tags) {
        this.tags.addAll(tags);
        return this;
    }

    public ChannelReferenceAssociations getChannelReferenceAssociations() {
        return channelReferenceAssociations;
    }

    public Associations setChannelReferenceAssociations(ChannelReferenceAssociations channelReferenceAssociations) {
        this.channelReferenceAssociations = channelReferenceAssociations;
        return this;
    }

    public boolean isObjectNull() {
        return tags.isEmpty() && channelReferenceAssociations.isObjectNull() && season == null;
    }

    public List<String> getCategories() {
        return categories;
    }

    public Associations setCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public Associations addCategories(String categories) {
        this.categories.add(categories);
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tags", tags)
                .add("channelReferenceAssociations", channelReferenceAssociations)
                .add("categories", categories)
                .add("collections", collections)
                .add("collectionIds", collectionIds)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Associations that = (Associations) o;
        return Objects.equal(tags, that.tags) &&
                Objects.equal(channelReferenceAssociations, that.channelReferenceAssociations) &&
                Objects.equal(categories, that.categories) &&
                Objects.equal(collections, that.collections) &&
                Objects.equal(collectionIds, that.collectionIds);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tags, channelReferenceAssociations, categories, collections, collectionIds);
    }

    public Content getSeason() {
        return season;
    }

    public void setSeason(Content season) {
        this.season = season;
    }
}
