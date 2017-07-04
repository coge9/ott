package com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Usage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alena_Aukhukova on 6/20/2016.
 */
public class MediaFactory {

    private MediaFactory(){
        super();
    }

    /**
     * Create list with empty MediaImage objects
     * @param imageCount - number of images
     * @return List of media images
     */
    public static List<MediaImage> createMediaWithImageCount(int imageCount) {
        List<MediaImage> mediaImages = new ArrayList<>();
        for (int i = 0; i < imageCount; i++) {
            mediaImages.add(new MediaImage().setUsage(Usage.getRandomUsage().getUsage()));
        }
        return mediaImages;
    }

    public static List<MediaImage> createMediaWithImageCountWithoutUsages(int imageCount) {
        List<MediaImage> mediaImages = new ArrayList<>();
        for (int i = 0; i < imageCount; i++) {
            mediaImages.add(new MediaImage());
        }
        return mediaImages;
    }
}
