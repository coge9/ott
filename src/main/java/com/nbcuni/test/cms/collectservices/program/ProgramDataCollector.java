package com.nbcuni.test.cms.collectservices.program;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;

/**
 * Created by Dzianis_Kulesh on 2/6/2017.
 * <p>
 * Interface design for collecting data about VIdeo Node.
 */
public interface ProgramDataCollector {

    public GlobalProgramEntity collectProgramInfo(String assetTitle);

    public GlobalProgramEntity collectRandomProgramInfo();
}
