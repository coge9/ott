package com.nbcuni.test.cms.bussinesobjects.tvecms.platform;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.pageobjectutils.entities.Image;

import java.util.Map;

public class PlatformEntity {

    private String name;
    private String machineName;
    private String settings;
    private String mvpdEntitlementServiceUrl;
    private Image globalHeaderBrandLogoImage;
    private Map<String, Boolean> viewPorts;
    private String uuid;
    private String revision;
    private PageForm defaultHomepage = new PageForm();
    private PageForm defaultAllShowsPage = new PageForm();

    public PageForm getDefaultHomepage() {
        return defaultHomepage;
    }

    public void setDefaultHomepage(PageForm defaultHomepage) {
        this.defaultHomepage = defaultHomepage;
    }

    public PageForm getDefaultAllShowsPage() {
        return defaultAllShowsPage;
    }

    public void setDefaultAllShowsPage(PageForm defaultAllShowsPage) {
        this.defaultAllShowsPage = defaultAllShowsPage;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getName() {
        return name;
    }

    public PlatformEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getMachineName() {
        return machineName;
    }

    public PlatformEntity setMachineName(String machineName) {
        this.machineName = machineName;
        return this;
    }

    public String getSettings() {
        return settings;
    }

    public PlatformEntity setSettings(String settings) {
        this.settings = settings;
        return this;
    }

    public String getMvpdEntitlementServiceUrl() {
        return mvpdEntitlementServiceUrl;
    }

    public PlatformEntity setMvpdEntitlementServiceUrl(String mvpdEntitlementServiceUrl) {
        this.mvpdEntitlementServiceUrl = mvpdEntitlementServiceUrl;
        return this;
    }

    public Image getGlobalHeaderBrandLogoImage() {
        return globalHeaderBrandLogoImage;
    }

    public PlatformEntity setGlobalHeaderBrandLogoImage(Image globalHeaderBrandLogoImage) {
        this.globalHeaderBrandLogoImage = globalHeaderBrandLogoImage;
        return this;
    }

    public Map<String, Boolean> getViewPorts() {
        return viewPorts;
    }

    public PlatformEntity setViewPorts(Map<String, Boolean> viewPorts) {
        this.viewPorts = viewPorts;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlatformEntity that = (PlatformEntity) o;
        return Objects.equal(name, that.name) &&
                Objects.equal(machineName, that.machineName) &&
                Objects.equal(settings, that.settings) &&
                Objects.equal(mvpdEntitlementServiceUrl, that.mvpdEntitlementServiceUrl) &&
                Objects.equal(globalHeaderBrandLogoImage, that.globalHeaderBrandLogoImage) &&
                Objects.equal(viewPorts, that.viewPorts) &&
                Objects.equal(uuid, that.uuid) &&
                Objects.equal(revision, that.revision) &&
                Objects.equal(defaultHomepage.getTitle(), that.defaultHomepage.getTitle()) &&
                Objects.equal(defaultAllShowsPage.getTitle(), that.defaultAllShowsPage.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, machineName, settings, mvpdEntitlementServiceUrl, globalHeaderBrandLogoImage,
                viewPorts, uuid, revision, defaultHomepage.getTitle(), defaultAllShowsPage.getTitle());
    }

    /**
     * Don't print image values
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("machineName", machineName)
                .add("settings", settings)
                .add("mvpdEntitlementServiceUrl", mvpdEntitlementServiceUrl)
                .add("globalHeaderBrandLogoImage", globalHeaderBrandLogoImage)
                .add("viewPorts", viewPorts)
                .add("uuid", uuid)
                .add("revision", revision)
                .add("defaultHomepage", defaultHomepage.getTitle())
                .add("defaultAllShowsPage", defaultAllShowsPage.getTitle())
                .toString();
    }

}
