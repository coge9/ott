package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;


public enum MvpdLogMessage {

    DEWHITELISTED("Not Whitelisted"), WHITELISTED("Whitelisted"), ENTITLED("MVPD entitled"), UNENTITLED("MVPD unentitled");


    private String message;

    MvpdLogMessage(final String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
