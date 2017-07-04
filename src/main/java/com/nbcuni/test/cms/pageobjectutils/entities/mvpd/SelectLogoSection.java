package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

public enum SelectLogoSection {

    ACTIVATION_PAGE_PICKER(1, "Activation Page Picker Logo Color"),
    ACTIVATION_PAGE_POST_LOGGED_IN(2, "Activation Page Post-Logged in Logo Color"),
    APP_PICKER(3, "App Picker Logo Color"),
    APP_POST_LOGGED_IN(4, "App Post-Logged in Logo Color");

    int indexOnUi;
    String nameOnMvpdLog;

    private SelectLogoSection(int index, String nameOnMvpdLog) {
        indexOnUi = index;
        this.nameOnMvpdLog = nameOnMvpdLog;
    }

    public int getIndex() {
        return indexOnUi;
    }

    public String getNameOnMvpdLog() {
        return nameOnMvpdLog;
    }
}
