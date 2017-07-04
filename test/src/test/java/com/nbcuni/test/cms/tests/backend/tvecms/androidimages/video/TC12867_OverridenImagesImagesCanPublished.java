package com.nbcuni.test.cms.tests.backend.tvecms.androidimages.video;


import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.Image;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import org.testng.annotations.Test;

import java.util.List;

/**
 *
 * @author Aliaksei_Dzmitrenka
 *  Step 1: Open edit page for aqa video
 *  Step 2: Open android tab
 *  Step 3: Make sure that images that should be overriden is present
 *  Step 4: Override all images
 *  Step 5: Publish and save
 *  Step 6: Make sure that all images are published
 *
 *
 *
 */

public class TC12867_OverridenImagesImagesCanPublished extends BaseAuthFlowTest {

    private MainRokuAdminPage mainRokuAdminPage;
    private RokuBackEndLayer backEndLayer;

    @Test(groups = {"program_android_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasAndroidDataProvider", enabled = true)
    public void checkOneTileThumbnailIsGeneratedForOTTProgram(String brand) {
        this.brand = brand;
        SoftAssert softAssert = new SoftAssert();
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEVideoContentPage videoContentPage = contentPage.openRandomEditOTTVideoPage();
        // videoContentPage.onImagesTab().overrideAllImages();
        videoContentPage.clickSave();
        List<Image> image = videoContentPage.onImagesTab().getAllImages();//onAndroidImagesTab().getOverridenImages();
        videoContentPage.elementPublishBlock().publishByTabName();

        RokuVideoJson actualMetadata = requestHelper.getSingleParsedResponse(mainRokuAdminPage.getLogURL(brand), SerialApiPublishingTypes.VIDEO);
        softAssert.assertTrue(actualMetadata.getImages().containsAll(image), "Not all images are published", "All images are published", webDriver);
        softAssert.assertAll();
    }

}
