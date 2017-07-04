package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.extrenallinks;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.EpisodePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;


/**
 * Step 1: Go To CMS as Editor.
 * Verify: Editor Panel is present.
 * <p/>
 * Step 2: Go to Content->Add Episode.
 * Verify: The Create New Episode Page is present.
 * <p/>
 * Step 3: Check Tab 'Episode Links' with fields: 'Link Title', 'Link URL'.
 * Verify: The tab 'Links' is present.
 * There are 2 input fields 'Link Title', 'Link URL'.
 * <p/>
 * Step 4: Set value 'test_title' to Link title, 'http://google.com' to Link URL and save.
 * Verify: The values are saved.
 * There are : 'test_title' within Link title, 'http://google.com' within Link URL.
 *
 * Created by Aleksandra_Lishaeva on 4/18/16.
 *
 */
public class TC14025_ExternalLinks_UserAbleToSetLinksForEpisode extends BaseAuthFlowTest {

    private Content series;
    private Content season;

    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;

    @Autowired
    @Qualifier("externalLinkEpisode")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        series = seriesCreationStrategy.createContentType();
        season = seasonCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setParentProgram(series);
        content = contentTypeCreationStrategy.createContentType();
        ((Episode) content).getEpisodeInfo().setParentSeason((Season) season);
    }

    @Test(groups = {"episode", "externalLinks"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkExternalLinkSet(String brand) {

        // Step 1.
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2-4
        rokuBackEndLayer.createContentType(series);
        rokuBackEndLayer.createContentType(season);
        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_EPISODE).apply();

        softAssert.assertTrue(contentPage.isContentPresent(), "The search result on given Episode name is empty", webDriver);
        ContentTypePage page = contentPage.clickEditLink(EpisodePage.class, content.getTitle());
        List<ExternalLinksInfo> expectedLinks = page.getExternalLinksData();

        softAssert.assertReflectEquals(expectedLinks, content.getExternalLinksInfo()
                , "The external links info is not matched with set"
                , "The external links info are matched with expected");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTc14025() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Exception e) {
            Utilities.logWarningMessage("Could not to delete the content");
        }
    }
}
