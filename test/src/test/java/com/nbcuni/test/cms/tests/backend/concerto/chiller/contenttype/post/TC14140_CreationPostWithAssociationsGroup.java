package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.post;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.Post;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan on 22.04.2016.
 */
public class TC14140_CreationPostWithAssociationsGroup extends BaseAuthFlowTest {

    /**
     * Step 1 - Go to CMS as admin
     * Verify: user should be logged in
     * Step 2. - Create new Series, Season, Episode
     * <p>
     * Step 3. - Navigate to add new Post
     * Verify: Add new Post page is opened
     * Step 4. - Fill all fields:
     * Title
     * Assocciations:
     * Series
     * Season
     * Episode
     * Verify: Fields is filled
     * Step 5. - Save as draft
     * Verify: Success message is presented
     * Step 6. - Check content list
     * Verify: New Post exists
     * Step 7. - Open created post for edit. Click on Association tab
     * Verify: [Series, Season, Episode] is checked
     */

    private Content series;
    private Content season;
    private Content episode;
    private Content post;

    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;

    @Autowired
    @Qualifier("defaultEpisode")
    private ContentTypeCreationStrategy episodeCreationStrategy;

    @Autowired
    @Qualifier("associationPost")
    private ContentTypeCreationStrategy postCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = seriesCreationStrategy.createContentType();

        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(series);

        episode = episodeCreationStrategy.createContentType();
        ((Episode) episode).getEpisodeInfo()
                .setParentSeries((Series) series)
                .setParentSeason((Season) season);

        post = postCreationStrategy.createContentType();
        ((Post) post).getAssociations().getChannelReferenceAssociations().getChannelReference()
                .setSeries(series.getTitle())
                .setSeason(season.getTitle())
                .setEpisode(episode.getTitle());
    }

    @Test(groups = {"post", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationPost(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);
        rokuBackEndLayer.createContentType(episode);

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(post);

        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        contentTypePage = rokuBackEndLayer.editContentType(post);

        Content postInfo = contentTypePage.getPageData();
        ChannelReference expectedChannelReference = post.getAssociations().getChannelReferenceAssociations().getChannelReference();
        ChannelReference actualChannelReference = postInfo.getAssociations().getChannelReferenceAssociations().getChannelReference();
        softAssert.assertEquals(expectedChannelReference, actualChannelReference, "", webDriver);

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePostTC14140() {
        try {
            rokuBackEndLayer.deleteContentType(post);
            rokuBackEndLayer.deleteContentType(season);
            rokuBackEndLayer.deleteContentType(series);
            rokuBackEndLayer.deleteContentType(episode);
        } catch (Throwable e) {
            Utilities.logSevereMessage("There in an error in tear-down method: " + Utilities.convertStackTraceToString(e));
        }

    }
}
