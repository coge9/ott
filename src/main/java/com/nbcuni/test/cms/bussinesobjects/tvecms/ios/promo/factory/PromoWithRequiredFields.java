package com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.Promo;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.basic.factory.BasicFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan_Karnilau on 8/29/2016.
 */
@Component("withRequiredPromo")
public class PromoWithRequiredFields implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {
        Promo promo = new Promo();
        promo.setBasic(BasicFactory.createWithRequired());
        return promo;
    }
}
