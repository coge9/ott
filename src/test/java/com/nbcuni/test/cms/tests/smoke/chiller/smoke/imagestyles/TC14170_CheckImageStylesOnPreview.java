package com.nbcuni.test.cms.tests.smoke.chiller.smoke.imagestyles;

import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.EditMultipleFilesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 4/25/16.
 */
public class TC14170_CheckImageStylesOnPreview extends BaseAuthFlowTest {

    /**
     * Steps
     * Step 1: Go To CMS as Editor
     * Verify: The Editor Dashboard are present
     *
     * Step 2: Go To Asset Library page Dashboard/Asset Library or /admin/ott/asset-library/thumbnails
     * Verify:The Asset Library Page is opened
     * There is some image gallary with list of images.
     *
     * Step 3: Select and click on some image
     * Verify: The Edit image page is present with image details
     * There is image origin thumbnail with Image preview link
     *
     * Step 4: Click on 'Image preview link'
     * Verify: There Image preview page is opened
     *
     * Step 5: Check scaled image styles
     * Verify:There are origin source and next custom image styles under that:
     * wmax1920_hmax1080_scale,wmax1600_hmax900_scale,wmax1200_hmax800_scale,wmax600_hmax300_scale,wmax400_hmax200_scale
     * */

    @Test(groups = {"chiller_image_styles", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkChillerImagePreview(final String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2
        AssetLibraryPage libraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);
        EditMultipleFilesPage multipleFilesPage = libraryPage.clickRandomAsset();

        softAssert.assertTrue(multipleFilesPage.isAllChillerImagesArePresent(softAssert)
                , "Not all of the chiller's images are present"
                , "All of the Chiller's images are present", webDriver);

        softAssert.assertAll();
    }
}
