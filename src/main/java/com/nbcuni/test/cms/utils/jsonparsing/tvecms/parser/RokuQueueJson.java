package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.VoidShelf;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.CuratedListType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RokuQueueJson implements Metadata {

    @SerializedName("id")
    private String id;

    @SerializedName("listType")
    private String listType;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("appId")
    private List<String> appId;

    @SerializedName("items")
    private List<ContentItemListObject> items;

    @Override
    public String getMpxId() {
        return null;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return null;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ContentItemListObject> getItems() {
        return items;
    }

    public void setItems(List<ContentItemListObject> items) {

        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAppId() {
        return appId;
    }

    public void setAppId(List<String> appId) {
        this.appId = appId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RokuQueueJson that = (RokuQueueJson) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(listType, that.listType) &&
                Objects.equal(name, that.name) &&
                Objects.equal(description, that.description) &&
                Objects.equal(appId, that.appId) &&
                Objects.equal(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, listType, name, description, appId, items);
    }

    public RokuQueueJson getObject(List<ContentItemListObject> itemsJson, CuratedListType type, Module module, List<String> platforms) {
        setId(module.getId());
        setModuleIndependentSettings(itemsJson, type, platforms);
        setName(module.getSlug().getSlugValue());
        return this;
    }

    public RokuQueueJson getObject(List<ContentItemListObject> itemsJson, CuratedListType type, Shelf shelf, List<String> platforms) {
        setId(shelf.getId());
        setModuleIndependentSettings(itemsJson, type, platforms);
        setName(shelf.getSlug().getSlugValue());
        return this;
    }

    public RokuQueueJson getObject(String brand, CuratedListType type, Module module, PageForm... pageInfo) {
        List<ContentItemListObject> itemList = new ArrayList<>();
        List<CuratedListItemJson> itemsTemp = new ArrayList<>();

        for (Map.Entry<Content, Boolean> entry : module.getContentEnabled().entrySet()) {

            Content content = entry.getKey();
            if (entry.getValue()) {
                CuratedListItemJson item = new CuratedListItemJson();
                switch (content.getType()) {
                    case SERIES: {
                        item.setId(((GlobalProgramEntity) content).getMpxAsset().getGuid());
                        item.setItemType("program");
                        itemsTemp.add(item);
                        break;
                    }
                    case VIDEO: {
                        item.setId(((GlobalVideoEntity) content).getMpxAsset().getGuid());
                        item.setItemType("video");
                        itemsTemp.add(item);
                        break;
                    }
                    default:
                        break;
                }
            }
        }
        itemList.addAll(itemsTemp);

        List<String> platforms = new ArrayList<>();
        if (pageInfo.length == 0) {
            platforms.add(CmsPlatforms.ROKU.getAppId(brand));
        } else {
            for (PageForm pageForm : pageInfo) {
                platforms.add(pageForm.getPlatform().getAppId(brand));
            }
        }
        return new RokuQueueJson().getObject(itemList, type, module, platforms);
    }

    public RokuQueueJson getObject(List<ContentItemListObject> itemsJson, CuratedListType type, VoidShelf shelf, List<String> platforms) {
        setId(shelf.getId());
        setModuleIndependentSettings(itemsJson, type, platforms);
        setName(shelf.getAlias().getSlugValue());
        return this;
    }

    private void setModuleIndependentSettings(List<ContentItemListObject> itemJson, CuratedListType type, List<String> platforms) {
        setDescription("n/a");
        setListType(type.toString().toLowerCase());
        setItems(itemJson);
        setAppId(platforms);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("listType", listType)
                .add("name", name)
                .add("description", description)
                .add("appId", appId)
                .add("items", items)
                .toString();
    }
}
