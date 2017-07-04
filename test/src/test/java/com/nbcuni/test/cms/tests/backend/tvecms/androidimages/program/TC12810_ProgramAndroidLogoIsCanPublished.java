package com.nbcuni.test.cms.tests.backend.tvecms.androidimages.program;


import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.Image;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 *
 * @author Aliaksei_Dzmitrenka
 *  Step 1: Open edit page for aqa program
 *  Step 2: Open android tab
 *  Step 3: Get expected info about program logo
 *  Step 4: Save and publish
 *  Step 5: Verify that program was published
 *
 */

public class TC12810_ProgramAndroidLogoIsCanPublished extends BaseAuthFlowTest {

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
        Image programLogo = programContentPage.onAndroidImagesTab().getProgramLogo();
        programContentPage.clickSave();
        programContentPage.elementPublishBlock().publishByTabName();

        RokuProgramJson actualMetadata = requestHelper.getSingleParsedResponse(mainRokuAdminPage.getLogURL(brand), SerialApiPublishingTypes.PROGRAM);
        Utilities.logInfoMessage(actualMetadata.getImages().toString());
        softAssert.assertTrue(actualMetadata.getImages().contains(programLogo), "programLogo is not present", webDriver);
        softAssert.assertAll();
    }

}
