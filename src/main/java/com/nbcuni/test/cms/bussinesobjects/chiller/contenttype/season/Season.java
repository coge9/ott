package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.SeasonPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.info.SeasonInfo;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

/**
 * Created by Ivan_Karnilau on 05-Apr-16.
 */
public class Season extends Content {

    private SeasonInfo seasonInfo = new SeasonInfo();

    @Override
    public String getTitle() {
        return getGeneralInfo().getTitle();
    }

    @Override
    public ItemTypes getType() {
        return ItemTypes.SEASON;
    }

    @Override
    public Class<? extends Page> getPage() {
        return SeasonPage.class;
    }

    public SeasonInfo getSeasonInfo() {
        return seasonInfo;
    }
}
