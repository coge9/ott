package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.*;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.Post;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.Blurb;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.PostInfoCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.WysiwigDescription;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Category;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by Ivan on 06.04.2016.
 * Annotation name = defaultPost
 */
@Component("defaultPost")
public class DefaultPostCreationStrategy implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        Post post = new Post();
        GeneralInfo generalInfo = new GeneralInfo();
        WysiwigDescription longDescription = new WysiwigDescription();
        WysiwigDescription mediumDescription = new WysiwigDescription();
        longDescription.setBlurb(
                new Blurb().setText(String.format(PostData.LONG_DESCRIPTION, postfix)));
        mediumDescription.setBlurb(
                new Blurb().setText(String.format(PostData.MEDIUM_DESCRIPTION, postfix)));
        generalInfo.setTitle(String.format(PostData.TITLE, postfix))
                .setByline(String.format(PostData.BYLINE, postfix))
                .setDateLine(PostData.DATE_LINE)
                .setShortDescription(String.format(PostData.SHORT_DESCRIPTION, postfix));
        post.setGeneralInfo(generalInfo);
        post.setPromotional(PromotionalCreator.getRandomPromotional());
        post.setPostInfo(PostInfoCreator.getRandomPostInfo(3).
                setLongDescription(longDescription).
                setMediumDescription(mediumDescription));
        post.setAssociations(new Associations().setChannelReferenceAssociations(new ChannelReferenceAssociations().setChannelReference(new ChannelReference())));
        post.getAssociations().setTags(TagsCreator.getRandomTags(3));
        post.getAssociations().setCategories(Arrays.asList(new String[]{Category.randomValue().getCategory()}));
        return post;
    }
}
