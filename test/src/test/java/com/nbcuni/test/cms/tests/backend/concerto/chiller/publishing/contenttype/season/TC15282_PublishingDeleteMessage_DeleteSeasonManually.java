package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.season;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.ContentTypeDeleteJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 07-Jul-16.
 */

/**
 * TC15282
 *
 * Pre-Conditions:
 * 1. Login in CMS D7 as admin
 * 2. Create Season with all required fields
 *
 * Steps
 * 1.
 * Go to content page
 * Content page is opened
 * 2.
 * Delete Season manually on content list screen
 * Series is deleted.
 * Publish delete message:
 * Request data:
 * uuid: e29a1f78-cdfc-4022-9a77-ab721dd370da
 * itemType: "season"
 * There are attributes:
 * action = 'delete'
 * entityType = 'seasons'
 */
public class TC15282_PublishingDeleteMessage_DeleteSeasonManually extends BaseAuthFlowTest {

    private Content season;
    private Series series;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;

    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = (Series) seriesCreationStrategy.createContentType();
        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(series);
    }

    @Test(groups = {"season_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testSeasonDeletePublishing(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season).publish();
        rokuBackEndLayer.deleteContentType(season);

        String url = rokuBackEndLayer.getLogURL(brand);

        ContentTypeDeleteJson expectedDeleteJson = new ContentTypeDeleteJson(season);

        ContentTypeDeleteJson actualDeleteJson = requestHelper.getDeleteResponses(url, ConcertoApiPublishingTypes.SEASON).get(0);

        LocalApiJson localApiJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.SEASON).get(0);
        String action = localApiJson.getAttributes().getAction().getStringValue();

        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");

        String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(season.getType().getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");

        softAssert.assertEquals(expectedDeleteJson, actualDeleteJson, "The actual data is not matched", "The JSON data is matched");

        softAssert.assertAll();

    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTC15282() {
        try {
            rokuBackEndLayer.deleteContentType(series);
        } catch (Exception e) {
            Utilities.logSevereMessage("Couldn't clean up the content");
        }
    }
}
