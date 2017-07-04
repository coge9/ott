package com.nbcuni.test.cms.utils;

import com.nbcuni.test.cms.backend.chiller.MetadataPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionGroupPage;
import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionsContentPage;
import com.nbcuni.test.cms.backend.chiller.pages.collections.CuratedCollectionPage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.*;
import com.nbcuni.test.cms.backend.chiller.pages.migration.MigrationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.apiinstances.ApiInstancesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MpxUpdaterPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ios.promo.PromoPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.dynamic.DynamicModulePage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.people.PeoplePage;
import com.nbcuni.test.cms.backend.tvecms.pages.taxonomy.TaxonomyPage;
import com.nbcuni.test.cms.pageobjectutils.Page;

/**
 * Created by Ivan_Karnilau on 11-Apr-16.
 */
public enum RokuPageMapping {

    ADD_NEW_PAGE("admin/ott/pages/add", AddNewPage.class),
    ADD_NEW_MEDIA_GALLERY("node/add/tve-media-gallery", MediaGalleryPage.class),
    ADD_NEW_SERIES("node/add/tve-series", SeriesPage.class),
    ADD_NEW_SEASON("node/add/tve-season", SeasonPage.class),
    ADD_NEW_EPISODE("node/add/tve-episode", EpisodePage.class),
    ADD_NEW_EVENT("node/add/tve-event", EventPage.class),
    CONTENT_PAGE("admin/content", ContentPage.class),
    METADATA_PAGE("admin/metadata", MetadataPage.class),
    TVE_PAGE("admin/ott/pages", TVEPage.class),
    ADD_NEW_PERSON_LINK("node/add/tve-person", PersonPage.class),
    ADD_NEW_ROLE_LINK("node/add/tve-role", RolePage.class),
    ASSET_LIBRARY("admin/ott/asset-library/thumbnails", AssetLibraryPage.class),
    ADD_NEW_CUATED_COLLECTION("admin/content/queues/add/curated_collection", CuratedCollectionPage.class),
    ADD_NEW_COLLECTION_GROUP("admin/content/queues/add/collection_group", CollectionGroupPage.class),
    COLLECTIONS_PAGE("admin/collection", CollectionsContentPage.class),
    ADD_NEW_POST("node/add/tve-post", PostPage.class),
    PEOPLE_PAGE("admin/people", PeoplePage.class),
    MIGRATION_PAGE("admin/content/migrate", MigrationPage.class),
    MPX_UPDATER("admin/content/ingest/settings", MpxUpdaterPage.class),
    API_INSTANCES("admin/config/services/api-services-instances", ApiInstancesPage.class),
    TAXONOMY("admin/structure/taxonomy", TaxonomyPage.class),
    PROMO("node/add/ott-promo", PromoPage.class),
    ADD_NEW_DYNAMIC("admin/ott/modules/add/dynamic", DynamicModulePage.class);

    private String url;
    private Class<? extends Page> clazz;
    RokuPageMapping(String url, Class<? extends Page> clazz) {
        this.url = url;
        this.clazz = clazz;
    }

    public static RokuPageMapping getPageMappingByClass(Class<? extends Page> clazz) {
        for (RokuPageMapping pageMapping : RokuPageMapping.values()) {
            if (pageMapping.clazz == clazz) {
                return pageMapping;
            }
        }
        throw new RuntimeException("Page not found");
    }

    public String getUrl() {
        return url;
    }

    public Class<? extends Page> getClazz() {
        return clazz;
    }
}
