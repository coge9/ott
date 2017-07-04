package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.EpisodePage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.info.EpisodeInfo;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

/**
 * Created by Ivan_Karnilau on 05-Apr-16.
 */
public class Episode extends Content {

    private EpisodeInfo episodeInfo = new EpisodeInfo();

    @Override
    public String getTitle() {
        return getGeneralInfo().getTitle();
    }

    @Override
    public ItemTypes getType() {
        return ItemTypes.EPISODE;
    }

    @Override
    public Class<? extends Page> getPage() {
        return EpisodePage.class;
    }

    public EpisodeInfo getEpisodeInfo() {
        return episodeInfo;
    }
}
