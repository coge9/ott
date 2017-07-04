package com.nbcuni.test.cms.collectservices.video;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;

/**
 * Created by Dzianis_Kulesh on 2/6/2017.
 * <p>
 * Interface design for collecting data about VIdeo Node.
 */
public interface VideoDataCollector {

    public GlobalVideoEntity collectVideoInfo(String assetTitle);

    public GlobalVideoEntity collectRandomVideoInfo();
}
