package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.PromotionalCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import static com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.info.SeriesInfoFactory.createForIosSeriesInfo;

@Component("seriesForIos")
public class ProgramCreationStrategy {

    public Series createProgramForIos() {
        Series program = new Series();
        String postfix = SimpleUtils.getRandomString(6);
        program.getGeneralInfo()
                .setSubhead(String.format(SeriesData.SUBHEAD, postfix))
                .setLongDescription(String.format(SeriesData.LONG_DESCRIPTION, postfix));
        program.setSeriesInfo(createForIosSeriesInfo());
        program.setPromotional(PromotionalCreator.getPromotionalForIosProgram());
        return program;
    }
}
