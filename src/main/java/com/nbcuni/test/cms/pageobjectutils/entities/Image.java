package com.nbcuni.test.cms.pageobjectutils.entities;

public class Image {

    private String path;
    private int width;
    private int height;
    private String extension;


    public Image() {
    }

    public Image(String path) {
        this.path = path;
    }

    public Image(int height, int width, String extension) {
        super();
        this.height = height;
        this.width = width;
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public Image setPath(String path) {
        this.path = path;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public Image setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public Image setWidth(int width) {
        this.width = width;
        return this;
    }

    public String getExtension() {
        return extension;
    }

    public Image setExtension(String extension) {
        this.extension = extension;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((extension == null) ? 0 : extension.hashCode());
        result = prime * result + height;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
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
        if (extension == null) {
            if (other.extension != null)
                return false;
        } else if (!extension.equals(other.extension))
            return false;
        if (height != other.height)
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        if (width != other.width)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Image [path=" + path + ", height=" + height + ", width="
                + width + ", extension=" + extension + "]";
    }

}
