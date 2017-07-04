package com.nbcuni.test.cms.backend.chiller.block.contenttype;


import com.nbcuni.test.cms.backend.chiller.block.contenttype.media.MediaBlock;
import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Promotional;
import com.nbcuni.test.cms.elements.TextField;
import org.openqa.selenium.support.FindBy;

/**
 * @author Aliaksei_Dzmitrenka
 */

public class PromotionalBlock extends BaseTabBlock {

    @FindBy(id = "edit-field-promotional-kicker-und-0-value")
    private TextField promotionalKickerField;

    @FindBy(id = "edit-field-promotional-title-und-0-value")
    private TextField promotionalTitleField;

    @FindBy(id = "edit-field-promotional-description-und-0-value")
    private TextField promotionalDescriptionField;

    @FindBy(xpath = ".//*[contains(@id,'edit-field-promotional-image')]")
    private MediaBlock promoMedia;

    public void enterPromotionalKicker(String promotionalKicker) {
        promotionalKickerField.enterText(promotionalKicker);
    }

    public void enterPromotionalTitle(String promotionalTitle) {
        promotionalTitleField.enterText(promotionalTitle);
    }

    public void enterPromotionalDescription(String promotionalDescription) {
        promotionalDescriptionField.enterText(promotionalDescription);
    }

    public String getPromotionalKicker() {
        return promotionalKickerField.getValue();
    }

    public String getPromotionalTitle() {
        return promotionalTitleField.getValue();
    }

    public String getPromotionalDescription() {
        return promotionalDescriptionField.getValue();
    }

    public Promotional getPromotional() {
        Promotional toReturn = new Promotional();
        toReturn.setPromotionalKicker(getPromotionalKicker());
        toReturn.setPromotionalTitle(getPromotionalTitle());
        toReturn.setPromotionalDescription(getPromotionalDescription());
        return toReturn;
    }

    public void enterPromotional(Promotional promotional) {
        enterPromotionalKicker(promotional.getPromotionalKicker());
        enterPromotionalTitle(promotional.getPromotionalTitle());
        enterPromotionalDescription(promotional.getPromotionalDescription());
    }
}
