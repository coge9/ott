package com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Shelf extends Module {

    private Boolean displayTitle = null;
    private List<String> assetsID = null;
    private String type = "Shelf";

    private String publishedDate = null;
    private String createdDate = null;
    private String modifiedDate = null;
    private String listId = null;

    public Shelf() {
        super();
    }

    public Shelf(String title, boolean displayTitle, Slug slug, String titleVariant, int assetsCount, String id) {
        super();
        this.title = title;
        this.displayTitle = displayTitle;
        this.slugInfo = slug;
        this.assetsCount = assetsCount;
        this.assets = new ArrayList<>();
        this.id = id;
        this.tileVariant = titleVariant;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Shelf setTitle(String title) {
        this.title = title;
        return this;
    }

    public Boolean isDisplayTitle() {
        return displayTitle;
    }

    public List<String> getAssetsID() {
        return assetsID;
    }

    public void setAssetsID(List<String> assetsID) {
        this.assetsID = assetsID;
    }

    public Shelf setDisplayTitle(boolean displayTitle) {
        this.displayTitle = displayTitle;
        return this;
    }

    public Shelf addAsset(String asset) {
        if (this.assets == null) {
            this.assets = new ArrayList<>();
        }
        this.assets.add(asset);
        return this;
    }

    public String getType() {
        return type;
    }

    public Shelf setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Shelf setId(String id) {
        this.id = id;
        return this;
    }

    public Boolean getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(Boolean displayTitle) {
        this.displayTitle = displayTitle;
    }

    public void setAssetsCount(Integer assetsCount) {
        this.assetsCount = assetsCount;
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

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((assets == null) ? 0 : assets.hashCode());
        result = prime * result + assetsCount;
        result = prime * result + (displayTitle ? 1231 : 1237);
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((super.tileVariant == null) ? 0 : super.tileVariant.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    private boolean equalsAssets(List<String> assets, List<String> otherAssets) {
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

        Shelf shelf = (Shelf) o;

        if (slugInfo != null ? !slugInfo.equals(shelf.slugInfo) : shelf.slugInfo != null)
            return false;
        if (assets != null ? !assets.equals(shelf.assets) : shelf.assets != null)
            return false;
        if (assetsCount != null ? !assetsCount.equals(shelf.assetsCount) : shelf.assetsCount != null)
            return false;
        /*if (displayTitle != null ? !displayTitle.equals(shelf.displayTitle) : shelf.displayTitle != null)
            return false;*/
        if (id != null ? !id.equals(shelf.id) : shelf.id != null)
            return false;
        if (title != null ? !title.equals(shelf.title) : shelf.title != null)
            return false;
        if (tileVariant != null ? !tileVariant.equals(shelf.tileVariant) : shelf.tileVariant != null)
            return false;
        if (type != null ? !type.equals(shelf.type) : shelf.type != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Shelf{" + "title='" + title + '\'' + ", displayTitle=" + displayTitle + ", titleVariant='" + tileVariant + '\''
                + ", assetsCount=" + assetsCount + ", assets=" + assets + ", type='" + type + '\'' + ", id='" + id + '\'' + ", alias='" + slugInfo + '\''
                + '}';
    }

}
