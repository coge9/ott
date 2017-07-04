package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Slug {
    private boolean isAutoSlug = true;
    private String slugValue;


    public Slug() {
        super();
    }

    public boolean isAutoSlug() {
        return isAutoSlug;
    }

    public Slug setAutoSlug(boolean autoSlug) {
        isAutoSlug = autoSlug;
        return this;
    }

    public String getSlugValue() {
        return slugValue;
    }

    public Slug setSlugValue(String slugValue) {
        this.isAutoSlug = false;
        this.slugValue = slugValue;
        return this;
    }

    public boolean isObjectNull() {
        return slugValue == null;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("isAutoSlug", isAutoSlug)
                .add("slugValue", slugValue)
                .toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Slug that = (Slug) o;

        return Objects.equal(this.isAutoSlug, that.isAutoSlug) &&
                Objects.equal(this.slugValue, that.slugValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isAutoSlug, slugValue);
    }
}
