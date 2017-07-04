package com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;

/**
 * Created by Alena_Aukhukova
 */
public class FeatureShowModule extends Module {

    private Boolean isDisplayTitle = null;
    private String tileVariant = "3 tile";

    public FeatureShowModule() {
        super();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public FeatureShowModule setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isDisplayTitle() {
        return isDisplayTitle;
    }

    public FeatureShowModule setDisplayTitle(boolean isDisplayTitle) {
        this.isDisplayTitle = isDisplayTitle;
        return this;
    }

    @Override
    public String getTileVariant() {
        return tileVariant;
    }

    @Override
    public FeatureShowModule setId(String id) {
        this.id = id;
        return this;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(title, isDisplayTitle, slugInfo, tileVariant, assets, assetsCount,
                id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("isDisplayTitle", isDisplayTitle)
                .add("alias", slugInfo)
                .add("tileVariant", tileVariant)
                .add("assets", assets)
                .add("assetsCount", assetsCount)
                .add("id", id)
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

        FeatureShowModule that = (FeatureShowModule) o;

        return Objects.equal(this.title, that.title) &&
                Objects.equal(this.isDisplayTitle, that.isDisplayTitle) &&
                Objects.equal(this.slugInfo, that.slugInfo) &&
                Objects.equal(this.tileVariant, that.tileVariant) &&
                Objects.equal(this.assets, that.assets) &&
                Objects.equal(this.assetsCount, that.assetsCount);
    }
}
