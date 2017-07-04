package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.Event;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Genre;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Rating;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan_Karnilau on 12-Apr-16.
 */
@Component("withoutRequiredEvent")
public class WithoutRequiredEventCreationStrategy implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        Event event = new Event();
        event.getGeneralInfo()
                .setMediumDescription(String.format(EventData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(EventData.LONG_DESCRIPTION, postfix));
        event.getEventInfo()
                .setGenre(Genre.ADVENTURE)
                .setRating(Rating.G)
                .setUnscripted(SimpleUtils.getRandomBoolean())
                .setSyndicated(SimpleUtils.getRandomBoolean())
                .setRelatedSeries(null)
                .setChanelOriginal(null)
                .setAirTime(null);
        return event;
    }
}
