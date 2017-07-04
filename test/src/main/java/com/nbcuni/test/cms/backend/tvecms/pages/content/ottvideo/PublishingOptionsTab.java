package com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Aleksandra_Lishaeva on 11/21/16.
 */
public class PublishingOptionsTab extends BaseTabBlock {

    @FindBy(xpath = ".//following-sibling::span")
    private Label publishedLabel;

    public boolean getPublishedState() {
        if (publishedLabel.getText().equalsIgnoreCase(PublishState.NOT_PUBSLISHED.getStateValue())) {
            return false;
        }
        return true;
    }
}
