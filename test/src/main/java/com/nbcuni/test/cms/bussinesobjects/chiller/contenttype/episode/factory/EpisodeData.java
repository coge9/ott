package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.factory;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ivan_Karnilau on 12-Apr-16.
 */
public class EpisodeData {
    static final String TITLE = "AQA Episode%s";
    static final String SUBHEAD = "AQA Episode subhead%s";
    static final String SHORT_DESCRIPTION = "AQA Episode short description%s";
    static final String MEDIUM_DESCRIPTION = "AQA Episode medium description%s";
    static final String LONG_DESCRIPTION = "AQA Episode long description%s";


    private EpisodeData(){
        super();
    }

    /**
     * @return date that round time to the nearest quarter hour
     */
    public static Date getRoundingDate() {
        Date whateverDateYouWant = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(whateverDateYouWant);

        int unroundedMinutes = calendar.get(Calendar.MINUTE);
        int mod = unroundedMinutes % 15;
        calendar.set(Calendar.MINUTE, unroundedMinutes + mod);
        return calendar.getTime();
    }
}
