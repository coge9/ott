package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.chiller.recursivepublishing;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionAbstractPage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.factory.FilesMetadataCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.MediaGallery;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.mediagallery.MediaGalleryJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.CollectionVerificator;
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
 *         Create MG
 *         Test
 *         Create Collection
 *         Add tested MG to Collection
 *         Publish it
 *         Verify:
 *         Collection is published
 *         Attached MG is published
 *         Image is published
 */

public class TC14874_RecursivePublishingCollection extends BaseAuthFlowTest {

    List<ImagesJson> actualImagesJson = null;
    private Collection collection;
    private Content mediaGallery;
    private FilesMetadata expectedFilesMetadata;
    @Autowired
    @Qualifier("curatedCollectionWithRequiredFields")
    private CollectionCreationStrategy collectionCreationStrategy;

    @Autowired
    @Qualifier("defaultMediaGallery")
    private ContentTypeCreationStrategy mgCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        mediaGallery = mgCreationStrategy.createContentType();
        collection = collectionCreationStrategy.createCollection();
        collection.getCollectionInfo().setItems(Arrays.asList(mediaGallery.getTitle()));
        expectedFilesMetadata = FilesMetadataCreationStrategy.createFilesMetadata();

    }

    @Test(groups = {"recursive_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void testImagesPOSTAsSoonDeployed(final String brand) {
        //Pre-condition
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        softAssert.assertTrue(rokuBackEndLayer.checkImagesPublishingByUpload(expectedFilesMetadata), "Image publishing checking has failed");

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(mediaGallery);
        contentTypePage.onMediaTab().onMediaBlock().addImagesFromLibrary(expectedFilesMetadata);
        contentTypePage.saveAsDraft();
        mediaGallery.setMediaImages(contentTypePage.onMediaTab().onMediaBlock().getMediaImages(expectedFilesMetadata));
        mediaGallery.setSlugInfo(contentTypePage.onSlugTab().getSlug());
        mediaGallery = rokuBackEndLayer.updateContentByUuid(mediaGallery);

        CollectionAbstractPage editPage = rokuBackEndLayer.createCollection(collection);
        collection.getCollectionInfo().setContentItems(Arrays.asList(mediaGallery));
        editPage.getActionBlock().publish();

        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        String url = editPage.getLogURL(brand);

        //Get Expected result
        try {
            actualImagesJson = requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.FILE_IMAGE);
        } catch (Exception e) {
            Utilities.logSevereMessage("There is no Image POST request");
        }

        softAssert.assertTrue(actualImagesJson.isEmpty(), "Images do not POST recursively with Curated Collection");

        CollectionJson expectedCollectionJson = new CollectionJson(collection);
        MediaGalleryJson expectedMediaGallery = new MediaGalleryJson((MediaGallery) mediaGallery);

        //Get Actual Post Request
        MediaGalleryJson actualMGJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.MEDIA_GALLERY);
        CollectionJson actualCollectionJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.COLLECTIONS);

        softAssert.assertTrue(new MediaGalleryVerificator().verify(expectedMediaGallery, actualMGJson), "The actual data is not matched", "The JSON data is matched", webDriver);
        softAssert.assertTrue(new CollectionVerificator().verify(expectedCollectionJson, actualCollectionJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContentType() {
        try {
            rokuBackEndLayer.deleteContentType(mediaGallery);
            rokuBackEndLayer.deleteCollection(collection);
        } catch (Exception e) {
            Utilities.logSevereMessage("Couldn't clean up the content");
        }
    }

}
