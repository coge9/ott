package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.PostPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.PostInfo;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

/**
 * Created by Ivan_Karnilau on 19-Apr-16.
 */
public class Post extends Content {

    private PostInfo postInfo = new PostInfo();

    @Override
    public String getTitle() {
        return getGeneralInfo().getTitle();
    }

    @Override
    public ItemTypes getType() {
        return ItemTypes.POST;
    }

    @Override
    public Class<? extends Page> getPage() {
        return PostPage.class;
    }

    public PostInfo getPostInfo() {
        return postInfo;
    }

    public Post setPostInfo(PostInfo postInfo) {
        this.postInfo = postInfo;
        return this;
    }

}
