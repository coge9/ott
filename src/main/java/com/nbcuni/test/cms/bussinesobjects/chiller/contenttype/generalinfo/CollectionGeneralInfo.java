package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.pageobjectutils.chiller.collection.TileType;

/**
 * Created by Ivan_Karnilau on 6/21/2017.
 */
public class CollectionGeneralInfo extends GeneralInfo {

    private TileType tileType;
    private Boolean displayTitle;

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public Boolean getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(Boolean displayTitle) {
        this.displayTitle = displayTitle;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", getTitle())
                .add("uuid", getUuid())
                .add("tileType", tileType)
                .add("byline", getByline())
                .add("displayTitle", displayTitle)
                .add("dateLine", getDateLine())
                .add("subtitle", getSubhead())
                .add("shortDescription", getShortDescription())
                .add("mediumDescription", getMediumDescription())
                .add("longDescription", getLongDescription())
                .add("sortTitle", getSortTitle())
                .add("revision", getRevision())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollectionGeneralInfo)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        CollectionGeneralInfo that = (CollectionGeneralInfo) o;
        return tileType == that.tileType &&
                Objects.equal(displayTitle, that.displayTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), tileType, displayTitle);
    }
}
