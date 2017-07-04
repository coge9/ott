package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;


public class Entitlement {

    private Platform platform;
    private Instance instance;
    private String brandName;
    private Boolean whiteListed;

    public Entitlement(Platform platform, Instance instance, String brandName,
                       Boolean whiteListed) {
        super();
        this.platform = platform;
        this.instance = instance;
        this.brandName = brandName;
        this.whiteListed = whiteListed;
    }

    public Boolean isWhiteListed() {
        return whiteListed;
    }

    public void setWhiteListed(Boolean whiteListed) {
        this.whiteListed = whiteListed;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "Entitlement [platform=" + platform + ", instance=" + instance
                + ", brandName=" + brandName + ", whiteListed=" + whiteListed
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((brandName == null) ? 0 : brandName.hashCode());
        result = prime * result
                + ((instance == null) ? 0 : instance.hashCode());
        result = prime * result
                + ((platform == null) ? 0 : platform.hashCode());
        result = prime * result
                + ((whiteListed == null) ? 0 : whiteListed.hashCode());
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
        Entitlement other = (Entitlement) obj;
        if (brandName == null) {
            if (other.brandName != null)
                return false;
        } else if (!brandName.equals(other.brandName))
            return false;
        if (instance != other.instance)
            return false;
        if (platform != other.platform)
            return false;
        if (whiteListed == null) {
            if (other.whiteListed != null)
                return false;
        } else if (!whiteListed.equals(other.whiteListed))
            return false;
        return true;
    }

}
