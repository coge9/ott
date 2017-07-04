package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.episode;


import com.nbcuni.test.cms.backend.chiller.pages.contenttype.EpisodePage;
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

public class TC14532_CheckPublishingWithAllFilledFields extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * Create Episode with all filled fields
     * <p>
     * Steps:
     * 1. Click on Publish button
     * Verify: publishing log
     *
     * Fields below should be published to SQS services:
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

    private Episode episode;
    private Content series;
    private Content season;

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
    public void initBusinessObject() {
        series = seriesCreationStrategy.createContentType();
        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(series);
        episode = (Episode) episodeCreationStrategy.createContentType();
        episode.getEpisodeInfo()
                .setParentSeries((Series) series)
                .setParentSeason((Season) season);
    }

    @Test(groups = {"episode_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testEpisodePublishingAttributes(String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //precondition
        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);

        EpisodePage editPage = (EpisodePage) rokuBackEndLayer.createContentType(episode);
        episode.setSlugInfo(editPage.getSlugInfo());

        //publishing
        editPage.publish();

        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        episode.getEpisodeInfo().getParentSeason().getGeneralInfo().setUuid(season.getGeneralInfo().getUuid());
        episode.getEpisodeInfo().getParentSeries().getGeneralInfo().setUuid(series.getGeneralInfo().getUuid());
        rokuBackEndLayer.updateContentByUuid(series);
        //set Episode uuid
        EpisodeJson expectedEpisodeJson = new EpisodeJson(episode);

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
            rokuBackEndLayer.deleteContentType(episode);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}

