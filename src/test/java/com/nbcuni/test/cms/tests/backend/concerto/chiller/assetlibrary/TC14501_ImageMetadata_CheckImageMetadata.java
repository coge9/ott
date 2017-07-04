package com.nbcuni.test.cms.tests.backend.concerto.chiller.assetlibrary;

import com.nbcuni.test.cms.backend.chiller.block.assetlibrary.FileBlock;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.EditMultipleFilesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.factory.FilesMetadataCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 12-May-16.
 */

/**
 * TC14501
 *
 * 1. Go to CMS as admin
 * Admin panel is present
 * 2. Navigate to the Dashboard -> Asset Library
 * Asset Library page is opened
 * 3. Upload any file [file_name]
 * File is uploaded
 * 4. Go to edit page for [file_name]
 * Edit page is opened
 * 5. Fill metadata fields:
 Caption (Plain Text)
 Description (Long Text)
 High Resolution (checkbox)
 Categories (Field implemented in US24973)
 Admin should be able to setup list of values for category field for Images.
 Tag (Field implemented in US24974)
 Alt Text (text)
 Credit (text)
 Copyright (text)
 Source (text)
 "Association" Section
 Fields are filled
 6. Save metadata
 Metadata is saved
 Validation: Check metadata
 Metadata is corrected
 */
public class TC14501_ImageMetadata_CheckImageMetadata extends BaseAuthFlowTest {

    private FilesMetadata expectedFilesMetadata;

    @BeforeMethod(alwaysRun = true)
    public void initFileMetadata() {
        expectedFilesMetadata = FilesMetadataCreationStrategy.createFilesMetadata();
    }

    @Test(groups = {"asset_library"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkImageMetadata(final String brand) {

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);

        String fileName = assetLibraryPage.uploadRandomFiles().get(0);
        EditMultipleFilesPage editMultipleFilesPage = new EditMultipleFilesPage(webDriver, aid);
        editMultipleFilesPage.save();

        assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);
        assetLibraryPage.filterByName(fileName);
        FileBlock fileBlock = assetLibraryPage.getFiles().get(0);
        fileBlock.click();

        editMultipleFilesPage = new EditMultipleFilesPage(webDriver, aid);
        editMultipleFilesPage.enterFilesMetadata(expectedFilesMetadata);
        editMultipleFilesPage.save();
        fileName = expectedFilesMetadata.getImageGeneralInfo().getTitle();

        assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);
        assetLibraryPage.filterByName(fileName);
        fileBlock = assetLibraryPage.getFiles().get(0);
        fileBlock.click();

        editMultipleFilesPage = new EditMultipleFilesPage(webDriver, aid);
        FilesMetadata actualFilesMetadata = editMultipleFilesPage.getPageData();

        softAssert.assertEquals(expectedFilesMetadata, actualFilesMetadata, "Metadata is not corrected", webDriver);

        softAssert.assertAll();
    }
}
