package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTerm;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.webdriver.Utilities;

/**
 * Created by Ivan_Karnilau on 07-Jul-16.
 */
public class ContentTypeDeleteJson extends AbstractEntity implements Cloneable {
    private String uuid;
    private String itemType;

    public ContentTypeDeleteJson() {
        super();
    }

    public ContentTypeDeleteJson(Content content) {
        getObject(content);
    }

    public ContentTypeDeleteJson(TaxonomyTerm term) {
        getObject(term);
    }

    public ContentTypeDeleteJson(FilesMetadata filesMetadata) {
        getObject(filesMetadata);
    }

    public ContentTypeDeleteJson(PlatformEntity entity) {
        this.uuid = entity.getUuid();
        this.itemType = "platform";
    }

    public ContentTypeDeleteJson(Collection collection) {
        this.uuid = collection.getGeneralInfo().getUuid();
        this.itemType = collection.getCollectionInfo().getItemType().getItemType();
    }

    public ContentTypeDeleteJson(PageForm pageForm) {
        this.uuid = pageForm.getUuid();
        this.itemType = ItemTypes.COLLECTIONS.getItemType();
    }

    public ContentTypeDeleteJson(Module module) {
        this.uuid = module.getUUID();
        this.itemType = ItemTypes.COLLECTIONS.getItemType();
    }

    private ContentTypeDeleteJson getObject(TaxonomyTerm term) {
        this.uuid = term.getUuid();
        this.itemType = term.getType().getItemType();
        return this;
    }

    private ContentTypeDeleteJson getObject(FilesMetadata filesMetadata) {
        this.uuid = filesMetadata.getImageGeneralInfo().getUuid();
        this.itemType = filesMetadata.getType().getItemType();
        return this;
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

    private ContentTypeDeleteJson getObject(Content content) {
        this.uuid = content.getGeneralInfo().getUuid();
        this.itemType = content.getType().getItemType();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ContentTypeDeleteJson that = (ContentTypeDeleteJson) o;
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

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
        return null;
    }
}
