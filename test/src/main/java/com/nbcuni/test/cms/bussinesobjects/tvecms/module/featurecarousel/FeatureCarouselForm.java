package com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 19-Dec-15.
 */
public class FeatureCarouselForm extends Module {

    private Boolean displayTitle = false;
    private String tileVariant = "1 tile";
    private String type = "Feature Carousel";
    private String updated = null;
    private String publishedDate = null;
    private String createdDate = null;
    private String modifiedDate = null;

    public FeatureCarouselForm() {
        super();
    }

    public FeatureCarouselForm(String title, Boolean displayTitle, Slug slug, String tileVariant, Integer assetsCount) {
        super();
        this.title = title;
        this.displayTitle = displayTitle;
        this.slugInfo = slug;
        this.tileVariant = tileVariant;
        this.assetsCount = assetsCount;
        this.assets = new ArrayList<>();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public FeatureCarouselForm setTitle(String title) {
        this.title = title;
        return this;
    }

    public Boolean isDisplayTitle() {
        return displayTitle;
    }

    public FeatureCarouselForm setDisplayTitle(boolean displayTitle) {
        this.displayTitle = displayTitle;
        return this;
    }

    @Override
    public String getTileVariant() {
        return tileVariant;
    }

    public String getUpdated() {
        return updated;
    }

    public FeatureCarouselForm setUpdated(String updated) {
        this.updated = updated;
        return this;
    }

    public String getType() {
        return type;
    }

    public FeatureCarouselForm setType(String type) {
        this.type = type;
        return this;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public FeatureCarouselForm setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public FeatureCarouselForm setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((assets == null) ? 0 : assets.hashCode());
        result = prime * result + assetsCount;
        result = prime * result + (displayTitle ? 1231 : 1237);
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((tileVariant == null) ? 0 : tileVariant.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((updated == null) ? 0 : updated.hashCode());
        return result;
    }

    public boolean equalsAssets(List<String> assets, List<String> otherAssets) {
        if (otherAssets == null)
            return false;
        if (assets.size() != otherAssets.size()) {
            return false;
        }
        List<String> newAssets = new LinkedList<>();
        List<String> newOtherAssets = new LinkedList<>();

        for (String asset : assets) {
            String newAsset;
            int index = asset.lastIndexOf("(") - 1;
            if (index > -1) {
                newAsset = asset.substring(0, index);
            } else {
                newAsset = asset;
            }
            newAssets.add(newAsset);
        }

        for (String asset : otherAssets) {
            String newAsset;
            int index = asset.lastIndexOf("(") - 1;
            if (index > -1) {
                newAsset = asset.substring(0, index);
            } else {
                newAsset = asset;
            }
            newOtherAssets.add(newAsset);
        }

        return newAssets.containsAll(newOtherAssets) && newOtherAssets.containsAll(newAssets);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        FeatureCarouselForm featureCarouselForm = (FeatureCarouselForm) o;

        if (slugInfo != null ? !slugInfo.equals(featureCarouselForm.slugInfo) : featureCarouselForm.slugInfo != null)
            return false;
        if (assets != null ? !this.equalsAssets(assets, featureCarouselForm.assets) : featureCarouselForm.assets != null)
            return false;
        if (assetsCount != null ? !assetsCount.equals(featureCarouselForm.assetsCount) : featureCarouselForm.assetsCount != null)
            return false;
        if (displayTitle != null ? !displayTitle.equals(featureCarouselForm.displayTitle) : featureCarouselForm.displayTitle != null)
            return false;
        if (title != null ? !title.equals(featureCarouselForm.title) : featureCarouselForm.title != null)
            return false;
        if (tileVariant != null ? !tileVariant.equals(featureCarouselForm.tileVariant) : featureCarouselForm.tileVariant != null)
            return false;
        if (type != null ? !type.equals(featureCarouselForm.type) : featureCarouselForm.type != null)
            return false;
        if (modifiedDate != null ? !modifiedDate.equals(featureCarouselForm.modifiedDate) : featureCarouselForm.modifiedDate != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Feature Carousel {" + "title='" + title + '\'' + ", displayTitle=" + displayTitle + ", titleVariant='" + tileVariant
                + '\'' + ", assetsCount=" + assetsCount + ", assets=" + assets
                + ", type='" + type + '\'' + ", updated='" + updated + '\'' + ", alias='" + slugInfo
                + '\'' + '}';
    }

    @Override
    public FeatureCarouselForm clone() {
        FeatureCarouselForm featureCarouselForm = new FeatureCarouselForm();
        featureCarouselForm.setAssets(new ArrayList<>(assets));
        featureCarouselForm.setTitle(title);
        featureCarouselForm.setSlug(slugInfo);
        featureCarouselForm.setAssetsCount(assetsCount);
        featureCarouselForm.setDisplayTitle(displayTitle);
        featureCarouselForm.setTitleVariant(tileVariant);
        featureCarouselForm.setType(type);
        featureCarouselForm.setUpdated(updated);
        return featureCarouselForm;
    }
}
