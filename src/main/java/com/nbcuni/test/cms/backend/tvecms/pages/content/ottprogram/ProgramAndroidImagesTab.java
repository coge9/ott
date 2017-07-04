package com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram;

import com.nbcuni.test.cms.backend.tvecms.pages.content.basetabs.BaseImagesTab;
import com.nbcuni.test.cms.elements.VideoContentImage;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.Image;
import com.nbcuni.test.cms.utils.thumbnails.android.program.AndroidProgramThumbnails;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramAndroidImagesTab extends BaseImagesTab {

    private static final String ANDROID_PROGRAM_LOGO =
            ".//*[@id='edit-field-program-logo-1-und-0-ajax-wrapper']/div";

    private static final String ANDROID_2560x1440_LOCATOR =
            ".//*[@id='edit-field-android-2560x1440-und-0-ajax-wrapper']/div";

    private static final String ANDROID_680x1176_LOCATOR =
            ".//*[@id='edit-field-android-680x1176-und-0-ajax-wrapper']/div";

    private static final String ANDROID_2088x1576_LOCATOR =
            ".//*[@id='edit-field-android-2088x1576-und-0-ajax-wrapper']/div";

    private static final String ANDROID_1440x2560_LOCATOR =
            ".//*[@id='edit-field-android-1440x2560-und-0-ajax-wrapper']/div";

    private static final String ANDROID_1200x1200_LOCATOR =
            ".//*[@id='edit-field-android-1200x1200-und-0-ajax-wrapper']/div";


    @FindBy(xpath = ".//*[contains(@class,'form-type-managed-file')]")
    private List<VideoContentImage> allsources;

    @FindBy(xpath = ANDROID_PROGRAM_LOGO)
    private VideoContentImage android_program_logo_area;

    @FindBy(xpath = ANDROID_2560x1440_LOCATOR)
    private VideoContentImage android2560x1440_area;

    @FindBy(xpath = ANDROID_680x1176_LOCATOR)
    private VideoContentImage android680x1176_area;

    @FindBy(xpath = ANDROID_2088x1576_LOCATOR)
    private VideoContentImage android2088x1576_area;

    @FindBy(xpath = ANDROID_1440x2560_LOCATOR)
    private VideoContentImage android1440x2560_area;

    @FindBy(xpath = ANDROID_1200x1200_LOCATOR)
    private VideoContentImage android1200x1200_area;

    public Image getProgramLogo() {
        return new Image(AndroidProgramThumbnails.getProgramLogoName(), android_program_logo_area.getImageSourceName(), android_program_logo_area.getSourceWidth(), android_program_logo_area.getSourceHeight());
    }

    public Map<ThumbnailsCutInterface, String> getActualAndroidImages() {
        Map<ThumbnailsCutInterface, String> toReturn = new HashMap<>();
        toReturn.putAll(android2560x1440_area.getThumbnails(AndroidProgramThumbnails.getThumbnailsByMpxSourceSize("2560x1440")));
        toReturn.putAll(android680x1176_area.getThumbnails(AndroidProgramThumbnails.getThumbnailsByMpxSourceSize("680x1176")));
        toReturn.putAll(android2088x1576_area.getThumbnails(AndroidProgramThumbnails.getThumbnailsByMpxSourceSize("2088x1576")));
        toReturn.putAll(android1440x2560_area.getThumbnails(AndroidProgramThumbnails.getThumbnailsByMpxSourceSize("1440x2560")));
        toReturn.putAll(android1200x1200_area.getThumbnails(AndroidProgramThumbnails.getThumbnailsByMpxSourceSize("1200x1200")));
        return toReturn;
    }

    public void isAllAndroidImagesPresent(SoftAssert softAssert) {
        android2560x1440_area.isImageStylesPresent(softAssert, AndroidProgramThumbnails.getThumbnailsByMpxSourceSize("2560x1440"));
        android680x1176_area.isImageStylesPresent(softAssert, AndroidProgramThumbnails.getThumbnailsByMpxSourceSize("680x1176"));
        android2088x1576_area.isImageStylesPresent(softAssert, AndroidProgramThumbnails.getThumbnailsByMpxSourceSize("2088x1576"));
        android1440x2560_area.isImageStylesPresent(softAssert, AndroidProgramThumbnails.getThumbnailsByMpxSourceSize("1440x2560"));
        android1200x1200_area.isImageStylesPresent(softAssert, AndroidProgramThumbnails.getThumbnailsByMpxSourceSize("1200x1200"));
    }

    public List<Image> getAllImages() {
        List<Image> toReturn = new ArrayList<>();
        Map<ThumbnailsCutInterface, String> imageMap = getActualAndroidImages();
        for (Map.Entry thumbnail : imageMap.entrySet()) {
            Image imageForAdding = new Image();
            imageForAdding.setName(thumbnail.getKey().toString());
            imageForAdding.setImageUrl(thumbnail.getValue().toString());
            toReturn.add(imageForAdding);
        }
        return toReturn;
    }

    public List<VideoContentImage> getAllSourcesBlocks() {
        return allsources;
    }

}
