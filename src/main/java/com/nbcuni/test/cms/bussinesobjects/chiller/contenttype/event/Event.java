package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.EventPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.info.EventInfo;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

/**
 * Created by Ivan_Karnilau on 05-Apr-16.
 */
public class Event extends Content {

    private EventInfo eventInfo = new EventInfo();
    private int imageCount = 0;

    @Override
    public String getTitle() {
        return getGeneralInfo().getTitle();
    }

    @Override
    public Class<? extends Page> getPage() {
        return EventPage.class;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    @Override
    public ItemTypes getType() {
        return ItemTypes.EVENT;
    }

    public EventInfo getEventInfo() {
        return eventInfo;
    }

}
