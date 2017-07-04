package com.nbcuni.test.cms.backend.tvecms.pages.taxonomy;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

public class TaxonomyPage extends MainRokuAdminPage {

    private final String addTermsLinkName = "add terms";
    private final String listTermsLinkName = "list terms";
    @FindBy(id = "taxonomy")
    private Table table;

    public TaxonomyPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public Table elementTable() {
        return table;
    }

    public AddTaxonomyTermPage clickAddTermsLinkForTerm(String termName) {
        table.getRowByTextInColumn(termName, 1).getCellByIndex(5).clickLinkByName(addTermsLinkName);
        return new AddTaxonomyTermPage(webDriver, aid);
    }


    public TaxonomyTermListPage clickListTermsLinkForTerm(String termName) {
        table.getRowByTextInColumn(termName, 1).getCellByIndex(4).clickLinkByName(listTermsLinkName);
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new TaxonomyTermListPage(webDriver, aid);
    }
}
