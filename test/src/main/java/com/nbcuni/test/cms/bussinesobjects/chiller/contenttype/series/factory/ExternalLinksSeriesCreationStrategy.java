package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.factory.ExternalLinkFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.info.SeriesInfoFactory;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan_Karnilau on 08-Apr-16.
 */
@Component("externalLinkSeries")
public class ExternalLinksSeriesCreationStrategy implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        Series series = new Series();
        series.getGeneralInfo()
                .setTitle(String.format(SeriesData.TITLE, postfix))
                .setSubhead(String.format(SeriesData.SUBHEAD, postfix))
                .setShortDescription(String.format(SeriesData.SHORT_DESCRIPTION, postfix))
                .setMediumDescription(String.format(SeriesData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(SeriesData.LONG_DESCRIPTION, postfix));
        series.setSeriesInfo(SeriesInfoFactory.createAllSeriesInfo());
        series.setExternalLinksInfo(ExternalLinkFactory.createRandomExternalLinkWithCount(1));
        return series;
    }
}
