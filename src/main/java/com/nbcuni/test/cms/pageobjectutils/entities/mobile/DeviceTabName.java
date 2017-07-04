package com.nbcuni.test.cms.pageobjectutils.entities.mobile;

public enum DeviceTabName {

    Android_Phone_Button("Android Phone Button", "android-phone"), Android_Tablet_Button("Android Tablet Button", "android-tablet"),
    IOS_Phone_Button("iOS Phone Button", "ios-phone"), IOS_Tablet_Button("iOS Tablet Button", "ios-tablet"), Windows_Phone_Button("Windows Phone Button", "windows-phone"),
    Windows_Tablet_Button("Windows Tablet Button", "windows-tablet"), No_App_Window("No App Window", "noapp"), No_Thanks_Button("No Thanks Button", "thanks");

    private String tabName;
    private String deviceLocatorName;

    DeviceTabName(final String tabName, final String deviceLocatorName) {
        this.tabName = tabName;
        this.deviceLocatorName = deviceLocatorName;
    }

    public String get() {
        return this.tabName;
    }

    public String getLocatorPartName() {
        return this.deviceLocatorName;
    }

}