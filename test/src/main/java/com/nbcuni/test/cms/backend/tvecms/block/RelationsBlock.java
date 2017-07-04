package com.nbcuni.test.cms.backend.tvecms.block;

import com.nbcuni.test.cms.elements.ExpanderWithLink;
import com.nbcuni.test.cms.elements.MultiSelectConcertoTheme;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class RelationsBlock extends AbstractContainer {

    @FindBy(id = "edit-relations")
    private ExpanderWithLink relations;

    @FindBy(id = "edit-weight")
    private TextField weight;

    @FindBy(id = "edit-relations")
    private MultiSelectConcertoTheme parentTerms;

    public int getWeight() {
        relations.expand();
        return Integer.valueOf(weight.getValue());
    }

    public void setWeight(int weightValue) {
        relations.expand();
        weight.enterText(String.valueOf(weightValue));
    }

    public List<String> getParentTerms() {
        return parentTerms.getSelected();
    }

    public void setParentTerms(List<String> termsToSet) {
        if (termsToSet != null) {
            relations.expand();
            parentTerms.select(termsToSet);
        }
    }
}
