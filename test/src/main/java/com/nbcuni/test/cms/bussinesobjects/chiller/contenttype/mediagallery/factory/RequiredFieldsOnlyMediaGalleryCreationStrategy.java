package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.MediaGallery;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Lyoha on 07/14/2016.
 * requiredMediaGallery
 */

@Component("requiredMediaGallery")
public class RequiredFieldsOnlyMediaGalleryCreationStrategy implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        MediaGallery mediaGallery = new MediaGallery();
        mediaGallery.getGeneralInfo()
                .setTitle(String.format(MediaGalleryData.TITLE, postfix))
                .setSubhead(String.format(MediaGalleryData.SUBHEAD, postfix));
        return mediaGallery;
    }
}
