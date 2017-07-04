package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.otherinfo;

import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;

/**
 * Created by alekca on 18.05.2016.
 */
public class PublishingOptions extends AbstractEntity {

    private Boolean sticky;
    private Boolean promoted;
    private Boolean published;
    private String revision;

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Boolean getSticky() {
        return sticky;
    }

    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    public Boolean getPromoted() {
        return promoted;
    }

    public void setPromoted(Boolean promoted) {
        this.promoted = promoted;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public boolean isObjectNull() {
        return sticky == null && promoted == null && published == null && revision == null;
    }

}
