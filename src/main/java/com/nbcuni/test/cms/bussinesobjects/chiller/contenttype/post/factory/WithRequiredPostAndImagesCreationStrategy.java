package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.factory;


import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.MediaFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.Post;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.PostInfo;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

@Component("requiredPostWithImageWithoutUsage")
public class WithRequiredPostAndImagesCreationStrategy implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        Post post = new Post();
        post.setGeneralInfo(new GeneralInfo());
        post.setPostInfo(new PostInfo());
        post.getGeneralInfo().setTitle(String.format(PostData.TITLE, postfix))
                .setDateLine(PostData.DATE_LINE);
        post.setMediaImages(MediaFactory.createMediaWithImageCountWithoutUsages(2));
        return post;
    }
}
