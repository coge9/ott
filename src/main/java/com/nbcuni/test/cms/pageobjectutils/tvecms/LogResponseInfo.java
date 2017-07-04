package com.nbcuni.test.cms.pageobjectutils.tvecms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by aleksandra_lishaeva on 9/25/15.
 */
public enum LogResponseInfo {
    MESSAGE_RESPONSE("POST request has been sent successfully"), REQUEST_STATUS("Request Status"), HTTP_CODE("HTTP Code"), ERROR_MESSAGE("Error Message"),
    STATUS_MESSAGE("Status Message"), RESPONSE_DATA("Response Data");

    private static final Random RANDOM = new Random();
    private static final List<LogResponseInfo> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = PageNames.values().length;
    private String name;

    private LogResponseInfo(String name) {
        this.name = name;
    }

    public static LogResponseInfo randomStatus() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public String getName() {
        return name;
    }
}
