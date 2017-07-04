package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.MediaFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.PromotionalCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.TagsCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.factory.ExternalLinkFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Category;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Ivan_Karnilau on 02-Jun-16.
 */
@Component("fullSeason")
public class FullSeasonCreationStrategy implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        Integer seasonNumber = SimpleUtils.getRandomIntInRange(1, 999);
        Integer productionNumber = SimpleUtils.getRandomIntInRange(1, 999);
        String title = String.format(SeasonData.TITLE, postfix);

        Season season = new Season();
        season.getGeneralInfo()
                .setTitle(title)
                .setSubhead(String.format(SeasonData.SUBHEAD, postfix))
                .setShortDescription(String.format(SeasonData.SHORT_DESCRIPTION, postfix))
                .setMediumDescription(String.format(SeasonData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(SeasonData.LONG_DESCRIPTION, postfix));
        season.getSeasonInfo()
                .setSeasonNumber(seasonNumber)
                .setProductionNumber(productionNumber)
                .setStartDate(new Date())
                .setEndDate(new Date());
        season.setPromotional(PromotionalCreator.getRandomPromotional());

        season.getSlugInfo().setSlugValue(title.replaceAll(" ", "-").toLowerCase());
        season.setExternalLinksInfo(ExternalLinkFactory.createRandomExternalLinkWithCount(1));
        season.setMediaImages(MediaFactory.createMediaWithImageCount(2));
        season.getAssociations().addCategories(Category.randomValue().getCategory()).setTags(TagsCreator.getRandomTags(1));
        return season;
    }
}
