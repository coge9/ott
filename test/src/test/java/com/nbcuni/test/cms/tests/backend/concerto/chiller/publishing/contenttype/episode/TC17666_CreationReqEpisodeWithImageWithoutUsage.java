package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.episode;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * TC17666
 * Pre-condition:Go to chiller CMS and create a Episode with Required fields
 * <p>
 * Steps:
 * 1.Go to CMS as Editor
 * The Editor Navigation Panel is present
 * <p>
 * 2.Go to Content and select the Episode from Pre-condition
 * The edit Page of the content item has opened
 * <p>
 * 3.Expand Media tab and add one Image or Video
 * Media item is added
 * <p>
 * 4.Leave the media items with default 'Empty' value
 * The default empty value is selected
 * <p>
 * 5.Click "Save", "Save and Publish or 'Save and Unpublish' buttons
 * The warning Massage has triggered that impossible to save Node without selected usage NOT empty value
 * <p>
 * 6.Select a usages for all added to Media items and Save
 * The node was saved without Issue
 * <p>
 * 7.Publish Node to API
 * and take a look at the Publish log of Node POST request
 * <p>
 * The Node POST request contains selected Usage values within mediaItems[] array
 */
public class TC17666_CreationReqEpisodeWithImageWithoutUsage extends BaseAuthFlowTest {

    private Episode episode;
    private Content series;
    private Content season;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;

    @Autowired
    @Qualifier("requiredEpisodeWithImageWithoutUsage")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        season = seasonCreationStrategy.createContentType();
        series = seriesCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(series);
        episode = (Episode) contentTypeCreationStrategy.createContentType();
        episode.getEpisodeInfo()
                .setParentSeries((Series) series)
                .setParentSeason((Season) season);
    }

    @Test(groups = {"episode_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationEpisodeWithEmptyUsage(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);

        ContentTypePage contentTypePage = rokuBackEndLayer.createAndPublishContentType(episode);
        softAssert.assertTrue(contentTypePage.getErrorMessage().contains(MessageConstants.MEDIA_USAGE_VALIDATION), "Validation message is not present",
                "Validation message is presented", webDriver);

        contentTypePage.getActionBlock().save();
        softAssert.assertTrue(contentTypePage.getErrorMessage().contains(MessageConstants.MEDIA_USAGE_VALIDATION), "Validation message is not present",
                "Validation message is presented", webDriver);

        contentTypePage.getActionBlock().unPublish();
        softAssert.assertTrue(contentTypePage.getErrorMessage().contains(MessageConstants.MEDIA_USAGE_VALIDATION), "Validation message is not present",
                "Validation message is presented", webDriver);

        contentTypePage.onMediaTab().onMediaBlock().selectRandomUsageForAllImages();
        contentTypePage.publish();
        softAssert.assertFalse(contentTypePage.getErrorMessage().contains(MessageConstants.MEDIA_USAGE_VALIDATION), "Validation message is still present",
                "Validation message is not present anymore ", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteEventTC17666() {
        try {
            rokuBackEndLayer.deleteContentType(series);
            rokuBackEndLayer.deleteContentType(season);
            rokuBackEndLayer.deleteContentType(episode);
        } catch (Exception e) {
            Utilities.logWarningMessage(e.getMessage());
        }
    }
}
