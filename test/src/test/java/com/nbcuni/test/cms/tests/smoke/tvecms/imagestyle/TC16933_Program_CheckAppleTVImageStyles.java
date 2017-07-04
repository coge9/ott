package com.nbcuni.test.cms.tests.smoke.tvecms.imagestyle;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.ContentTabs;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class TC16933_Program_CheckAppleTVImageStyles extends BaseAuthFlowTest {

    /**
     * Pre-conditions:
     * 1. Go to CMS
     * 2. GO to "Content" menu
     * 3. Click "edit" next to any Program asset
     *
     * Step 1. go to "AppleTV Images tab"
     * Verify: Image asset type is present: "[AppleTV 1920x1080]"
     * Image asset type is present: "[AppleTV 1920x486]"
     * Image asset type is present: "[AppleTV 1704x440]"
     * Image asset type is present: "[AppleTV 1600x900]"
     * Image asset type is present: "[AppleTV 771x292]"
     * Image asset type is present: "[AppleTV 600x600]"

     * 2. check "[AppleTV 1920x1080]" source image
     * source image is present
     * 3. click on "preview" link below source image from previous step
     * check for image styles
     * Image styles with related images are present:
     * "[landscape_1920x1080_x1]" Size: 1920x1080
     * 4. check "[AppleTV 1920x486]" source image
     * source image is present
     * 5. click on "preview" link below source image from previous step
     * check for image styles
     * Image styles with related images are present:
     * "[landscap_1920x486_x1]" Size: 1920x486
     * 6. check "[AppleTV 1704x440]" source image
     * source image is present
     * 7. click on "preview" link below source image from previous step
     * check for image styles
     * Image styles with related images are present:
     * "[landscap_1704x440_x1]" Size: 1704x440
     * 8. check "[AppleTV 1600x900]" source image
     * source image is present
     * 9. click on "preview" link below source image from previous step
     * check for image styles
     * Image styles with related images are present:
     * "[landscap_127x71_x1]" Size: 127x71
     * "[landscap_127x71_x1_5]" Size: 190x110
     * "[landscap_255x143_x1]" Size: 255x143
     * "[landscap_255x143_x1_5]" Size: 382x215
     * "[landscap_540x304_x1]" Size: 540x304
     * "[landscap_540x304_x1_5]" Size: 720x405

     * 10. check "[AppleTV 771x292]" source image
     * source image is present
     * 11. click on "preview" link below source image from previous step
     * check for image styles
     * Image styles with related images are present:
     * "[landscap_771x292_x1]" Size: 771x292
     * 12. check "[AppleTV 600x600]" source image
     * source image is present
     * 13. click on "preview" link below source image from previous step
     * check for image styles
     * Image styles with related images are present:
     * "[square_600x600_x1]" Size: 600x600
     * */


    @Test(groups = {"roku_smoke", "program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkCustomThumbnailAreGeneratedForOTTProgram(final String brand) {

        //Pre-condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        String randomProgramTitle = contentPage.getRandomAsset(ContentType.TVE_PROGRAM);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(randomProgramTitle);

        //Step 1,2
        if (!programContentPage.isTabPresent(ContentTabs.APPLETV_IMAGES)) {
            Utilities.logSevereMessage("AplletTV images tab is not present, probably platform was not configured.");
            throw new SkipException("AplletTV images tab is not present, probably platform was not configured.");
        }
        softAssert.assertTrue(programContentPage.onAppleTVImagesTab().isAllAppleTVImagesStylesPresent(softAssert),
                "Some AppleTV styles are not present");
        //Step 3,4,5
        softAssert.assertTrue(programContentPage.onAppleTVImagesTab().isAllAppleTVImagesSourcePresent(),
                "Some AppleTV Image sources aren't present", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }
}
