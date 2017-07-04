package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class MediaImage {

    private String url;
    private String name;
    private String usage;
    private String uuid;
    private Integer width;
    private Integer height;

    public MediaImage() {
        super();
    }

    public MediaImage(String url, String name, String usage) {
        super();
        this.url = url;
        this.name = name;
        this.usage = usage;
    }

    public String getUuid() {
        return uuid;
    }

    public MediaImage setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public MediaImage setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getName() {
        return name;
    }

    public MediaImage setName(String name) {
        this.name = name;
        return this;
    }

    public String getUsage() {
        return usage;
    }

    public MediaImage setUsage(String usage) {
        this.usage = usage;
        return this;

    }

    public Integer getHeight() {
        return height;
    }

    public MediaImage setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public Integer getWidth() {
        return width;
    }

    public MediaImage setWidth(Integer width) {
        this.width = width;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url, name, usage, uuid, width, height);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MediaImage other = (MediaImage) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equalsIgnoreCase(other.name))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (usage == null) {
            if (other.usage != null)
                return false;
        } else if (!usage.equals(other.usage))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("url", url)
                .add("name", name)
                .add("usage", usage)
                .add("uuid", uuid)
                .add("width", width)
                .add("height", height)
                .toString();
    }

    public boolean isObjectNull() {
        return url == null && name == null && usage == null;
    }

}
