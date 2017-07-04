package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.info;

import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.*;

/**
 * Created by Ivan on 06.04.2016.
 */
public class SeriesInfo {

    private Genre genre = null;
    private String guid = null;
    private Rating rating = null;
    private Boolean unscripted = null;
    private Status status = null;
    private Boolean syndicated = null;
    private String relatedSeries = null;
    private Type type = null;
    private ProgrammingTimeframe programmingTimeframe = null;
    private Integer regularlyScheduledDuration = null;
    private String tmsId;

    public Genre getGenre() {
        return genre;
    }

    public SeriesInfo setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public Rating getRating() {
        return rating;
    }

    public SeriesInfo setRating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public Boolean getUnscripted() {
        return unscripted;
    }

    public SeriesInfo setUnscripted(Boolean unscripted) {
        this.unscripted = unscripted;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public SeriesInfo setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Boolean getSyndicated() {
        return syndicated;
    }

    public SeriesInfo setSyndicated(Boolean syndicated) {
        this.syndicated = syndicated;
        return this;
    }

    public String getRelatedSeries() {
        return relatedSeries;
    }

    public SeriesInfo setRelatedSeries(String relatedSeries) {
        this.relatedSeries = relatedSeries;
        return this;
    }

    public SeriesInfo setGiud(String guid) {
        this.guid = guid;
        return this;
    }

    public String getGuid() {
        return guid;
    }

    public Type getType() {
        return type;
    }

    public SeriesInfo setType(Type type) {
        this.type = type;
        return this;
    }

    public ProgrammingTimeframe getProgrammingTimeframe() {
        return programmingTimeframe;
    }

    public SeriesInfo setProgrammingTimeframe(ProgrammingTimeframe programmingTimeframe) {
        this.programmingTimeframe = programmingTimeframe;
        return this;
    }

    public Integer getRegularlyScheduledDuration() {
        return regularlyScheduledDuration;
    }

    public SeriesInfo setRegularlyScheduledDuration(Integer regularlyScheduledDuration) {
        this.regularlyScheduledDuration = regularlyScheduledDuration;
        return this;
    }

    public boolean isObjectNull() {
        return genre == null &&
                rating == null &&
                unscripted == null &&
                status == null &&
                syndicated == null &&
                relatedSeries == null &&
                type == null &&
                programmingTimeframe == null &&
                regularlyScheduledDuration == null;
    }

    public String getTmsId() {
        return tmsId;
    }

    public SeriesInfo setTmsId(String tmsId) {
        this.tmsId = tmsId;
        return this;
    }
}
