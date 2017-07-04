package com.nbcuni.test.cms.utils.jsonparsing.services.registryservice;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

/**
 * Created by Aleksandra_Lishaeva on 5/19/16.
 */
public class RegistryServiceEntity {

    private String type = null;
    private String name = null;
    private Integer seasonNumber = null;
    private Integer episodeNumber = null;

    public RegistryServiceEntity(ItemTypes type, Content content) {
        setService(type, content);
    }

    public RegistryServiceEntity(Content content) {
        setService(content.getType(), content);
    }

    private void setService(ItemTypes type, Content content) {
        this.type = type.getItemType();
        this.name = content.getTitle();
        if (type.equals(ItemTypes.SEASON)) {
            if (((Season) content).getSeasonInfo().getParentProgram() != null) {
                this.name = ((Season) content).getSeasonInfo().getParentProgram().getTitle();
            } else {
                this.name = ((Season) content).getSeasonInfo().getProgram();
            }
            this.seasonNumber = ((Season) content).getSeasonInfo().getSeasonNumber();
        } else if (type.equals(ItemTypes.EPISODE)) {
            this.name = ((Episode) content).getEpisodeInfo().getParentSeries().getTitle();
            this.episodeNumber = ((Episode) content).getEpisodeInfo().getEpisodeNumber();
            this.seasonNumber = ((Episode) content).getEpisodeInfo().getParentSeason().getSeasonInfo().getSeasonNumber();
        }
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }


}
