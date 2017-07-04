package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.program.onetile.programone;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.GettingImages;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan_Karnilau on 27-Oct-15.
 */
public class TC10035_UpdateAllEditorsFields extends BaseAuthFlowTest {

    private static final String INITIAL_TITLE = "AQA_OTT_PROGRAM";
    private static final Map<String, String> initialDataForMpx;
    private static final String INITIAL_CTA = "Initial CTA text";
    private static final String INITIAL_HEADLINE = "Initial Headline text";
    private static final String INITIAL_TEMPLATE_STYLE = "dark";
    private static final String UPDATED_TITLE = "AQA_OTT_PROGRAM_UPDATED";
    private static final Map<String, String> updatedDataForMpx;
    private static final String UPDATED_CTA = "Updated CTA text";
    private static final String UPDATED_HEADLINE = "Updated Headline text";
    private static final String UPDATED_TEMPLATE_STYLE = "dark";

    static {
        initialDataForMpx = new HashMap<>();
        initialDataForMpx.put("Title", INITIAL_TITLE);
    }

    static {
        updatedDataForMpx = new HashMap<>();
        updatedDataForMpx.put("Title", UPDATED_TITLE);
    }

    public void initialState() {
        Utilities.logInfoMessage("Initial state");

        SoftAssert softAssert = new SoftAssert();

        rokuBackEndLayer.loginAndSearchAssetInMPX(INITIAL_TITLE);
        rokuBackEndLayer.updateMpxData(initialDataForMpx);

        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        mainRokuAdminPage.runCron(brand);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(INITIAL_TITLE);

        programContentPage.fillForm(INITIAL_CTA, INITIAL_HEADLINE);
        programContentPage.elementTemplateStyle().selectRadioButtonByName(INITIAL_TEMPLATE_STYLE);

        programContentPage.clickSave();

        mainRokuAdminPage.runCron(brand);

        contentPage = mainRokuAdminPage.openContentPage(brand);
        programContentPage = contentPage.openEditOTTProgramPage(INITIAL_TITLE);
        String imageLink = programContentPage.onRokuImagesTab().getOneTileProgramTwoCustomLinks()
                .get(CustomImageTypes.ONE_TILE_PROGRAM_1);

        String patterFilePath = GettingImages.getInitial1TileProgram1();
        softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(patterFilePath,
                imageLink, 100.0), "Initial Images are not equals", "Initial Images are equals");

        mainRokuAdminPage.logOut(brand);

        softAssert.assertAll();
    }

    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkOneTileThumbnailIsGeneratedForOTTProgram(final String brand) {
        Utilities.logInfoMessage("Check that 1tile thumbnail is generated for OTT Program");
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        initialState();
        SoftAssert softAssert = new SoftAssert();
        rokuBackEndLayer.loginAndSearchAssetInMPX(INITIAL_TITLE);
        rokuBackEndLayer.updateMpxData(updatedDataForMpx);

        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        mainRokuAdminPage.runCron(brand);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(UPDATED_TITLE);

        programContentPage.fillForm(UPDATED_CTA, UPDATED_HEADLINE);
        programContentPage.elementTemplateStyle().selectRadioButtonByName(UPDATED_TEMPLATE_STYLE);

        programContentPage.clickSave();

        mainRokuAdminPage.runCron(brand);

        contentPage = mainRokuAdminPage.openContentPage(brand);
        programContentPage = contentPage.openEditOTTProgramPage(UPDATED_TITLE);
        String imageLink = programContentPage.onRokuImagesTab().getOneTileProgramTwoCustomLinks()
                .get(CustomImageTypes.ONE_TILE_PROGRAM_1);

        String patterFilePath = GettingImages.getAllFieldsUpdated1TileProgram1();
        softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(patterFilePath,
                imageLink, 100.0), "Images are not equals", "Images are equals");

        softAssert.assertAll();
    }
}
