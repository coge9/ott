package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.media.MediaBlock;
import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import org.openqa.selenium.support.FindBy;

public class MediaTab extends BaseTabBlock {

    @FindBy(id = "edit-field-media")
    protected MediaBlock mediaBlock;

    @FindBy(id = "edit-field-cover-media")
    protected CoverMediaBlock coverMediaBlock;

    public MediaBlock onMediaBlock() {
        return mediaBlock;
    }

    public CoverMediaBlock onCoverMediaBlock() {
        return coverMediaBlock;
    }
}
