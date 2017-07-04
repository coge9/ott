package com.nbcuni.test.cms.backend.chiller.pages.contenttype;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.EventInfoBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.Event;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.info.EventInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 12-Apr-16.
 */
public class EventPage extends ContentTypePage {

    @FindBy(id = "edit-group_programming")
    private EventInfoBlock eventInfoBlock;

    public EventPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    public EventPage enterContentTypeData(Content content) {
        Event event = (Event) content;
        this.enterBasicData(event.getGeneralInfo());
        enterCastCredit(content.getCastAndCredit());
        this.enterProgrammingData(event.getEventInfo());
        enterAssociationData(event.getAssociations());
        enterPromotionalData(event.getPromotional());
        enterExternalLinksData(event.getExternalLinksInfo());
        enterMediaData(event.getMediaImages());
        return this;
    }

    private EventPage enterBasicData(GeneralInfo generalInfo) {
        generalInfoBlock.expandTab();
        generalInfoBlock.enterTitle(generalInfo.getTitle());
        generalInfoBlock.enterSubhead(generalInfo.getSubhead());
        generalInfoBlock.enterShortDescription(generalInfo.getShortDescription());
        generalInfoBlock.enterMediumDescription(generalInfo.getMediumDescription());
        generalInfoBlock.enterLongDescription(generalInfo.getLongDescription());
        return this;
    }

    private EventPage enterProgrammingData(EventInfo eventInfo) {
        if (eventInfo.isObjectNull()) {
            return this;
        }
        onProgramingTab();
        eventInfoBlock.selectGenre(eventInfo.getGenre());
        eventInfoBlock.selectRating(eventInfo.getRating());
        eventInfoBlock.checkUnscripted(eventInfo.getUnscripted());
        eventInfoBlock.selectStatus(eventInfo.getStatus());
        eventInfoBlock.checkSyndicated(eventInfo.getSyndicated());
        eventInfoBlock.selectRelatedSeries(eventInfo.getRelatedSeries());
        eventInfoBlock.checkType(eventInfo.getEventType());
        eventInfoBlock.enterReleaseYear(eventInfo.getReleaseYear());
        eventInfoBlock.checkChanelOriginal(eventInfo.getChanelOriginal());
        eventInfoBlock.enterAirTime(eventInfo.getAirTime());
        return this;
    }

    public EventInfoBlock onProgramingTab() {
        eventInfoBlock.expandTab();
        return eventInfoBlock;
    }

    @Override
    public EventPage create(Content content) {
        return (EventPage) this.enterContentTypeData(content).saveAsDraft();
    }

    @Override
    public EventPage createAndPublish(Content content) {
        return (EventPage) this.enterContentTypeData(content).publish();
    }

    @Override
    public Event getPageData() {
        return null;
    }
}
