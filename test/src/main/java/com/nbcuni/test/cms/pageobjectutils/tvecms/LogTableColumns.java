package com.nbcuni.test.cms.pageobjectutils.tvecms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by aleksandra_lishaeva on 10/21/15.
 */
public enum LogTableColumns {

    ID("ID "), OBJECTTYPE("Object Type"), OBJECT_ID("Object ID"), INSTANCE("Instance"), ENDPOINT("Endpoint"),
    REQUEST_OPTIONS("Request Options"), RESPONSE_STATUS("Response Status "), RESPONSE_MESSAGE("Response Message"),
    RESPONSE_DATA("Response Data"), INFO("Info"), SESSION_ID("Session Id"), USER("User"), DATE("Date");

    private static final Random RANDOM = new Random();
    private static final List<LogTableColumns> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = PageNames.values().length;
    private String name;

    private LogTableColumns(String name) {
        this.name = name;
    }

    public static LogTableColumns randomStatus() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public String getName() {
        return name;
    }

}
