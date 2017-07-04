package com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist.additempopup;

import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.elements.table.TableRow;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alena_Aukhukova
 */
public class AddItemsPopupBlock extends AddItemsPopupAbstract {

    private static final String CHECKBOX_XPATH = ".//input[contains(@id,'edit-views-bulk-operations')]";
    public static final String TITLE = "Title";

    @FindBy(xpath = ".//input[contains(@class,'select-all form-checkbox') or contains(@id,'select-all')]")
    private CheckBox selectAll;

    @FindBy(xpath = ".//*[@id='edit-actionott-list-action-add-selected']")
    private Button submit;

    @FindBy(xpath = ".//*[contains(@id,'edit-submit-ott-list-add-items')]")
    private Button apply;

    @FindBy(xpath = ".//input[@value='Reset']")
    private Button reset;

    @FindBy(xpath = ".//input[contains(@id, 'edit-title')]")
    private TextField searchFilter;

    @FindBy(xpath = ".//div[@id='ott-list-modal-status-bar']")
    private AddedItemsBlock addedItemsBlock;

    public TextField elementSearchFilter() {
        return searchFilter;
    }

    public AddedItemsBlock elementAddedItemsBlock() {
        return addedItemsBlock;
    }

    /**
     * Actions
     */
    public void clickSelectAll() {
        selectAll.clickWithAjaxWait();
    }

    /**
     * Methods for filtering
     */
    public void clickFilterApply() {
        apply.clickWithAjaxWait();
    }

    public void clickFilterReset() {
        reset.clickWithAjaxWait();
    }

    public List<String> filterAssetsByTitle(String title) {
        elementSearchFilter().enterText(title);
        clickFilterApply();
        checkAssetOnCurrentPage(title);
        if (!tableContent.isVisible()) {
            return null;
        }
        return tableContent.getValuesFromColumn(TITLE);
    }

    /**
     * Methods for adding
     */

    @Override
    public void checkAsset(String asset) {
        filterAssetsByTitle(asset);
        WaitUtils.perform(getWebDriver()).waitForPageLoad();
        Utilities.logInfoMessage("Check Asset with name: [" + asset + "]");
    }

    @Override
    public String addRandomAsset() {
        String title = checkRandomAsset();
        clickAddSelected();
        WaitUtils.perform(getWebDriver()).waitForPageLoad();
        return title;
    }

    @Override
    public String checkRandomAsset() {
        TableRow asset = tableContent.getRandomRow();
        String title = asset.getCellByColumnTitle(TITLE).getCellInnerText();
        CheckBox assertCheck = new CheckBox(asset, CHECKBOX_XPATH);
        assertCheck.check();
        title = StringUtils.normalizeSpace(title);
        Utilities.logInfoMessage("Random asset [" + title + "] is checked");
        return title;
    }

    public void checkAssetOnCurrentPage(String name) {
        if (tableContent.isVisible()) {
            TableRow asset = tableContent.getRowByTextInColumn(name, TITLE);
            CheckBox assertCheck = new CheckBox(asset, CHECKBOX_XPATH);
            assertCheck.check();
            Utilities.logInfoMessage("Check Asset with name: [" + name + "]");
        }
    }

    public boolean isItemSelectedOnCurrentPage(String title) {
        TableRow assetRow = elementTableContent().getRowByTextInColumn(title, TITLE);
        CheckBox itemCheckBox = new CheckBox(assetRow, CHECKBOX_XPATH);
        return itemCheckBox.isSelected();
    }

    public boolean isItemsSelectedOnCurrentPage(List<String> titles) {
        boolean result = true;
        for (String title : titles) {
            if (!isItemSelectedOnCurrentPage(title)) {
                result = false;
                Utilities.logSevereMessageThenFail("Item [" + title + "] isn't selected");
            }
        }
        return result;
    }

    public List<String> getAllAssetTitlesFromPage() {
        return elementTableContent().getValuesFromColumn(TITLE);
    }

    public List<String> getAllAssetTitlesFromAllPages() {
        List<String> result = new LinkedList<>();
        result.addAll(getAllAssetTitlesFromPage());
        while (elementTableContent().getPager().isNextPagePresent()) {
            elementTableContent().getPager().clickNextLink();
            result.addAll(getAllAssetTitlesFromPage());
        }
        return result;
    }
}
