package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.TextField;
import org.openqa.selenium.support.FindBy;

public class UrlPathSettingsBlock extends BaseTabBlock {
    @FindBy(id = "edit-path-pathauto")
    private CheckBox autoSlug;

    @FindBy(id = "edit-path-alias")
    private TextField slug;

    public Slug getSlug() {
        Slug toReturn = new Slug();
        toReturn.setAutoSlug(autoSlug.isSelected());
        toReturn.setSlugValue(slug.getValue());
        return toReturn;
    }

    public void enterSlug(Slug slug) {
        boolean isAutoSlug = slug.isAutoSlug();
        autoSlug.selectStatus(isAutoSlug);
        if (!isAutoSlug) {
            waitFor().waitForPageLoad(2);
            this.slug.enterText(slug.getSlugValue());
        }
    }
}
