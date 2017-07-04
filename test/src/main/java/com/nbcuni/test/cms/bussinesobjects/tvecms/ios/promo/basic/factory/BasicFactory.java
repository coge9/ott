package com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.basic.factory;

import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.basic.Basic;
import com.nbcuni.test.cms.utils.SimpleUtils;

/**
 * Created by Ivan_Karnilau on 23-Aug-16.
 */
public class BasicFactory {

    private static final String prefix = "AQA promo %s";

    private static final String title = String.format(prefix, "title%s");
    private static final String promoKicker = String.format(prefix, "promo kicker%s");
    private static final String promoTitle = String.format(prefix, "promo title%s");
    private static final String promoDescription = String.format(prefix, "promo description%s");

    public static Basic createWithRequired() {
        String postfix = SimpleUtils.getRandomString(6);
        Basic basic = new Basic();
        basic.setTitle(String.format(title, postfix));
        return basic;
    }

    public static Basic createFull() {
        String postfix = SimpleUtils.getRandomString(6);

        Basic basic = new Basic();
        basic.setTitle(String.format(title, postfix));
        basic.setPromoKicker(String.format(promoKicker, postfix));
        basic.setPromoTitle(String.format(promoTitle, postfix));
        basic.setPromoDescription(String.format(promoDescription, postfix));

        return basic;
    }
}
