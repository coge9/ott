package com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.promo.LinkUsage;

/**
 * Created by Ivan_Karnilau on 23-Aug-16.
 */
public class Link {
    private String displayText;
    private String urlContentItem;
    private String uuid;
    private LinkUsage usage;
    private boolean isContent;

    private String itemType;

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getUrlContentItem() {
        return urlContentItem;
    }

    public void setUrlContentItem(String urlContentItem) {
        this.urlContentItem = urlContentItem;
    }

    public LinkUsage getUsage() {
        return usage;
    }

    public void setUsage(LinkUsage usage) {
        this.usage = usage;
    }

    public boolean isContent() {
        return isContent;
    }

    public void setIsContent(boolean isContent) {
        this.isContent = isContent;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Link link = (Link) o;
        return isContent == link.isContent &&
                Objects.equal(displayText, link.displayText) &&
                Objects.equal(urlContentItem, link.urlContentItem) &&
                Objects.equal(uuid, link.uuid) &&
                usage == link.usage &&
                Objects.equal(itemType, link.itemType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(displayText, urlContentItem, uuid, usage, isContent, itemType);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("displayText", displayText)
                .add("urlContentItem", urlContentItem)
                .add("uuid", uuid)
                .add("usage", usage)
                .add("isContent", isContent)
                .add("itemType", itemType)
                .toString();
    }

}
