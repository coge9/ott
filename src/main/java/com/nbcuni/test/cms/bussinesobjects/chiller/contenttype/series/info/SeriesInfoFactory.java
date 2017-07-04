package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.info;

import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.*;
import com.nbcuni.test.cms.utils.SimpleUtils;

/**
 * Created by Alena_Aukhukova on 6/22/2016.
 */
public class SeriesInfoFactory {
    public static SeriesInfo createRequiredSeriesInfo() {
        return new SeriesInfo()
                .setStatus(Status.UPCOMING)
                .setType(Type.randomValue());
    }

    public static SeriesInfo createAllSeriesInfo() {
        SeriesInfo seriesInfo = new SeriesInfo();
        seriesInfo.setStatus(Status.randomValue());
        setOptionalSeriesInfo(seriesInfo);
        setRandomSeriesType(seriesInfo);
        return seriesInfo;
    }

    public static SeriesInfo createForIosSeriesInfo() {
        SeriesInfo seriesInfo = new SeriesInfo();
        seriesInfo.setStatus(Status.randomValue())
                .setGenre(Genre.randomValue())
                .setUnscripted(false)
                .setSyndicated(false);
        return seriesInfo;
    }

    public static SeriesInfo createWithoutRequiredSeriesInfo() {
        return setOptionalSeriesInfo(new SeriesInfo());
    }

    /**
     * Set Random Type and set Programming Timeframe and
     * Regularly Scheduled Duration if Series Type = TV Series
     *
     * @param seriesInfo
     * @return
     */
    private static SeriesInfo setRandomSeriesType(SeriesInfo seriesInfo) {
        Integer regularlyScheduledDuration = SimpleUtils.getRandomIntInRange(1, 999);
        Type randomSeriesType = Type.randomValue();
        seriesInfo.setType(randomSeriesType);
        if (randomSeriesType.getValue().equals(Type.TV_SERIES)) {
            seriesInfo.setProgrammingTimeframe(ProgrammingTimeframe.randomValue())
                    .setRegularlyScheduledDuration(regularlyScheduledDuration);
        }
        return seriesInfo;
    }

    private static SeriesInfo setOptionalSeriesInfo(SeriesInfo seriesInfo) {
        return seriesInfo.setGenre(Genre.randomValue())
                .setRating(Rating.randomValue())
                .setUnscripted(SimpleUtils.getRandomBoolean())
                .setSyndicated(SimpleUtils.getRandomBoolean())
                .setRelatedSeries(null);
    }
}
