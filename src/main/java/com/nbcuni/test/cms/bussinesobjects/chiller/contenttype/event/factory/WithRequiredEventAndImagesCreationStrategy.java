package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.factory;


import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.MediaFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.PromotionalCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.Event;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.EventType;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Genre;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Rating;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Status;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

@Component("requiredEventWithImageWithoutUsage")
public class WithRequiredEventAndImagesCreationStrategy implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {

        String postfix = SimpleUtils.getRandomString(6);

        Event event = new Event();
        event.getGeneralInfo()
                .setTitle(String.format(EventData.TITLE, postfix))
                .setSubhead(String.format(EventData.SUBHEAD, postfix))
                .setShortDescription(String.format(EventData.SHORT_DESCRIPTION, postfix))
                .setMediumDescription(String.format(EventData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(EventData.LONG_DESCRIPTION, postfix));
        event.getEventInfo()
                .setGenre(Genre.ADVENTURE)
                .setRating(Rating.G)
                .setUnscripted(SimpleUtils.getRandomBoolean())
                .setStatus(Status.UPCOMING)
                .setSyndicated(SimpleUtils.getRandomBoolean())
                .setRelatedSeries(null)
                .setEventType(EventType.LIVE_EVENT)
                .setReleaseYear(null)
                .setChanelOriginal(null)
                .setAirTime(EventData.getRoundingDate());
        event.setPromotional(PromotionalCreator.getRandomPromotional());
        event.setMediaImages(MediaFactory.createMediaWithImageCountWithoutUsages(2));
        return event;
    }
}
