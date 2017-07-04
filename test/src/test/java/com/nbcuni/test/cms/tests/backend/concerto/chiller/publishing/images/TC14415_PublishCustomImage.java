package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.images;


import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.EditMultipleFilesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.factory.FilesMetadataCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.ImageVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Step 1.    Go To CMS As Admin
 * Verify:    The admin Panel is present
 * <p>
 * Step 2. Go To Asset Libarry Page (Dashboard->Asset Library)
 * Verify: The The Asset Library Page is opened. There is a list of images
 * <p>
 * Step 3. Upload a custom image to asset library
 * Verify:    The image is uploaded. Edit page of asset library is present
 * <p>
 * Step 4. Click button 'publish'
 * Verify:    The POST request is send to API. Response message is 'success'
 * <p>
 * Step 5. Check scheme of JSON within POST request and values
 * Verify: The JSON is next: http://docs.concertoapiingestmaster.apiary.io/#reference/image/post-image/generate-message-body-to-create-or-update-image
 */

public class TC14415_PublishCustomImage extends BaseAuthFlowTest {
    private FilesMetadata expectedFilesMetadata;

    @BeforeMethod(alwaysRun = true)
    public void initFileMetadata() {
        expectedFilesMetadata = FilesMetadataCreationStrategy.createFilesMetadata();
    }

    @Test(groups = {"image_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void test(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        //open random image
        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);

        String fileName = assetLibraryPage.uploadRandomFiles().get(0);
        expectedFilesMetadata.getImageGeneralInfo().setTitle(fileName);
        EditMultipleFilesPage editMultipleFilesPage = new EditMultipleFilesPage(webDriver, aid);
        editMultipleFilesPage.enterFilesMetadata(expectedFilesMetadata);
        editMultipleFilesPage.save();

        softAssert.assertTrue(editMultipleFilesPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        //reopen image
        editMultipleFilesPage = rokuBackEndLayer.openForEditImageFromAssetLibrary(fileName);
        ImagesJson expectedImage = editMultipleFilesPage.updateMetadataForPublishing(expectedFilesMetadata);
        //publish and verify image json
        editMultipleFilesPage.publish();
        String url = editMultipleFilesPage.getLogURL(brand);
        softAssert.assertTrue(editMultipleFilesPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        ImagesJson actualImageJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.FILE_IMAGE);
        softAssert.assertTrue(new ImageVerificator().verify(expectedImage, actualImageJson), "The actual JSON data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}
