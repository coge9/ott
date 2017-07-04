package com.nbcuni.test.cms.backend.tvecms.pages.taxonomy;

import com.nbcuni.test.cms.backend.tvecms.block.ActionBlock;
import com.nbcuni.test.cms.backend.tvecms.block.RelationsBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTerm;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

public class AddTaxonomyTermPage extends MainRokuAdminPage {

    @FindBy(id = "edit-name")
    private TextField name;

    @FindBy(id = "edit-description-value")
    private TextField descr;

    @FindBy(xpath = ".//select[contains(@id,'edit-description-format')]")
    private DropDownList textFormat;

    @FindBy(id = "edit-relations")
    private RelationsBlock relations;

    @FindBy(id = "edit-actions")
    private ActionBlock actionBlock;

    public AddTaxonomyTermPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public String getName() {
        return name.getValue();
    }

    public AddTaxonomyTermPage setName(String nameValue) {
        name.enterText(nameValue);
        return this;
    }

    public String getDescription() {
        return descr.getValue();
    }

    public AddTaxonomyTermPage setDescription(String descrValue) {
        textFormat.selectFromDropDown("Plain text");
        descr.enterText(descrValue);
        return this;
    }

    public RelationsBlock getRelations() {
        return relations;
    }

    public ActionBlock getActionBlock() {
        return actionBlock;
    }

    public AddTaxonomyTermPage createTaxonomyTerm(TaxonomyTerm term) {
        setName(term.getTitle());
        setDescription(term.getDescription());
        relations.setParentTerms(term.getParents());
        relations.setWeight(term.getWeight());
        WebDriverUtil.getInstance(webDriver).scrollPageUp();
        actionBlock.save();
        return this;
    }

    public TaxonomyTerm getData() {
        TaxonomyTerm taxonomyTerm = new TaxonomyTerm();
        taxonomyTerm.setTitle(name.getValue());
        taxonomyTerm.setDescription(descr.getValue());
        taxonomyTerm.setParents(relations.getParentTerms());
        taxonomyTerm.setWeight(relations.getWeight());
        return taxonomyTerm;
    }
}
