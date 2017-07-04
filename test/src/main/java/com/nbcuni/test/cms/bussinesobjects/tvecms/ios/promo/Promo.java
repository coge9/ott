package com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ios.promo.PromoPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.basic.Basic;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.Links;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

/**
 * Created by Ivan_Karnilau on 23-Aug-16.
 */
public class Promo extends Content {

    private Basic basic = new Basic();
    private Links links = new Links();
    private MediaImage image = new MediaImage();

    public MediaImage getImage() {
        return image;
    }

    public void setImage(MediaImage image) {
        this.image = image;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    @Override
    public Class<? extends Page> getPage() {
        return PromoPage.class;
    }

    @Override
    public String getTitle() {
        return getBasic().getTitle();
    }

    @Override
    public ItemTypes getType() {
        return ItemTypes.PROMO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promo promo = (Promo) o;
        return Objects.equal(basic, promo.basic) &&
                Objects.equal(links, promo.links) &&
                Objects.equal(image, promo.image);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(basic, links, image);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("basic", basic)
                .add("links", links)
                .add("image", image)
                .toString();
    }
}
