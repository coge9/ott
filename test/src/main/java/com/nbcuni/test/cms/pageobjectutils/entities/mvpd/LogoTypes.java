package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum LogoTypes {

    NONE("-None-", ""),
    BLACK_LOGO("Black Logo", "black"),
    BLACK_LOGO_2X("Black Logo 2x", "black2x"),
    WHITE_LOGO("White Logo", "white"),
    WHITE_LOGO_2X("White Logo 2x", "white2x"),
    COLOR_LOGO("Color Logo", "color"),
    COLOR_LOGO_2X("Color Logo 2x", "color2x"),
    ATV_LOGO("ATV Logo", "atv"),
    ATV_LOGO_2X("ATV Logo 2x", "atv2x");

    private static final List<LogoTypes> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    private static List<LogoTypes> logoTypesThatMustBeCreated = Arrays.asList(LogoTypes.BLACK_LOGO, LogoTypes.BLACK_LOGO_2X, LogoTypes.WHITE_LOGO, LogoTypes.WHITE_LOGO_2X, LogoTypes.COLOR_LOGO, LogoTypes.COLOR_LOGO_2X, LogoTypes.ATV_LOGO, LogoTypes.ATV_LOGO_2X);
    private String type;
    private String typeForBulkUpload;

    LogoTypes(final String tag, final String tagBulkIpload) {
        this.type = tag;
        this.typeForBulkUpload = tagBulkIpload;
    }

    public static List<LogoTypes> getLogoTypesThatMustBeCreated() {
        return logoTypesThatMustBeCreated;
    }

    public static LogoTypes getTypeByUploadName(String name) {
        for (LogoTypes type : LogoTypes.values()) {
            if (type.getBulkUploadName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public static LogoTypes randomType() {
        LogoTypes type = LogoTypes.NONE;
        while (type.equals(LogoTypes.NONE)) {
            type = VALUES.get(RANDOM.nextInt(SIZE));
        }
        return type;
    }

    public String get() {
        return this.type;
    }

    public String getBulkUploadName() {
        return this.typeForBulkUpload;
    }
}