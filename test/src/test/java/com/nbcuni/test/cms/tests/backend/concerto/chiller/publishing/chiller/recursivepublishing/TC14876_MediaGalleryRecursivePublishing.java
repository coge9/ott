package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.chiller.recursivepublishing;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.factory.FilesMetadataCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.MediaGallery;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.mediagallery.MediaGalleryJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.MediaGalleryVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Aliaksei_Dzmitrenka
 *         <p>
 *         Pre-condition
 *         Get info about image in asset library
 *         Test
 *         Create MG
 *         Add tested images to MG
 *         Publish it
 *         Verify:
 *         Attached MG is published
 *         Image is published
 */

public class TC14876_MediaGalleryRecursivePublishing extends BaseAuthFlowTest {

    List<ImagesJson> actualImagesJson = null;
    private Content content;
    private FilesMetadata expectedFilesMetadata1, expectedFilesMetadata2;
    private List<FilesMetadata> allMetaData;
    @Autowired
    @Qualifier("defaultMediaGallery")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
        expectedFilesMetadata1 = FilesMetadataCreationStrategy.createFilesMetadata();
        expectedFilesMetadata2 = FilesMetadataCreationStrategy.createFilesMetadata();
        allMetaData = Arrays.asList(expectedFilesMetadata1, expectedFilesMetadata2);

    }

    @Test(groups = {"recursive_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationMediaGallery(String brand) {
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        softAssert.assertTrue(rokuBackEndLayer.checkImagesPublishingByUpload(expectedFilesMetadata1, expectedFilesMetadata2), "Image publishing checking has failed");

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);
        contentTypePage.onMediaTab().onMediaBlock().addImagesFromLibrary(allMetaData);
        contentTypePage.saveAsDraft();
        content.setMediaImages(contentTypePage.onMediaTab().onMediaBlock().getMediaImages(expectedFilesMetadata1, expectedFilesMetadata2));
        content.setSlugInfo(contentTypePage.onSlugTab().getSlug());
        contentTypePage.publish();
        String url = contentTypePage.getLogURL(brand);
        content = rokuBackEndLayer.updateContentByUuid(content);
        MediaGalleryJson expectedMediaGallery = new MediaGalleryJson((MediaGallery) content);

        try {
            actualImagesJson = requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.FILE_IMAGE);
        } catch (Exception e) {
            Utilities.logSevereMessage("There is no Image POST request");
        }

        softAssert.assertTrue(actualImagesJson.isEmpty(), "Images do not POST recursively with Media Gallery");
        MediaGalleryJson actualMGJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.MEDIA_GALLERY);
        softAssert.assertTrue(new MediaGalleryVerificator().verify(expectedMediaGallery, actualMGJson), "The actual data is not matched", "The JSON data is matched", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContentType() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Exception e) {
            Utilities.logSevereMessage("Couldn't clean up the content");
        }
    }
}
