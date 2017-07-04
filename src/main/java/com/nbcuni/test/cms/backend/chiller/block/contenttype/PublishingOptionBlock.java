package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.otherinfo.PublishingOptions;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.DropDownList;
import org.openqa.selenium.support.FindBy;

/**
 * Created by alekca on 18.05.2016.
 */
public class PublishingOptionBlock extends BaseTabBlock {

    @FindBy(id = "edit-status")
    private CheckBox publishStatus;

    @FindBy(id = "edit-promote")
    private CheckBox promoted;

    @FindBy(id = "edit-sticky")
    private CheckBox sticky;

    @FindBy(id = "edit-revision-scheduler-operation")
    private DropDownList revision;

    public boolean getPublishStatus() {
        return publishStatus.isSelected();
    }

    public boolean getPromoted() {
        return promoted.isSelected();
    }

    public boolean getSticky() {
        return sticky.isSelected();
    }

    public String getRevision() {
        String selected = revision.getSelectedValue();
        if (!selected.equalsIgnoreCase("none")) {
            return selected;
        }
        return "";
    }

    /**
     * The method to collect publishing options's tab values of the node to the PublishingOption object
     *
     * @return publishing options entity object
     */
    public PublishingOptions getPublishingOptions() {
        PublishingOptions options = new PublishingOptions();
        options.setPublished(getPublishStatus());
        options.setPromoted(getPromoted());
        options.setSticky(getSticky());
        options.setRevision(getRevision());
        return options;
    }
}
