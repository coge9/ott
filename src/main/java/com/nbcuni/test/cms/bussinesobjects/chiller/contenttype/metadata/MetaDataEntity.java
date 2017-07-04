package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;

/**
 * Created by Alena_Aukhukova on 6/20/2016.
 */
public abstract class MetaDataEntity extends Content {
    protected MetadataInfo metadataInfo = new MetadataInfo();

    public MetadataInfo getMetadataInfo() {
        return metadataInfo;
    }

    public void setMetadataInfo(MetadataInfo metadataInfo) {
        this.metadataInfo = metadataInfo;
    }

    @Override
    public String getTitle() {
        String firstName = metadataInfo.getFirstName();
        String lastName = metadataInfo.getLastName();

        if (lastName == null) {
            return firstName;
        } else {
            return firstName + " " + lastName;
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("metadataInfo", metadataInfo)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}

        MetaDataEntity that = (MetaDataEntity) o;

        return Objects.equal(this.metadataInfo, that.metadataInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(metadataInfo);
    }
}
