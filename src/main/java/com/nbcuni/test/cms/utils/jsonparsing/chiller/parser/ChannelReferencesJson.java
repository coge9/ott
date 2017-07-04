package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser;

import com.google.common.base.MoreObjects;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.SoftAssert;

/**
 * Created by alekca on 27.05.2016.
 */
public class ChannelReferencesJson {

    protected String programUuid;
    protected String programItemType = ItemTypes.SERIES.getItemType();
    private String seasonUuid;
    private String episodeUuid;

    public String getProgramUuid() {
        return programUuid;
    }

    public ChannelReferencesJson setProgramUuid(String programUuid) {
        this.programUuid = programUuid;
        return this;
    }

    public String getProgramItemType() {
        return programItemType;
    }

    public ChannelReferencesJson setProgramItemType(String programItemType) {
        this.programItemType = programItemType;
        return this;
    }

    public String getEpisodeUuid() {
        return episodeUuid;
    }

    public ChannelReferencesJson setEpisodeUuid(String episodeUuid) {
        this.episodeUuid = episodeUuid;
        return this;
    }

    public String getSeasonUuid() {
        return seasonUuid;
    }

    public ChannelReferencesJson setSeasonUuid(String seasonUuid) {
        this.seasonUuid = seasonUuid;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        ChannelReferencesJson other = (ChannelReferencesJson) obj;
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(other.programUuid, programUuid, "The program UUID do not matched", "The program UUID are matched");
        softAssert.assertEquals(other.programItemType, programItemType, "The program Item Type do not matched", "The program Item Type are matched");
        softAssert.assertEquals(other.seasonUuid, seasonUuid, "The season UUID do not matched", "The season UUID are matched");
        if (episodeUuid == null) {
            return softAssert.getTempStatus() && other.episodeUuid == null;
        } else {
            softAssert.assertEquals(other.episodeUuid, episodeUuid, "The episode UUID do not matched", "The episode UUID are matched");
        }
        return softAssert.getTempStatus();
    }

    public ChannelReferencesJson getObject(ChannelReference channelReference) {
        this.programUuid = channelReference.getSeries();
        this.programItemType = channelReference.getItemType();
        this.seasonUuid = channelReference.getSeason();
        this.episodeUuid = channelReference.getEpisode();
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("seasonUuid", seasonUuid)
                .add("episodeUuid", episodeUuid)
                .add("programUuid", programUuid)
                .add("programItemType", programItemType)
                .toString();
    }

    @Override
    public int hashCode() {
        int result = programUuid.hashCode();
        result = 31 * result + programItemType.hashCode();
        result = 31 * result + seasonUuid.hashCode();
        result = 31 * result + episodeUuid.hashCode();
        return result;
    }
}
