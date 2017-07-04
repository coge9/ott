package com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.Promo;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.basic.factory.BasicFactory;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.factory.LinksFactory;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.media.MediaFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan_Karnilau on 8/29/2016.
 */
@Component("withAllPromo")
public class PromoWithAllFields implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {
        Promo promo = new Promo();
        promo.setBasic(BasicFactory.createFull());
        promo.setLinks(LinksFactory.createWithAll());
        promo.setImage(MediaFactory.createMediaImage());
        return promo;
    }
}
