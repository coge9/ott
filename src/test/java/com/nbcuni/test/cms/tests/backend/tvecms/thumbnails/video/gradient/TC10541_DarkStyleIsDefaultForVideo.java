package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.video.gradient;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.TemplateStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 3/14/16.
 */
public class TC10541_DarkStyleIsDefaultForVideo extends BaseAuthFlowTest {

    private String assetTitle;
    private String assetMPXId;
    private ContentPage contentPage;

    /**
     * Pre-Condition:
     * Make sure it is initial ingest. (program was not modified after it was ingested).
     * As alternative option it is possible to delete file and node for video and update it with MPX Updater.
     * Steps:
     * Step 1. Go to content page
     * Verify: User is on content page
     * <p/>
     * Step 2. Open edit page for any tve video
     * Verify: User is on edit video page
     * <p/>
     * Step 3: Verify template style selected value
     * Verify: Selected value is Dark
     */

    private void initialIngestAsset() {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Delete Node
        contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.searchByType(ContentType.TVE_VIDEO).apply();
        assetTitle = contentPage.getFullTitleOfFirstElement();
        assetMPXId = contentPage.getMPXId(assetTitle);
        contentPage.clickDeleteButton(assetTitle);

        //Ingest file initially
        rokuBackEndLayer.updateMPXAssetById(assetMPXId);
    }


    @Test(groups = {"video_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkDarkGradientIsDefault(String brand) {

        //Pre-Condition
        initialIngestAsset();

        //Step 1
        mainRokuAdminPage.openContentPage(brand);

        //Step 2
        EditTVEVideoContentPage videoContentPage = contentPage.openEditTVEVideoPage(assetTitle);

        //Step 3
        Assertion.assertTrue(videoContentPage.isTemplateStyleSelected(TemplateStyle.DARK), "Not Dark Style is selected for Video on default", webDriver);
        Utilities.logInfoMessage("The default template style for Video is Dark");
    }
}
