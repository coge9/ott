package com.nbcuni.test.cms.tests.backend.concerto.chiller.mpx;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ChillerVideoPage;
import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MpxUpdaterPage;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.tests.backend.tvecms.mpx.BaseMPXAccountSet;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 13-May-16.
 */

/**
 * TC14628
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
 * Validation: Check MPX metadata
 * Metadata ingests from MPX
 */
public class TC14628_VideoIngest extends BaseMPXAccountSet {

    @Test(groups = {"roku_ingest_chiller_hard"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void videoIngest(String brand) throws InterruptedException {
        Utilities.logInfoMessage("Check that user can ingest MPX Video content on roku " + brand);

        MpxUpdaterPage mpxUpdaterPage = mainRokuAdminPage.openPage(MpxUpdaterPage.class, brand);
        mpxUpdaterPage.updateAsset(config.getRokuMPXVideoID(brand, Instance.STAGE));

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        softAssert.assertTrue(contentPage.searchByType(ContentType.TVE_VIDEO).apply(), "Videos are present", webDriver);

        String title = contentPage.getFullTitleOfFirstElement();

        ChillerVideoPage chillerVideoPage = contentPage.clickEditLink(ChillerVideoPage.class, title);
        MpxAsset actualMPXAsset = chillerVideoPage.getMpxInfo();

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        MpxAsset expectedMPXAsset = mpxLayer.getMPXAsset();

        softAssert.assertTrue(expectedMPXAsset.verifyObject(actualMPXAsset), "Metadata is not corrected", webDriver);

        softAssert.assertAll();
    }
}
