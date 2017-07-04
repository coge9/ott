package com.nbcuni.test.cms.backend.chiller.pages.contenttype;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.media.MediaBlock;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.video.MPXInfoTab;
import com.nbcuni.test.cms.backend.interfaces.pages.EditVideoPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.Video;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alekca on 13.05.2016.
 */
public class ChillerVideoPage extends ContentTypePage implements EditVideoPage {

    @FindBy(id = "edit-group_additional_info")
    protected MPXInfoTab mpxInfoTab;
    @FindBy(id = "edit-title")
    private TextField title;

    public ChillerVideoPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    public ContentTypePage enterContentTypeData(Content content) {
        enterMediaData(content.getMediaImages());
        enterAssociationData(content.getAssociations());
        enterPromotionalData(content.getPromotional());
        return this;
    }

    @Override
    public ContentTypePage create(Content content) {
        this.enterContentTypeData(content).saveAsDraft();
        return this;
    }

    @Override
    public ContentTypePage createAndPublish(Content content) {
        return this.enterContentTypeData(content).publish();
    }

    @Override
    public ContentTypePage enterMediaData(int imageNumber) {
        MediaBlock mediaBlock = onMediaTab().onMediaBlock();
        mediaBlock.removeAllElements();
        mediaBlock.addImagesFromLibrary(imageNumber);
        return this;
    }

    public List<MediaImage> getMediaData() {
        return onMediaTab().onMediaBlock().getMediaImages();
    }

    public MPXInfoTab onMpxInfoTab() {
        mpxInfoTab.expandTab();
        return mpxInfoTab;
    }

    @Override
    public ChillerVideoPage enterAssociationData(Associations associations) {
        if (!associations.isObjectNull()) {
            onAssociationsTab().enterAssociations(associations);
        }
        return this;
    }

    @Override
    public Associations getAssociations() {
        return onAssociationsTab().getAssociationsInfo();
    }

    @Override
    public Video getPageData() {
        Video video = new Video();
        video.setTitle(title.getValue());
        video.setMediaImages(getMediaData());
        video.setAssociations(this.getAssociations());
        video.setPromotional(getPromotional());
        video.setMpxAsset(getMpxInfo());
        video.setSlugInfo(getSlugInfo());
        DevelPage develPage = openDevelPage();
        video.getGeneralInfo().setRevision(Integer.parseInt(develPage.getVid()));
        video.getGeneralInfo().setUuid(develPage.getUuid());
        return video;
    }

    @Override
    public String getTitle() {
        return title.getValue();
    }

    @Override
    public List<ImageSource> getImageSources(String brand) {
        List<ImageSource> sources = new LinkedList<ImageSource>();
        List<MediaImage> mediaData = this.getMediaData();
        for (MediaImage mediaImage : mediaData) {
            ImageSource source = new ImageSource();
            source.setImageName(mediaImage.getName());
            source.setImageUrl(mediaImage.getUrl());
            source.setUsage(mediaImage.getUsage());
            source.setOverriden(false);
            sources.add(source);
        }
        return sources;
    }

    @Override
    public MpxAsset getMpxInfo() {
        MpxAsset mpxAsset = onMpxInfoTab().getMetadata();
        mpxAsset.setTitle(title.getValue());
        return mpxAsset;
    }

}
