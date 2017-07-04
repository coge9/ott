package com.nbcuni.test.cms.utils.thumbnails;

import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.pageobjectutils.tvecms.TemplateStyle;
import com.nbcuni.test.cms.utils.Config;

import java.io.File;

/**
 *
 * @author Aliaksei_Dzmitrenka
 *
 */

public class ImageFiles {

    private static final String videoThumbnailsPath = "video" + File.separator;
    private static final String programThumbnailsPath = "program" + File.separator;
    private static final String threeTilePath = "3_tile" + File.separator;
    private static final String oneTilepath = "1_tile" + File.separator;
    private String thumbnailsImagePath;
    private String brand;

    @SuppressWarnings("unused")
    private ImageFiles() {
    }

    public ImageFiles(String brand) {
        this.brand = brand;
        thumbnailsImagePath = Config.getInstance().getPathToThumbnailsImages() + this.brand + File.separator;
    }

    public String getVideoThreeTileThumbnail(CustomImageTypes imageType) {
        return thumbnailsImagePath + videoThumbnailsPath + threeTilePath + imageType.getImageFileNmae();
    }

    public String getVideoOneTileThumbnail(CustomImageTypes imageType, TemplateStyle... style) {
        if (style.length == 0) {
            return thumbnailsImagePath + videoThumbnailsPath + oneTilepath + "dark" + File.separator + imageType.getImageFileNmae();
        }
        return thumbnailsImagePath + videoThumbnailsPath + oneTilepath + style[0].getStyle() + File.separator + imageType.getImageFileNmae();
    }

    public String getProgramOneTileThumbnail(CustomImageTypes imageType, TemplateStyle... style) {
        if (style.length == 0) {
            return thumbnailsImagePath + programThumbnailsPath + oneTilepath + "dark" + File.separator + imageType.getImageFileNmae();
        }
        return thumbnailsImagePath + programThumbnailsPath + oneTilepath + style[0].getStyle() + File.separator + imageType.getImageFileNmae();
    }

    public String getProgramThreeTileThumbnail(CustomImageTypes imageType) {
        return thumbnailsImagePath + programThumbnailsPath + threeTilePath + imageType.getImageFileNmae();
    }
}
