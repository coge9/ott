package com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto.platform;

import com.google.common.base.MoreObjects;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformEntity;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Ivan_Karnilau on 11/17/2016.
 */
public class PlatformJson extends AbstractEntity implements Cloneable {

    private String uuid;
    private String itemType;
    private int revision;
    private String appId;
    private String defaultHomepageCollection;
    private String defaultSeriesPageCollection;
    private SettingsJson settings;

    public PlatformJson() {
    }

    public PlatformJson(PlatformEntity platform) {
        this.uuid = platform.getUuid();
        this.revision = Integer.parseInt(platform.getRevision());
        this.appId = platform.getMachineName();
        this.itemType = ItemTypes.PLATFORM.getItemType();
        this.defaultHomepageCollection = platform.getDefaultHomepage() != null ? platform.getDefaultHomepage().getUuid() : null;
        this.defaultSeriesPageCollection = platform.getDefaultAllShowsPage() != null ? platform.getDefaultAllShowsPage().getUuid() : null;
        if (!platform.getSettings().isEmpty()) {
            SettingsJson settingsJson = new SettingsJson();
            try {
                settingsJson.setId((String) new ObjectMapper().readValue(platform.getSettings(), HashMap.class).get("id"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.settings = settingsJson;
        }
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDefaultHomepageCollection() {
        return defaultHomepageCollection;
    }

    public void setDefaultHomepageCollection(String defaultHomepageCollection) {
        this.defaultHomepageCollection = defaultHomepageCollection;
    }

    public String getDefaultSeriesPageCollection() {
        return defaultSeriesPageCollection;
    }

    public void setDefaultSeriesPageCollection(String defaultSeriesPageCollection) {
        this.defaultSeriesPageCollection = defaultSeriesPageCollection;
    }

    public SettingsJson getSettings() {
        return settings;
    }

    public void setSettings(SettingsJson settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uuid", uuid)
                .add("itemType", itemType)
                .add("revision", revision)
                .add("appId", appId)
                .add("defaultHomepageCollection", defaultHomepageCollection)
                .add("defaultSeriesPageCollection", defaultSeriesPageCollection)
                .add("settings", settings)
                .toString();
    }
}
