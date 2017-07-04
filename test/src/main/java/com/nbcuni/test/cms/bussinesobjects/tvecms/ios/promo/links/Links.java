package com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 8/30/2016.
 */
public class Links {

    private List<Link> links = new LinkedList<>();

    public void addLink(Link link) {
        this.links.add(link);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Links links1 = (Links) o;
        return Objects.equal(links, links1.links);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(links);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("links", links)
                .toString();
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
