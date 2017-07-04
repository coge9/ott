package com.nbcuni.test.cms.pageobjectutils.tvecms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum HeaderStyle {

    WITH_TITLE_WITH_MENU("Header with Page Title"), WITHOUT_TITLE_WITH_MENU(
            "Header without Page Title"), WITH_TITLE_WITHOUT_MENU(
            "Header with Page Title without menu"), WITHOUT_TITLE_WITHOUT_MENU(
            "Header without Page Title without menu");

    private static final List<HeaderStyle> VALUES = Collections
            .unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    private String nameInDropDown;

    HeaderStyle(String nameInDropDown) {
        this.nameInDropDown = nameInDropDown;
    }

    public static HeaderStyle randomHeaderStyle() {
        HeaderStyle headerStyle;
        headerStyle = VALUES.get(RANDOM.nextInt(SIZE));
        return headerStyle;
    }

    public String get() {
        return nameInDropDown;
    }

    public void setNameInDropDown(String nameInDropDown) {
        this.nameInDropDown = nameInDropDown;
    }

}
