package com.nbcuni.test.cms.utils.transformers;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic.Dynamic;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.dynamic.Programs;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.ListItemsJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.ModuleApi;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.ModuleNodeJson;
import com.nbcuni.test.cms.utils.sortcontent.LatestVideos;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 5/23/2017.
 */
public class ModuleJsonTransformer {

    public static CollectionJson fromDynamicModuleToCollection(Dynamic dynamic, String brand) {
        CollectionJson json = new CollectionJson(dynamic);

        List<ListItemsJson> items = new LinkedList<>();
        if (dynamic.getContentType().equals(ContentType.TVE_VIDEO)) {
            List<GlobalVideoEntity> videos;
            if (dynamic.getPrograms().equals(Programs.ALL)) {
                videos = LatestVideos.getPublishedVideos(brand, dynamic.getSortBy(),
                        dynamic.getOrder(), dynamic.getMaxContentItem());
            } else {
                videos = LatestVideos.getPublishedVideos(brand, dynamic.getAssets(), dynamic.getSortBy(),
                        dynamic.getOrder(), dynamic.getMaxContentItem());
            }
            videos.stream().forEach(video -> items.add(new ListItemsJson()
                    .setItemType("video").setUuid(video.getMpxAsset().getMpxAssetUuid())));
        } else {
            List<GlobalProgramEntity> programs = LatestVideos.getPublishedPrograms(brand, dynamic.getOrder(), dynamic.getMaxContentItem());
            programs.stream().forEach(program -> items.add(new ListItemsJson()
                    .setItemType("series").setUuid(program.getMpxAsset().getMpxAssetUuid())));
        }
        json.setListItems(items);
        ModuleNodeJson moduleNodeJson = new ModuleApi(brand).getModuleByTitle(dynamic.getTitle());
        json.setUuid(moduleNodeJson.getUuid());
        json.setRevision(Integer.parseInt(moduleNodeJson.getVid()));
        return json;
    }
}
