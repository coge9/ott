package com.nbcuni.test.cms.tests.smoke.chiller.smoke.publishing.images;

import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.EditMultipleFilesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.factory.FilesMetadataCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public class TC14936_ValidateImagesJson extends BaseAuthFlowTest {

    /**
     Pre-conditions:
     1. Login in CMS as Admin
     2. Go To Asset Library Page (Dashboard->Asset Library)
     3. Upload an Image

     Step 1: Click button 'Save', by each POST request sends to the Amazon API
     Verify: The API log present 'success' status message of POST request

     Step 2: Validate Scheme of POST request for Image
     Verify: The JSON scheme of send Image is matched with scheme available by URL below:
     http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/image
     */


    private FilesMetadata expectedFilesMetadata;

    @BeforeMethod(alwaysRun = true)
    public void initFileMetadata() {
        expectedFilesMetadata = FilesMetadataCreationStrategy.createFilesMetadata();
    }

    @Test(groups = {"image_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void validateImagesScheme(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        //open random image
        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);

        String fileName = assetLibraryPage.uploadRandomFiles().get(0);
        EditMultipleFilesPage editMultipleFilesPage = new EditMultipleFilesPage(webDriver, aid);
        editMultipleFilesPage.save();
        softAssert.assertTrue(editMultipleFilesPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);

        String url = editMultipleFilesPage.getLogURL(brand);
        softAssert.assertTrue(editMultipleFilesPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateImageBySchema(localApiJson.getRequestData().toString()), "The validation has failed", "The validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

}
