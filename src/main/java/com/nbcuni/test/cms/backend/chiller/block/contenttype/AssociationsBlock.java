package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReferenceAssociations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Tag;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.MultiSelectConcertoTheme;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author Aliaksei_Dzmitrenka
 */

public class AssociationsBlock extends BaseTabBlock {

    @FindBy(id = "edit-field-tags")
    protected TagsBlock tagsBlock;

    @FindBy(id = "edit-field-series-season-episode")
    protected ChannelReferenceBlock channelReferenceBlock;

    @FindBy(xpath = ".//div[contains(@id,'categories')]")
    private MultiSelectConcertoTheme categories;

    @FindBy(xpath = ".//select[contains(@id,'edit-field-current-season-und')]")
    private DropDownList season;

    public void enterChannelReferenceAssociations(ChannelReferenceAssociations channelReferenceAssociations) {
        channelReferenceBlock.enterChannelReference(channelReferenceAssociations.getChannelReference());
    }

    public Associations getAssociationsInfo() {
        Associations toReturn = new Associations();
        if (channelReferenceBlock.isVisible()) {
            ChannelReferenceAssociations referenceAssociations = new ChannelReferenceAssociations().setChannelReference(channelReferenceBlock.getChannelReferenceInfo());
            toReturn.setChannelReferenceAssociations(referenceAssociations);
        }
        if (season.isPresent()) {
            Season seasonEntity = new Season();
            seasonEntity.getGeneralInfo().setTitle(this.season.getSelectedValue());
            toReturn.setSeason(seasonEntity);
        }
        toReturn.setTags(tagsBlock.getTags())
                .setCategories(categories.getSelected());
        return toReturn;
    }

    public void enterAssociations(Associations associations) {
        List<Tag> tags = associations.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tagsBlock.enterTags(tags);
        }
        ChannelReferenceAssociations channelReferenceAssociations = associations.getChannelReferenceAssociations();
        if (channelReferenceAssociations != null) {
            enterChannelReferenceAssociations(channelReferenceAssociations);
        }


        this.categories.clearSelection();
        for (String category : associations.getCategories()) {
            this.categories.select(category);
        }
        if (associations.getSeason() != null) {
            selectSeason(associations.getSeason().getTitle());
        }
    }

    public Boolean isTagsPresentInAutocomplete(List<Tag> tags) {
        return tagsBlock.isTagsPresentInAutocomplete(tags);
    }

    public void enterTags(List<Tag> tags) {
        tagsBlock.enterTags(tags);
    }

    public List<Tag> getTags() {
        return tagsBlock.getTags();
    }

    public void clearTags() {
        tagsBlock.clearTags();
    }

    public void selectCategory(String category) {
        categories.select(category);
    }

    public List<String> getSelectedCategories() {
        return categories.getSelected();
    }

    public void selectSeason(String season) {
        this.season.selectFromDropDown(season);
    }

    public void clearCategories() {
        this.categories.clearSelection();
    }
}
