package com.nbcuni.test.cms.tests.backend.concerto.chiller.assetlibrary;

import com.nbcuni.test.cms.backend.chiller.block.assetlibrary.FileBlock;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 15-Apr-16.
 */

/**
 * 1. Go to CMS as admin
 * Admin panel is present
 * 2. Navigate to the Dashboard -> Asset Library
 * Asset Library page is opened
 * Validation: Check dimension for first image
 * Dimension is 316px x 214px
 */
public class TC14082_ImageHandling_CheckDimensionImages extends BaseAuthFlowTest {

    private final int expectedWidth = 316;
    private final int expectedHeight = 214;

    @Test(groups = {"asset_library"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkDimensionImages(final String brand) {

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);
        FileBlock fileBlock = assetLibraryPage.getFiles().get(0);

        softAssert.assertEquals(expectedHeight, fileBlock.getHeight(), "Height is not correct", "Height is correct", webDriver);
        softAssert.assertEquals(expectedWidth, fileBlock.getWidth(), "Width is not correct", "Width is correct", webDriver);

        softAssert.assertAll();
    }
}
