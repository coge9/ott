package com.nbcuni.test.cms.bussinesobjects.tvecms;

import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;

/**
 * Created by Dzianis_Kulesh on 2/7/2017.
 * <p>
 * Class which is going to represent image source in the CMS admin.
 */
public class ImageSource {

    private String name;
    private String machineName;
    private String imageName;
    private String imageUrl;
    private String usage;
    private Boolean isOverriden;
    private CmsPlatforms platform;
    private String uuid;
    private String vid;
    private int width;
    private int height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Boolean getOverriden() {
        return isOverriden;
    }

    public void setOverriden(Boolean overriden) {
        isOverriden = overriden;
    }

    public CmsPlatforms getPlatform() {
        return platform;
    }

    public void setPlatform(CmsPlatforms platform) {
        this.platform = platform;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "ImageSource{" +
                ", machineName='" + machineName + '\'' +
                ", imageName='" + imageName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", usage='" + usage + '\'' +
                ", isOverriden=" + isOverriden +
                ", platform=" + platform +
                ", uuid='" + uuid + '\'' +
                ", vid='" + vid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImageSource that = (ImageSource) o;

        if (machineName != null ? !machineName.equals(that.machineName) : that.machineName != null) {
            return false;
        }
        if (imageName != null ? !imageName.equals(that.imageName) : that.imageName != null) {
            return false;
        }
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null) {
            return false;
        }
        if (usage != null ? !usage.equals(that.usage) : that.usage != null) {
            return false;
        }
        if (isOverriden != null ? !isOverriden.equals(that.isOverriden) : that.isOverriden != null) {
            return false;
        }
        if (platform != that.platform) {
            return false;
        }
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) {
            return false;
        }
        return vid != null ? vid.equals(that.vid) : that.vid == null;

    }

    @Override
    public int hashCode() {
        int result = machineName != null ? machineName.hashCode() : 0;
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (usage != null ? usage.hashCode() : 0);
        result = 31 * result + (isOverriden != null ? isOverriden.hashCode() : 0);
        result = 31 * result + (platform != null ? platform.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (vid != null ? vid.hashCode() : 0);
        return result;
    }
}
