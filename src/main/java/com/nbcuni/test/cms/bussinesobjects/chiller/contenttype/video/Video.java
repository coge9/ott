package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ChillerVideoPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;

/**
 * Created by alekca on 13.05.2016.
 */
public class Video extends Content {
    private MpxAsset mpxAsset = new MpxAsset();


    @Override
    public String getTitle() {
        return getGeneralInfo().getTitle();
    }

    public Video setTitle(String title) {
        getGeneralInfo().setTitle(title);
        return this;
    }

    @Override
    public ItemTypes getType() {
        return ItemTypes.VIDEO;
    }

    @Override
    public Class<? extends Page> getPage() {
        return ChillerVideoPage.class;
    }

    public MpxAsset getMpxAsset() {
        return mpxAsset;
    }

    public void setMpxAsset(MpxAsset mpxAsset) {
        this.mpxAsset = mpxAsset;
    }
}
