package com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.FeatureShowModule;
import com.nbcuni.test.cms.utils.SimpleUtils;

import static com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.FeatureShowModuleConstant.RANDOM_ASSET;

/**
 * Created by Alena_Aukhukova
 */
public class CreateFeatureShowModule {

    private CreateFeatureShowModule(){
        super();
    }

    public static FeatureShowModule createDefault() {
        String title = "Feature Show Module title " + SimpleUtils.getRandomString(4);
        Slug slugInfo = new Slug().setSlugValue(title.replaceAll(" ", "-").toLowerCase()).setAutoSlug(false);
        FeatureShowModule featureShowModule = new FeatureShowModule();
        featureShowModule.setTitle(title).setDisplayTitle(true);
        featureShowModule.setSlug(slugInfo);
        return featureShowModule;
    }

    public static FeatureShowModule createUpdatedModuleForAdmin(FeatureShowModule entityForUpdate) {
        entityForUpdate.setTitle(entityForUpdate.getTitle() + "_new")
                .setDisplayTitle(false)
                .setAssets(RANDOM_ASSET)
                .setAssetsCount(1);
        Slug slugInfo = new Slug().setSlugValue(entityForUpdate.getTitle().replaceAll(" ", "-").toLowerCase()).setAutoSlug(false);
        entityForUpdate.setSlug(slugInfo);
        return entityForUpdate;
    }

    public static FeatureShowModule createUpdatedModuleForEditor() {
        FeatureShowModule featureShowModule = new FeatureShowModule();
        String title = "Feature Show Module title " + SimpleUtils.getRandomString(4);
        Slug slugInfo = new Slug().setSlugValue(title.replaceAll(" ", "-").toLowerCase()).setAutoSlug(false);
        featureShowModule.setTitle(title + "_new")
                .setDisplayTitle(true).setAssets(RANDOM_ASSET);
        featureShowModule.setSlug(slugInfo);
        return featureShowModule;
    }

    public static FeatureShowModule createEmpty() {
        FeatureShowModule featureShowModule = new FeatureShowModule();
        featureShowModule.setTitle("")
                .setDisplayTitle(true);
        return featureShowModule;
    }

}
