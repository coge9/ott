package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.otherinfo;

import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;


/**
 * Created by alekca on 19.05.2016.
 */
public class MediaGalleryMigrationData extends AbstractEntity {

    private String title;
    private String uid;
    private String vid;
    private String created;
    private String changed;
    private String comment;
    private String translate;
    private String language;

    private PublishingOptions publishingOptions = new PublishingOptions();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getChanged() {
        return changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public PublishingOptions getPublishingOptions() {
        return this.publishingOptions;
    }

    public void setPublishingOptions(PublishingOptions options) {
        this.publishingOptions = options;
    }

}
