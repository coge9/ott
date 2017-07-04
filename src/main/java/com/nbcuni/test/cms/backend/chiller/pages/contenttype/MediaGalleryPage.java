package com.nbcuni.test.cms.backend.chiller.pages.contenttype;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.GeneralInfoBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.MediaGallery;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 06-Apr-16.
 */
public class MediaGalleryPage extends ContentTypePage {

    @FindBy(id = "edit-group_general_info")
    private GeneralInfoBlock generalInfoBlock;

    public MediaGalleryPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    public MediaGalleryPage enterContentTypeData(Content content) {
        this.enterGeneralInfoData(content.getGeneralInfo());
        enterAssociationData(content.getAssociations());
        this.enterMediaData(content.getMediaImages());
        enterPromotionalData(content.getPromotional());
        return this;
    }

    private MediaGalleryPage enterGeneralInfoData(GeneralInfo generalInfo) {
        generalInfoBlock.expandTab();
        generalInfoBlock.enterTitle(generalInfo.getTitle());
        generalInfoBlock.enterShortDescription(generalInfo.getShortDescription());
        generalInfoBlock.enterMediumDescription(generalInfo.getMediumDescription());
        generalInfoBlock.enterLongDescription(generalInfo.getLongDescription());
        generalInfoBlock.enterSubhead(generalInfo.getSubhead());
        return this;
    }

    @Override
    public MediaGalleryPage create(Content content) {
        this.enterContentTypeData(content);
        saveAsDraft();
        return this;
    }

    @Override
    public MediaGalleryPage createAndPublish(Content content) {
        this.enterContentTypeData(content);
        return (MediaGalleryPage) publish();
    }

    @Override
    public MediaGallery getPageData() {
        MediaGallery mediaGallery = new MediaGallery();
        mediaGallery.getGeneralInfo()
                .setTitle(this.generalInfoBlock.getTitle())
                .setShortDescription(this.generalInfoBlock.getShortDescription())
                .setMediumDescription(this.generalInfoBlock.getMediumDescription())
                .setLongDescription(this.generalInfoBlock.getLongDescription());
        mediaGallery.setPublishingOptions(onPublishingOptionsTab().getPublishingOptions());
        return mediaGallery;
    }
}
