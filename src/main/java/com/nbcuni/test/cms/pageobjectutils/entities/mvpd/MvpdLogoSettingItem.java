package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

public class MvpdLogoSettingItem {

    private String brandName;
    private SelectLogoSection section;
    private Platform platform;
    private SelectLogoType type;
    private String imageServiceMethod;
    private LogoFallBackType fallbackLogo;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public SelectLogoSection getSectionName() {
        return section;
    }

    public void setSectionName(SelectLogoSection section) {
        this.section = section;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public SelectLogoType getType() {
        return type;
    }

    public void setType(SelectLogoType type) {
        this.type = type;
    }

    public SelectLogoSection getSection() {
        return section;
    }

    public void setSection(SelectLogoSection section) {
        this.section = section;
    }

    public String getImageServiceMethod() {
        return imageServiceMethod;
    }

    public void setImageServiceMethod(String imageServiceMethod) {
        this.imageServiceMethod = imageServiceMethod;
    }

    public LogoFallBackType getFallbackLogo() {
        return fallbackLogo;
    }

    public void setFallbackLogo(LogoFallBackType fallbackLogo) {
        this.fallbackLogo = fallbackLogo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((brandName == null) ? 0 : brandName.hashCode());
        result = prime * result
                + ((fallbackLogo == null) ? 0 : fallbackLogo.hashCode());
        result = prime
                * result
                + ((imageServiceMethod == null) ? 0 : imageServiceMethod
                .hashCode());
        result = prime * result
                + ((platform == null) ? 0 : platform.hashCode());
        result = prime * result + ((section == null) ? 0 : section.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MvpdLogoSettingItem other = (MvpdLogoSettingItem) obj;
        if (brandName == null) {
            if (other.brandName != null)
                return false;
        } else if (!brandName.equals(other.brandName))
            return false;
        if (fallbackLogo != other.fallbackLogo)
            return false;
        if (imageServiceMethod == null) {
            if (other.imageServiceMethod != null)
                return false;
        } else if (!imageServiceMethod.equals(other.imageServiceMethod))
            return false;
        if (platform != other.platform)
            return false;
        if (section != other.section)
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MvpdLogoSettingItem [brandName=" + brandName + ", section="
                + section + ", platform=" + platform + ", type=" + type
                + ", imageServiceMethod=" + imageServiceMethod
                + ", fallbackLogo=" + fallbackLogo + "]";
    }
}
