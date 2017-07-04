package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.extrenallinks;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.EventPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 4/18/16.
 */
public class TC14043_ExternalLinks_UserAbleToAddNewLinksForEvent extends BaseAuthFlowTest {

    private Content contentExpected;
    private ContentTypePage contentTypePage;

    /**
     * Pre-Condition:
     *Create a event item within CMS
     *Fill fields 'Link title'="Link title 1" and 'Link URL'="Link Url1"
     *
     * Step 1: Go To CMS as Editor
     * Verify: Editor Panel is present
     * <p/>
     * Step 2: Go to Content, find the Event created in pre-condition and opened on edit
     * Verify: The Edit page of Event is present
     * <p/>
     *
     * Step 3: Go to 'Event Link' tab
     * Verify: The Event edit page is opened
     *
     * Step 4: Check Tab 'Event Links' with fields: 'Link Title', 'Link URL'
     * Verify: The tab 'Links' is present
     * There are 2 input fields 'Link Title', 'Link URL'
     * <p/>
     * Step 5: Click add items
     * Verify: New fields are present
     */

    @Autowired
    @Qualifier("externalLinkEvent")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObjectFirst() {
        contentExpected = contentTypeCreationStrategy.createContentType();
    }


    @Test(groups = {"event", "externalLinks"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkExternalLinkSet(final String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2-4
        contentTypePage = rokuBackEndLayer.createContentType(contentExpected);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_EVENT).apply();

        softAssert.assertTrue(contentPage.isContentPresent(), "The search result on given Event name is empty", webDriver);
        ContentTypePage page = contentPage.clickEditLink(EventPage.class, contentExpected.getTitle());
        List<ExternalLinksInfo> actualResult = page.getExternalLinksData();
        softAssert.assertReflectEquals(contentExpected.getExternalLinksInfo(), actualResult
                , "The external links info is not matched with set"
                , "The external links info are matched with expected");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTC14043() {
        rokuBackEndLayer.deleteContentType(contentExpected);
    }
}
