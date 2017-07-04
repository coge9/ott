package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by Alena_Aukhukova on 5/4/2016.
 */
public class ListItemsJson {

    private String uuid;
    private String itemType;

    public ListItemsJson(String uuid, String itemType) {
        this.uuid = uuid;
        this.itemType = itemType;
    }

    public ListItemsJson() {
    }

    public String getUuid() {
        return uuid;
    }

    public ListItemsJson setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getItemType() {
        return itemType;
    }

    public ListItemsJson setItemType(String itemType) {
        this.itemType = itemType;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uuid", uuid)
                .add("itemType", itemType)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListItemsJson that = (ListItemsJson) o;

        return Objects.equal(this.uuid, that.uuid) &&
                Objects.equal(this.itemType, that.itemType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid, itemType);
    }
}
