package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.MediaGallery;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan_Karnilau on 07-Apr-16.
 */
@Component("withoutRequiredMediaGallery")
public class WithoutRequiredMediaGalleryCreationStrategy implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        MediaGallery mediaGallery = new MediaGallery();
        mediaGallery.getGeneralInfo()
                .setShortDescription(String.format(MediaGalleryData.SHORT_DESCRIPTION, postfix))
                .setMediumDescription(String.format(MediaGalleryData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(MediaGalleryData.LONG_DESCRIPTION, postfix));
        return mediaGallery;
    }
}
