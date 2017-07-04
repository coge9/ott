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
 * Created by Ivan_Karnilau on 24-May-16.
 */

/**
 * Steps:
 * 1.Go To CMS as Editor
 * Verify: The Editor panel is present
 * <p>
 * 2. Go to Content -> Add Season Content type
 * Verify: The Create Season Page is present
 * <p>
 * 3.Fill all the fields per all sections and save
 * Verify:The new Season content type is created<br/>
 * All fields are filled<br/>
 * <p>
 * 4.Go To the edit page of created Season
 * Verify:The edit Page is present
 * <p>
 * 5.Click button 'Publish' and send POST request to the Amazon API
 * Verify: The API log present 'success' status message of POST request
 * <p>
 * 6.Analize POST request for Season
 * Verify:The JSON scheme of Season like below with all filled fields:
 * http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/season
 */
public class TC14694_PublishCreatedSeasonContentType extends BaseAuthFlowTest {

    private Content season;
    private Content relatedSeries;

    @Autowired
    @Qualifier("withRequiredFieldsSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @Autowired
    @Qualifier("fullSeason")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        relatedSeries = seriesCreationStrategy.createContentType();
        season = contentTypeCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setProgram(relatedSeries.getTitle());
    }

    @Test(groups = {"season_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testSeasonPublishing(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //pre-condition
        ContentTypePage relatedSeriesPage = rokuBackEndLayer.createContentType(relatedSeries);
        relatedSeries.getGeneralInfo().setUuid(relatedSeriesPage.openDevelPage().getUuid());

        ContentTypePage seasonPage = rokuBackEndLayer.createContentType(season);
        //setSlug
        season.setSlugInfo(seasonPage.onSlugTab().getSlug());
        season.setMediaImages(seasonPage.onMediaTab().onMediaBlock().getMediaImages());
        season.setPromotional(seasonPage.onPromotionalTab().getPromotional());
        ((Season) season).getSeasonInfo().setProgram(relatedSeries.getGeneralInfo().getUuid());

        DevelPage develPage = seasonPage.openDevelPage();
        String seasonUuid = develPage.getUuid();
        season.getGeneralInfo().setRevision(Integer.parseInt(develPage.getVid()));
        season.getGeneralInfo().setUuid(seasonUuid);

        //create season with whole data
        ContentTypePage editPage = rokuBackEndLayer.editContentType(season);

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Expected result
        SeasonJson expectedSeason = new SeasonJson((Season) season);

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
            rokuBackEndLayer.deleteContentType(season);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }

}
