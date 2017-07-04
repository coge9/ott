package com.nbcuni.test.cms.backend.tvecms.pages.panelizer;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.contentpage.MainContentPanelizerPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.RadioButtonsGroup;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizer.PanelizerTemplates;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.By;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 13-Jan-16.
 */
public class LayoutPanelizerPage extends MainRokuAdminPage {

    private DropDownList category = new DropDownList(webDriver, By.id("edit-categories"));
    private RadioButtonsGroup layouts = new RadioButtonsGroup(webDriver,
            "//*[contains(@id,'panels-layout-category') and @style and not(contains(@style,'display: none;'))]");
    private Button continueButton = new Button(webDriver, By.id("edit-next"));
    private Button save = new Button(webDriver, By.id("edit-return"));

    public LayoutPanelizerPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public void selectCategoryByName(String categoryName) {
        category.selectFromDropDown(categoryName);
    }

    public void checkLayoutByName(String layoutName) {
        layouts.selectRadioButtonByName(layoutName);
    }

    public void clickContinue() {
        continueButton.click();
    }

    public void clickSave() {
        save.click();
    }

    public MainContentPanelizerPage createTemplate(PanelizerTemplates templates) {
        this.selectCategoryByName(templates.getCategory());
        this.checkLayoutByName(templates.getLayout());
        this.clickContinue();
        this.clickSave();
        return new MainContentPanelizerPage(webDriver, aid, templates);
    }

    @Override
    public List<String> verifyPage() {
        return null;
    }
}
