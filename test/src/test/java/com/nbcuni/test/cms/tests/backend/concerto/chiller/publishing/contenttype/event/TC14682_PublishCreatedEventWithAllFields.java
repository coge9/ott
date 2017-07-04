package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.event;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.EventPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.Event;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.event.EventJson;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceEntity;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceHelper;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.EventVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public class TC14682_PublishCreatedEventWithAllFields extends BaseAuthFlowTest {
    private RegistryServiceHelper serviceHelper = new RegistryServiceHelper(brand);
    private RegistryServiceEntity serviceEntity;

    /**
     * Steps:
     * 1.Go To CMS as Editor
     * Verify: The Editor panel is present
     * <p>
     * 2. Go to Content -> Add Event Content type
     * Verify: The Create event Page is present
     * <p>
     * 3.Fill all the fields per all sections and save
     * Verify:The new Event content type is created<br/>
     * All fields are filled<br/>
     * <p>
     * 4.Go To the edit page of created Event
     * Verify:The edit Page is present
     * <p>
     * 5.Click button 'Publish' and send POST request to the Amazon API
     * Verify: The API log present 'success' status message of POST request
     * <p>
     * 6.Analize POST request for Event
     * Verify:The JSON scheme of Event llok like below with all filled fields:
     * http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/event
     */

    private Content series;

    @Autowired
    @Qualifier("fullEvent")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {

        series = seriesCreationStrategy.createContentType();
        content = contentTypeCreationStrategy.createContentType();
        ((Event) content).getEventInfo().setRelatedSeries(series.getTitle());
    }

    @Test(groups = {"event_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testEventPublishing(String brand) {
        Event event = (Event) content;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //pre-condition
        rokuBackEndLayer.createContentType(series, true);
        event.getEventInfo().setRelatedSeriesUuid(series.getGeneralInfo().getUuid());

        //create event with whole data
        EventPage editPage = (EventPage) rokuBackEndLayer.createContentType(content);
        //setMediaImages
        event.setMediaImages(editPage.onMediaTab().onMediaBlock().getMediaImages());

        //setSlug
        event.setSlugInfo(editPage.onSlugTab().getSlug());

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //set Event uuid
        event = (Event) rokuBackEndLayer.updateContentByUuid(event);

        //Get Expected result
        EventJson expectedEvent = new EventJson().getObject(event);

        //Get Actual Post Request
        EventJson actualEventJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.EVENT);
        softAssert.assertTrue(new EventVerificator().verify(expectedEvent, actualEventJson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteEventTC14682() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }

}
