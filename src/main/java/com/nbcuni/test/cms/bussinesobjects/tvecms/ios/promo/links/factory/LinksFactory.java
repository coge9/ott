package com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.factory;

import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.Links;

/**
 * Created by Ivan_Karnilau on 23-Aug-16.
 */
public class LinksFactory {

    private LinksFactory(){
        super();
    }

    public static Links createWithAll() {
        Links links = new Links();
        links.addLink(LinkFactory.createLinkWithUrl());
        return links;
    }
}
