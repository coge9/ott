package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser;

import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;

/**
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public class MediaJson extends PromoMedia {

    private String usage;

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public MediaJson setObject(MediaImage mediaImage) {
        setItemType(ITEM_TYPE);
        setUsage(mediaImage.getUsage());
        setUuid(mediaImage.getUuid());
        return this;
    }

    @Override
    public String toString() {
        return "MediaJson{" +
                "usage='" + usage + '\'' +
                "uuid='" + uuid + '\'' +
                ", itemType='" + itemType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MediaJson that = (MediaJson) o;

        return Objects.equal(this.usage, that.usage) &&
                Objects.equal(this.uuid, that.uuid) &&
                Objects.equal(this.itemType, that.itemType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(usage, uuid, itemType);
    }
}
