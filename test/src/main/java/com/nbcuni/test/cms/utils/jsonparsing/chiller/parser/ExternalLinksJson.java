package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser;


import com.google.common.base.MoreObjects;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;

/**
 * Created by Aleksandra_Lishaeva on 5/24/16.
 */
public class ExternalLinksJson extends AbstractEntity {
    private String text;
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ExternalLinksJson setObject(ExternalLinksInfo info) {
        setText(info.getExtrenalLinkTitle());
        setHref(info.getExtrenalLinkUrl());
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("text", text)
                .add("href", href)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        ExternalLinksJson other = (ExternalLinksJson) obj;
        if (text == null) {
            if (other.text != null) {
                return false;
            }
        } else if (!text.equals(other.text)) {
            return false;
        }
        if (href == null) {
            if (other.href != null) {
                return false;
            }
        } else if (!href.equals(other.href)) {
            return false;
        }
        return true;
    }

}
