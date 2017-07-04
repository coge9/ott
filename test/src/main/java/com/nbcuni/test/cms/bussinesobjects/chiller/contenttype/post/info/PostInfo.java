package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 20.04.2016.
 */
public class PostInfo {

    public WysiwigDescription longDescription = new WysiwigDescription();
    public WysiwigDescription mediumDescription = new WysiwigDescription();
    private List<Blurb> blurbs = new ArrayList<>();
    public PostInfo() {
    }

    public List<Blurb> getBlurbs() {
        return blurbs;
    }

    public PostInfo setBlurbs(List<Blurb> blurbs) {
        this.blurbs = blurbs;
        return this;
    }

    public WysiwigDescription getLongDescription() {
        return longDescription;
    }

    public PostInfo setLongDescription(WysiwigDescription longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public WysiwigDescription getMediumDescription() {
        return mediumDescription;
    }

    public PostInfo setMediumDescription(WysiwigDescription mediumDescription) {
        this.mediumDescription = mediumDescription;
        return this;
    }

    public PostInfo addBlurb(Blurb blurb) {
        if (blurbs == null) {
            blurbs = new ArrayList<>();
        }
        blurbs.add(blurb);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostInfo postInfo = (PostInfo) o;

        return blurbs != null ? blurbs.equals(postInfo.blurbs) : postInfo.blurbs == null;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (blurbs != null ? blurbs.hashCode() : 0);
        result = prime * result + (longDescription != null ? longDescription.hashCode() : 0);
        result = prime * result + (mediumDescription != null ? mediumDescription.hashCode() : 0);
        return result;
    }

    public boolean isObjectNull() {
        return blurbs == null && mediumDescription == null && longDescription == null;
    }
}
