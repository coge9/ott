package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class CuratedListItemJson implements ContentItemListObject {

    @SerializedName("id")
    private String id;

    @SerializedName("itemType")
    private String itemType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getItemType() {
        return itemType;
    }

    @Override
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuratedListItemJson that = (CuratedListItemJson) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(itemType, that.itemType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, itemType);
    }

    @Override
    public String toString() {
        return "CuratedListItemJson id = " + id + ", type = " + itemType;
    }


}
