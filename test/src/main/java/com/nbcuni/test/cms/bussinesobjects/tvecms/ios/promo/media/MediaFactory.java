package com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.media;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.utils.ImageUtil;

import java.io.File;

/**
 * Created by Ivan_Karnilau on 8/30/2016.
 */
public class MediaFactory {

    private MediaFactory(){
        super();
    }

    public static MediaImage createMediaImage() {
        MediaImage image = new MediaImage();

        File file = ImageUtil.createRandomImages(1920, 1080, 1).get(0);
        image.setUrl(file.getAbsolutePath());
        image.setName(file.getName());
        image.setUsage("Primary");
        return image;
    }
}
