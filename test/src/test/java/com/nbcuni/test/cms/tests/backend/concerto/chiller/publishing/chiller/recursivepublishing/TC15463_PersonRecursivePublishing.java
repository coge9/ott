package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.chiller.recursivepublishing;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.PersonPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.factory.FilesMetadataCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.PersonEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.metadata.MetadataJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.MetadataValidator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Aliaksei_Dzmitrenka
 *         <p>
 *         Pre-condition
 *         Get info about image in asset library
 *         Test
 *         Create Person
 *         Add tested images to Person
 *         Publish it
 *         Verify:
 *         Attached Person is published
 *         Image is published
 */

public class TC15463_PersonRecursivePublishing extends BaseAuthFlowTest {

    List<ImagesJson> actualImagesJson = null;
    private FilesMetadata expectedFilesMetadata;
    @Autowired
    @Qualifier("defaultPerson")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
        expectedFilesMetadata = FilesMetadataCreationStrategy.createFilesMetadata();
    }

    @Test(groups = {"recursive_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testPersonPublishing(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        rokuBackEndLayer.openAdminPage();
        softAssert.assertTrue(rokuBackEndLayer.checkImagesPublishingByUpload(expectedFilesMetadata), "Image publishing checking has failed");

        rokuBackEndLayer.createContentType(content);
        PersonPage editPage = new PersonPage(webDriver, aid);
        editPage.onMediaTab().onMediaBlock().addImagesFromLibrary(expectedFilesMetadata);
        editPage.saveAsDraft();

        //Get Expected result
        MetadataJson expectedPerson = editPage.getMetadataForPublishing((PersonEntity) content);

        //Step 1
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        MetadataJson actualPerson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.PERSON);
        try {
            actualImagesJson = requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.FILE_IMAGE);
        } catch (Exception e) {
            Utilities.logSevereMessage("There is no Image POST request");
        }

        softAssert.assertTrue(actualImagesJson.isEmpty(), "Images do not POST recursively with Person");
        softAssert.assertTrue(new MetadataValidator().verify(expectedPerson, actualPerson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

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
