package com.nbcuni.test.cms.tests.backend.concerto.chiller.migration;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.otherinfo.MediaGalleryMigrationData;
import com.nbcuni.test.cms.utils.database.MySqlTestDataService;
import com.nbcuni.test.cms.utils.database.mysql.EntityMySQLFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 5/6/16.
 */
public class TestSSHConnection {

    @Test(groups = "chiller_migration_test")
    public void testConnection() {
        List<MediaGalleryMigrationData> mediaGalleries = EntityMySQLFactory.getInstance(MySqlTestDataService.getInstance("chiller"))
                .getMediaGallery();
        Utilities.logSevereMessageThenFail("Media Gelery size is: " + mediaGalleries.size());
    }
}
