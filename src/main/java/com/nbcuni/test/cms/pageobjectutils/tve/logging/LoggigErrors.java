package com.nbcuni.test.cms.pageobjectutils.tve.logging;

public enum LoggigErrors {

    NONE("None"), ERROR_WARNING("Errors and warnings"), ALL("All messages");

    private String message;

    private LoggigErrors(final String message) {
        this.message = message;
    }

    public static LoggigErrors get(final String value) {
        for (final LoggigErrors name : LoggigErrors.values()) {
            if (name.getMessage().equals(value)) return name;
        }
        return null;
    }

    public String getMessage() {
        return message;
    }
}