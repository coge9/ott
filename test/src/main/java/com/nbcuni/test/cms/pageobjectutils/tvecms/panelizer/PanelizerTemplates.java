package com.nbcuni.test.cms.pageobjectutils.tvecms.panelizer;

import com.nbcuni.test.cms.backend.tvecms.block.panelizer.contentblock.*;

/**
 * Enum for Panelizer layouts
 */
public enum PanelizerTemplates {

    ANDROID_HOME_PAGE("Page layouts: Android", "Home page", "Android home page", AndroidHomePageBlock.class),
    ANDROID_ALL_SHOWS("Page layouts: Android", "All shows", "Android all shows page", AndroidAllShowsBlock.class),
    IOS_HOME_PAGE("Page layouts: iOS", "Home page", "iOS home page", AndroidHomePageBlock.class),
    IOS_ALL_SHOWS("Page layouts: iOS", "All shows", "iOS all shows page", AndroidAllShowsBlock.class),
    ROKU_HOMEPAGE("Page layouts: Roku", "Home page", "Roku home page", RokuHomepageBlock.class),
    ROKU_ALL_SHOWS("Page layouts: Roku", "All shows", "Roku all shows page", RokuAllShowsBlock.class),
    APPLETV_HOMEPAGE("Page layouts: AppleTV", "Home page", "AppleTV home page", AppleTvHomepageBlock.class),
    APPLETV_ALL_SHOWS("Page layouts: AppleTV", "All shows", "AppleTV all shows page", AppleTvAllShowsBlock.class),
    FIRETV_HOMEPAGE("Page layouts: FireTV", "Home page", "FireTV home page", RokuHomepageBlock.class),
    FIRETV_ALL_SHOWS("Page layouts: FireTV", "All shows", "FireTV all shows page", RokuAllShowsBlock.class),
    XBOXONE_HOMEPAGE("Page layouts: XboxOne", "Home page", "XboxOne home page", RokuHomepageBlock.class),
    XBOXONE_ALL_SHOWS("Page layouts: XboxOne", "All shows", "XboxOne all shows page", RokuAllShowsBlock.class),
    DEFAULT("Page layouts: Roku", "Home page", "Default", RokuHomepageBlock.class);

    private String category;
    private String layout;
    private String title;
    private Class clazz;
    PanelizerTemplates(String category, String layout, String title, Class<? extends MainContentPanelizerBlock> clazz) {
        this.category = category;
        this.layout = layout;
        this.title = title;
        this.clazz = clazz;
    }

    public String getLayout() {
        return layout;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public Class getClazz() {
        return clazz;
    }
}
