package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info;

import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.post.BlurbMedia;

/**
 * Created by Ivan on 20.04.2016.
 */
public class Blurb {

    private String title = null;

    @SerializedName("body")
    private String text = null;

    private BlurbMedia blurbMedia = null;

    public Blurb() {
        super();
    }

    public Blurb(String title, BlurbMedia blurbMedia) {
        this.title = title;
        this.blurbMedia = blurbMedia;
    }

    public String getTitle() {
        return title;
    }

    public Blurb setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public Blurb setText(String text) {
        this.text = text;
        return this;
    }

    public BlurbMedia getBlurbMedia() {
        return blurbMedia;
    }

    public Blurb setBlurbMedia(BlurbMedia blurbMedia) {
        this.blurbMedia = blurbMedia;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        Blurb other = (Blurb) obj;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }


}
