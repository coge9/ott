package com.nbcuni.test.cms.utils.database.mysql;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.otherinfo.MediaGalleryMigrationData;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.otherinfo.PublishingOptions;
import com.nbcuni.test.cms.pageobjectutils.entities.User;
import com.nbcuni.test.cms.pageobjectutils.tveservice.Queries;
import com.nbcuni.test.cms.utils.database.DataBaseService;
import com.nbcuni.test.cms.utils.migration.UploadQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityMySQLFactory {
    private static volatile EntityMySQLFactory instance;
    private static DataBaseService dataBaseService;

    private EntityMySQLFactory() {
    }

    public static EntityMySQLFactory getInstance(DataBaseService dataBaseService) {
        EntityMySQLFactory.dataBaseService = dataBaseService;
        if (instance == null) {
            synchronized (EntityMySQLFactory.class) {
                if (instance == null) {
                    instance = new EntityMySQLFactory();
                }
            }
        }
        return instance;
    }

    public List<MediaGalleryMigrationData> getMediaGallery() {
        List<MediaGalleryMigrationData> mediaGalleries = new ArrayList<>();
        final List<Map<String, String>> data = dataBaseService.executeQueryResultAsListBySsh(UploadQuery.getQuery(Queries.MEDIA_GALLERY));
        for (final Map<String, String> row : data) {
            MediaGalleryMigrationData mediaGallery = new MediaGalleryMigrationData();
            mediaGallery.setTitle(row.get("title"));
            PublishingOptions options = new PublishingOptions();
            options.setPublished(Boolean.valueOf(row.get("status")));
            options.setPromoted(Boolean.valueOf(row.get("promote")));
            options.setSticky(Boolean.valueOf(row.get("sticky")));
            mediaGallery.setPublishingOptions(options);
            mediaGallery.setComment(row.get("comment"));
            mediaGallery.setChanged(row.get("changed"));
            mediaGallery.setCreated(row.get("created"));
            mediaGallery.setTranslate(row.get("translate"));
            mediaGallery.setLanguage(row.get("language"));
            mediaGallery.setVid(row.get("vid"));
            mediaGallery.setUid(row.get("uid"));
            mediaGalleries.add(mediaGallery);
        }
        return mediaGalleries;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        final List<Map<String, String>> data = dataBaseService.executeQueryResultAsListBySsh(UploadQuery.getQuery(Queries.USERS));
        for (final Map<String, String> row : data) {
            User user = new User();
            user.setUsername(row.get("name"));
            user.setEmail(row.get("mail"));
            users.add(user);
        }
        return users;
    }
}
