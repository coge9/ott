package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.PromotionalCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.info.SeriesInfoFactory;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ShowColors;
import com.nbcuni.test.cms.pageobjectutils.tvecms.TemplateStyle;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

/**
 * Created by aleksandra_lishaeva on 6/16/17.
 */
@Component("gradientColorSeries")
public class GradientShowColorCreationStrategy implements ContentTypeCreationStrategy {
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
        series.getGradientColorInfo().
                setGradient(TemplateStyle.LIGHT).
                setColor(ShowColors.randomInstance().getValue());
        series.setSeriesInfo(SeriesInfoFactory.createAllSeriesInfo());
        series.setPromotional(PromotionalCreator.getRandomPromotional());
        series.getSlugInfo().setSlugValue(title.replaceAll(" ", "-").toLowerCase());
        return series;
    }
}
