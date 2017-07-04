package com.nbcuni.test.cms.tests.smoke.chiller.smoke.publishing.episode;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public class TC14693_ValidateEpisodeJson extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * Make sure there is a Episode content type
     * Steps:
     * <p/>
     * 1.Go To CMS as Editor
     * Verify:The Editor panel is present
     * <p/>
     * 2.Go to Content and select Episode from pre-condition
     * Verify: The Edit Episode Page is opened
     * <p/>
     * 3.Click button 'Publish' and send POST request to the Amazon API
     * Verify: The API log present 'success' status message of POST request
     * <p/>
     * 4.Validate Scheme of POST request for Event
     * Veriy: The JSON scheme of send Event is matched with scheme available by URL below:
     * http://http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/json+schema/epiosde
     */

    private Content series;
    private Content season;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;

    @Autowired
    @Qualifier("defaultEpisode")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;


    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        season = seasonCreationStrategy.createContentType();
        series = seriesCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(series);
        content = contentTypeCreationStrategy.createContentType();
        ((Episode) content).getEpisodeInfo().setParentSeason((Season) season);
    }

    @Test(groups = {"episode_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void validateEpiosdeScheme(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Creation related content
        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);

        //create event with data
        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);

        //publishing
        contentTypePage.publish();
        String url = contentTypePage.getLogURL(brand);
        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateEpisodeBySchema(localApiJson.getRequestData().toString()), "The validation has failed", "The validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deletePersonTC14693() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Throwable e) {
            Utilities.logSevereMessage("There in an error in tear-down method: " + Utilities.convertStackTraceToString(e));
        }
    }
}
