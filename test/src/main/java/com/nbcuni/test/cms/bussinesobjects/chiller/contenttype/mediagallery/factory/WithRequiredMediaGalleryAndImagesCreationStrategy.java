package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.factory;


import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.MediaFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.PromotionalCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.TagsCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.mediagallery.MediaGallery;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Category;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

@Component("requiredMediaGalleryWithImageWithoutUsage")
public class WithRequiredMediaGalleryAndImagesCreationStrategy implements ContentTypeCreationStrategy {
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
                .setTags(TagsCreator.getRandomTags(4))
                .addCategories(Category.BACKSTAGE.getCategory())
                .addCategories(Category.FUN_FACTS.getCategory());
        mediaGallery.setMediaImages(MediaFactory.createMediaWithImageCountWithoutUsages(2));
        mediaGallery.setPromotional(PromotionalCreator.getRandomPromotional());
        return mediaGallery;
    }
}
