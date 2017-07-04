package com.nbcuni.test.cms.tests.backend.concerto.chiller.assetlibrary;

import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.EditMultipleFilesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 15-Apr-16.
 */

/**
 * TC14080
 *
 * 1. Go to CMS as admin
 * Admin panel is present
 * 2. Navigate to the Dashboard -> Asset Library
 * Asset Library page is opened
 * 3. Add new file [file_name]
 * File is added
 * 4. Navigate to the Asset Library page
 * Asset Library page is opened
 * 5. Type [file_name] in text field "File name", apply
 * Validation:    Check results
 * Filtered images contain [file_name] in titles
 */
public class TC14080_ImageHandling_CheckFiltering extends BaseAuthFlowTest {

    @Test(groups = {"asset_library"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkFiltering(final String brand) {

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);

        List<String> expectedFileNames = assetLibraryPage.uploadRandomFiles();
        EditMultipleFilesPage editMultipleFilesPage = new EditMultipleFilesPage(webDriver, aid);
        editMultipleFilesPage.save();

        assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);
        assetLibraryPage.filterByName(expectedFileNames.get(0));
        assetLibraryPage = new AssetLibraryPage(webDriver, aid);

        List<String> actualFilesNames = assetLibraryPage.getFilesNames();
        softAssert.assertEquals(1, actualFilesNames.size(), "File is not one", webDriver);
        softAssert.assertEquals(expectedFileNames, actualFilesNames, "File's name is not correct",
                "File's name is correct", webDriver);

        softAssert.assertAll();
    }
}
