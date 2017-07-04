package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.extrenallinks;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.EventPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
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
 * Step 2: Go to Content->Add Event.
 * Verify: The Create New Event Page is present.
 * <p/>
 * Step 3: Check Tab 'Event Links' with fields: 'Link Title', 'Link URL'.
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
public class TC14029_ExternalLinks_UserAbleToSetLinksForEvent extends BaseAuthFlowTest {

    @Autowired
    @Qualifier("externalLinkEvent")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"event", "externalLinks"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkExternalLinkSet(final String brand) {

        // Step 1.
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2-4
        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_EVENT).apply();

        softAssert.assertTrue(contentPage.isContentPresent(), "The search result on given Event name is empty", webDriver);
        ContentTypePage page = contentPage.clickEditLink(EventPage.class, content.getTitle());
        List<ExternalLinksInfo> expextedResult = content.getExternalLinksInfo();

        softAssert.assertReflectEquals(expextedResult, page.getExternalLinksData(),
                "The external links info is not matched with set"
                , "The external links info are matched with expected");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTc14029() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Exception e) {
            Utilities.logWarningMessage("Couldn't delete the content");
        }
    }
}
