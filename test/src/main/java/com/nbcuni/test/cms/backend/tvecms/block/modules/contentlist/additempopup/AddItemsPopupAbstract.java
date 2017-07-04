package com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist.additempopup;

import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by Alena_Aukhukova on 2/17/2016.
 */
public abstract class AddItemsPopupAbstract extends AbstractContainer {

    protected static final String CHECKBOX_XPATH = ".//input[contains(@id,'edit-views-bulk-operations')]";

    @FindBy(xpath = ".//*[@id='edit-actionott-list-action-add-selected']")
    protected Button submit;

    @FindBy(xpath = ".//a[@class='close']")
    protected Link close;

    @FindBy(xpath = ".//table[contains(@class,'views-table') or contains(@class,'views-view-grid')]")
    protected Table tableContent;

    public Table elementTableContent() {
        return tableContent;
    }

    public void clickAddSelected() {
        submit.clickWithAjaxWait();
        Utilities.logInfoMessage("Click on 'Add selected'");
    }

    public void closePopup() {
        close.click();
        Utilities.logInfoMessage("Click on 'Close Popup'");
    }

    // Methods for adding

    public void addAsset(String asset) {
        Utilities.logInfoMessage("Asset with name [" + asset + "] is added");
        checkAsset(asset);
        clickAddSelected();
    }

    public void addAssets(List<String> assetsList) {
        for (String title : assetsList) {
            checkAsset(title);
        }
        clickAddSelected();
    }

    public String addRandomAsset() {
        String title = checkRandomAsset();
        clickAddSelected();
        return title;
    }

    //Methods for checking
    public abstract String checkRandomAsset();

    public abstract void checkAsset(String name);

}
