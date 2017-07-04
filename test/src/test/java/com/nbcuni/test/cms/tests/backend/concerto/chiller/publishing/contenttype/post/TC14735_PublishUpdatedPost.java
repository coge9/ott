package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.post;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.PostPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.taxonomy.TaxonomyPage;
import com.nbcuni.test.cms.backend.tvecms.pages.taxonomy.TaxonomyTermListPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReferenceAssociations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Tag;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.Post;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.Blurb;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Category;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.post.PostJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Pre-Conditions:
 * <p>
 * 1. Login in CMS as admin
 * <p>
 * 2. Create Series with all required fields
 * <p>
 * 3. Create Season with all required fields and aasining created series (Assotiation tab)
 * <p>
 * 4. Create Episode with all required fields and aasining created series, created season (Assotiation tab)
 * <p>
 * 5. Create Post with all required fields
 * <p>
 * 6. Open created post for edit
 *
 * Steps:
 * <p>
 * 1. Click on Publish button
 * Verify: Link to the publishing log is present.
 * The API log present 'success' status message of POST request
 * <p>
 * 2. Update post: Fill all fields and click on Save button
 * Verify: Status message is present with text '[post] has been saved.'
 * <p>
 * 3. Click on publish button
 * Verify: Link to the publishing log is present.
 * The API log present 'success' status message of POST request
 * <p>
 * 4. Verify publishing log
 * Verify: All fields are present and values are correct according
 * http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/posts
 */
public class TC14735_PublishUpdatedPost extends BaseAuthFlowTest {

    private Content series;

    private Content season;

    private Content episode;

    private Content post;
    private Post newPost;

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
    @Qualifier("defaultPost")
    private ContentTypeCreationStrategy contentCreationStrategy;

    @Autowired
    @Qualifier("requiredPost")
    private ContentTypeCreationStrategy postCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = seriesCreationStrategy.createContentType();

        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(series);

        episode = episodeCreationStrategy.createContentType();
        ((Episode) episode).getEpisodeInfo().setParentSeries((Series) series);
        ((Episode) episode).getEpisodeInfo().setParentSeason((Season) season);

        newPost = (Post) contentCreationStrategy.createContentType();
        ChannelReference ref = new ChannelReference()
                .setSeries(series.getTitle())
                .setSeason(season.getTitle())
                .setEpisode(episode.getTitle());
        ((Post) newPost).getAssociations()
                .setChannelReferenceAssociations(new ChannelReferenceAssociations().setChannelReference(ref));

        post = postCreationStrategy.createContentType();

    }

    @Test(groups = {"post_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void createPost(final String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);
        rokuBackEndLayer.createContentType(episode);

        PostPage editPage = (PostPage) rokuBackEndLayer.createContentType(post);
        editPage.publish();
        softAssert.assertTrue(editPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);

        editPage = (PostPage) rokuBackEndLayer.updateContent(post, newPost);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);

        newPost.setMediaImages(editPage.onMediaTab().onMediaBlock().getMediaImages());
        newPost.setSlugInfo(editPage.onSlugTab().getSlug());

        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing",
                "The status message is shown after publishing", webDriver);

        rokuBackEndLayer.updateContentByUuid(newPost);
        newPost.getAssociations().getChannelReferenceAssociations().getChannelReference().setSeries(series.getGeneralInfo().getUuid());
        newPost.getAssociations().getChannelReferenceAssociations().getChannelReference().setSeason(season.getGeneralInfo().getUuid());
        newPost.getAssociations().getChannelReferenceAssociations().getChannelReference().setEpisode(episode.getGeneralInfo().getUuid());

        getTagsUuid(newPost.getAssociations().getTags());
        getCategoriesUuid(newPost.getAssociations().getCategories());

        PostJson actualPostJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.POST);
        PostJson expectedPostJson = new PostJson().getObject(newPost);
        removeHtmlTags(actualPostJson);

        softAssert.assertTrue(actualPostJson.verifyObject(expectedPostJson), "The actual data is not matched",
                "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    private void getTagsUuid(List<Tag> tags) {
        for (Tag tag : tags) {
            tag.setTagUuid(rokuBackEndLayer.getTaxonomyTermUuid(tag));
        }
    }

    private void getCategoriesUuid(List<String> categories) {
        for (String category : categories) {
            TaxonomyPage taxonomyPage = mainRokuAdminPage.openPage(TaxonomyPage.class, brand);
            TaxonomyTermListPage termListPage = taxonomyPage.clickListTermsLinkForTerm("Post Categories");
            termListPage.clickEditLinkForTerm(category);
            webDriver.get(webDriver.getCurrentUrl().replace("edit", "devel"));
            DevelPage develPage = new DevelPage(webDriver, aid);
            Category.get(category).setUuid(develPage.getUuid());
        }
    }

    private void removeHtmlTags(PostJson actualPostJson) {
        // Remove html tags from Medium Description, Long Description and Blurb
        // Text values
        String tagRegExp = "\\<.*?>";
        actualPostJson.setMediumDescription(actualPostJson.getMediumDescription().replaceAll(tagRegExp, ""));
        actualPostJson.setLongDescription(actualPostJson.getLongDescription().replaceAll(tagRegExp, ""));
        for (Blurb blurb : actualPostJson.getBlurb()) {
            blurb.setText(blurb.getText().replaceAll(tagRegExp, ""));
        }
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTypes() {
        try {
            rokuBackEndLayer.deleteContentType(newPost);
            rokuBackEndLayer.deleteContentType(episode);
            rokuBackEndLayer.deleteContentType(season);
            rokuBackEndLayer.deleteContentType(series);
        } catch (Exception e) {
            Utilities.logSevereMessage("Couldn't clean up the content");
        }
    }
}
