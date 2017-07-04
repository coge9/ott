package com.nbcuni.test.cms.tests.smoke.tvecms.imagestyle;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aliaksei_Klimenka1 on 8/22/2016.
 */
public class TC15304_Program_CheckIosImageStyles extends BaseAuthFlowTest {


    /**
     * Pre-conditions:
     * 1. Go to CMS
     * 2. GO to "Content" menu
     * 3. Click "edit" next to any Program asset
     *
     * Step 1. go to "iOS Images tab"
     * Verify: Image asset type is present: "[iOS 1600x900]"
     *         Image asset type is present: "[iOS 1965x1108]"
     * Step 2. check "[iOS 1600x900]" source image
     * Verify: source image is present
     *
     * Step 3. click on "preview" link below source image from previous step check for image styles
     * Verify: Image styles with related images are present:
     *      [landscape.widescreen.size1024.x1]" Size: 1024 x 576
     *      [landscape.widescreen.size640.x2]" Size: 1280 x 576
     *      [landscape.widescreen.size350.x2]" Size: 700 x 394
     * Step 4. check "[iOS 1965x1108]" source image
     * Verify: source image is present
     *
     * Step 5. click on "preview" link below source image from previous step check for image styles
     * Verify:
     * Image styles with related images are present:
     *      [landscape.widescreen.size1024.x1]" Size: 1024 x 576
     *      [landscape.widescreen.size640.x2]" Size: 1280 x 576
     *      [landscape.widescreen.size350.x2]" Size: 700 x 394
     * */


    @Test(groups = {"roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkCustomThumbnailAreGeneratedForOTTProgram(final String brand) {

        //Pre-condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        String randomProgramTitle = contentPage.getRandomAsset(ContentType.TVE_PROGRAM);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(randomProgramTitle);

        //Step 1,2
        softAssert.assertTrue(programContentPage.onIosImagesTab().isAllIosImagesStylesPresent(softAssert),
                "Some Ios styles are not present");
        //Step 3,4,5
        softAssert.assertTrue(programContentPage.onIosImagesTab().isAllIosImagesSourcePresent(),
                "Some Ios Images Source isn't Present", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }
}
