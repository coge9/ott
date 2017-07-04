package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;

/**
 * Created by Alena_Aukhukova on 5/12/2016.
 */
public class ImageStyleJson {

    private String type;

    private String href;

    private Integer width;

    private Integer height;

    public ImageStyleJson(MediaImage mediaImage) {
        type = mediaImage.getName();
        href = mediaImage.getUrl();
        width = mediaImage.getWidth();
        height = mediaImage.getHeight();
    }

    public ImageStyleJson() {
        super();
    }

    public String getType() {
        return type;
    }

    public ImageStyleJson setType(String type) {
        this.type = type;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public ImageStyleJson setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public Integer getWidth() {
        return width;
    }

    public ImageStyleJson setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public String getHref() {
        if (href.contains("?itok=")) {
            href = href.substring(0, href.indexOf("?itok="));
        }
        if (href.contains("https")) {
            href = href.replace("https", "http");
        }
        return href;
    }

    public ImageStyleJson setHref(String href) {
        this.href = href;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImageStyleJson that = (ImageStyleJson) o;

        return Objects.equal(this.type, that.type) &&
                Objects.equal(this.getHref(), that.getHref()) &&
                Objects.equal(this.width, that.width) &&
                Objects.equal(this.height, that.height);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, href, width, height);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("href", href)
                .add("width", width)
                .add("height", height)
                .toString();
    }
}
