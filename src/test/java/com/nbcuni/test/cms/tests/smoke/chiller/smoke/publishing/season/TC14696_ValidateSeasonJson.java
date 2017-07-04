package com.nbcuni.test.cms.tests.smoke.chiller.smoke.publishing.season;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
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
 * Created by Ivan_Karnilau on 24-May-16.
 */

/**
 * TC14696
 *
 * Steps:
 * 1. Go To CMS as Editor
 * The Editor panel is present
 * 2. Go to Content -> Add Season Content type
 * The Create season Page is present
 * 3. Fill all the fields per all sections and save
 * The new Season content type is created<br/>
 * All fields are filled<br/>
 * 4. Go To the edit page of created Season
 * the edit Page is present
 * 5. Click button 'Publish' and send POST request to the Amazon API
 * The API log present 'success' status message of POST request
 * 6. Analize POST request scheme for Season
 * The JSON scheme of Season llok like below with all filled fields:
 * http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com//json+schema/season
 */
public class TC14696_ValidateSeasonJson extends BaseAuthFlowTest {

    private Series series;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy seriesTypeCreationStrategy;


    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = (Series) seriesTypeCreationStrategy.createContentType();
        content = contentTypeCreationStrategy.createContentType();
        ((Season) content).getSeasonInfo().setParentProgram(series);
    }

    @Test(groups = {"season_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void validateSeasonScheme(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();


        //create season with data
        rokuBackEndLayer.createContentType(series);
        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);

        //publishing
        contentTypePage.publish();
        String url = contentTypePage.getLogURL(brand);
        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateSeasonBySchema(localApiJson.getRequestData().toString()), "The validation has failed", "The validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteContent() {
        try {
            rokuBackEndLayer.deleteContentType(series);
            rokuBackEndLayer.deleteContentType(content);
        } catch (Exception e) {
            Utilities.logSevereMessage("Couldn't clean up the content");
        }
    }
}
