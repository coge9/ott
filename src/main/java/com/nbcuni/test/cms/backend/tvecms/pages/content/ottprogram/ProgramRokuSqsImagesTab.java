package com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram;

import com.nbcuni.test.cms.backend.tvecms.pages.content.basetabs.BaseImagesTab;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.SourceMatcher;
import com.nbcuni.test.cms.elements.VideoContentImage;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.utils.SoftAssert;
import org.openqa.selenium.support.FindBy;

import java.util.List;


// Tab related to the new ROKU platform which is going to be published to ConcertoAPI
public class ProgramRokuSqsImagesTab extends BaseImagesTab {

    @FindBy(xpath = ".//*[contains(@class,'form-type-managed-file')]")
    private List<VideoContentImage> allsources;

    public List<VideoContentImage> getAllSourcesBlocks() {
        return allsources;
    }

    public void verifyThumbnails(SoftAssert softAssert) {
        for (VideoContentImage source : allsources) {
            String name = source.getTitleLabel();
            SourceMatcher matcher = SourceMatcher.getSource(name, ContentType.TVE_PROGRAM, CmsPlatforms.ROKU_SQS);
            List<ImageStyles> styles = matcher.getSourceClassInstance().getImageStyles();
            softAssert.assertTrue(source.isSourcePresent(), "Source " + name + " is not present");
            if (source.isSourcePresent()) {
                source.isImageStylesPresent(styles, softAssert);
            }
        }
    }
}
