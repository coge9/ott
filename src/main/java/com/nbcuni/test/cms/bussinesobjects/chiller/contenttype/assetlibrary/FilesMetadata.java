package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

/**
 * Created by Ivan_Karnilau on 12-May-16.
 */
public class FilesMetadata {

    private ImageGeneralInfo imageGeneralInfo = new ImageGeneralInfo();
    private Associations associations = new Associations();

    public ImageGeneralInfo getImageGeneralInfo() {
        return imageGeneralInfo;
    }

    public void setImageGeneralInfo(ImageGeneralInfo imageGeneralInfo) {
        this.imageGeneralInfo = imageGeneralInfo;
    }

    public Associations getAssociations() {
        return associations;
    }

    public void setAssociations(Associations associations) {
        this.associations = associations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FilesMetadata that = (FilesMetadata) o;

        if (imageGeneralInfo != null ? !imageGeneralInfo.equals(that.imageGeneralInfo) : that.imageGeneralInfo != null)
            return false;
        return associations != null ? associations.equals(that.associations) : that.associations == null;
    }

    @Override
    public int hashCode() {
        int result = imageGeneralInfo != null ? imageGeneralInfo.hashCode() : 0;
        result = 31 * result + (associations != null ? associations.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FilesMetadata{" +
                "imageGeneralInfo=" + imageGeneralInfo +
                ", associations=" + associations +
                '}';
    }

    public ItemTypes getType() {
        return ItemTypes.IMAGE;
    }
}
