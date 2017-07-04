package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

import java.util.Random;

public enum LogoFallBackType {

    BLACK("black"), WHITE("white"), COLOR("color");

    private String name;

    LogoFallBackType(final String name) {
        this.name = name;

    }

    public static LogoFallBackType getTypeByName(String typeName) {
        for (LogoFallBackType type : LogoFallBackType.values()) {
            if (type.getName().equals(typeName)) {
                return type;
            }
        }
        return null;
    }

    public static LogoFallBackType getRandomType() {
        return LogoFallBackType.values()[new Random().nextInt(LogoFallBackType.values().length)];
    }

    public String getName() {
        return this.name;
    }
}