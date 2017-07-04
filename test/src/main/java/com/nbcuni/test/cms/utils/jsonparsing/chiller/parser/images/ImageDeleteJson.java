package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;

/**
 * Created by Ivan_Karnilau on 07-Jul-16.
 */
public class ImageDeleteJson extends AbstractEntity implements Cloneable {
    private String uuid;
    private String itemType;

    public ImageDeleteJson() {
        super();
    }

    public ImageDeleteJson(FilesMetadata filesMetadata) {
        getObject(filesMetadata);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    private ImageDeleteJson getObject(FilesMetadata filesMetadata) {
        this.uuid = filesMetadata.getImageGeneralInfo().getUuid();
        this.itemType = filesMetadata.getType().getItemType();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImageDeleteJson that = (ImageDeleteJson) o;
        return Objects.equal(uuid, that.uuid) &&
                Objects.equal(itemType, that.itemType);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uuid", uuid)
                .add("itemType", itemType)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid, itemType);
    }
}
