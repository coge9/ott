package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.annotations.SerializedName;


public class AbstractNode {

    @SerializedName("itemType")
    protected String itemType;

    @SerializedName("title")
    protected String title;

    @SerializedName("id")
    protected String id;

    @SerializedName("mpxId")
    protected String mpxId;

    @SerializedName("description")
    protected String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return itemType;
    }

    public void setType(String type) {
        this.itemType = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMpxId() {
        return mpxId;
    }

    public void setMpxId(String mpxId) {
        this.mpxId = mpxId;
    }
}
