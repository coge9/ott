package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.MediaFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.PromotionalCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.TagsCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.factory.ExternalLinkFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Category;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import static com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.info.SeriesInfoFactory.createAllSeriesInfo;

/**
 * Created by Alena_Aukhukova on 5/27/2016.
 */
@Component("fullSeries")
public class FullSeriesCreationStrategy implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        Series series = new Series();
        String title = String.format(SeriesData.TITLE, postfix);
        series.getGeneralInfo()
                .setTitle(title)
                .setSubhead(String.format(SeriesData.SUBHEAD, postfix))
                .setShortDescription(String.format(SeriesData.SHORT_DESCRIPTION, postfix))
                .setMediumDescription(String.format(SeriesData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(SeriesData.LONG_DESCRIPTION, postfix));
        series.setSeriesInfo(createAllSeriesInfo());
        series.setPromotional(PromotionalCreator.getRandomPromotional());
        series.getSlugInfo().setSlugValue(title.replaceAll(" ", "-").toLowerCase());
        series.setExternalLinksInfo(ExternalLinkFactory.createRandomExternalLinkWithCount(1));
        series.setMediaImages(MediaFactory.createMediaWithImageCount(2));
        series.getAssociations().addCategories(Category.randomValue().getCategory()).setTags(TagsCreator.getRandomTags(1));
        return series;
    }
}
