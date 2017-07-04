package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.annotations.SerializedName;

public class Image implements Cloneable {

    @SerializedName("name")
    public String name;

    @SerializedName("imageUrl")
    public String imageUrl;

    @SerializedName("width")
    public int width;

    @SerializedName("height")
    public int height;

    @SerializedName("update")
    public boolean update;

    public Image() {
        this.update = true;
    }

    /**
     * @param name - name of image
     * @param imageUrl it is just name of source at cms level
     * @param width - width of image
     * @param height - height of image
     */
    public Image(String name, String imageUrl, int width, int height) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.width = width;
        this.height = height;
        this.update = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result
                + ((imageUrl == null) ? 0 : imageUrl.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (update ? 1231 : 1237);
        result = prime * result + width;
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
        Image other = (Image) obj;
        if (height != other.height)
            return false;
        if (imageUrl == null) {
            if (other.imageUrl != null)
                return false;
        } else if ((!imageUrl.contains(other.imageUrl)) && (!other.imageUrl.contains(imageUrl)))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (update != other.update)
            return false;
        if (width != other.width)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Image [name=" + name + ", imageUrl=" + imageUrl + ", width="
                + width + ", height=" + height + ", update=" + update + "]";
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
