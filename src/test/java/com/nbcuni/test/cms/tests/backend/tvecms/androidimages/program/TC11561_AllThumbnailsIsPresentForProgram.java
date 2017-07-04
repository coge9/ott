package com.nbcuni.test.cms.tests.backend.tvecms.androidimages.program;


import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 *
 * @author Aliaksei_Dzmitrenka
 *     Step 1: Open Aqa test program
 *  Step 2: Open preview for android images
 *  Step 3: Make sure that all images are present
 *
 *
 */

public class TC11561_AllThumbnailsIsPresentForProgram extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private RokuBackEndLayer backEndLayer;

    @Test(groups = {"program_android_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider", enabled = true)
    public void checkOneTileThumbnailIsGeneratedForOTTProgram(String brand) {
        this.brand = brand;
        Utilities.logInfoMessage("Check that android thumbnail is generated for OTT Program");

        SoftAssert softAssert = new SoftAssert();
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openRandomEditOTTProgramPage();
        programContentPage.onAndroidImagesTab().isAllAndroidImagesPresent(softAssert);
        softAssert.assertAll();
    }

}
