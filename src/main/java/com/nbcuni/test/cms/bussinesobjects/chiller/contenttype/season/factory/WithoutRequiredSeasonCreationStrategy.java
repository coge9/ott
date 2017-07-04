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
@Component("withoutRequiredSeason")
public class WithoutRequiredSeasonCreationStrategy implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);

        Season season = new Season();
        season.getGeneralInfo()
                .setMediumDescription(String.format(SeasonData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(SeasonData.LONG_DESCRIPTION, postfix));
        season.getSeasonInfo()
                .setStartDate(new Date())
                .setEndDate(new Date());
        return season;
    }
}
