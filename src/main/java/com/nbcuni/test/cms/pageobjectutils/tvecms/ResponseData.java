package com.nbcuni.test.cms.pageobjectutils.tvecms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by aleksandra_lishaeva on 9/25/15.
 */
public enum ResponseData {
    CREATED("201", "Created"),
    CONFLICT("409", "conflict"),
    SUCCESS("success", "success"),
    ACCESS_DENIED("Access Denied!", "Access Denied!"),
    UPDATED("200", "OK");

    private static final Random RANDOM = new Random();
    private static final List<ResponseData> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = PageNames.values().length;
    private String responseStatus;
    private String responseMessage;

    private ResponseData(String responseStatus, String responseMessage) {
        this.responseStatus = responseStatus;
        this.responseMessage = responseMessage;
    }

    public static ResponseData randomStatus() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
