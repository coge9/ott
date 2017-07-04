package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.pageobjectutils.Page;

/**
 * Created by Aleksandra_Lishaeva on 4/18/16.
 */
public class ExternalLinksInfo extends AbstractEntity {

    private String contentLinkTitle = null;
    private String contentLinkUrl = null;

    @Override
    public int hashCode() {
        return Objects.hashCode(contentLinkUrl, contentLinkTitle);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("External Link URLs", contentLinkUrl)
                .add("External Link Titles", contentLinkTitle)
                .toString();
    }

    public String getExtrenalLinkTitle() {
        return contentLinkTitle;
    }

    public ExternalLinksInfo setExtrenalLinkTitle(String title) {
        this.contentLinkTitle = title;
        return this;
    }

    public String getExtrenalLinkUrl() {
        return contentLinkUrl;
    }

    public ExternalLinksInfo setExtrenalLinkUrl(String url) {
        this.contentLinkUrl = url;
        return this;
    }

    public ExternalLinksInfo getExternalLinksInfo() {
        return this;
    }

    public Class<? extends Page> getPage() {
        return ContentTypePage.class;
    }

    public boolean isObjectNull() {
        return contentLinkTitle == null && contentLinkUrl == null;
    }
}
