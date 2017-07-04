package com.nbcuni.test.cms.backend.chiller.pages.contenttype;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.AssociationsBlock;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.PromotionalBlock;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.post.PostGeneralInfoBlock;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.post.PostInfoBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.Post;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.PostInfo;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 20-Apr-16.
 */
public class PostPage extends ContentTypePage {

    @FindBy(id = "edit-group_association")
    protected AssociationsBlock postAssociationsBlock;
    @FindBy(id = "edit-group_promotional")
    protected PromotionalBlock postPromotionalBlock;
    @FindBy(id = "edit-group_general_info")
    private PostGeneralInfoBlock postGeneralInfoBlock;
    @FindBy(id = "edit-group_list")
    private PostInfoBlock postInfoBlock;

    public PostPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    private PostGeneralInfoBlock getPostGeneralInfoBlock() {
        postGeneralInfoBlock.expandTab();
        return postGeneralInfoBlock;
    }

    private PostInfoBlock getPostInfoBlock() {
        postInfoBlock.expandTab();
        return postInfoBlock;
    }

    private PostPage enterGeneralInfoData(GeneralInfo generalInfo) {
        if (generalInfo == null) {
            return this;
        }
        getPostGeneralInfoBlock().enterGeneralInfoData(generalInfo);
        return this;
    }

    private PostPage enterPostInfoData(PostInfo postInfo) {
        if (postInfo == null || postInfo.isObjectNull()) {
            return this;
        }
        getPostGeneralInfoBlock().enterLongMediumDescriptionData(postInfo);
        getPostInfoBlock().enterBlurbsData(postInfo);
        return this;
    }

    private PostPage enterAssociationsData(Associations associations) {
        if (associations == null) {
            return this;
        }
        onAssociationsTab().enterAssociations(associations);
        return this;
    }

    @Override
    public PostPage enterContentTypeData(Content content) {
        this.enterGeneralInfoData(content.getGeneralInfo());
        this.enterPostInfoData(((Post) content).getPostInfo());
        super.enterPromotionalData(content.getPromotional());
        this.enterAssociationsData(content.getAssociations());
        this.enterMediaData(content.getMediaImages());
        return this;
    }

    @Override
    public PostPage create(Content content) {
        this.enterContentTypeData(content).saveAsDraft();
        return this;
    }

    @Override
    public PostPage createAndPublish(Content content) {
        return (PostPage) this.enterContentTypeData(content).publish();
    }

    @Override
    public Post getPageData() {
        Post post = new Post();
        post.setGeneralInfo(getPostGeneralInfoBlock().getPostGeneralInfoData());
        post.setPostInfo(getPostInfoBlock().getPostInfoData());
        post.setAssociations(onAssociationsTab().getAssociationsInfo());
        return post;
    }
}
