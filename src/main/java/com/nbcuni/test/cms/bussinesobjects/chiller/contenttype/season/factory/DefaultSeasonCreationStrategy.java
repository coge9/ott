package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Ivan on 12.04.2016.
 */
@Component("defaultSeason")
public class DefaultSeasonCreationStrategy implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        Integer seasonNumber = SimpleUtils.getRandomIntInRange(1, 999);
        Integer productionNumber = SimpleUtils.getRandomIntInRange(1, 999);

        Season season = new Season();
        season.getGeneralInfo()
                .setTitle(String.format(SeasonData.TITLE, postfix))
                .setSubhead(String.format(SeasonData.SUBHEAD, postfix))
                .setShortDescription(String.format(SeasonData.SHORT_DESCRIPTION, postfix))
                .setMediumDescription(String.format(SeasonData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(SeasonData.LONG_DESCRIPTION, postfix));
        season.getSeasonInfo()
                .setSeasonNumber(seasonNumber)
                .setProductionNumber(productionNumber)
                .setStartDate(new Date())
                .setEndDate(new Date());
        return season;
    }
}
