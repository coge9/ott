package com.nbcuni.test.cms.tests.backend.concerto.chiller.migration.nodes;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.otherinfo.MediaGalleryMigrationData;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.database.MySqlTestDataService;
import com.nbcuni.test.cms.utils.database.mysql.EntityMySQLFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alekca on 18.05.2016.
 */
public class TC14635_CheckMediaGalaryMigratedSmoke extends BaseAuthFlowTest {

    @Test(groups = "gallery_migration", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testThatAllNodeAreMigrated(String brand) {
        List<MediaGalleryMigrationData> mediaGalleries = EntityMySQLFactory.getInstance(MySqlTestDataService.getInstance("chiller"))
                .getMediaGallery();
        Utilities.logInfoMessage("Media Gallery size is: " + mediaGalleries.size());

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        List<MediaGalleryMigrationData> mediaGalleriesMigrated = new ArrayList<>();
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        for (MediaGalleryMigrationData mediaGallery : mediaGalleries) {

            contentPage.searchByType(ContentType.TVE_MEDIA_GALLERY).searchByTitle(mediaGallery.getTitle()).apply();
            softAssert.assertTrue(contentPage.isContentPresent(), "The Media Gallery node: " + mediaGallery.getTitle() + " is absent from content and didn't migrated",
                    "The Media Gallery node is migrated: " + mediaGallery.getTitle(), webDriver);
        }

        softAssert.assertAll();

    }
}
