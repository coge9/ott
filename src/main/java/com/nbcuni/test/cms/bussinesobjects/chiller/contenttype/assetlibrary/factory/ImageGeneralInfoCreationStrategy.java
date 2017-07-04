package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.ImageGeneralInfo;
import com.nbcuni.test.cms.utils.SimpleUtils;

/**
 * Created by Ivan_Karnilau on 07-Jun-16.
 */
public class ImageGeneralInfoCreationStrategy {

    private ImageGeneralInfoCreationStrategy(){
        super();
    }

    public static ImageGeneralInfo createFilesMetadata() {
        ImageGeneralInfo imageGeneralInfo = new ImageGeneralInfo();

        imageGeneralInfo
                .setTitle(SimpleUtils.getRandomString(6).toLowerCase())
                .setAltText(SimpleUtils.getRandomString(6))
                .setSource(SimpleUtils.getRandomString(6))
                .setCredit(SimpleUtils.getRandomString(6))
                .setCopyright(SimpleUtils.getRandomString(6))
                .setDescription(SimpleUtils.getRandomString(30))
                .setCaption(SimpleUtils.getRandomString(30))
                .setHighResolution(true);

        return imageGeneralInfo;
    }
}
