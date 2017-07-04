package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.MediaFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReferenceAssociations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.PromotionalCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.TagsCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.Video;
import org.springframework.stereotype.Component;

/**
 * Created by alekca on 15.05.2016.
 */
@Component("customFieldsVideo")
public class VideoAllCustomFields implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        Video video = new Video();
        video.setPromotional(PromotionalCreator.getRandomPromotional());
        video.getAssociations().setChannelReferenceAssociations(new ChannelReferenceAssociations().setChannelReference(new ChannelReference()));
        video.getAssociations().setTags(TagsCreator.getRandomTags(1));
        video.setMediaImages(MediaFactory.createMediaWithImageCount(2));
        return video;
    }
}
