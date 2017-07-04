package com.nbcuni.test.cms.backend.tvecms.pages.taxonomy;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableRow;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class TaxonomyTermListPage extends MainRokuAdminPage {

    private final Label pageTitle = new Label(webDriver,
            ".//h1[@class='page-title']");
    private final String editLinkName = "edit";
    @FindBy(id = "taxonomy")
    private Table table;

    public TaxonomyTermListPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public Table elementTable() {
        return table;
    }

    public Label elementPageTitle() {
        return pageTitle;
    }

    public Boolean isTermPresentInTable(String termName) {
        TableRow termRow = table.getRowByTextInColumn(termName, 1);
        if (termRow == null) {
            return false;
        } else {
            return true;
        }
    }

    public void clickEditLinkForTerm(String termName) {
        if (isTermPresentInTable(termName)) {
            table.getRowByTextInColumn(termName, 1).getCellByIndex(3)
                    .clickLinkByName(editLinkName);
            return;
        } else {
            if (table.getPager().isNextPagePresent()) {
                table.getPager().clickNextLink();
                clickEditLinkForTerm(termName);
            } else {
                Utilities.logSevereMessageThenFail("Term " + termName + " is not present in the table");
                return;
            }
        }
    }

    public String getEditLinkForTerm(String termName) {
        if (isTermPresentInTable(termName)) {
            String link = table.getRowByTextInColumn(termName, 1)
                    .getCellByIndex(3).getLinkByName(editLinkName);
        } else {
            Utilities.logSevereMessageThenFail("Term " + termName + " is not present in the table");
        }
        return null;
    }

    public List<String> getAllEditLinkForAllTerms() {
        List<String> links = new ArrayList<String>();
        Utilities.logSevereMessageThenFail("Collect all edit links");
        int numberOfRows = table.getNumberOfRows();
        for (int i = 1; i <= numberOfRows; i++) {
            String link = table.getRowByIndex(i).getCellByIndex(3)
                    .getLinkByName(editLinkName);
            links.add(link);
        }
        return links;
    }

    public TaxonomyViewPortPage clickEditLinkForRandomTerm() {
        table.getRandomRow().getCellByIndex(3).clickLinkByName("edit");
        Utilities.logInfoMessage("Open random term for edit");
        return new TaxonomyViewPortPage(webDriver, aid);
    }

    public void clickEditLinkForTerm(int termIndex) {
        if (table.getNumberOfRows() > termIndex) {
            table.getRowByIndex(termIndex).getCellByIndex(3)
                    .clickLinkByName(editLinkName);
        } else {
            Utilities.logSevereMessageThenFail("Row number is less than " + termIndex);
        }
    }

    public String getEditLinkForTerm(int termIndex) {
        return table.getRowByIndex(termIndex).getCellByIndex(3)
                .getLinkByName(editLinkName);
    }

    public List<String> getAllTermNames() {
        return table.getValuesFromColumn(1);
    }
}
