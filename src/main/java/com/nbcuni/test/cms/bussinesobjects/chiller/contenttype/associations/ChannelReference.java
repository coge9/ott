package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

/**
 * Created by Ivan_Karnilau on 22-Apr-16.
 */
public class ChannelReference {

    private String series = null;
    private String itemType = ItemTypes.SERIES.getItemType();
    private String season = null;
    private String episode = null;

    public ChannelReference(Content program, Content season, Content episode) {
        this.series = program.getGeneralInfo().getUuid();
        this.itemType = program.getType().getItemType();
        this.season = season.getGeneralInfo().getUuid();
        this.episode = episode.getGeneralInfo().getUuid();
    }

    public ChannelReference() {
        super();
    }

    public String getEpisode() {
        return episode;
    }

    public ChannelReference setEpisode(String episode) {
        this.episode = episode;
        return this;
    }

    public String getSeries() {
        return series;
    }

    public ChannelReference setSeries(String series) {
        this.series = series;
        return this;
    }

    public String getSeason() {
        return season;
    }

    public ChannelReference setSeason(String season) {
        this.season = season;
        return this;
    }

    public String getItemType() {
        return itemType;
    }

    public ChannelReference setItemType(String itemType) {
        this.itemType = itemType;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("series", series)
                .add("itemType", itemType)
                .add("season", season)
                .add("episode", episode)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(series, itemType, season, episode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChannelReference that = (ChannelReference) o;

        return Objects.equal(this.series, that.series) &&
                Objects.equal(this.itemType, that.itemType) &&
                Objects.equal(this.season, that.season) &&
                Objects.equal(this.episode, that.episode);
    }

    public boolean isObjectNull() {
        return series == null && season == null && episode == null;
    }

}
