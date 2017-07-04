package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.chiller.recursivepublishing;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.factory.FilesMetadataCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.season.SeasonJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.SeasonVerification;
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
 *         Create Season
 *         Add tested images to Season
 *         Publish it
 *         Verify:
 *         Attached Season is published
 *         Image is published
 */
public class TC15458_SeasonRecursivePublishing extends BaseAuthFlowTest {

    List<ImagesJson> actualImagesJson = null;
    private Content season;
    private Content relatedSeries;
    private FilesMetadata expectedFilesMetadata;
    @Autowired
    @Qualifier("withRequiredFieldsSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        relatedSeries = seriesCreationStrategy.createContentType();
        season = contentTypeCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setProgram(relatedSeries.getTitle());
        expectedFilesMetadata = FilesMetadataCreationStrategy.createFilesMetadata();
    }

    @Test(groups = {"recursive_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testSeasonPublishing(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        softAssert.assertTrue(rokuBackEndLayer.checkImagesPublishingByUpload(expectedFilesMetadata), "Image publishing checking has failed");

        //pre-condition
        ContentTypePage relatedSeriesPage = rokuBackEndLayer.createContentType(relatedSeries);
        relatedSeries.getGeneralInfo().setUuid(relatedSeriesPage.openDevelPage().getUuid());

        ContentTypePage seasonPage = rokuBackEndLayer.createContentType(season);
        seasonPage.onMediaTab().onMediaBlock().addImagesFromLibrary(expectedFilesMetadata);
        seasonPage.saveAsDraft();
        //setSlug
        season.setSlugInfo(seasonPage.onSlugTab().getSlug());
        season.setMediaImages(seasonPage.onMediaTab().onMediaBlock().getMediaImages());
        season.setPromotional(seasonPage.onPromotionalTab().getPromotional());
        ((Season) season).getSeasonInfo().setProgram(relatedSeries.getGeneralInfo().getUuid());


        season = rokuBackEndLayer.updateContentByUuid(season);

        //create season with whole data
        ContentTypePage editPage = rokuBackEndLayer.editContentType(season);

        softAssert.assertTrue(editPage.isStatusMessageShown(), "Status message is not presented after saving",
                "Status message is presented after saving", webDriver);

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Expected result
        SeasonJson expectedSeason = new SeasonJson((Season) season);

        //Get Actual Post Request
        try {
            actualImagesJson = requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.FILE_IMAGE);
        } catch (Exception e) {
            Utilities.logSevereMessage("There is no Image POST request");
        }

        softAssert.assertTrue(actualImagesJson.isEmpty(), "Images do not POST recursively with Season");
        SeasonJson actualSeasonJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.SEASON);
        softAssert.assertTrue(new SeasonVerification().verify(expectedSeason, actualSeasonJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContentType() {
        try {
            rokuBackEndLayer.deleteContentType(relatedSeries);
            rokuBackEndLayer.deleteContentType(season);
        } catch (Exception e) {
            Utilities.logSevereMessage("Couldn't clean up the content");
        }
    }
}
