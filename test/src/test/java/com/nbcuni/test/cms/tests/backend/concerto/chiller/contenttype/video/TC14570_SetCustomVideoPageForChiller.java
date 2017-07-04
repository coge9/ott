package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.video;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.Video;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by alekca on 13.05.2016.
 */
public class TC14570_SetCustomVideoPageForChiller extends BaseAuthFlowTest {

    private Content series;
    private Content season;
    private Content episode;

    /**
     * Steps:
     * 1.Go To chiller CMS as editor
     * Verify:The Editor panel is present
     * <p>
     * 2.Go To Content page
     * Verify:There is a list of TVE Video
     * <p>
     * 3.Select a video on edit and check custom sections is present
     * Verify: -Media field is present
     * -Association section is present
     * -Promotional section is present
     * <p>
     * 4.set fields on Promotional section
     * Verify: Promotional fields is set to
     * Promotional title ="test promotional title".
     * promotional description = test promotional description"
     * promotional kicker= test promotional kicker"
     * <p>
     * 5.Upload custom image
     * Verify: The image is upload
     * <p>
     * 6.go to Association section and fill fieds
     * Verify:Next fields are filled:
     * -categories selected from taxonomy
     * -channel references(season,series and epiosede) are selected from ddl
     * -tags selected from taxonomy
     * <p>
     * 7.Set slug fields are present per Page
     * Verify:slug fields are set
     */
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
    @Qualifier("customFieldsVideo")
    private ContentTypeCreationStrategy videoTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = seriesCreationStrategy.createContentType();

        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setProgram(series.getTitle());

        episode = episodeCreationStrategy.createContentType();
        ((Episode) episode).getEpisodeInfo()
                .setParentSeries((Series) series)
                .setParentSeason((Season) season);

        content = videoTypeCreationStrategy.createContentType();

        ((Video) content).getAssociations().getChannelReferenceAssociations().getChannelReference()
                .setSeries(series.getTitle())
                .setSeason(season.getTitle())
                .setEpisode(episode.getTitle());
    }

    @Test(groups = {"chiller_video"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void setChillerCustomVideoFields(String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Precondition
        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);
        rokuBackEndLayer.createContentType(episode);

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_VIDEO).apply();
        ((Video) content).setTitle(contentPage.getFullTitleOfFirstElement());

        //Step 3
        ContentTypePage contentTypePage = rokuBackEndLayer.updateContent(content, content);

        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

    }
}
