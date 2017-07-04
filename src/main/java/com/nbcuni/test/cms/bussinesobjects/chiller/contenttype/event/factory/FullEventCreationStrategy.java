package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.MediaFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.PromotionalCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.TagsCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.Event;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.*;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Ivan_Karnilau on 12-Apr-16.
 */
@Component("fullEvent")
public class FullEventCreationStrategy implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);

        ExternalLinksInfo linksInfo = new ExternalLinksInfo();
        linksInfo.setExtrenalLinkTitle(SimpleUtils.getRandomString(6)).setExtrenalLinkUrl(SimpleUtils.getRandomString(6));

        Event event = new Event();
        event.getGeneralInfo()
                .setTitle(String.format(EventData.TITLE, postfix))
                .setSubhead(String.format(EventData.SUBHEAD, postfix))
                .setShortDescription(String.format(EventData.SHORT_DESCRIPTION, postfix))
                .setMediumDescription(String.format(EventData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(EventData.LONG_DESCRIPTION, postfix));
        event.getEventInfo()
                .setGenre(Genre.randomValue())
                .setRating(Rating.randomValue())
                .setUnscripted(SimpleUtils.getRandomBoolean())
                .setStatus(Status.randomValue())
                .setSyndicated(SimpleUtils.getRandomBoolean())
                .setRelatedSeries(null)
                .setEventType(EventType.MOVIE)
                .setReleaseYear(new Date())
                .setChanelOriginal(true)
                .setAirTime(EventData.getRoundingDate());
        event.setPromotional(PromotionalCreator.getRandomPromotional());
        event.setMediaImages(MediaFactory.createMediaWithImageCount(1));
        event.setExternalLinksInfo(Arrays.asList(linksInfo));
        event.getAssociations().addCategories(Category.randomValue().getCategory()).setTags(TagsCreator.getRandomTags(1));
        return event;
    }
}
