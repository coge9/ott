package com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.utils.SimpleUtils;

/**
 * Created by Ivan_Karnilau on 19-Dec-15.
 */
public class CreateFactoryFeatureCarousel {

    private static Slug slugInfo = new Slug().setAutoSlug(false);

    private CreateFactoryFeatureCarousel(){
        super();
    }

    public static FeatureCarouselForm createDefaultFeatureCarousel() {
        String title = "Feature Carousel";
        slugInfo.setSlugValue("carousel");
        Boolean displayTitle = true;
        String tileVariant = "1 tile";
        Integer countAssets = 0;
        return new FeatureCarouselForm(title, displayTitle, slugInfo, tileVariant, countAssets);
    }

    public static FeatureCarouselForm createFeatureCarouselWithRandomTitle() {
        String title = "Feature Carousel " + SimpleUtils.getRandomString(4);
        return createEmptyFeatureCarousel().setTitle(title);
    }

    public static FeatureCarouselForm createEmptyFeatureCarousel() {
        return new FeatureCarouselForm();
    }

    public static FeatureCarouselForm createRandomFeatureCarousel() {
        String title = "Feature Carousel" + SimpleUtils.getRandomString(4);
        slugInfo.setSlugValue("carousel" + SimpleUtils.getRandomString(4));
        Boolean displayTitle = true;
        String tileVariant = "1 tile";
        Integer countAssets = 0;
        return new FeatureCarouselForm(title, displayTitle, slugInfo, tileVariant, countAssets);
    }

}
