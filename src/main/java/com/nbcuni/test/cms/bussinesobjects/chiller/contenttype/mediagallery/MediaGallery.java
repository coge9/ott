package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.MediaGalleryPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.otherinfo.PublishingOptions;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

/**
 * Created by Ivan_Karnilau on 05-Apr-16.
 */
public class MediaGallery extends Content {

    private String coverMediaUUId = null;

    private PublishingOptions publishingOptions = new PublishingOptions();

    @Override
    public String getTitle() {
        return getGeneralInfo().getTitle();
    }

    @Override
    public ItemTypes getType() {
        return ItemTypes.MEDIA_GALLERY;
    }

    @Override
    public Class<? extends Page> getPage() {
        return MediaGalleryPage.class;
    }

    public PublishingOptions getPublishingOptions() {
        return publishingOptions;
    }

    public void setPublishingOptions(PublishingOptions publishingOptions) {
        this.publishingOptions = publishingOptions;
    }

    public String getCoverMediaUUId() {
        return coverMediaUUId;
    }

    public void setCoverMediaUUId(String coverMediaUUId) {
        this.coverMediaUUId = coverMediaUUId;
    }

}
