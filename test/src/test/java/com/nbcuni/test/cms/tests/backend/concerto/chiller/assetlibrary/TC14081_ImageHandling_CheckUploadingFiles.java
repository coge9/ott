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
 * TC14081
 *
 * 1. Go to CMS as admin
 * Admin panel is present
 * 2. Navigate to the Dashboard -> Asset Library
 * Asset Library page is opened
 * 3. Click Upload files
 * Default upload files window is presented
 * 4. Selelect several files and click Open
 * Default upload files window is closed
 * Validation: Check files
 * Files from step 4 is presented
 */
public class TC14081_ImageHandling_CheckUploadingFiles extends BaseAuthFlowTest {

    @Test(groups = {"asset_library"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkUploadingFiles(final String brand) {

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);

        List<String> expectedFileNames = assetLibraryPage.uploadRandomFiles(2);
        EditMultipleFilesPage editMultipleFilesPage = new EditMultipleFilesPage(webDriver, aid);
        editMultipleFilesPage.save();

        assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);
        List<String> actualFilesNames = assetLibraryPage.getFilesNames();
        softAssert.assertTrue(actualFilesNames.containsAll(expectedFileNames), "Files are not uploaded", "Files are uploaded", webDriver);

        softAssert.assertAll();
    }
}
