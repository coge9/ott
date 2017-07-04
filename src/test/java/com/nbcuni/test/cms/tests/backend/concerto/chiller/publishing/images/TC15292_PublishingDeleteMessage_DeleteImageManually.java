package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.images;

import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.EditMultipleFilesPage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.ContentTypeDeleteJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 12-Jul-16.
 */

/**
 * TC15292
 *
 * Pre-Conditions:
 * 1. Login in CMS D7 as admin
 * 2. Upload new Image
 *
 * Steps
 * 1.
 * Go to content page
 * Content page is opened
 * 2.
 * Delete Image manually on Asset Library
 * Image is deleted.
 * Publish delete message:
 * Request data:
 * uuid: e29a1f78-cdfc-4022-9a77-ab721dd370da
 * itemType: "image"
 * There are attributes:
 * action = 'delete'
 * entityType = 'images'
 */
public class TC15292_PublishingDeleteMessage_DeleteImageManually extends BaseAuthFlowTest {
    private FilesMetadata expectedFilesMetadata = new FilesMetadata();

    @Test(groups = {"image_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void test(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);

        String fileName = assetLibraryPage.uploadRandomFiles().get(0);
        expectedFilesMetadata.getImageGeneralInfo().setTitle(fileName);
        EditMultipleFilesPage editMultipleFilesPage = new EditMultipleFilesPage(webDriver, aid);
        editMultipleFilesPage.save();

        editMultipleFilesPage = rokuBackEndLayer.openForEditImageFromAssetLibrary(expectedFilesMetadata.getImageGeneralInfo().getTitle());

        DevelPage develPage = editMultipleFilesPage.openDevelPage();
        expectedFilesMetadata.getImageGeneralInfo().setUuid(develPage.getUuid());

        mainRokuAdminPage.openPage(AssetLibraryPage.class, brand).deleteFile(expectedFilesMetadata);

        String url = rokuBackEndLayer.getLogURL(brand);

        ContentTypeDeleteJson expectedDeleteJson = new ContentTypeDeleteJson(expectedFilesMetadata);

        ContentTypeDeleteJson actualDeleteJson = requestHelper.getDeleteResponses(url, ConcertoApiPublishingTypes.FILE_IMAGE).get(0);

        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        String action = localApiJson.getAttributes().getAction().getStringValue();

        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");

        String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(expectedFilesMetadata.getType().getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");

        softAssert.assertEquals(expectedDeleteJson, actualDeleteJson, "The actual data is not matched", "The JSON data is matched");

        softAssert.assertAll();
    }
}
