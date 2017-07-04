package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.season;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.season.SeasonJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.SeasonVerification;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 02-Jun-16.
 */
public class TC14695_PublishSeasonContentTypeWithUpdated extends BaseAuthFlowTest {

    private Content updatedSeason;
    private Content relatedSeries;
    private Content season;

    @Autowired
    @Qualifier("withRequiredFieldsSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @Autowired
    @Qualifier("fullSeason")
    private ContentTypeCreationStrategy updatedSeasonCreationStrategy;

    @Autowired
    @Qualifier("fullSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        relatedSeries = seriesCreationStrategy.createContentType();
        updatedSeason = updatedSeasonCreationStrategy.createContentType();
        ((Season) updatedSeason).getSeasonInfo().setParentProgram(relatedSeries);
        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(relatedSeries);
        ((Season) updatedSeason).getSeasonInfo().setSeasonNumber(((Season) season).getSeasonInfo().getSeasonNumber());

    }

    @Test(groups = {"season_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testSeasonPublishing(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //pre-condition
        ContentTypePage relatedSeriesPage = rokuBackEndLayer.createContentType(relatedSeries);
        relatedSeries.getGeneralInfo().setUuid(relatedSeriesPage.openDevelPage().getUuid());

        ContentTypePage editPage = rokuBackEndLayer.createContentType(season);
        editPage.publish();

        editPage = rokuBackEndLayer.updateContent(season, updatedSeason);
        //setSlug
        updatedSeason.setSlugInfo(editPage.onSlugTab().getSlug());
        updatedSeason.setMediaImages(editPage.onMediaTab().onMediaBlock().getMediaImages());
        updatedSeason.setPromotional(editPage.onPromotionalTab().getPromotional());
        ((Season) updatedSeason).getSeasonInfo().setProgram(relatedSeries.getGeneralInfo().getUuid());

        DevelPage develPage = editPage.openDevelPage();
        String seasonUuid = develPage.getUuid();
        updatedSeason.getGeneralInfo().setRevision(Integer.parseInt(develPage.getVid()));
        updatedSeason.getGeneralInfo().setUuid(seasonUuid);

        //create updatedSeason with whole data
        editPage = rokuBackEndLayer.editContentType(updatedSeason);

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Expected result
        SeasonJson expectedSeason = new SeasonJson((Season) updatedSeason);

        //Get Actual Post Request
        SeasonJson actualSeasonJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.SEASON);
        softAssert.assertTrue(new SeasonVerification().verify(expectedSeason, actualSeasonJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContentType() {
        try {
            rokuBackEndLayer.deleteContentType(relatedSeries);
            rokuBackEndLayer.deleteContentType(updatedSeason);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }

}
