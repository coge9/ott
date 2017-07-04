package com.nbcuni.test.cms.backend.tvecms.pages.platform;

import com.nbcuni.test.cms.backend.tvecms.pages.ConfirmationPage;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableCell;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class TvePlatformsPage extends Page {
    private static final String EXPECTED_MESSAGE_AFTER_SAVING = "Status message\nPlatform \"%s\" has been saved.";
    private static final String EXPECTED_MESSAGE_AFTER_DELETING = "Status message\nDeleted Platform %s.";
    private static final String TITLES_XPATH = "//td[1]/a";
    private static final String DELETE_XPATH = ".//a[contains(text(),'delete')]";
    private static final String columnName = "TITLE";
    private final Link addOttAppLink = new Link(webDriver, "//ul[@class='action-links']//a");
    @FindBy(xpath = "//div[@id='block-system-main']//table[2]")
    private Table appsTable;

    public TvePlatformsPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public Table elementAppsTable() {
        return appsTable;
    }

    public Link elementAddOttAppLink() {
        return addOttAppLink;
    }

    public Boolean isPlatformPresent(String appName) {
        return appsTable.getRowByTextInColumn(appName, 1) != null;
    }

    public String getExpectedMessageAfterSaving(String appTitle) {
        return String.format(EXPECTED_MESSAGE_AFTER_SAVING, appTitle);
    }

    public String getExpectedMessageAfterDeleting(String appTitle) {
        return String.format(EXPECTED_MESSAGE_AFTER_DELETING, appTitle);
    }

    /**
     * Method to get a List of names of Platforms, created in the CMS
     * @return list of platform names
     */
    public List<String> getAllPlatformTitlesOnPage() {
        List<String> titles = new ArrayList<String>();
        List<WebElement> titlesElements = webDriver.findElements(By.xpath(TITLES_XPATH));
        titlesElements.forEach(element -> titles.add(element.getText().trim()));
        return titles;
    }

    public List<String> getConfiguredPlatforms() {
        List<String> titles = new ArrayList<String>();
        List<WebElement> titlesElements = webDriver.findElements(By.xpath(TITLES_XPATH));
        for (WebElement title : titlesElements) {
            String platformName = title.getText().trim();
            if (CmsPlatforms.isPlatform(platformName)) {
                titles.add(platformName);
            }
        }
        return titles;
    }

    public AddPlatformPage clickEditPlatform(String platformName) {
        appsTable.getRowByTextInColumn(platformName, 1).getCellByIndex(2).clickLinkByName("edit");
        return new AddPlatformPage(webDriver, aid);
    }

    public AddPlatformPage clickEditRandomPlatform() {
        elementAppsTable().getRandomRow().getCellByIndex(2).clickLinkByName("edit");
        return new AddPlatformPage(webDriver, aid);
    }

    public TvePlatformsPage clickDelete(String title) {
        Utilities.logInfoMessage("Click delete for page: " + title);
        TableCell operationsCell = appsTable.getRowByTextInColumn(title, "LABEL").getCellByIndex(3);
        operationsCell.getInnerElementInCell(By.xpath(DELETE_XPATH)).click();
        ConfirmationPage confirmationPage = new ConfirmationPage(webDriver, aid);
        confirmationPage.clickSubmit();
        return new TvePlatformsPage(webDriver, aid);
    }

    @Override
    public List<String> verifyPage() {
        return null;
    }
}
