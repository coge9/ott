package com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram;

import com.nbcuni.test.cms.backend.tvecms.pages.content.basetabs.BaseImagesTab;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.elements.VideoContentImage;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.ProgramConcertoImageSourceType;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.appletv.program.AppleTVProgramThumbnails;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.ProgramConcertoImageSourceType.*;

/**
 * Created by Ivan_Karnilau on 12/1/2016.
 */
public class ProgramAppleTVImagesTab extends BaseImagesTab {

    @FindBy(xpath = ".//*[@id='edit-field-program-appletv-1920x1080']/div")
    private VideoContentImage appleTV1920x1080Area;

    @FindBy(xpath = ".//*[@id='edit-field-program-appletv-1920x486']/div")
    private VideoContentImage appleTV1920x486Area;

    @FindBy(xpath = ".//*[@id='edit-field-program-appletv-1704x440']/div")
    private VideoContentImage appleTV1704x440Area;

    @FindBy(xpath = ".//*[@id='edit-field-program-appletv-1600x900']/div")
    private VideoContentImage appleTV1600x900Area;

    @FindBy(xpath = ".//*[@id='edit-field-program-appletv-771x292']/div")
    private VideoContentImage appleTV771x292Area;

    @FindBy(xpath = ".//*[@id='edit-field-program-appletv-600x600']/div")
    private VideoContentImage appleTV600x600Area;


    @FindBy(xpath = ".//*[contains(@class,'form-type-managed-file')]")
    private List<VideoContentImage> allsources;

    public boolean isAllAppleTVImagesStylesPresent(SoftAssert softAssert) {
        appleTV1920x1080Area.isImageStylesPresent(softAssert, AppleTVProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1920x1080.getType()));
        appleTV1920x486Area.isImageStylesPresent(softAssert, AppleTVProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1920x486.getType()));
        appleTV1704x440Area.isImageStylesPresent(softAssert, AppleTVProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1704x440.getType()));
        appleTV1600x900Area.isImageStylesPresent(softAssert, AppleTVProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1600x900.getType()));
        appleTV771x292Area.isImageStylesPresent(softAssert, AppleTVProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_771x292.getType()));
        appleTV600x600Area.isImageStylesPresent(softAssert, AppleTVProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_600x600.getType()));
        return softAssert.getTempStatus();
    }

    public boolean isAllAppleTVImagesSourcePresent() {
        return appleTV1920x1080Area.isPresent()
                && appleTV1920x486Area.isPresent()
                && appleTV1704x440Area.isPresent()
                && appleTV1600x900Area.isPresent()
                && appleTV771x292Area.isPresent()
                && appleTV600x600Area.isPresent();
    }

    public Map<ThumbnailsCutInterface, String> getActualAppleTVImages() {
        Map<ThumbnailsCutInterface, String> toReturn = new HashMap<>();
        toReturn.putAll(appleTV1920x1080Area.getThumbnails(AppleTVProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1920x1080.getType())));
        toReturn.putAll(appleTV1920x486Area.getThumbnails(AppleTVProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1920x486.getType())));
        toReturn.putAll(appleTV1704x440Area.getThumbnails(AppleTVProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1704x440.getType())));
        toReturn.putAll(appleTV1600x900Area.getThumbnails(AppleTVProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1600x900.getType())));
        toReturn.putAll(appleTV771x292Area.getThumbnails(AppleTVProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_771x292.getType())));
        toReturn.putAll(appleTV600x600Area.getThumbnails(AppleTVProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_600x600.getType())));
        return toReturn;
    }

    public Map<String, VideoContentImage> getAppleTVSourceImages() {
        Map<String, VideoContentImage> toReturn = new HashMap<>();
        toReturn.put(SOURCE_1920x1080.getType(), appleTV1920x1080Area);
        toReturn.put(SOURCE_1920x486.getType(), appleTV1920x486Area);
        toReturn.put(SOURCE_1704x440.getType(), appleTV1704x440Area);
        toReturn.put(SOURCE_1600x900_APPLETV.getType(), appleTV1600x900Area);
        toReturn.put(SOURCE_771x292.getType(), appleTV771x292Area);
        toReturn.put(SOURCE_600x600.getType(), appleTV600x600Area);
        return toReturn;
    }

    public List<MediaImage> getMediaImages() {
        List<MediaImage> mediaImages = new ArrayList<>();
        for (ProgramConcertoImageSourceType sourceType : ProgramConcertoImageSourceType.values()) {
            MediaImage mediaImage = new MediaImage();
            mediaImage.setUsage(sourceType.getUsage()).setName(sourceType.getMachineName());
            mediaImages.add(mediaImage);
        }
        return mediaImages;
    }

    public List<VideoContentImage> getAllSourcesBlocks() {
        return allsources;
    }
}
