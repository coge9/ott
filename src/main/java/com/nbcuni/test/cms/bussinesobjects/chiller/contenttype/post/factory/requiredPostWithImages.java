package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.factory;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.post.BlurbMedia;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.PromotionalCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.Post;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.Blurb;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.PostInfoCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.WysiwigDescription;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Lyoha on 06.04.2016.
 * requiredPostImageBlurb
 */
@Component("postWithImagesInDescription")
public class requiredPostWithImages implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        WysiwigDescription longDescription = new WysiwigDescription();
        WysiwigDescription mediumDescription = new WysiwigDescription();
        longDescription.setBlurb(
                new Blurb().setText(String.format(PostData.LONG_DESCRIPTION, postfix)).setBlurbMedia(BlurbMedia.IMAGE)
        );
        mediumDescription.setBlurb(
                new Blurb().setText(String.format(PostData.MEDIUM_DESCRIPTION, postfix)).setBlurbMedia(BlurbMedia.IMAGE)
        );

        Post post = new Post();
        GeneralInfo generalInfo = new GeneralInfo();
        generalInfo.setTitle(String.format(PostData.TITLE, postfix))
                .setByline(String.format(PostData.BYLINE, postfix))
                .setDateLine(PostData.DATE_LINE)
                .setShortDescription(String.format(PostData.SHORT_DESCRIPTION, postfix));
        post.setGeneralInfo(generalInfo);
        post.setPromotional(PromotionalCreator.getRandomPromotional());
        post.setPostInfo(
                PostInfoCreator.getImageBlurb().
                        setLongDescription(longDescription).
                        setMediumDescription(mediumDescription));
        return post;
    }
}
