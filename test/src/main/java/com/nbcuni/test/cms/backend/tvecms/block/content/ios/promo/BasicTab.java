package com.nbcuni.test.cms.backend.tvecms.block.content.ios.promo;

import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.basic.Basic;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 23-Aug-16.
 */
public class BasicTab extends AbstractContainer {

    @FindBy(id = "edit-title")
    private TextField title;

    @FindBy(id = "edit-field-promo-kicker-und-0-value")
    private TextField promoKicker;

    @FindBy(id = "edit-field-promo-title-und-0-value")
    private TextField promoTitle;

    @FindBy(id = "edit-field-promo-description-und-0-value")
    private TextField promoDescription;

    @FindBy(id = "edit-field-promo-alias-und-0-value")
    private TextField alias;

    private void enterTitle(String title) {
        this.title.enterText(title);
    }

    private void enterPromoKicker(String promoKicker) {
        this.promoKicker.enterText(promoKicker);
    }

    private void enterPromoTitle(String promoTitle) {
        this.promoTitle.enterText(promoTitle);
    }

    private void enterPromoDescription(String promoDescription) {
        this.promoDescription.enterText(promoDescription);
    }

    public void enterData(Basic basic) {
        this.enterTitle(basic.getTitle());
        this.enterPromoKicker(basic.getPromoKicker());
        this.enterPromoTitle(basic.getPromoTitle());
        this.enterPromoDescription(basic.getPromoDescription());
    }

    public Basic getData() {
        Basic basic = new Basic();
        basic.setTitle(title.getValue());
        basic.setPromoKicker(promoKicker.getValue());
        basic.setPromoTitle(promoTitle.getValue());
        basic.setPromoDescription(promoDescription.getValue());
        return basic;
    }
}
