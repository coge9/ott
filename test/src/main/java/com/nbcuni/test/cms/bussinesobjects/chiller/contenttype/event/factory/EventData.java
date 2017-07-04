package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.factory;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ivan_Karnilau on 12-Apr-16.
 */
public class EventData {
    static final String TITLE = "AQA Event%s";
    static final String SUBHEAD = "AQA Event subhead%s";
    static final String SHORT_DESCRIPTION = "AQA Event short description%s";
    static final String MEDIUM_DESCRIPTION = "AQA Event medium description%s";
    static final String LONG_DESCRIPTION = "AQA Event long description%s";

    private EventData(){
        super();
    }

    public static Date getRoundingDate() {
        Calendar calendar = Calendar.getInstance();
        int unroundedMinutes = calendar.get(Calendar.MINUTE);
        int mod = unroundedMinutes % 15;
        calendar.set(Calendar.MINUTE, unroundedMinutes - mod);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
