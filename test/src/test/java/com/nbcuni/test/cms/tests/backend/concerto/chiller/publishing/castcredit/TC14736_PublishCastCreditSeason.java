package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.castcredit;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.SeasonPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.castcredit.CastCreditJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.castcredit.CastEntity;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceHelper;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.CastCreditVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Pre-Conditions:
 * Create TVE Season
 * Steps:
 * 1.go to CMS
 * Verify: user is in CMS
 * <p>
 * 2.go to Content list
 * Find TVE Season created in prec.
 * Click "Edit" next to it
 * Verify:  Edit Node page is opened
 * 3 Go to "CAST AND CREDITS" block
 * set Person = [person_1]
 * set Role = [role_1]
 * save node
 * Verify: node is saved
 * <p>
 * 4.Publish node
 * Verify: node is published
 * <p>
 * 5.Open full publishing report
 * Verify: full publishing report is published in new tab
 * <p>
 * 6. Check that there are two logs:
 * Verify: OBJECT TYPE = node
 * OBJECT TYPE = cast_credits
 * there is publishing logs for node and cast_credits
 * <p>
 * 7. Check RESPONSE STATUS of cast_credits
 * Verify: RESPONSE STATUS = success
 * Validation     Check that publishing log satisfies the conditions of schema:
 * <p>
 * http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/json+schema/castCredit
 * publishing log satisfies the conditions
 */
public class TC14736_PublishCastCreditSeason extends BaseAuthFlowTest {

    private Content series;
    private Content person;
    private Content role;
    private Season season;
    private RegistryServiceHelper serviceHelper = new RegistryServiceHelper(brand);

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;

    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @Autowired
    @Qualifier("defaultPerson")
    private ContentTypeCreationStrategy personCreationStrategy;

    @Autowired
    @Qualifier("defaultRole")
    private ContentTypeCreationStrategy roleCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        person = personCreationStrategy.createContentType();
        role = roleCreationStrategy.createContentType();
        series = seriesCreationStrategy.createContentType();
        season = (Season) seasonCreationStrategy.createContentType();
        season.getSeasonInfo().setParentProgram(series);
        season.setCastAndCredit(Arrays.asList(new CastEntity().setPerson(person.getTitle()).setRole(role.getTitle())));
    }

    @Test(groups = {"castCredit_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkCastCreditSeasonPublishing(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        CastEntity cast = new CastEntity();
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //precondition
        rokuBackEndLayer.createContentType(series);

        rokuBackEndLayer.createContentType(person);
        cast.setPerson(person.getGeneralInfo().getUuid());

        rokuBackEndLayer.createContentType(role);
        cast.setRole(role.getGeneralInfo().getUuid());

        SeasonPage editPage = (SeasonPage) rokuBackEndLayer.createContentType(season, false);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "Status message is not presented after saving",
                "Status message is presented after saving", webDriver);

        season = (Season) rokuBackEndLayer.updateChannelReferenceByUuid(season, series, season);
        season.setCastAndCredit(Arrays.asList(cast));

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Expected result
        CastCreditJson expectedCastCredit = new CastCreditJson(season);

        //Get Actual Post Request
        CastCreditJson actualCastCreditJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.CAST_CREDIT);
        softAssert.assertTrue(new CastCreditVerificator().verify(expectedCastCredit, actualCastCreditJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTypesTC14736() {
        rokuBackEndLayer.deleteContentType(season);
        rokuBackEndLayer.deleteContentType(series);
    }

}
