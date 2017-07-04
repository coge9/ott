package com.nbcuni.test.cms.tests.backend.concerto.chiller.assetlibrary;

import com.nbcuni.test.cms.backend.chiller.block.assetlibrary.FileBlock;
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
 * TC14083
 *
 * 1. Go to CMS as admin
 * Admin panel is present
 * 2. Navigate to the Dashboard -> Asset Library
 * Asset Library page is opened
 * 3. Upload new file [file_name]
 * File is uploaded
 * 4. Check delete button
 * Delete button is disable
 * 5. Mark [file_name]
 * Delete button is enable
 * 6. Click Deselect All
 * [file_name] is not marked
 * 7. Mark [file_name] and click delete
 * File is deleted
 */
public class TC14083_ImageHandling_CheckDeletingFiles extends BaseAuthFlowTest {

    @Test(groups = {"asset_library", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkDeletingFiles(final String brand) {

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);

        List<String> expectedFileNames = assetLibraryPage.uploadRandomFiles();
        EditMultipleFilesPage editMultipleFilesPage = new EditMultipleFilesPage(webDriver, aid);
        editMultipleFilesPage.save();

        assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);
        FileBlock fileBlock = assetLibraryPage.getFiles().get(0);

        softAssert.assertFalse(assetLibraryPage.isDeleteSelectedFilesEnable(), "DeleteSelectedFiles is enable", webDriver);

        fileBlock.check();

        softAssert.assertTrue(assetLibraryPage.isDeleteSelectedFilesEnable(), "DeleteSelectedFiles is not enable", webDriver);

        assetLibraryPage.deselectAll();

        softAssert.assertFalse(fileBlock.isSelected(), "File is marked", webDriver);

        fileBlock.check();

        assetLibraryPage.deleteSelectedFiles();

        assetLibraryPage = new AssetLibraryPage(webDriver, aid);
        assetLibraryPage.filterByName(expectedFileNames.get(0));

        softAssert.assertFalse(assetLibraryPage.getFilesNames().contains(expectedFileNames.get(0)), "File is not deleted",
                "File is deleted", webDriver);

        softAssert.assertAll();
    }
}
