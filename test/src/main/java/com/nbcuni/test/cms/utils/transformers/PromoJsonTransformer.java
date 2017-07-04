package com.nbcuni.test.cms.utils.transformers;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Promotional;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.GlobalPromoEntity;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ChannelReferencesJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto.PromoJson;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class which is going to convert GlobalPromoEntity to different objects used in
 * publishing.
 */
public class PromoJsonTransformer {

    // Logic for NON-Chiller brands using in Concerto API publishing.
    public static synchronized PromoJson forConcertoApiAll(GlobalPromoEntity promo) {
        PromoJson promoJson = getCommonConcertoData(promo);
        // add empty list of media
        promoJson.setMedia(promo.getImageSource() != null ? Collections.singletonList(MediaJsonTransformer
                .getMediaJsonForConcertoPromo(promo.getImageSource())) : new ArrayList<>());
        return promoJson;
    }

    // Common logic for Concerto API publishing.
    private static synchronized PromoJson getCommonConcertoData(GlobalPromoEntity promo) {
        PromoJson promoJson = new PromoJson();
        promoJson.setTitle(promo.getTitle());
        promoJson.setSlug(promo.getSlugInfo().getSlugValue());
        promoJson.setItemType(ItemTypes.PROMO.getItemType());
        promoJson.setRevision(promo.getGeneralInfo().getRevision());
        promoJson.setPublished(promo.getPublished());
        promoJson.setProgram(new ChannelReferencesJson());
        promoJson.setPromoKicker("".equals(promo.getPromotional().getPromotionalKicker()) ? null : promo.getPromotional().getPromotionalKicker());
        promoJson.setPromoDescription("".equals(promo.getPromotional().getPromotionalDescription()) ? null : promo.getPromotional().getPromotionalDescription());
        promoJson.setPromoTitle("".equals(promo.getPromotional().getPromotionalTitle()) ? null : promo.getPromotional().getPromotionalTitle());
        promoJson.setTags(new ArrayList<>());
        promoJson.setCategories(new ArrayList<>());
        return promoJson;
    }

    public static synchronized GlobalPromoEntity getGlobalPromoEntity(GlobalNodeJson globalNodeJson, String brand) {
        GlobalPromoEntity globalPromoEntity = new GlobalPromoEntity();
        globalPromoEntity.setTitle(globalNodeJson.getTitle());
        globalPromoEntity.setUpdatedDate(globalNodeJson.getUpdatedDate());
        Promotional promotional = new Promotional();
        promotional.setPromotionalKicker(globalNodeJson.getFeatureCarouselCta());
        promotional.setPromotionalTitle(globalNodeJson.getFeatureCarouselHeadline());
        promotional.setPromotionalDescription(null);
        globalPromoEntity.setPromotional(promotional);
        Slug slug = new Slug();
        slug.setAutoSlug(globalNodeJson.getUrlPath());
        slug.setSlugValue(globalNodeJson.getSlug());
        globalPromoEntity.setSlugInfo(slug);
        globalPromoEntity.setImageSource(globalNodeJson.getImageSources().isEmpty() ? null :
                urlTransformer(globalNodeJson.getImageSources().get(0), brand));
        Associations associations = new Associations();
        associations.getChannelReferenceAssociations().setChannelReference(new ChannelReference().setSeries(null)
                .setItemType("series"));
        globalPromoEntity.setAssociations(associations);
        globalPromoEntity.getGeneralInfo().setRevision(Integer.parseInt(globalNodeJson.getVid()));
        globalPromoEntity.getGeneralInfo().setUuid(globalNodeJson.getUid());
        globalPromoEntity.setPublished(globalNodeJson.getPublished());
        return globalPromoEntity;
    }

    private static synchronized ImageSource urlTransformer(ImageSource imageSource, String brand) {
        String urlPart = String.format("%ssites/%s/files/", Config.getInstance().getRokuHomePage(brand), brand);
        Config.getInstance().getRokuHomePage(brand);
        String url = imageSource.getImageUrl();
        url = url.replace("public://", urlPart);
        imageSource.setImageUrl(url);
        return imageSource;

    }

}
