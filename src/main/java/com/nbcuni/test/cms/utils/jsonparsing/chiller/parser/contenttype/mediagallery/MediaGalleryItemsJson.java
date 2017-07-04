package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.mediagallery;

import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.PromoMedia;

/**
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public class MediaGalleryItemsJson extends PromoMedia {

    private String caption;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String usage) {
        this.caption = usage;
    }

    public MediaGalleryItemsJson setObject(MediaImage mediaImage) {
        setItemType(ITEM_TYPE);
        setCaption("");
        setUuid(mediaImage.getUuid());
        return this;
    }


    @Override
    public String toString() {
        return "MediaJson{" +
                "caption='" + caption + '\'' +
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

        MediaGalleryItemsJson that = (MediaGalleryItemsJson) o;

        return Objects.equal(this.caption, that.caption) &&
                Objects.equal(this.uuid, that.uuid) &&
                Objects.equal(this.itemType, that.itemType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(caption, uuid, itemType);
    }
}
