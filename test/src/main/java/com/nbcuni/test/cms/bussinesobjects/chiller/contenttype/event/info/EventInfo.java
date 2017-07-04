package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.info;

import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.EventType;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Genre;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Rating;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Status;

import java.util.Date;

/**
 * Created by Ivan_Karnilau on 12-Apr-16.
 */
public class EventInfo {

    private Genre genre = null;
    private Rating rating = null;
    private Boolean unscripted = null;
    private Status status = null;
    private Boolean syndicated = null;
    private String relatedSeries = null;
    private String relatedSeriesUuid = null;
    private EventType type = null;
    private Date releaseYear = null;
    private Boolean chanelOriginal = null;
    private Date airTime = null;
    private String castAndCreditUuid = null;

    public String getCastAndCredit() {
        return castAndCreditUuid;
    }

    public void setCastAndCredit(String castAndCreditUuid) {
        this.castAndCreditUuid = castAndCreditUuid;
    }

    public Genre getGenre() {
        return genre;
    }

    public EventInfo setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public Rating getRating() {
        return rating;
    }

    public EventInfo setRating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public Boolean getUnscripted() {
        return unscripted;
    }

    public EventInfo setUnscripted(Boolean unscripted) {
        this.unscripted = unscripted;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public EventInfo setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Boolean getSyndicated() {
        return syndicated;
    }

    public EventInfo setSyndicated(Boolean syndicated) {
        this.syndicated = syndicated;
        return this;
    }

    public String getRelatedSeries() {
        return relatedSeries;
    }

    public EventInfo setRelatedSeries(String relatedSeries) {
        this.relatedSeries = relatedSeries;
        return this;
    }

    public EventType getEventType() {
        return type;
    }

    public EventInfo setEventType(EventType type) {
        this.type = type;
        return this;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public EventInfo setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public Boolean getChanelOriginal() {
        return chanelOriginal;
    }

    public EventInfo setChanelOriginal(Boolean chanelOriginal) {
        this.chanelOriginal = chanelOriginal;
        return this;
    }

    public String getRelatedSeriesUuid() {
        return relatedSeriesUuid;
    }

    public void setRelatedSeriesUuid(String relatedSeriesUuid) {
        this.relatedSeriesUuid = relatedSeriesUuid;
    }

    public Date getAirTime() {
        return airTime;
    }

    public EventInfo setAirTime(Date airTime) {
        this.airTime = airTime;
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
                releaseYear == null &&
                chanelOriginal == null &&
                airTime == null;
    }
}
