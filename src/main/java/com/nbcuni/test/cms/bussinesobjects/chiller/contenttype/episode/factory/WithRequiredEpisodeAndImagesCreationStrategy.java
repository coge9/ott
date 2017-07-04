package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.factory;


import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.MediaFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Rating;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Type;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("requiredEpisodeWithImageWithoutUsage")
public class WithRequiredEpisodeAndImagesCreationStrategy implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {

        String postfix = SimpleUtils.getRandomString(6);
        Integer episodeNumber = SimpleUtils.getRandomIntInRange(1, 999);
        Integer secondaryEpisodeNumber = SimpleUtils.getRandomIntInRange(1, 999);
        Integer productionNumber = SimpleUtils.getRandomIntInRange(1, 999);

        Episode episode = new Episode();
        episode.getGeneralInfo()
                .setTitle(String.format(EpisodeData.TITLE, postfix))
                .setSubhead(String.format(EpisodeData.SUBHEAD, postfix))
                .setShortDescription(String.format(EpisodeData.SHORT_DESCRIPTION, postfix));
        episode.getEpisodeInfo()
                .setEpisodeType(Type.randomValue())
                .setEpisodeNumber(episodeNumber)
                .setSecondaryEpisodeNumber(secondaryEpisodeNumber)
                .setOriginalAirDate(new Date())
                .setRating(Rating.randomValue())
                .setProductionNumber(productionNumber)
                .setSupplementaryAirDate(EpisodeData.getRoundingDate());
        episode.setMediaImages(MediaFactory.createMediaWithImageCountWithoutUsages(2));
        return episode;
    }
}
