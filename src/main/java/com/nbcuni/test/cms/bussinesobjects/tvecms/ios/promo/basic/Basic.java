package com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.basic;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by Ivan_Karnilau on 23-Aug-16.
 */
public class Basic {
    private String title;
    private String promoKicker;
    private String promoTitle;
    private String promoDescription;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPromoKicker() {
        return promoKicker;
    }

    public void setPromoKicker(String promoKicker) {
        this.promoKicker = promoKicker;
    }

    public String getPromoTitle() {
        return promoTitle;
    }

    public void setPromoTitle(String promoTitle) {
        this.promoTitle = promoTitle;
    }

    public String getPromoDescription() {
        return promoDescription;
    }

    public void setPromoDescription(String promoDescription) {
        this.promoDescription = promoDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Basic basic = (Basic) o;
        return Objects.equal(title, basic.title) &&
                Objects.equal(promoKicker, basic.promoKicker) &&
                Objects.equal(promoTitle, basic.promoTitle) &&
                Objects.equal(promoDescription, basic.promoDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, promoKicker, promoTitle, promoDescription);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("promoKicker", promoKicker)
                .add("promoTitle", promoTitle)
                .add("promoDescription", promoDescription)
                .toString();
    }
}
