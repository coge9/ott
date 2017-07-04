package com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic.Dynamic;
import com.nbcuni.test.cms.pageobjectutils.entities.rules.OrderType;
import com.nbcuni.test.cms.pageobjectutils.entities.rules.SortingType;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.dynamic.Programs;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.dynamic.VideoType;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CreationFactoryDynamicModule {

    private static final int maxCount = 5;
    public static final String DYNAMIC = "dynamic";
    public static final String DYNAMIC_TITLE = "Dynamic ";

    private CreationFactoryDynamicModule(){
        super();
    }

    public static Dynamic createDynamicDefault() {
        Dynamic dynamic = new Dynamic();
        String title = "Dynamic";
        Slug slugInfo = new Slug().setAutoSlug(false).setSlugValue("dynamicCollection");
        Boolean displayTitle = true;
        ContentType contentType = ContentType.TVE_VIDEO;
        VideoType videoType = VideoType.ALL;
        Integer maxItems = 0;
        SortingType sortBy = SortingType.AIR_DATE;
        OrderType order = OrderType.DESC;
        Programs programs = Programs.ALL;

        dynamic.setTitle(title).setSlug(slugInfo);
        dynamic.setDisplayTitle(displayTitle);
        dynamic.setContentType(contentType);
        dynamic.setVideoType(videoType);
        dynamic.setMaxContentItem(maxItems);
        dynamic.setSortBy(sortBy);
        dynamic.setOrder(order);
        dynamic.setPrograms(programs);

        return dynamic;
    }

    public static Dynamic createDynamicForVideo() {
        Dynamic dynamic = new Dynamic();
        String title = DYNAMIC_TITLE + SimpleUtils.getRandomString(4);
        Slug slugInfo = new Slug().setAutoSlug(false).setSlugValue(DYNAMIC);

        Integer maxItems = new Random().nextInt(maxCount);

        dynamic.setTitle(title).setSlug(slugInfo);
        dynamic.setMaxContentItem(maxItems);

        return dynamic;
    }

    public static Dynamic createDynamicForVideo(SortingType sortBy) {
        Dynamic dynamic = new Dynamic();
        String title = DYNAMIC_TITLE + SimpleUtils.getRandomString(4);
        Slug slugInfo = new Slug().setAutoSlug(false).setSlugValue(DYNAMIC);
        Integer maxItems = new Random().nextInt(maxCount);

        dynamic.setTitle(title).setSlug(slugInfo);
        dynamic.setMaxContentItem(maxItems);
        dynamic.setSortBy(sortBy);

        return dynamic;
    }

    public static Dynamic createCustomizeDynamicForVideo(String brand) {
        Dynamic dynamic = new Dynamic();
        String title = DYNAMIC_TITLE + SimpleUtils.getRandomString(4);
        Slug slugInfo = new Slug().setAutoSlug(false).setSlugValue(DYNAMIC);
        Integer maxItems = maxCount;

        dynamic.setTitle(title).setSlug(slugInfo);
        dynamic.setMaxContentItem(maxItems);
        dynamic.setPrograms(Programs.CUSTOMIZE);
        List<String> programs = new ArrayList<>(new NodeApi(brand).getAllPublishedPrograms().getEntity().values());
        dynamic.setAssets(programs.stream().limit(new Random().nextInt(programs.size())).collect(Collectors.toList()));

        return dynamic;
    }

    public static Dynamic createDynamicForProgram() {
        Dynamic dynamic = new Dynamic();
        String title = DYNAMIC_TITLE + SimpleUtils.getRandomString(4);
        Slug slugInfo = new Slug().setAutoSlug(false).setSlugValue(DYNAMIC);
        Integer maxItems = new Random().nextInt(maxCount);

        dynamic.setTitle(title).setSlug(slugInfo);
        dynamic.setMaxContentItem(maxItems);

        return dynamic;
    }
}
