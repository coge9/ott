package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.Post;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.PostInfo;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan_Karnilau on 07-Apr-16.
 */
@Component("withoutRequiredPost")
public class WithoutRequiredPostCreationStrategy implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {
        Post post = new Post();
        post.setGeneralInfo(new GeneralInfo());
        post.setPostInfo(new PostInfo());
        return post;
    }
}
