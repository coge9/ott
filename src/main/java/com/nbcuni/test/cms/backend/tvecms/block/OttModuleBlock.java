package com.nbcuni.test.cms.backend.tvecms.block;

import com.nbcuni.test.cms.bussinesobjects.tvecms.module.ottmodule.OttModuleForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.elements.AbstractElement;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 15-Sep-15.
 */
public class OttModuleBlock extends AbstractElement {

    private static final String MODULE_PATH_BEGIN = "/admin/ott/modules/manage/";
    private static final String MODULE_PATH_END = "/edit?";
    private static final String CONFIGURATION_TAB = ".//fieldset";
    private static final String CONFIGURATION_TAB_LINK = ".//fieldset//a";
    private static final String ENABLE_STATUS = ".//input[contains(@id,'edit-field-ott-page-ott-module-und-') and contains(@id,'-ott-module-status')]";
    private static final String YES_LOCKED = ".//input[contains(@id,'edit-field-ott-page-ott-module-und-') and contains(@id,'-ott-module-locked')]";
    private static final String OTT_MODULE_XPATH = ".//input[@autocomplete]";
    private static final String OTT_MODULE_LINK_XPATH = ".//td[1]/a";
    private static final String TABLE_DATA = ".//table[contains(@class,'views-table')]";
    private static final String TITLE_VALUE = ".//*[@class='view-header']/strong";
    private static final String LIST_VALUE = ".//td[contains(@class,'ott-module-list')]//li";
    private static final String TILE_VARIANT_VALUE = ".//td/div[contains(@class,'ott-module-tile-variant')]";
    private static final String IMAGE_STYLE_VALUE = ".//td/div[contains(@class,'ott-module-image-style')]";
    private static final String DISPLAY_TITLE_VALUE = ".//td[contains(@class,'views-field-display-title')]";
    private static final String UPDATED_VALUE = ".//td[contains(@class,'views-field-updated')]";
    private static final String OTT_DRAGADROP_XPATH = "./td[@class='field-multiple-drag']";
    private static final String SHELF_SELECT_XPATH = ".//select[@id='edit-reference-id']";
    private static final String ADD_MODULE_BUTTON_XPATH = ".//input[@id='edit-add']";
    private static final String ASSETS_LIST_XPATH = ".//div[contains(@class,'item-list')]//li";
    private static final String EDIT_MODULE_XPATH = ".//*[@class='view-header']/a";
    private static final String ID_MODULE_XPATH = ".//*[contains(@name,'[ott_module][reference_id]')]";

    public OttModuleBlock(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public OttModuleBlock(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public OttModuleBlock(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public OttModuleBlock(WebElement element) {
        super(element);
    }

    public void selectModule(String name) {
        DropDownList shelf = new DropDownList(driver, SHELF_SELECT_XPATH);
        WebDriverUtil.getInstance(driver).scrollIntoView(shelf.element());
        shelf.selectFromDropDown(name);
        WaitUtils.perform(driver).waitForElementClickable(ADD_MODULE_BUTTON_XPATH);
        Button addBtn = new Button(driver, ADD_MODULE_BUTTON_XPATH);
        addBtn.click();
    }

    public Input getShelfField() {
        return new Input(driver, element().findElement(By.xpath(OTT_MODULE_XPATH)));
    }

    public String getShelf() {
        return element().findElement(By.xpath(TITLE_VALUE)).getText();
    }

    public String getShelfId() {
        return element().findElement(By.xpath(ID_MODULE_XPATH)).getAttribute(HtmlAttributes.VALUE.get());
    }

    public WebElement getShelfDragDrop() {
        return element().findElement(By.xpath(OTT_DRAGADROP_XPATH));
    }

    public boolean isStatusEnabled() {
        return this.getEnabledStatus().getAttribute(HtmlAttributes.CHECKED.get()).equals("checked");
    }

    public WebElement getEnabledStatus() {
        return element().findElement(By.xpath(ENABLE_STATUS));
    }

    public void selectStatus(Boolean status) {
        WebElement statusChbx = element().findElement(By.xpath(ENABLE_STATUS));
        if (status != statusChbx.isSelected()) {
            statusChbx.click();
        }
    }

    public void selectLocked(Boolean status) {
        WebElement statusChbx = element().findElement(By.xpath(YES_LOCKED));
        if (status != statusChbx.isSelected()) {
            statusChbx.click();
        }
    }

    public boolean isLocked() {
        return this.getYesLocked().isSelected();
    }

    public WebElement getYesLocked() {
        return element().findElement(By.xpath(YES_LOCKED));
    }

    public void fillBlock(OttModuleForm ottModuleForm) {
        this.selectModule(ottModuleForm.getTitle());
    }

    public OttModuleForm getOttModuleForm() {
        OttModuleForm ottModuleForm = new OttModuleForm();
        ottModuleForm.setTitle(this.getShelf());
        ottModuleForm.setStatus(this.isStatusEnabled());
        ottModuleForm.setLocked(this.isLocked());
        return ottModuleForm;
    }

    public boolean isConfigurationTabExpand() {
        return !element().findElement(By.xpath(CONFIGURATION_TAB)).getAttribute(HtmlAttributes.CLASS.get())
                .contains("collapsed");
    }

    public boolean isTablePresent() {
        return element().findElements(By.xpath(TABLE_DATA)) != null;
    }

    public void clickByShelfLink() {
        WebElement tableData = element().findElement(By.xpath(TABLE_DATA));
        tableData.findElement(By.xpath(TITLE_VALUE)).click();
    }

    public void clickEditModule() {
        element().findElement(By.xpath(EDIT_MODULE_XPATH)).click();
    }

    public Shelf getShelfData() {
        if (!this.isTablePresent()) {
            return null;
        }
        WebElement tableData = element().findElement(By.xpath(TABLE_DATA));
        String title = tableData.findElement(By.xpath(TITLE_VALUE)).getText().trim();
        List<WebElement> listElements = tableData.findElements(By.xpath(LIST_VALUE));
        String tileVariant = tableData.findElement(By.xpath(TILE_VARIANT_VALUE)).getText().trim();
        String imageStyle = tableData.findElement(By.xpath(IMAGE_STYLE_VALUE)).getText().trim();
        boolean displayTitle = tableData.findElement(By.xpath(DISPLAY_TITLE_VALUE)).getText().trim().equals("Yes");
        String updated = tableData.findElement(By.xpath(UPDATED_VALUE)).getText().trim();
        List<String> list = new ArrayList<>();
        for (WebElement element : listElements) {
            list.add(element.getText().trim());
        }

        Shelf shelf = new Shelf();
        shelf.setTitle(title).setTitleVariant(tileVariant);
        shelf.setDisplayTitle(displayTitle).setAssetsCount(list.size());
        return shelf;
    }

    public List<String> getAssets() {
        List<String> assets = new ArrayList<>();
        for (WebElement asset : element().findElements(By.xpath(ASSETS_LIST_XPATH))) {
            assets.add(asset.getText());
        }
        return assets;
    }
}
