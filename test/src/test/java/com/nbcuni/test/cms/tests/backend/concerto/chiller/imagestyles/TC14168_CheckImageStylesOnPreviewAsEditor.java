package com.nbcuni.test.cms.tests.backend.concerto.chiller.imagestyles;

import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.EditMultipleFilesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ChillerExpectedImage;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ChillerImageEntity;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ChillerThumbnails;
import org.testng.annotations.Test;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by Aleksandra_Lishaeva on 4/25/16.
 */
public class TC14168_CheckImageStylesOnPreviewAsEditor extends BaseAuthFlowTest {

    private Map<ChillerThumbnails, BufferedImage> expectedImages;

    /**
     * Steps
     * Step 1: Go To CMS as Editor
     * Verify: The Editor Dashboard are present
     * <p/>
     * Step 2: Go To Asset Library page Dashboard/Asset Library or /admin/ott/asset-library/thumbnails
     * Verify:The Asset Library Page is opened
     * There is some image gallary with list of images.
     * <p/>
     * Step 3: Select and click on some image
     * Verify: The Edit image page is present with image details
     * There is image origin thumbnail with Image preview link
     * <p/>
     * Step 4: Click on 'Image preview link'
     * Verify: There Image preview page is opened
     * <p/>
     * Step 5: Check scaled image styles
     * Verify:There are origin source and next custom image styles under that:
     * wmax1920_hmax1080_scale,wmax1600_hmax900_scale,wmax1200_hmax800_scale,wmax600_hmax300_scale,wmax400_hmax200_scale
     */

    @Test(groups = {"chiller_image_styles"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkChillerImagePreview(final String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2
        AssetLibraryPage libraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);
        EditMultipleFilesPage multipleFilesPage = libraryPage.clickRandomAsset();
        expectedImages = new ChillerExpectedImage().getExpectedChillerImage(multipleFilesPage.getOriginalChiller());

        Map<ChillerThumbnails, ChillerImageEntity> actualImages = multipleFilesPage.getChillerImagesOnUI();
        for (Map.Entry<ChillerThumbnails, ChillerImageEntity> entry : actualImages.entrySet()) {
            ChillerThumbnails key = entry.getKey();
            if (key.getScaling()) {
                String value = entry.getValue().getSrc();
                softAssert.assertTrue(ImageUtil.compareImageAndUrl(expectedImages.get(key), value, 95.0), "Images [" + key.name() + "] are not equals. second one is acrual variant ", "Images [" + key.name() + "] are equals");
            }
        }

        softAssert.assertAll();
    }
}
