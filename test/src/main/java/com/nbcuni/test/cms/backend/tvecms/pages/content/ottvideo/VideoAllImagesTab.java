package com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo;

import com.nbcuni.test.cms.backend.tvecms.pages.content.basetabs.BaseImagesTab;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.videosource.VideoOriginalImage;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.videosource.VideoPortrait720x960;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.videosource.VideoThreeTileImage;
import com.nbcuni.test.cms.elements.FileUploader;
import com.nbcuni.test.cms.elements.VideoContentImage;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageUsage;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.video.VideoIosSource;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.Image;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class VideoAllImagesTab extends BaseImagesTab {

    private static final String THREE_TILE_PROGRAM_LOCATOR =
            "//div[contains(@class,'form-item-field-3-tile-program-source-und-0') and contains(@class,'form-type-managed-file')]";

    @FindBy(xpath = THREE_TILE_PROGRAM_LOCATOR)
    private VideoContentImage threeTileProgramArea;

    @FindBy(xpath = "//*[@id='edit-field-mpx-original-image']")
    private VideoContentImage originalImageArea;

    @FindBy(xpath = "//*[@id='edit-field-video-portrait-720-960']")
    private VideoContentImage video_portrait_720_960;

    @FindBy(xpath = ".//*[contains(@class,'form-type-managed-file')]")
    private List<VideoContentImage> allsources;


    public VideoContentImage elementThreeTileProgramArea() {
        return threeTileProgramArea;
    }

    public VideoContentImage elementOriginalImage() {
        return originalImageArea;
    }

    public VideoContentImage elementVideoPortrait720x960() {
        return video_portrait_720_960;
    }

    public boolean isOriginalCustomLinkPresent(SoftAssert softAssert) {
        return originalImageArea.verifyThumbnailsPresent(new VideoOriginalImage(), softAssert);
    }

    public boolean isVideoThreeTileCustomLinkPresent(SoftAssert softAssert) {
        return threeTileProgramArea.verifyThumbnailsPresent(new VideoThreeTileImage(), softAssert);
    }

    public boolean isVideoPortrait720x960CustomLinkPresent(SoftAssert softAssert) {
        return video_portrait_720_960.verifyThumbnailsPresent(new VideoPortrait720x960(), softAssert);
    }

    @Override
    public FileUploader elementFeatureCarouselHero() {
        return oneTileSource;
    }

    public List<VideoContentImage> getAllVideoSources() {
        List<VideoContentImage> toReturn = new ArrayList<>();
        toReturn.add(originalImageArea);
        toReturn.add(video_portrait_720_960);
        toReturn.add(threeTileProgramArea);
        return toReturn;
    }

    public List<Image> getAllImages() {
        List<Image> toReturn = new ArrayList<>();
        toReturn.addAll(originalImageArea.getImages(CustomImageTypes.getVideoOneTileTypes()));
        toReturn.addAll(video_portrait_720_960.getImages(CustomImageTypes.getVideoThreeTileVideoSourceTypes()));
        toReturn.addAll(threeTileProgramArea.getImages(CustomImageTypes.getVideoThreeTileProgramTypes()));
        return toReturn;
    }

    public List<MediaImage> getMediaImages() {
        List<MediaImage> mediaImages = new ArrayList<>();
        MediaImage mediaImage = new MediaImage();
        mediaImage.setUsage(ImageUsage.PRIMARY.getUsage()).setName(VideoIosSource.ORIGINAL_IMAGE.getMachineName());
        mediaImages.add(mediaImage);
        return mediaImages;
    }
}
