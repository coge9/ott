package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

import com.nbcuni.test.cms.utils.jsonparsing.services.branddetails.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Brand {
    private String brandName;
    private String brandRequestorId;
    private List<Resource> resources = new ArrayList<Resource>();
    private String brandK2Id;
    private boolean disableNewWindowWorkflow;
    private boolean spanishLanguage = false;
    private Map<String, Boolean> mvpdLogoImageType = new HashMap<String, Boolean>();
    private List<MvpdLogo> logos = new ArrayList<MvpdLogo>();
    private MvpdLogoSettings mvpdLogoSettings;

    public Brand(final String id, final String brandName,
                 final String brandRequestorId, final String brandK2Id,
                 final boolean disableNewWindowWorkflow,
                 final boolean spanishLanguage) {
        super();
        this.brandName = brandName;
        this.brandRequestorId = brandRequestorId;
        this.brandK2Id = brandK2Id;
        this.disableNewWindowWorkflow = disableNewWindowWorkflow;
        this.spanishLanguage = spanishLanguage;
    }

    public Brand() {
        super();
    }

    @Override
    public String toString() {
        return "Brand [brandName=" + brandName + ", brandRequestorId="
                + brandRequestorId + ", resources=" + resources
                + ", brandK2Id=" + brandK2Id + ", disableNewWindowWorkflow="
                + disableNewWindowWorkflow + ", spanishLanguage="
                + spanishLanguage + "]";
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(final String brandName) {
        this.brandName = brandName;
    }

    public String getBrandRequestorId() {
        return brandRequestorId;
    }

    public void setBrandRequestorId(final String brandRequestorId) {
        this.brandRequestorId = brandRequestorId;
    }

    public int getNumberOfResourceIds() {
        return resources.size();
    }

    public Resource getResource(int index) {
        if (index <= (resources.size() - 1)) {
            return resources.get(index);
        } else
            return null;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public void addResource(String id, String name) {
        Resource resource = new Resource(id, name);
        resources.add(resource);
    }

    public void clearResources() {
        resources.clear();
    }

    public String getBrandK2Id() {
        return brandK2Id;
    }

    public void setBrandK2Id(final String brandK2Id) {
        this.brandK2Id = brandK2Id;
    }

    public boolean isDisableNewWindowWorkflow() {
        return disableNewWindowWorkflow;
    }

    public void setDisableNewWindowWorkflow(
            final boolean disableNewWindowWorkflow) {
        this.disableNewWindowWorkflow = disableNewWindowWorkflow;
    }

    public boolean isSpanishLanguage() {
        return spanishLanguage;
    }

    public void setSpanishLanguage(boolean spanishLanguage) {
        this.spanishLanguage = spanishLanguage;
    }

    public Map<String, Boolean> getMvpdLogoImageType() {
        return mvpdLogoImageType;
    }

    public void setMvpdLogoImageType(Map<String, Boolean> mvpdLogoImageType) {
        this.mvpdLogoImageType = mvpdLogoImageType;
    }

    public void addMvpdLogoImageType(String platform, boolean status) {
        mvpdLogoImageType.put(platform, status);
    }

    public List<MvpdLogo> getLogos() {
        return logos;
    }

    public void setLogos(final List<MvpdLogo> logos) {
        this.logos = logos;
    }

    public MvpdLogo getLogoByType(LogoTypes type) {
        for (MvpdLogo logo : logos) {
            if (type.equals(logo.getType())) {
                return logo;
            }
        }
        return null;
    }

    public void clearLogos() {
        this.getLogos().clear();
    }

    public void removeLogo(MvpdLogo logo) {
        logos.remove(logo);
    }

    public void addLogo(final String path, final String type) {
        LogoTypes logoType = null;
        for (final LogoTypes typeElement : LogoTypes.values()) {
            if (typeElement.get().equals(type)) {
                logoType = typeElement;
            }
        }
        final MvpdLogo logo = new MvpdLogo(logoType, path);
        logos.add(logo);
    }

    public void addLogo(MvpdLogo logo) {
        logos.add(logo);
    }

    public MvpdLogoSettings getMvpdLogoSettings() {
        return mvpdLogoSettings;
    }

    public void setMvpdLogoSettings(MvpdLogoSettings mvpdLogoSettings) {
        this.mvpdLogoSettings = mvpdLogoSettings;
    }

    public void addMvpdLogoSetting(final SelectLogoSection section,
                                   Platform platform, SelectLogoType type) {
        MvpdLogoSettingItem setting = new MvpdLogoSettingItem();
        setting.setBrandName(brandRequestorId);
        setting.setSectionName(section);
        setting.setPlatform(platform);
        setting.setType(type);
        addMvpdLogoSetting(setting);
    }

    public void addMvpdLogoSetting(MvpdLogoSettingItem setting) {
        mvpdLogoSettings.getMvpdLogoSetings().add(setting);
    }
}