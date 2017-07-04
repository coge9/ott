package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser;

import com.google.common.base.Objects;

/**
 * Created by Aleksandra_Lishaeva on 5/24/16.
 */
public class PromoMedia {
    protected String ITEM_TYPE = "image";

    protected String uuid;
    protected String itemType;

    public String getUuid() {
        return uuid;
    }

    public PromoMedia setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getItemType() {
        return itemType;
    }

    public PromoMedia setItemType(String itemType) {
        this.itemType = itemType;
        return this;
    }

    public PromoMedia setObject() {
        setItemType(ITEM_TYPE);
        setUuid(this.uuid);
        return this;
    }

    @Override
    public String toString() {
        return "PromoMedia{" +
                "uuid='" + uuid + '\'' +
                ", itemType='" + itemType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromoMedia that = (PromoMedia) o;
        return Objects.equal(uuid, that.uuid) &&
                Objects.equal(itemType, that.itemType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid, itemType);
    }
}
