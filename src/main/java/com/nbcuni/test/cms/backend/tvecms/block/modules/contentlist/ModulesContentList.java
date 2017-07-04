package com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist;

import com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist.additempopup.AddItemsPopupAbstract;
import com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist.additempopup.AddItemsPopupBlock;
import com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist.additempopup.AddItemsPopupForShowBlock;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableRow;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlTags;
import com.nbcuni.test.cms.utils.RegexUtil;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 24-Dec-15.
 */
public class ModulesContentList extends AbstractContainer {

    public static final String TITLE = "Title";
    private static final String REMOVE_XPATH = ".//input[contains(@id,'remove')]";
    private static final String SHOW_LATEST_XPATH = ".//input[contains(@id,'-show-latest')]";
    private static final String ENABLED_XPATH = ".//input[contains(@id,'-enabled') and not(contains(@id,'show-latest'))]";
    private static final String SHOW_LATEST_MAX_COUNT_XPATH = ".//input[contains(@id,'show-latest-max-count')]";
    public static final String NODE_ID_PATTERN = ".*/node/(\\d+)/edit.*";
    @FindBy(xpath = "//a[@title='Add items']")
    private Link addItems;
    @FindBy(xpath = "//td[@class='ott-list-col-title']/a")
    private Link itemTitle;
    @FindBy(id = "ott-list-table")
    private Table assetTable;
    @FindBy(xpath = "//*[@id='modalContent']")
    private AddItemsPopupForShowBlock addItemsPopupForShowBlock;
    @FindBy(xpath = "//*[@id='modalContent']")
    private AddItemsPopupBlock addItemsPopupBlock;

    public Table elementTable() {
        return assetTable;
    }

    public AddItemsPopupAbstract clickAddItems() {
        addItems.clickWithAjaxWait();
        if (isShowModule()) {
            return addItemsPopupForShowBlock;

        } else {
            return addItemsPopupBlock;
        }
    }

    private boolean isShowModule() {
        String currentPageTitle = webDriver.getTitle();
        if (StringUtils.isNotEmpty(currentPageTitle)) {
            return currentPageTitle.toLowerCase().contains("show") || currentPageTitle.toLowerCase().contains("dynamic");
        }
        return false;
    }

    public void addContentAssets(List<String> assetsList) {
        AddItemsPopupAbstract addItemsPopup = clickAddItems();
        addItemsPopup.addAssets(assetsList);
    }

    public void addContentAssetsWithOrder(List<String> assetsList) {
        addContentAssets(assetsList);
    }

    public void addContentAsset(String assetName) {
        AddItemsPopupAbstract addItemsPopup = clickAddItems();
        addItemsPopup.addAsset(assetName);
    }

    public String addRandomAsset() {
        Utilities.logInfoMessage("Add Random value to Content List");
        AddItemsPopupAbstract addItemsPopup = clickAddItems();
        return addItemsPopup.addRandomAsset();
    }

    public void removeAsset(String asset) {
        Utilities.logInfoMessage("Remove asset with name" + asset);
        TableRow assetRow = assetTable.getRowByTextInColumn(asset, TITLE);
        assetRow.element().findElement(By.xpath(REMOVE_XPATH)).click();
    }

    public void removeAllAsset() {
        Utilities.logInfoMessage("Remove all assets with name");
        List<TableRow> rows = assetTable.getRows();
        for (int i = 0; i < rows.size(); i++) {
            Button deleteButton = new Button(assetTable.getRows().get(0), REMOVE_XPATH);
            deleteButton.clickWithAjaxWait();
        }
    }

    public String removeRandomAsset() {
        TableRow rowForDelete = assetTable.getRandomRow();
        String rowTitle = rowForDelete.getCellByColumnTitle(TITLE).getCellInnerText();
        Utilities.logInfoMessage("Remove random asset [" + rowTitle + "]");
        rowForDelete.element().findElement(By.xpath(REMOVE_XPATH)).click();
        return rowTitle;
    }

    public List<String> getAssets() {
        return assetTable.getValuesFromColumn(TITLE);
    }


    /**
     * Method allowed to get content entities of content which was added to the module using content API.
     *
     *@param brand - brnad on which test is executed.
     *@return list of GlobalNodeJson
     * */
    public List<GlobalNodeJson> getContentEntitiesFromApi(String brand) {
        List<GlobalNodeJson> entities = new LinkedList<>();
        int numberOfRows = assetTable.getNumberOfRows();
        NodeApi nodeApi = new NodeApi(brand);
        for (int i = 1; i <= numberOfRows; i++) {
            WebElement titleLink = assetTable.getRowByIndex(i).getCellByColumnTitle(TITLE).getInnerElementInCell(By.tagName(HtmlTags.A.get()));
            String linkHref = titleLink.getAttribute(HtmlAttributes.HREF.get());
            String nodeId = RegexUtil.getGroup(linkHref, NODE_ID_PATTERN, 1);
            entities.add(nodeApi.getNodeById(nodeId));
        }
        return entities;
    }

    /**
     * Method allowed to get UUIDs of content which was added to the module.
     *
     *@param brand - brnad on which test is executed.
     *@return list of UUIDs
     *
     * */
    public List<String> getUuids(String brand) {
        List<GlobalNodeJson> entities = getContentEntitiesFromApi(brand);
        List<String> uuids = new LinkedList<String>();
        entities.forEach(entity -> uuids.add(entity.getUuid()));
        return uuids;
    }

    public List<String> addRandomAssets(int number) {
        Utilities.logInfoMessage("Add Random values to Content List");
        List<String> assets = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            assets.add(addRandomAsset());
        }
        return assets;
    }

    public void checkLatestEpisodeByName(String name) {
        List<TableRow> assets = assetTable.getRows();
        for (TableRow asset : assets) {
            if (name.equalsIgnoreCase(asset.getCellByColumnTitle(TITLE).getCellInnerText())) {
                CheckBox showLatest = new CheckBox(asset, SHOW_LATEST_XPATH);
                showLatest.check();
            }
        }
    }

    public void setEnabledItemStatusByName(String name, Boolean status) {
        List<TableRow> assets = assetTable.getRows();
        for (TableRow asset : assets) {
            if (name.equalsIgnoreCase(asset.getCellByColumnTitle(TITLE).getCellInnerText())) {
                CheckBox showLatest = new CheckBox(asset, ENABLED_XPATH);
                showLatest.selectStatus(status);
            }
        }
    }

    public void uncheckLatestEpisodeByName(String name) {
        List<TableRow> assets = assetTable.getRows();
        for (TableRow asset : assets) {
            if (name.equals(asset.getCellByColumnTitle("TITLE").getCellInnerText())) {
                CheckBox showLatest = new CheckBox(asset, SHOW_LATEST_XPATH);
                showLatest.uncheck();
            }
        }
    }

    public String getMaxCount(String name) {
        Utilities.logInfoMessage("Get value from Max Count field for item : " + name);
        List<TableRow> assets = assetTable.getRows();
        for (TableRow asset : assets) {
            if (name.equals(asset.getCellByColumnTitle(TITLE).getCellInnerText())) {
                TextField maxCount = new TextField(asset, SHOW_LATEST_MAX_COUNT_XPATH);
                return maxCount.getValue();
            }
        }
        throw new TestRuntimeException("It;s impossible to get value from max count field, refer to exception below");
    }

    public void setMaxCount(String assetName, String value) {
        Utilities.logInfoMessage("Set value: " + assetName + " from Max Count field for item : " + assetName);
        List<TableRow> assets = assetTable.getRows();
        for (TableRow asset : assets) {
            if (assetName.equals(asset.getCellByColumnTitle(TITLE).getCellInnerText())) {
                WebElement maxCount = asset.getCellByColumnTitle("SHOW LATEST").geCellElement().findElement(By.xpath(SHOW_LATEST_MAX_COUNT_XPATH));
                maxCount.clear();
                maxCount.sendKeys(value);
                break;
            }
        }
    }

    public boolean isMaxCountPresent(String assetName) {
        Utilities.logInfoMessage("Check rather max count present ");
        List<TableRow> assets = assetTable.getRows();
        for (TableRow asset : assets) {
            if (assetName.equals(asset.getCellByColumnTitle("Title").getCellInnerText())) {
                TextField maxCount = new TextField(asset, SHOW_LATEST_MAX_COUNT_XPATH);
                return maxCount.isVisible();
            }
        }
        throw new TestRuntimeException("It's impossible to identify max count field, refer to exception below");
    }

    public void clickOnAssetTitle(String assetName) {
        assetTable.getRowByTextInColumn(assetName, Columns.TITLE.toString()).getCellByColumnTitle(Columns.TITLE.toString()).clickLinkByName(assetName);
    }

    private enum Columns {
        ENABLED, TYPE, TITLE, STATUS, ACTIONS
    }
}
