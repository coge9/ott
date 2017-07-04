package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Tag;
import com.nbcuni.test.cms.elements.TextFieldWithSimpleAutocomplete;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 12-May-16.
 */
public class TagsBlock extends AbstractContainer {

    private static final String COMMA = ", ";
    @FindBy(id = "edit-field-tags-und")
    private TextFieldWithSimpleAutocomplete tagsField;

    public void clearTags() {
        tagsField.clearText();
    }

    public void enterTags(List<Tag> tags) {
        if (CollectionUtils.isEmpty(tags)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Tag tag : tags) {
            sb.append(COMMA + tag.getTag());
        }
        tagsField.enterText(sb.toString().substring(2));
    }

    public List<Tag> getTags() {
        List<Tag> toReturn = new ArrayList<>();
        if (!tagsField.getValue().isEmpty()) {
            for (String oneTag : tagsField.getValue().split(COMMA)) {
                toReturn.add(new Tag(oneTag));
            }
        }
        return toReturn;
    }

    public boolean isTagPresentInAutocomplete(Tag tag) {
        return tagsField.isValuePresentInAutocompleteList(tag.getTag());
    }

    public boolean isTagsPresentInAutocomplete(List<Tag> tags) {
        SoftAssert softAssert = new SoftAssert();
        for (Tag tag : tags) {
            softAssert.assertTrue(isTagPresentInAutocomplete(tag), "Tag [" + tag + "] is not present in autocomplete", "Tag [" + tag + "] is present in autocomplete");
        }
        softAssert.assertAll();
        return softAssert.isNotAnyError();
    }
}
