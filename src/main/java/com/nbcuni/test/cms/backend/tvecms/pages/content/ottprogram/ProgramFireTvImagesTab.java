package com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram;

import com.nbcuni.test.cms.backend.tvecms.pages.content.basetabs.BaseImagesTab;
import com.nbcuni.test.cms.elements.VideoContentImage;
import org.openqa.selenium.support.FindBy;

import java.util.List;


// Tab related to the FireTV platform which is going to be published to ConcertoAPI
public class ProgramFireTvImagesTab extends BaseImagesTab {

    @FindBy(xpath = ".//*[contains(@class,'form-type-managed-file')]")
    private List<VideoContentImage> allsources;

    public List<VideoContentImage> getAllSourcesBlocks() {
        return allsources;
    }
}
