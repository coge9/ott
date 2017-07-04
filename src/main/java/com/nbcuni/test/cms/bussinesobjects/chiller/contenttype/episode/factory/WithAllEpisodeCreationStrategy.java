package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.MediaFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.PromotionalCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.TagsCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Category;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Rating;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Type;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Aliaksei_Klimenka1 on 7/4/2016.
 */
@Component("withAllEpisode")
public class WithAllEpisodeCreationStrategy implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        Integer episodeNumber = SimpleUtils.getRandomIntInRange(1, 999);
        Integer secondaryEpisodeNumber = SimpleUtils.getRandomIntInRange(1, 999);
        Integer productionNumber = SimpleUtils.getRandomIntInRange(1, 999);
        String linkTitle = SimpleUtils.getRandomString(6);
        String linkUrl = SimpleUtils.getRandomString(6);
        ExternalLinksInfo linksInfo = new ExternalLinksInfo();
        linksInfo.setExtrenalLinkTitle(SimpleUtils.getRandomString(6)).setExtrenalLinkUrl(SimpleUtils.getRandomString(6));


        Episode episode = new Episode();
        episode.getGeneralInfo()
                .setTitle(String.format(EpisodeData.TITLE, postfix))
                .setSubhead(String.format(EpisodeData.SUBHEAD, postfix))
                .setShortDescription(String.format(EpisodeData.SHORT_DESCRIPTION, postfix))
                .setMediumDescription(String.format(EpisodeData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(EpisodeData.LONG_DESCRIPTION, postfix));
        episode.getEpisodeInfo()
                .setEpisodeType(Type.randomValue())
                .setEpisodeNumber(episodeNumber)
                .setSecondaryEpisodeNumber(secondaryEpisodeNumber)
                .setOriginalAirDate(new Date())
                .setRating(Rating.randomValue())
                .setProductionNumber(productionNumber)
                .setSupplementaryAirDate(EpisodeData.getRoundingDate());
        episode.setMediaImages(MediaFactory.createMediaWithImageCount(1));
        episode.getAssociations().addCategories(Category.randomValue().getCategory()).setTags(TagsCreator.getRandomTags(1));
        episode.setPromotional(PromotionalCreator.getRandomPromotional());
        episode.setExternalLinksInfo(Arrays.asList(linksInfo.setExtrenalLinkTitle(linkTitle)
                .setExtrenalLinkUrl(linkUrl)));

        return episode;
    }
}
