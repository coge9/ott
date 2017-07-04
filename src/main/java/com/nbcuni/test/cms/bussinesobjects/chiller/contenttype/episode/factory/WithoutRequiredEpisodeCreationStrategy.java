package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Rating;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Ivan_Karnilau on 12-Apr-16.
 */
@Component("withoutRequiredEpisode")
public class WithoutRequiredEpisodeCreationStrategy implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        Integer secondaryEpisodeNumber = SimpleUtils.getRandomIntInRange(1, 999);
        Integer productionNumber = SimpleUtils.getRandomIntInRange(1, 999);

        Episode episode = new Episode();
        episode.getGeneralInfo()
                .setMediumDescription(String.format(EpisodeData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(EpisodeData.LONG_DESCRIPTION, postfix));
        episode.getEpisodeInfo()
                .setParentSeries(new Series())
                .setParentSeason(new Season())
                .setSecondaryEpisodeNumber(secondaryEpisodeNumber)
                .setOriginalAirDate(new Date())
                .setRating(Rating.G)
                .setProductionNumber(productionNumber)
                .setSupplementaryAirDate(EpisodeData.getRoundingDate());
        return episode;
    }
}
