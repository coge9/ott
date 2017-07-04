package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.extrenallinks;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.SeasonPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
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
 * Created by Aleksandra_Lishaeva on 4/18/16.
 */
public class TC14041_ExternalLinks_UserAbleToAddNewLinksForSeason extends BaseAuthFlowTest {
    ;
    private ContentTypePage contentTypePage;
    private Series series;

    /**
     * Pre-Condition:
     *Create a season item within CMS
     *Fill fields 'Link title'="Link title 1" and 'Link URL'="Link Url1"
     *
     * Step 1: Go To CMS as Editor
     * Verify: Editor Panel is present
     * <p/>
     * Step 2: Go to Content, find the Season created in pre-condition and opened on edit
     * Verify: The Edit page of season is present
     * <p/>
     *
     * Step 3: Go to 'Season Link' tab
     * Verify: The Season edit page is opened
     *
     * Step 4: Check Tab 'Season Links' with fields: 'Link Title', 'Link URL'
     * Verify: The tab 'Links' is present
     * There are 2 input fields 'Link Title', 'Link URL'
     * <p/>
     * Step 5: Click add items
     * Verify: New fields are present
     */

    @Autowired
    @Qualifier("externalLinkSeason")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @Autowired
    @Qualifier("externalLinkSeries")
    private ContentTypeCreationStrategy seriesTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObjectFirst() {
        content = contentTypeCreationStrategy.createContentType();
        series = (Series) seriesTypeCreationStrategy.createContentType();
        ((Season) content).getSeasonInfo().setParentProgram(series);
    }


    @Test(groups = {"season", "externalLinks"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkExternalLinkSet(final String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2-4
        contentTypePage = rokuBackEndLayer.createContentType(series);
        contentTypePage = rokuBackEndLayer.createContentType(content);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_SEASON).apply();

        softAssert.assertTrue(contentPage.isContentPresent(), "The search result on given Season name is empty", webDriver);
        ContentTypePage page = contentPage.clickEditLink(SeasonPage.class, content.getTitle());
        List<ExternalLinksInfo> expextedResult = content.getExternalLinksInfo();

        softAssert.assertReflectEquals(expextedResult, page.getExternalLinksData(),
                "The external links info is not matched with set"
                , "The external links info are matched with expected");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTC14041() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Exception e) {
            Utilities.logWarningMessage("Couldn't delete the content");
        }
    }
}
