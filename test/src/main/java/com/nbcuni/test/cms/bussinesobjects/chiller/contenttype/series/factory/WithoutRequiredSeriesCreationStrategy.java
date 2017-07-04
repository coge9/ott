package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.info.SeriesInfoFactory;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan_Karnilau on 11-Apr-16.
 */
@Component("withoutRequiredSeries")
public class WithoutRequiredSeriesCreationStrategy implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        Series series = new Series();
        series.getGeneralInfo()
                .setMediumDescription(String.format(SeriesData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(SeriesData.LONG_DESCRIPTION, postfix));
        series.setSeriesInfo(SeriesInfoFactory.createWithoutRequiredSeriesInfo());
        return series;
    }
}
