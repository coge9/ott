package com.nbcuni.test.cms.elements;

import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ivan_Karnilau on 28-Sep-15.
 */
public class TabsGroup extends AbstractElement {

    private static final String TABS_GROUP = ".//li";
    private static final String CURRENT_TAB = ".//li[contains(@class,'selected')]";

    public TabsGroup(CustomWebDriver driver, String xPath) {
        super(driver, xPath);
    }

    public TabsGroup(CustomWebDriver driver, By byLocator) {
        super(driver, byLocator);
    }

    public TabsGroup(CustomWebDriver driver, WebElement element) {
        super(driver, element);
    }

    public TabsGroup(WebElement webElement) {
        super(webElement);
    }

    public List<Tab> getTabList() {
        List<Tab> tabs = new ArrayList<>();
        List<WebElement> tabsList = element().findElements(By.xpath(TABS_GROUP));
        for (WebElement tab : tabsList) {
            tabs.add(new Tab(driver, tab));
        }
        return tabs;
    }

    public void openTabByName(String name) {
        if (!getCurrentTabName().equals(name)) {
            util().scrollPageUp();
            List<Tab> tabs = this.getTabList();
            for (Tab tab : tabs) {
                if (tab.getName().equalsIgnoreCase(name)) {
                    Utilities.logInfoMessage("Open '" + name + "' tab");
                    tab.open();
                    break;
                }
            }
        }
    }

    public void openTabByIndex(int index) {
        this.getTabList().get(index - 1).open();
    }

    public List<String> getTabsNames() {
        List<String> tabsNames = new ArrayList<>();
        List<Tab> tabs = this.getTabList();
        for (Tab tab : tabs) {
            tabsNames.add(tab.getName());
        }
        return tabsNames;
    }

    public Boolean isTabOpen(String tabName) {
        return getCurrentTabName().equals(tabName);

    }

    public String getCurrentTabName() {
        getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        List<WebElement> elements = element().findElements(By.xpath(CURRENT_TAB));
        getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        if (!elements.isEmpty()) {
            return elements.get(0).getText();
        }
        return "";
    }

    public int getCurrentTabIndex() {
        String tabName = getCurrentTabName();
        List<Tab> tabNames = getTabList();
        for (int i = 1; i < tabNames.size(); i++) {
            if (tabNames.get(i).equals(tabName)) {
                return i;
            }
        }
        return 1;
    }

    public boolean isTabPresent(String name) {
        return getTabsNames().contains(name);
    }

    public int getNumberOfTabs() {
        return this.getTabList().size();
    }
}
