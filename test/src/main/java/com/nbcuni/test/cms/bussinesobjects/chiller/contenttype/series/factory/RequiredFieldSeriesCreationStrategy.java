package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Promotional;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.info.SeriesInfoFactory;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Alena_Aukhukova on 5/27/2016.
 */
@Component("withRequiredFieldsSeries")
public class RequiredFieldSeriesCreationStrategy implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        Series series = new Series();
        String title = String.format(SeriesData.TITLE, postfix);
        series.getGeneralInfo()
                .setTitle(title)
                .setSubhead(String.format(SeriesData.SUBHEAD, postfix))
                .setShortDescription(String.format(SeriesData.SHORT_DESCRIPTION, postfix));
        series.setSeriesInfo(SeriesInfoFactory.createRequiredSeriesInfo());
        series.setPromotional(new Promotional());
        series.getSlugInfo().setSlugValue(title.replaceAll(" ", "-").toLowerCase());
        return series;
    }

}
