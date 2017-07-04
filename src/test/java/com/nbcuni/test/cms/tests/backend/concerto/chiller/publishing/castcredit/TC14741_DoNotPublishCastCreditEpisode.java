package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.castcredit;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.EpisodePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Pre-Conditions:
 * Create TVE Episode
 * Steps:
 * <p>
 * 1. Go to CMS
 * Verify: User is in CMS
 * <p>
 * 2.Go to Content list
 * Find TVE Episode created in prec.
 * Click "Edit" next to it
 * Verify: Edit Node page is opened
 * <p>
 * 3. Go to "CAST AND CREDITS" block
 * set Person = ""
 * set Role = ""
 * save node
 * Verify: Node is saved
 * <p>
 * 4.Publish node
 * Verify: Node is published
 * <p>
 * 5.Open full publishing report
 * Verify: Full publishing report is published in new tab
 * Validation     check that there is no logs for OBJECT TYPE = cast_credits
 * there is no publishing logs for cast_credits
 */
public class TC14741_DoNotPublishCastCreditEpisode extends BaseAuthFlowTest {

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
    @Qualifier("defaultEpisode")
    private ContentTypeCreationStrategy episodeCreationStrategy;


    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = seriesCreationStrategy.createContentType();
        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(series);
        episode = (Episode) episodeCreationStrategy.createContentType();
        episode.getEpisodeInfo().setParentSeason((Season) season);
    }

    @Test(groups = {"castCredit_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkCastCreditEpisodeNotPublished(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //precondition
        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);

        EpisodePage editPage = (EpisodePage) rokuBackEndLayer.createContentType(episode);

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);

        //Get Actual Post Request
        List<LocalApiJson> localApiJsons = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.CAST_CREDIT);

        for (LocalApiJson localApiJson : localApiJsons) {
            softAssert.assertFalse(localApiJson.getRequestOptions().getAttributes().getEntityType().equals(ItemTypes.CAST_CREDIT.getEntityType()),
                    "The cast and credit POST go to API for episode without filled fields",
                    "The empty set cast and credit do not POST to API for episode");
            softAssert.assertFalse(localApiJson.getRequestData().getAsJsonObject().get("itemType").equals(ItemTypes.CAST_CREDIT.getItemType()),
                    "The current item type is cast and credit",
                    "The current item type is not cast and credit");
        }
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

}
