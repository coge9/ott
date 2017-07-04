package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.MediaFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReferenceAssociations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.PromotionalCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.TagsCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.MediaGallery;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Category;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan on 06.04.2016.
 */
@Component("fullMediaGallery")
public class FullMediaGalleryCreationStrategy implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        MediaGallery mediaGallery = new MediaGallery();
        mediaGallery.getGeneralInfo()
                .setTitle(String.format(MediaGalleryData.TITLE, postfix))
                .setShortDescription(String.format(MediaGalleryData.SHORT_DESCRIPTION, postfix))
                .setMediumDescription(String.format(MediaGalleryData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(MediaGalleryData.LONG_DESCRIPTION, postfix))
                .setSubhead(String.format(MediaGalleryData.SUBHEAD, postfix));
        mediaGallery.getAssociations()
                .setTags(TagsCreator.getRandomTags(1))
                .addCategories(Category.randomValue().getCategory())
                .setChannelReferenceAssociations(new ChannelReferenceAssociations().setChannelReference(new ChannelReference()));
        mediaGallery.setMediaImages(MediaFactory.createMediaWithImageCount(2));
        mediaGallery.setPromotional(PromotionalCreator.getRandomPromotional());
        return mediaGallery;
    }
}
