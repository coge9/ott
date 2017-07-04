package com.nbcuni.test.cms.collectservices.promo;

import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.GlobalPromoEntity;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.transformers.PromoJsonTransformer;

/**
 * Implementation of interface which collect info about promo node from Content API. Collect data for all brands both chiller and NON-chiller.
 */
public class ContentApiPromoDataCollector implements PromoDataCollector {

    private String brand;

    public ContentApiPromoDataCollector(String brand) {
        this.brand = brand;
    }

    /**
     * *********************************************************************************
     * Method Name: collectPromoInfo
     * Description: method for collecting promo DATA in current implementation from Content API.
     *
     * @param assetTitle - title of the promo under test.
     * @return GlobalPromoEntity
     * ***********************************************************************************
     */
    @Override
    public GlobalPromoEntity collectPromoInfo(String assetTitle) {
        NodeApi nodeApi = new NodeApi(brand);
        GlobalNodeJson promoNode = nodeApi.getPromoNodeByName(assetTitle);
        return PromoJsonTransformer.getGlobalPromoEntity(promoNode, brand);
    }
}
