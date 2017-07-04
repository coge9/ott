package com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.APIType;

/**
 * Created by Dzianis Kulesh on 3/23/2017.
 */
public class PlatformNodeJson {

    /**
     * @author Dzianis Kulesh
     * <p/>
     * This class is represent concrete node json for Content API
     */

    private String name;
    private String title;

    @SerializedName("machine_name")
    private String machineName;
    private String uid;

    @SerializedName("app_type")
    private String appType;

    private APIType serviceInstance;

    @SerializedName("page_default")
    private String pageDefault;

    @SerializedName("page_all_shows")
    private String pageAllShows;

    private String uuid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public APIType getServiceInstance() {
        return serviceInstance;
    }

    public void setServiceInstance(APIType serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    public String getPageDefault() {
        return pageDefault;
    }

    public void setPageDefault(String pageDefault) {
        this.pageDefault = pageDefault;
    }

    public String getPageAllShows() {
        return pageAllShows;
    }

    public void setPageAllShows(String pageAllShows) {
        this.pageAllShows = pageAllShows;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlatformNodeJson that = (PlatformNodeJson) o;
        return Objects.equal(name, that.name) &&
                Objects.equal(title, that.title) &&
                Objects.equal(machineName, that.machineName) &&
                Objects.equal(uid, that.uid) &&
                Objects.equal(appType, that.appType) &&
                serviceInstance == that.serviceInstance &&
                Objects.equal(pageDefault, that.pageDefault) &&
                Objects.equal(pageAllShows, that.pageAllShows) &&
                Objects.equal(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, title, machineName, uid, appType, serviceInstance, pageDefault, pageAllShows, uuid);
    }

    @Override
    public String toString() {
        return "PlatformNodeJson{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", machineName='" + machineName + '\'' +
                ", uid='" + uid + '\'' +
                ", appType='" + appType + '\'' +
                ", serviceInstance=" + serviceInstance +
                ", pageDefault='" + pageDefault + '\'' +
                ", pageAllShows='" + pageAllShows + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
