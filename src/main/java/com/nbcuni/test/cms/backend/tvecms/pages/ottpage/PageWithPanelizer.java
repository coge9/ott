package com.nbcuni.test.cms.backend.tvecms.pages.ottpage;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.SettingsPanelizerPage;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.RegexUtil;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;


public class PageWithPanelizer extends MainRokuAdminPage {

    private final String ACTIONS_BLOCK_XPATH = ".//*[@id='block-system-main']//*[contains(@class,'panel-display')]";
    private final String ACTIONS_LINK_XPATH = ".//*[@id='block-system-main']//a[contains(@text,'Coonfigure')]";
    private Link actionsLink = new Link(webDriver, ACTIONS_LINK_XPATH);
    private String LINKS_XPATH = ".//a[text()='%s']";
    Link settingsLinks = new Link(webDriver, LINKS_XPATH);
    private String CONTEXT_MENU_XPATH = ".//*[@id='block-system-main']//div[contains(@class, 'contextual-links-processed')]";
    //elements
    private Label modulesLabel = new Label(webDriver, "//div[contains(@class,'pane-title')]");

    public PageWithPanelizer(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    private void clickPanelizeSettings() {
        webDriver.mouseOver(ACTIONS_BLOCK_XPATH, ACTIONS_LINK_XPATH);
    }

    public <T> T editSettings(Class<T> classOfT, EditSettings settings) {
        Utilities.logInfoMessage("Select settings to edit");
        clickPanelizeSettings();
        LINKS_XPATH = String.format(LINKS_XPATH, settings.getValue());
        settingsLinks.click();
        if (classOfT.isInstance(SettingsPanelizerPage.class)) {

            return (T) new SettingsPanelizerPage(webDriver, aid);
        }
        if (classOfT.isInstance(EditPageWithPanelizer.class)) {

            return (T) new EditPageWithPanelizer(webDriver, aid);
        }
        throw new TestRuntimeException("No appropriate class instance was found to cast object");
    }

    public boolean isSettingLinksPresent() {
        clickPanelizeSettings();
        List<String> missedLinks = new ArrayList<>();
        for (EditSettings setting : EditSettings.values()) {
            LINKS_XPATH = String.format(LINKS_XPATH, setting.getValue());
            if (!settingsLinks.isPresent()) {
                missedLinks.add("Element: " + setting.getValue() + " is missed from menu");
            }
        }
        return missedLinks.isEmpty();
    }

    public String getShelfName() {
        String shelfLabel = modulesLabel.getText();
        return RegexUtil.getFirstSubstringByRegex(shelfLabel, ".+[^ \\[edit\\]]"); //delete from title '[edit]'
    }

    public boolean isModulePresent() {
        return modulesLabel.isPresent();
    }

    public List<String> getShelvesName() {
        List<WebElement> modules = modulesLabel.elements();
        List<String> modulesLabels = new ArrayList<>();

        for (WebElement module : modules) {
            modulesLabels.add(module.getText());
        }
        return modulesLabels;
    }

    public enum EditSettings {
        EDIT_SETTINGS("Edit panelizer settings"), EDIT_LAYOUT("Edit panelizer layout"),
        EDIT_CONTEXT("Edit panelizer contexts"), EDIT_CONTENT("Edit panelizer content");

        private String value;

        EditSettings(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
