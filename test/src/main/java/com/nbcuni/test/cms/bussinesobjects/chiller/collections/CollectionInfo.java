package com.nbcuni.test.cms.bussinesobjects.chiller.collections;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alena_Aukhukova on 5/31/2016.
 */
public class CollectionInfo {

    //type for publishing
    private ItemTypes itemType = ItemTypes.COLLECTIONS;

    private int itemsCount = 0;

    //type for filtering on Collection page
    private String type;

    private List<String> items = new ArrayList<>();

    //fields for publishing verification services
    private List<Content> contentItems = new ArrayList<>();
    private List<Collection> contentCollectionItems = new ArrayList<>();

    public String getType() {
        return type;
    }

    public CollectionInfo setType(CollectionType collectionType) {
        this.type = collectionType.getValue();
        return this;
    }

    public ItemTypes getItemType() {
        return itemType;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public CollectionInfo setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
        return this;
    }

    public List<String> getItems() {
        return items;
    }

    public CollectionInfo setItems(List<String> items) {
        this.items = items;
        return this;
    }

    public CollectionInfo addItem(String item) {
        this.items.add(item);
        return this;
    }

    public List<Content> getContentItems() {
        return contentItems;
    }

    public void setContentItems(List<Content> contentItems) {
        this.contentItems = contentItems;
    }

    public List<Collection> getContentCollectionItems() {
        return contentCollectionItems;
    }

    public void setContentCollectionItems(List<Collection> contentCollectionItems) {
        this.contentCollectionItems = contentCollectionItems;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("itemType", itemType)
                .add("itemsCount", itemsCount)
                .add("type", type)
                .add("items", items)
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
        CollectionInfo that = (CollectionInfo) o;
        return itemsCount == that.itemsCount &&
                Objects.equal(itemType, that.itemType) &&
                Objects.equal(type, that.type) &&
                Objects.equal(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(itemType, itemsCount, type, items);
    }

    public enum CollectionType {
        CURATED_COLLECTION("Curated Collection"),
        COLLECTION_GROUP("Collection Group");
        private String value;

        CollectionType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
