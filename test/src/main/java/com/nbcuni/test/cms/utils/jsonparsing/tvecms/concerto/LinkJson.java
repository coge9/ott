package com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.Link;

/**
 * Created by Ivan_Karnilau on 9/1/2016.
 */
public class LinkJson extends AbstractEntity {
    private String text;
    private String href;
    private String uuid;
    private String content;
    private String usage;
    private String itemType;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
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
        LinkJson linkJson = (LinkJson) o;
        return Objects.equal(text, linkJson.text) &&
                Objects.equal(href, linkJson.href) &&
                Objects.equal(uuid, linkJson.uuid) &&
                Objects.equal(content, linkJson.content) &&
                Objects.equal(usage, linkJson.usage) &&
                Objects.equal(itemType, linkJson.itemType);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("text", text)
                .add("href", href)
                .add("uuid", uuid)
                .add("content", content)
                .add("usage", usage)
                .add("itemType", itemType)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text, href, uuid, content, usage, itemType);
    }

    public LinkJson getObject(Link link) {
        this.text = link.getDisplayText();
        this.usage = link.getUsage().getValueForPublishing();
        this.content = String.valueOf(link.isContent());
        if (link.isContent()) {
            this.uuid = link.getUuid();
            this.itemType = link.getItemType();
        } else {
            this.href = link.getUrlContentItem();
        }
        return this;
    }


}
