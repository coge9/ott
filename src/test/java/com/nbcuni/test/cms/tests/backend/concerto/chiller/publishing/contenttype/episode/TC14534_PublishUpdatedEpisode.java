package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.episode;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.episode.EpisodeJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.EpisodeVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Created by Ivan_Karnilau on 15-Jun-16.
 */

/**
 * Pre-Conditions:
 * Create Episode with all filled fields
 * <p>
 * Steps:
 * 1. Open Episode from precondition
 * 2. Update some metadata
 * 3. Save
 * 4. Click on Publish button
 * Verify: publishing log
 *
 * Fields below should be published to SQS services, changes in meta data is present::
 *1.UUID
 *2.Slug
 *3.Revision
 *4.General Info
 *  1.Title
 *  2.Subhead
 *  3.Short Description
 *  4.Medium Description
 *  5.Long Description
 *  6.Series/Season
 *  7.Episode type
 *  8.Episode number
 *  9.Secondary Episode number
 *  10.Original Air Date
 *  11.TV Rating
 *  12.Production Number
 *  13.Supplementary Air Date
 *5.Media
 *  1.Images
 *6.Association
 *  1.Categories
 *  2.Tags
 *8.External Links
 *  1.URL
 *  2.Link Title
 *8.Promotional
 *  1.Promotional Kicker
 *  2.Promotional Title
 *  3.Promotional Description
 *  4.Promotional image
 **/
public class TC14534_PublishUpdatedEpisode extends BaseAuthFlowTest {

    ContentTypePage editPage;
    private Episode episode;
    private Episode updatedEpisode;
    private Content series;
    private Content season;
    private Content updatedSeason;
    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;

    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @Autowired
    @Qualifier("withAllEpisode")
    private ContentTypeCreationStrategy episodeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBuisnessObject() {
        series = seriesCreationStrategy.createContentType();

        updatedSeason = seasonCreationStrategy.createContentType();
        ((Season) updatedSeason).getSeasonInfo().setParentProgram(series);

        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(series);
        ((Season) updatedSeason).getSeasonInfo().setSeasonNumber(((Season) season).getSeasonInfo().getSeasonNumber());

        updatedEpisode = (Episode) episodeCreationStrategy.createContentType();
        updatedEpisode.getEpisodeInfo()
                .setParentSeries((Series) series)
                .setParentSeason((Season) updatedSeason);
        episode = (Episode) episodeCreationStrategy.createContentType();
        episode.getEpisodeInfo()
                .setParentSeries((Series) series)
                .setParentSeason((Season) season);
        //update are done due the fact , that Season # and Episode numbers are locked per initial creation on update
        updatedEpisode.getEpisodeInfo().setEpisodeNumber(episode.getEpisodeInfo().getEpisodeNumber());

    }

    @Test(groups = {"episode_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testPublishUpdatedEpisode(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(series);

        rokuBackEndLayer.createContentType(season);

        rokuBackEndLayer.createContentType(updatedSeason);


        editPage = rokuBackEndLayer.createContentType(episode);

        editPage.publish();

        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);


        editPage = rokuBackEndLayer.updateContent(episode, updatedEpisode);
        updatedEpisode.getGeneralInfo().setRevision(Integer.parseInt(editPage.openDevelPage().getVid()));

        editPage.openEditPage();
        updatedEpisode.getSlugInfo().setSlugValue(editPage.getSlugInfo().getSlugValue());

        editPage.publish();
        String url = editPage.getLogURL(brand);
        rokuBackEndLayer.updateContentByUuid(updatedEpisode);

        updatedEpisode.getEpisodeInfo().getParentSeason().getGeneralInfo().setUuid(updatedSeason.getGeneralInfo().getUuid());
        updatedEpisode.getEpisodeInfo().getParentSeries().getGeneralInfo().setUuid(series.getGeneralInfo().getUuid());

        //Get Expected result
        EpisodeJson expectedEpisodeJson = new EpisodeJson(updatedEpisode);

        //Get Actual Post Request
        EpisodeJson actualEpisodeJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.EPISODE);
        softAssert.assertTrue(new EpisodeVerificator().verify(expectedEpisodeJson, actualEpisodeJson),
                "The actual data is not matched", "The JSON data is matched", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContentType() {
        try {
            rokuBackEndLayer.deleteContentType(series);
            rokuBackEndLayer.deleteContentType(season);
            rokuBackEndLayer.deleteContentType(updatedSeason);
            rokuBackEndLayer.deleteContentType(updatedEpisode);
        } catch (Exception e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
