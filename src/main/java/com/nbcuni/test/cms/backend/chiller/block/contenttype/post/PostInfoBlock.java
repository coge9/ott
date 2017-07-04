package com.nbcuni.test.cms.backend.chiller.block.contenttype.post;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.Blurb;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.PostInfo;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ivan on 21.04.2016.
 */
public class PostInfoBlock extends BaseTabBlock {

    @FindBy(xpath = ".//table[contains(@id,'field-blurb-values')]/tbody/tr")
    private List<BlurbBlock> blurbBlocks;

    @FindBy(xpath = ".//button[contains(@id,'edit-field-blurb-und-add-more')]")
    private Button addSection;

    public void enterBlurbsData(PostInfo postInfo) {
        List<Blurb> blurbs = postInfo.getBlurbs();
        for (int i = 0; i < blurbs.size(); i++) {
            blurbBlocks.get(i).enterBlurbData(blurbs.get(i));
            WebDriverUtil.getInstance(webDriver).scrollPageUp();
            addSection.clickWithAjaxWait();
        }
    }

    public void deleteBlurbs(List<Blurb> blurbs) {
        for (BlurbBlock blurbBlock : blurbBlocks) {
            if (blurbs.contains(blurbBlock.getBlurbData())) {
                blurbBlock.delete();
            }
        }

    }

    public PostInfo getPostInfoData() {
        PostInfo postInfo = new PostInfo();
        for (BlurbBlock blurbBlock : blurbBlocks) {
            postInfo.addBlurb(blurbBlock.getBlurbData());
        }
        return postInfo;
    }

    public void deleteBlurb(Blurb blurb) {
        this.deleteBlurbs(Arrays.asList(blurb));
    }
}
