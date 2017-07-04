package com.nbcuni.test.cms.utils;

import java.time.ZoneId;

/**
 * Created by Dzianis_Kulesh on 2/6/2017.
 * <p>
 * Class which going to contains all constants using in all framework.
 */
public class GlobalConstants {

    public static final String SERIAL_API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final String NY_ZONE_NAME = "America/New_York";
    public static final ZoneId NY_ZONE = ZoneId.of(GlobalConstants.NY_ZONE_NAME);
    public static final String TRUE_STRING = "true";
    public static final String FALSE_STRING = "false";
    public static final String DEVELOPMENT = "Development";
    public static final String MVPD = "MVPD";


    private GlobalConstants() {

    }
}
