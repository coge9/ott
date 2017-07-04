package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.factory;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.post.BlurbMedia;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReferenceAssociations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Promotional;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.Post;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.Blurb;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.PostInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.WysiwigDescription;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan on 06.04.2016.
 */
@Component("associationPost")
public class PostWithAssociationCreationStrategy implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        Post post = new Post();
        GeneralInfo generalInfo = new GeneralInfo();
        WysiwigDescription longDescription = new WysiwigDescription();
        WysiwigDescription mediumDescription = new WysiwigDescription();
        longDescription.setBlurb(
                new Blurb().setText(String.format(PostData.LONG_DESCRIPTION, postfix)).setBlurbMedia(BlurbMedia.IMAGE)
        );
        mediumDescription.setBlurb(
                new Blurb().setText(String.format(PostData.MEDIUM_DESCRIPTION, postfix)).setBlurbMedia(BlurbMedia.IMAGE)
        );
        generalInfo.setTitle(String.format(PostData.TITLE, postfix))
                .setByline(String.format(PostData.BYLINE, postfix))
                .setDateLine(PostData.DATE_LINE)
                .setShortDescription(String.format(PostData.SHORT_DESCRIPTION, postfix));
        post.setGeneralInfo(generalInfo);
        post.setPostInfo(new PostInfo().
                setLongDescription(longDescription).
                setMediumDescription(mediumDescription));
        post.setPromotional(new Promotional());
        post.setAssociations(new Associations().setChannelReferenceAssociations(new ChannelReferenceAssociations().setChannelReference(new ChannelReference())));
        return post;
    }
}
