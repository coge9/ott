package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by Ivan_Karnilau on 05-Apr-16.
 */
public class GeneralInfo {

    private String title = null;
    private String uuid = null;
    private String byline = null;
    private String dateLine = null;
    private String subtitle = null;
    private String shortDescription = null;
    private String mediumDescription = null;
    private String longDescription = null;
    private String sortTitle = null;
    private int revision = 0;

    public String getTitle() {
        return title;
    }

    public GeneralInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSubhead() {
        return subtitle;
    }

    public GeneralInfo setSubhead(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public GeneralInfo setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public String getMediumDescription() {
        return mediumDescription;
    }

    public GeneralInfo setMediumDescription(String mediumDescription) {
        this.mediumDescription = mediumDescription;
        return this;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public GeneralInfo setLongDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public int getRevision() {
        return this.revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getByline() {
        return byline;
    }

    public GeneralInfo setByline(String byline) {
        this.byline = byline;
        return this;
    }

    public String getDateLine() {
        return dateLine;
    }

    public GeneralInfo setDateLine(String dateLine) {
        this.dateLine = dateLine;
        return this;
    }

    public String getSortTitle() {
        return sortTitle;
    }

    public GeneralInfo setSortTitle(String sortTitle) {
        this.sortTitle = sortTitle;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("uuid", uuid)
                .add("byline", byline)
                .add("subtitle", subtitle)
                .add("shortDescription", shortDescription)
                .add("mediumDescription", mediumDescription)
                .add("longDescription", longDescription)
                .add("revision", revision)
                .add("sortTitle", sortTitle)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeneralInfo that = (GeneralInfo) o;
        return revision == that.revision &&
                Objects.equal(title, that.title) &&
                Objects.equal(uuid, that.uuid) &&
                Objects.equal(byline, that.byline) &&
                Objects.equal(subtitle, that.subtitle) &&
                Objects.equal(shortDescription, that.shortDescription) &&
                Objects.equal(mediumDescription, that.mediumDescription) &&
                Objects.equal(longDescription, that.longDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, uuid, byline, subtitle, shortDescription, mediumDescription, longDescription, revision);
    }
}
