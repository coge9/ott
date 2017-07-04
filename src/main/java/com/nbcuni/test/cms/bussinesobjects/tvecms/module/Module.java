package com.nbcuni.test.cms.bussinesobjects.tvecms.module;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.utils.transformers.GlobalNodeJsonToContentTransformer;

import java.util.*;

/**
 * Created by Ivan_Karnilau on 27-Jan-16.
 */
public class Module implements Cloneable {

    protected String uuid = null;
    protected Slug slugInfo = new Slug();
    protected Integer revision = null;
    protected String tileVariant = null;
    protected List<String> assets = new ArrayList<String>();
    protected Integer assetsCount = 0;
    protected String id = null;
    protected String title = null;
    private List<Content> contents = new ArrayList<>();
    private Map<Content, Boolean> contentEnabled = new HashMap<>();

    public String getTileVariant() {
        return tileVariant;
    }

    public Module setTitleVariant(String tile) {
        this.tileVariant = tile;
        return this;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision != null ? Integer.valueOf(revision) : null;
    }

    public Slug getSlug() {
        return slugInfo;
    }

    public Module setSlug(Slug slug) {
        slugInfo = slug;
        return this;
    }

    public List<Content> getContents() {
        return contents;
    }

    public Module setContents(List<Content> contents) {
        this.contents = contents;
        this.contentEnabled = new LinkedHashMap<>();
        for (Content content : contents) {
            contentEnabled.put(content, true);
        }
        return this;
    }

    public Map<Content, Boolean> getContentEnabled() {
        return contentEnabled;
    }

    public Module setContentEnabled(Map<Content, Boolean> contentEnabled) {
        this.contentEnabled = contentEnabled;
        return this;
    }

    public List<String> getAssets() {
        return assets;
    }

    public Module setAssets(String asset) {
        assets.add(asset);
        if (assetsCount != null) {
            assetsCount++;
        } else {
            assetsCount = 1;
        }
        return this;
    }

    public Module setAssets(List<String> assets) {
        this.assets.addAll(assets);
        this.assetsCount = assets.size();
        for (String asset : assets) {
            GlobalNodeJsonToContentTransformer jsonToContentTransformer = new GlobalNodeJsonToContentTransformer();
            contentEnabled.put(jsonToContentTransformer.transformToContent(asset), true);
        }
        contents.addAll(contentEnabled.keySet());
        return this;
    }

    public int getAssetsCount() {
        return assetsCount;
    }

    public Module setAssetsCount(int assetsCount) {
        this.assetsCount = assetsCount;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Module setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getId() {
        return id;
    }

    public Module setId(String id) {
        this.id = id;
        return this;
    }
}
