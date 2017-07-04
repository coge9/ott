package com.nbcuni.test.cms.tests.backend.concerto.chiller.mpx;

import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ChillerVideoPage;
import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MpxUpdaterPage;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.tests.backend.tvecms.mpx.BaseMPXAccountSet;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 17-May-16.
 */

/**
 * TC14632
 *
 * Pre-Conditions:
 * 1. Delete MPX account
 * 2. Set MPX account for Chiller
 *
 * 1. Go to CMS as admin
 * The Admin Dashboard are present
 * 2. Go to MPX Updater page
 * MPX Updater page is opened
 * 3. Update single Video by MPX ID
 * Video is updated
 * 4. Go to Content page and edit video from step 3
 * Edit page is opened
 * 5. Get [image_name] with asset type "Original Image"

 * Validation:    Check Media tab
 * Check Asset Library

 * [image_name] is present on Media tab
 * [image_name] is present in Asset Library

 */

public class TC14632_VideoImageIngest extends BaseMPXAccountSet {

    private static final String ASSET_TYPE = "Original Image";

    @Test(groups = {"roku_ingest_chiller_hard"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void videoImageIngest(String brand) throws InterruptedException {
        Utilities.logInfoMessage("Check that user can ingest MPX Video image on roku " + brand);

        MpxUpdaterPage mpxUpdaterPage = mainRokuAdminPage.openPage(MpxUpdaterPage.class, brand);
        mpxUpdaterPage.updateAsset(config.getRokuMPXVideoID(brand, Instance.STAGE));

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        String expectedMpxThumbnail = mpxLayer.getThumbnailByAssetType(ASSET_TYPE).getTitle().split("\\.")[0];

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        softAssert.assertTrue(contentPage.searchByType(ContentType.TVE_VIDEO).apply(), "Videos are present", webDriver);

        String title = contentPage.getFullTitleOfFirstElement();

        ChillerVideoPage chillerVideoPage = contentPage.clickEditLink(ChillerVideoPage.class, title);

        String actualMpxThumbnailFromMediaTab = chillerVideoPage.onMediaTab().onMediaBlock().getMediaImages().get(0).getName();

        softAssert.assertContains(actualMpxThumbnailFromMediaTab, expectedMpxThumbnail,
                "Image is not corrected on Media tab", "Image is corrected on Media tab", webDriver);

        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);

        softAssert.assertTrue(assetLibraryPage.isImageInLibrary(actualMpxThumbnailFromMediaTab),
                "Image is not present in Asset Library", "Image is present in Asset Library", webDriver);

        softAssert.assertAll();
    }
}
