package com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram;

import com.nbcuni.test.cms.backend.tvecms.pages.content.basetabs.BaseImagesTab;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.elements.VideoContentImage;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.ProgramConcertoImageSourceType;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;
import com.nbcuni.test.cms.utils.thumbnails.ios.program.IosProgramThumbnails;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.ProgramConcertoImageSourceType.SOURCE_1600x900;
import static com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.ProgramConcertoImageSourceType.SOURCE_1965x1108;

/**
 * Created by Aliaksei_Klimenka1 on 7/7/2016.
 */
public class ProgramIosImagesTab extends BaseImagesTab {

    @FindBy(xpath = ".//*[@id='edit-field-ios-1600x900']/div")
    private VideoContentImage ios1600x900Area;

    @FindBy(xpath = ".//*[@id='edit-field-ios-1965x1108']/div")
    private VideoContentImage ios1965x1108Area;


    @FindBy(xpath = ".//*[contains(@class,'form-type-managed-file')]")
    private List<VideoContentImage> allsources;

    public VideoContentImage getIos1600x900Area() {
        return ios1600x900Area;
    }

    public VideoContentImage getIos1965x1108Area() {
        return ios1965x1108Area;
    }

    public boolean isAllIosImagesStylesPresent(SoftAssert softAssert) {
        ios1600x900Area.isImageStylesPresent(softAssert, IosProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1600x900.getType()));
        ios1965x1108Area.isImageStylesPresent(softAssert, IosProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1965x1108.getType()));
        return softAssert.getTempStatus();
    }

    public boolean isAllIosImagesSourcePresent() {
        return (ios1600x900Area.isPresent() && ios1965x1108Area.isPresent());
    }

    public Map<ThumbnailsCutInterface, String> getActualIosImages() {
        Map<ThumbnailsCutInterface, String> toReturn = new HashMap<>();
        toReturn.putAll(ios1600x900Area.getThumbnails(IosProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1600x900.getType())));
        toReturn.putAll(ios1965x1108Area.getThumbnails(IosProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1965x1108.getType())));
        return toReturn;
    }

    public Map<ThumbnailsCutInterface, String> getIosProgram1600x900Images() {
        Map<ThumbnailsCutInterface, String> toReturn = new HashMap<>();
        toReturn.putAll(ios1600x900Area.getThumbnails(IosProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1600x900.getType())));
        return toReturn;
    }

    public Map<ThumbnailsCutInterface, String> getIosProgram1965x1108Images() {
        Map<ThumbnailsCutInterface, String> toReturn = new HashMap<>();
        toReturn.putAll(ios1965x1108Area.getThumbnails(IosProgramThumbnails.getTumbnailsByMpxSourceSize(SOURCE_1965x1108.getType())));
        return toReturn;
    }

    public Map<String, VideoContentImage> getIosSourceImages() {
        Map<String, VideoContentImage> toReturn = new HashMap<>();
        toReturn.put(SOURCE_1600x900.getType(), ios1600x900Area);
        toReturn.put(SOURCE_1965x1108.getType(), ios1965x1108Area);
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
