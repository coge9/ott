package com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo;

import com.nbcuni.test.cms.backend.tvecms.pages.content.ios.promo.PromoPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

import java.time.ZonedDateTime;

/**
 * Business object which is going to represent data for
 * PROMO in CMS.
 *
 */
public class GlobalPromoEntity extends Content {
    private ZonedDateTime updatedDate;
    private ImageSource imageSource;

    @Override
    public String getTitle() {
        return getGeneralInfo().getTitle();
    }

    public GlobalPromoEntity setTitle(String title) {
        getGeneralInfo().setTitle(title);
        return this;
    }

    @Override
    public ItemTypes getType() {
        return ItemTypes.PROMO;
    }

    @Override
    public Class<? extends Page> getPage() {
        return PromoPage.class;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public ImageSource getImageSource() {
        return imageSource;
    }

    public void setImageSource(ImageSource imageSource) {
        this.imageSource = imageSource;
    }
}
