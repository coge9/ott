package com.nbcuni.test.cms.bussinesobjects.chiller.collections;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.pageobjectutils.Page;

/**
 * Created by Alena_Aukhukova on 5/31/2016.
 */
public abstract class Collection extends AbstractEntity {

    protected GeneralInfo generalInfo;
    private Associations associations = new Associations();
    private Slug slugInfo = new Slug();
    private CollectionInfo collectionInfo = new CollectionInfo();

    public abstract Class<? extends Page> getPage();

    public abstract String getTitle();

    public Associations getAssociations() {
        return associations;
    }

    public Collection setAssociations(Associations associations) {
        this.associations = associations;
        return this;
    }

    public GeneralInfo getGeneralInfo() {
        return generalInfo;
    }

    public Collection setGeneralInfo(GeneralInfo generalInfo) {
        this.generalInfo = generalInfo;
        return this;
    }

    public Slug getSlugInfo() {
        return slugInfo;
    }

    public void setSlugInfo(Slug slugInfo) {
        this.slugInfo = slugInfo;
    }

    public CollectionInfo getCollectionInfo() {
        return collectionInfo;
    }

    public void setCollectionInfo(CollectionInfo collectionInfo) {
        this.collectionInfo = collectionInfo;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("generalInfo", generalInfo)
                .add("associations", associations)
                .add("slugInfo", slugInfo)
                .add("collectionInfo", collectionInfo)
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
        Collection that = (Collection) o;
        return Objects.equal(generalInfo, that.generalInfo) &&
                Objects.equal(associations, that.associations) &&
                Objects.equal(slugInfo, that.slugInfo) &&
                Objects.equal(collectionInfo, that.collectionInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(generalInfo, associations, slugInfo, collectionInfo);
    }
}
