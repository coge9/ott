package com.nbcuni.test.cms.collectservices.promo;

import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.GlobalPromoEntity;

/**
 * Interface design for collecting data about Promo Node.
 */
public interface PromoDataCollector {

    GlobalPromoEntity collectPromoInfo(String assetTitle);
}
