package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.factory;

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
     * @param imageCount - number of items
     * @return list of MediaImage objects.
     */
    public static List<MediaImage> createMediaWithImageCount(int imageCount) {
        List<MediaImage> mediaImages = new ArrayList<>();
        for (int i = 0; i < imageCount; i++) {
            mediaImages.add(new MediaImage().setUsage(Usage.getRandomUsage().getUsage()));
        }
        return mediaImages;
    }
}
