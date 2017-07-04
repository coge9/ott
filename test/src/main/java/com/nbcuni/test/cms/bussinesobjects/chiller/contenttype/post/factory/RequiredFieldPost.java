package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.Post;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

@Component("requiredPost")
public class RequiredFieldPost implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        String postfix = SimpleUtils.getRandomString(6);
        Post post = new Post();
        GeneralInfo generalInfo = new GeneralInfo();
        generalInfo.setTitle(String.format(PostData.TITLE, postfix))
                .setDateLine(PostData.DATE_LINE);
        post.setGeneralInfo(generalInfo);
        return post;
    }
}
