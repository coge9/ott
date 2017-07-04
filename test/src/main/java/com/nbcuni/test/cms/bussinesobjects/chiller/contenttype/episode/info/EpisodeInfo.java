package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.info;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Rating;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Type;

import java.util.Date;

/**
 * Created by Ivan_Karnilau on 12-Apr-16.
 */
public class EpisodeInfo {

    private Type type = null;
    private Integer episodeNumber = null;
    private Integer seasonNumber = null;
    private Integer secondaryEpisodeNumber = null;
    private Date originalAirDate = null;
    private Rating rating = null;
    private Integer productionNumber = null;
    private Date supplementaryAirDate = null;
    private Season parentSeason = new Season();
    private Series parentSeries = new Series();

    public Season getParentSeason() {
        return parentSeason;
    }

    public EpisodeInfo setParentSeason(Season parentSeason) {
        this.parentSeason = parentSeason;
        if (parentSeason.getSeasonInfo().getParentProgram() != null) {
            this.setParentSeries((Series) parentSeason.getSeasonInfo().getParentProgram());
        }
        this.setSeasonNumber(parentSeason.getSeasonInfo().getSeasonNumber());
        return this;
    }

    public Series getParentSeries() {
        return parentSeries;
    }

    public EpisodeInfo setParentSeries(Series parentSeries) {
        this.parentSeries = parentSeries;
        return this;
    }

    public Type getEpisodeType() {
        return type;
    }

    public EpisodeInfo setEpisodeType(Type type) {
        this.type = type;
        return this;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public EpisodeInfo setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
        return this;
    }

    public Integer getSecondaryEpisodeNumber() {
        return secondaryEpisodeNumber;
    }

    public EpisodeInfo setSecondaryEpisodeNumber(Integer secondaryEpisodeNumber) {
        this.secondaryEpisodeNumber = secondaryEpisodeNumber;
        return this;
    }

    public Date getOriginalAirDate() {
        return originalAirDate;
    }

    public EpisodeInfo setOriginalAirDate(Date originalAirDate) {
        this.originalAirDate = originalAirDate;
        return this;
    }

    public Rating getRating() {
        return rating;
    }

    public EpisodeInfo setRating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public Integer getProductionNumber() {
        return productionNumber;
    }

    public EpisodeInfo setProductionNumber(Integer productionNumber) {
        this.productionNumber = productionNumber;
        return this;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Date getSupplementaryAirDate() {
        return supplementaryAirDate;
    }

    public EpisodeInfo setSupplementaryAirDate(Date supplementaryAirDate) {
        this.supplementaryAirDate = supplementaryAirDate;
        return this;
    }

    public boolean isObjectNull() {
        return type == null &&
                episodeNumber == null &&
                secondaryEpisodeNumber == null &&
                originalAirDate == null &&
                rating == null &&
                productionNumber == null &&
                supplementaryAirDate == null;

    }
}
