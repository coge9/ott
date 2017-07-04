package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection;

import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ChannelReferencesJson;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alena_Aukhukova on 5/4/2016.
 */
public class CollectionJson extends AbstractEntity implements Cloneable {

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("itemType")
    private String itemType;

    @SerializedName("revision")
    private Integer revision;

    @SerializedName("title")
    private String title;

    @SerializedName("slug")
    private String slug;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("mediumDescription")
    private String mediumDescription;

    @SerializedName("longDescription")
    private String longDescription;

    @SerializedName("tileType")
    private Integer tileType;

    @SerializedName("categories")
    private List<String> categories;

    @SerializedName("tags")
    private List<String> tags;

    @SerializedName("listItems")
    private List<ListItemsJson> listItems;

    @SerializedName("program")
    private ChannelReferencesJson program;

    public CollectionJson(Collection collection) {
        getObject(collection);
    }

    public CollectionJson(Module module) {
        getObject(module);
    }

    public CollectionJson(PageForm page) {
        getObject(page);
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

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getMediumDescription() {
        return mediumDescription;
    }

    public void setMediumDescription(String mediumDescription) {
        this.mediumDescription = mediumDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<ListItemsJson> getListItems() {
        return listItems;
    }

    public void setListItems(List<ListItemsJson> listItems) {
        this.listItems = listItems;
    }

    public ChannelReferencesJson getProgram() {
        return program;
    }

    public void setProgram(ChannelReferencesJson program) {
        this.program = program;
    }

    public int getTileType() {
        return tileType;
    }

    public void setTileType(int tileType) {
        this.tileType = tileType;
    }

    private CollectionJson getObject(Collection collection) {
        this.uuid = collection.getGeneralInfo().getUuid();
        this.itemType = collection.getCollectionInfo().getItemType().getItemType();
        this.revision = collection.getGeneralInfo().getRevision();
        this.title = collection.getTitle();
        this.slug = collection.getSlugInfo().getSlugValue();
        this.mediumDescription = collection.getGeneralInfo().getMediumDescription();
        this.shortDescription = collection.getGeneralInfo().getShortDescription();
        this.longDescription = collection.getGeneralInfo().getLongDescription();


        this.listItems = new ArrayList<>();
        List<Content> contentTypeItems = collection.getCollectionInfo().getContentItems();
        if (CollectionUtils.isNotEmpty(contentTypeItems)) {
            for (Content content : contentTypeItems) {
                String contentUuid = content.getGeneralInfo().getUuid();
                String contentItemType = content.getType().getItemType();
                this.listItems.add(new ListItemsJson(contentUuid, contentItemType));
            }
        }
        List<Collection> contentCollectionItems = collection.getCollectionInfo().getContentCollectionItems();
        if (CollectionUtils.isNotEmpty(contentCollectionItems)) {
            for (Collection content : contentCollectionItems) {
                String contentItemType = content.getCollectionInfo().getItemType().getItemType();
                this.listItems.add(new ListItemsJson(null, contentItemType));
            }
        }
        this.program = new ChannelReferencesJson().getObject(collection.getAssociations().getChannelReferenceAssociations().getChannelReference());
        return this;
    }

    private CollectionJson getObject(Module module) {
        this.uuid = module.getUUID();
        this.itemType = ItemTypes.COLLECTIONS.getItemType();
        this.revision = module.getRevision();
        this.title = module.getTitle();
        this.slug = module.getSlug().getSlugValue();
        this.mediumDescription = null;
        this.shortDescription = null;
        this.longDescription = null;
        this.tileType = Integer.parseInt(SimpleUtils.getNumbersFromString(module.getTileVariant()));
        this.categories = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.program = new ChannelReferencesJson().setProgramItemType(ItemTypes.SERIES.getItemType());

        this.listItems = new LinkedList<>();
        if (module.getContents() != null) {
            for (Content content : module.getContents()) {
                if (module.getContentEnabled().get(content)) {
                    this.listItems.add(new ListItemsJson(content.getGeneralInfo().getUuid(), content.getType().getItemType()));
                }
            }
        }
        return this;
    }


    private CollectionJson getObject(PageForm page) {
        this.uuid = page.getUuid();
        this.itemType = ItemTypes.COLLECTIONS.getItemType();
        this.revision = Integer.valueOf(page.getRevision());
        this.title = page.getTitle();
        this.slug = page.getAlias().getSlugValue();
        this.mediumDescription = null;
        this.shortDescription = null;
        this.longDescription = null;
        this.tileType = null;
        this.categories = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.program = new ChannelReferencesJson().setProgramItemType(ItemTypes.SERIES.getItemType());

        this.listItems = page.getItemsJson();
        return this;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean equals(Object obj) {
        Utilities.logInfoMessage("Comparison of two Collection objects from Amazon API JSON");
        if (this == obj) {
            Utilities.logInfoMessage("Validation passed.");
            return true;
        }
        if (obj == null) {
            Utilities.logSevereMessage("There is error in validation");
            return false;
        }
        if (getClass() != obj.getClass()) {
            Utilities.logSevereMessage("Wrong object is passed.");
            return false;
        }
        CollectionJson other = (CollectionJson) obj;
        return other.verifyObject(this);
    }

}
