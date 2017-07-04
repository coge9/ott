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
 * Created by Ivan_Karnilau on 19-Oct-15.
 */

/**
 * TC 10036
 *
 * Step 1: Go to MPX
 *
 * Step 2: Edit next to test OTT Program
 *
 * Step 3: Update Title and save
 *
 * Step 4: go to Roku CMS
 * Verify: user is on CMS
 *
 * Step 5: Run Cron
 * Verify: Cron is done
 *
 * Step 6: go to /admin/content
 * click "edit" next to test OTT Program
 * Verify: OTT Program page is opened
 *
 * Step 7: see THUMBNAILS block
 * find "tile1" line
 * Verify: 1tile image URL is displayed
 *
 * Step 8: open 1tile thumbnail URL in browser
 * Verify: 1080x405 image is displayed
 * all appropriate data is updated on thumbnail
 */
public class TC10036_UpdateTitleField extends BaseAuthFlowTest {

    private static final String INITIAL_TITLE = "AQA_OTT_PROGRAM";
    private static final Map<String, String> initialDataForMpx;
    private static final String INITIAL_CTA = "Initial CTA text";
    private static final String INITIAL_HEADLINE = "Initial Headline text";
    private static final String INITIAL_TEMPLATE_STYLE = "dark";
    private static final String UPDATED_TITLE = "AQA_OTT_PROGRAM_UPDATED";
    private static final Map<String, String> updatedDataForMpx;

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

    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkOneTileThumbnailIsGeneratedForOTTProgram() {
        Utilities.logInfoMessage("Check that 1tile thumbnail is generated for OTT Program");
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        SoftAssert softAssert = new SoftAssert();
        initialState();
        rokuBackEndLayer.loginAndSearchAssetInMPX(INITIAL_TITLE);
        rokuBackEndLayer.updateMpxData(updatedDataForMpx);

        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        mainRokuAdminPage.runCron(brand);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(UPDATED_TITLE);
        String imageLink = programContentPage.onRokuImagesTab().getOneTileProgramTwoCustomLinks()
                .get(CustomImageTypes.ONE_TILE_PROGRAM_1);

        String patterFilePath = GettingImages.getTitleFieldUpdated1TileProgram1();
        softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(patterFilePath,
                imageLink, 100.0), "Images are not equals", "Images are equals");

        softAssert.assertAll();
    }
}
