package com.nbcuni.test.cms.tests.smoke.tvecms.imagestyle;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.PlatformApi;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 26-Oct-15.
 */
public class TC12853_Program_CheckAllThumbnailIsGenerated extends BaseAuthFlowTest {

    /**
     * Step 1. go to Roku CMS
     * Verify: user is on CMS
     * <p/>
     * Step 2. go to /admin/content
     * Verify: click "edit" next to test OTT Program
     * OTT Program page is opened
     * <p/>
     * Step 3. see THUMBNAILS block
     * find "tile1" line
     * Verify: 1tile image URL is displayed
     * <p/>
     * Step 4. open 1tile thumbnail URL in browser
     * 1080x405 image is displayed
     * image consists of:
     * -MPX image (983x554 Featured, iPhoneRetina, Large5)
     * -CTA BOX (Dark gradient - 216px Fill Color: Black; Gradient Dark: Black @ 100% > Black @ 0% 190px)
     * Play icon is displayed
     */

    private String initialTitle;


    @Test(groups = {"program_thumbnails", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkCustomThumbnailAreGeneratedForOTTProgram(final String brand) {
        Utilities.logInfoMessage("Check that 1tile thumbnail is generated for OTT Program");
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        SoftAssert softAssert = new SoftAssert();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        initialTitle = contentPage.getRandomAsset(ContentType.TVE_PROGRAM);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(initialTitle);
        Boolean isRokuPublishedToConcerto = new PlatformApi(brand).isRokuPublishedToConcerto();
        if (isRokuPublishedToConcerto) {
            programContentPage.onRokuSqsImagesTab().verifyThumbnails(softAssert);
        } else {
            programContentPage.onRokuImagesTab().verifyThumbnails(softAssert);
        }
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }
}
