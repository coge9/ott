package com.nbcuni.test.cms.backend.tvecms.pages.content;

import com.nbcuni.test.cms.backend.ContentAbstractPage;
import com.nbcuni.test.cms.backend.chiller.pages.content.SelectInstancePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.elements.table.Pager;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableCell;
import com.nbcuni.test.cms.elements.table.TableRow;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ContentFormat;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.ReflectionUtils;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.SkipException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ContentPage extends ContentAbstractPage {
    public static final String PAGE_TITLE = "Content";
    public static final String CONTENT_TITLE = "Title";
    public static final String OPERATIONS = "OPERATIONS";
    public static final String CONTENT_TYPE_WITH_PARTIAL_TITLE_S_NOT_FOUND = "Content type with partial title '%s' not found.";
    public static final String CONTENT_TYPE_WITH_TITLE_S_NOT_FOUND = "Content type with title '%s' not found.";
    public static final String INVALID_PAGE_TITLE_ON = "Invalid page title on ";
    private static final String EDIT_LINK_XPATH = ".//td[@class='views-field views-field-pub-editorial']//a[text()='Edit']";
    private static final String CHECK_ITEM_CHECKBOX_XPATH = ".//*[contains(@id,'edit-views-bulk-operations-')]";
    private static final String BASE_PAGINATION = "//ul[@class='pager']";
    private static final String FIRST_PAGE = "//ul[@class='pager']//li[contains(@class,'first')]/a";
    private static final String LAST_PAGE = "//ul[@class='pager']//li[contains(@class,'last')]/a";
    private static final String CURRENT_PAGE = "//ul[@class='pager']//li[contains(@class,'pager-current')]";
    private static final String DELETE_LINK = "//div[@class='ctools-content']//a[text()='Delete']";
    private Pager pager;
    @FindBy(id = "edit-submit-content")
    private Button applyButton;

    @FindBy(id = "edit-operation")
    private DropDownList operationDdl;

    @FindBy(xpath = ".//*[@id='edit-submit--2']")
    private Button execute;

    @FindBy(id = "edit-submit")
    private Button confirmExecute;

    @FindBy(xpath = ".//a[contains(@class,'ctools-twisty')]")
    private Link optionsLink;

    @FindBy(xpath = "//div[@class='ctools-content']//a[text()='Delete']")
    private Link deleteLink;

    @FindBy(xpath = "//div[@class='ctools-content']//a[text()='Edit' or text()='Editar']")
    private Link editLink;

    public ContentPage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
        pager = new Pager(webDriver);
    }

    /**
     * Available options:<br>
     * - Any -<br>
     * Shortform content<br>
     * Full episodic content<br>
     *
     * @param contentFormat - format of content
     * @return - list of titles for filtered content
     */
    public List<String> filterByContentFormat(ContentFormat contentFormat) {
        List<String> assetsNames = new ArrayList<>();
        List<TableRow> tableRows = table.getRows();
        for (TableRow row : tableRows) {
            if (row.getCellByColumnTitle("Full Episode").getCellInnerText().equalsIgnoreCase(contentFormat.get())) {
                assetsNames.add(row.getCellByColumnTitle(CONTENT_TITLE).getCellInnerText());
            }
        }
        return assetsNames;
    }

    @Override
    public boolean apply() {
        applyButton.pause(1);
        applyButton.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return isContentPresent();
    }

    public ContentPage selectOperation(Operation operation) {
        Utilities.logInfoMessage("Select operation: " + operation);
        operationDdl.selectFromDropDown(operation.getValue());
        return this;
    }

    public SelectInstancePage clickExecute() {
        execute.clickWithAjaxWait();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new SelectInstancePage(webDriver, aid);
    }

    public boolean isOperationDDLPresent() {
        return operationDdl.isVisible();
    }

    public List<String> getAvailableOperations() {
        return operationDdl.getValuesToSelect();
    }

    public boolean isOperationPresent(Operation operation) {
        return this.operationDdl.isValuePresentInDropDown(operation.getValue());
    }

    public void checkAnItem(String title) {
        searchByTitle(title);
        apply();
        TableCell cell = table.getRowByTextInColumn(title, CONTENT_TITLE).getCellByIndex(1);
        CheckBox checkBox = new CheckBox(cell, CHECK_ITEM_CHECKBOX_XPATH);
        checkBox.check();
    }

    public void checkItemWithoutSearch(String title, String type) {
        List<TableRow> rows = table.getRowsByTextInColumn(title, CONTENT_TITLE);
        for (TableRow tableRow : rows) {
            if (tableRow.getCellByColumnTitle("TYPE").getCellInnerText().contains(type)) {
                TableCell cell = tableRow.getCellByIndex(1);
                CheckBox checkBox = new CheckBox(cell, CHECK_ITEM_CHECKBOX_XPATH);
                checkBox.check();
            }
        }
    }

    public String clickEditLinkForRandomAsset() {
        int index = random.nextInt(table.getRows().size());
        String toReturn = table.getRows().get(index).getCellByColumnTitle(CONTENT_TITLE).getCellInnerText();
        table.getRows().get(index).getCellByColumnTitle(OPERATIONS).clickLinkByName("Edit");
        return toReturn;
    }

    public <T extends Page> T clickEditLink(Class<T> clazz, String assetTitle) {
        searchByTitle(assetTitle);
        apply();
        WaitUtils.perform(webDriver).waitForPageLoad();
        Utilities.logInfoMessage("Click on edit link for " + assetTitle);
        editLink.clickJS();
        return ReflectionUtils.getInstance(clazz, webDriver, aid);
    }

    public String getContentFullTitle(final String titlePart) {
        searchByTitle(titlePart);
        final boolean isPresent = apply();
        if (!isPresent) {
            throw new TestRuntimeException(String.format(CONTENT_TYPE_WITH_PARTIAL_TITLE_S_NOT_FOUND, titlePart));
        }
        return table.getCellTextByRowIndex(1, CONTENT_TITLE);
    }

    /**
     * Get list of content titles
     *
     * @return list of titles for the content
     */
    public List<String> getAllContentList() {
        List<String> assetsNames = new ArrayList<>();

        if (pager.isPagingPresent()) {
            do {
                assetsNames.addAll(loopTheContentOnTitle());
            } while (!pager.isLastPage());
        } else assetsNames.addAll(loopTheContentOnTitle());
        return assetsNames;
    }

    private List<String> loopTheContentOnTitle() {
        List<String> assetsNames = new ArrayList<>();
        List<TableRow> tableRows = table.getRows();
        for (TableRow row : tableRows) {
            WebElement element = row.getCellByColumnTitle(CONTENT_TITLE).geCellElement().findElement(By.xpath(".//a"));
            assetsNames.add(WebDriverUtil.getInstance(webDriver).getText(element));
        }
        return assetsNames;
    }

    public List<String> getPublishedElementsByCount(int count) {
        searchByPublishedState(PublishState.get("Yes"));
        apply();
        final List<TableRow> rows = table.getRows().subList(0, count);
        List<String> titlesNames = new LinkedList<>();
        for (TableRow row : rows) {
            String title = row.getCellByColumnTitle(CONTENT_TITLE).getCellInnerText();
            titlesNames.add(title);
        }
        return titlesNames;
    }

    public String getNodePublishState(String title) {
        searchByTitle(title);
        apply();
        return table.getRowByTextInColumn(title, CONTENT_TITLE).getCellByColumnTitle("STATUS").getCellInnerText();
    }

    public List<RokuVideoJson> getVideoObjects(List<String> items, String brand) {
        Utilities.logInfoMessage("Get List of Videos objects for published episodes for items count: " + items.size());
        List<RokuVideoJson> videos = new ArrayList<>();
        for (String videoName : items) {
            videos.add(getVideoObject(videoName, brand));
        }
        return videos;
    }

    public List<RokuVideoJson> getVideoObjectsById(List<String> items) {
        Utilities.logInfoMessage("Get List of Videos objects for published episodes for items count: " + items.size());
        List<RokuVideoJson> videos = new ArrayList<>();
        for (String videoID : items) {
            videos.add(getVideoObjectById(videoID));
        }
        return videos;
    }

    public RokuVideoJson getVideoObject(String videoName, String brand) {
        EditTVEVideoContentPage editVideoPage = openEditTVEVideoPage(videoName);
        RokuVideoJson videoJson = editVideoPage.getVideoObjectFromNodeMetadata();
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        videoJson.addImages(editVideoPage.onImagesTab().getAllImages());
        if (!videoJson.getShowName().isEmpty()) {
            videoJson.setShowId(backEndLayer.getShowGuid(videoJson.getShowName()));
        }
        if (videoJson.getShowId().isEmpty()) {
            videoJson.setShowId("false");
        }
        return videoJson;
    }

    public RokuVideoJson getVideoObjectById(String id) {
        EditTVEVideoContentPage editVideoPage = openEditOTTVideoPageById(id);
        RokuVideoJson videoJson = editVideoPage.getVideoObjectFromNodeMetadata();
        editVideoPage.clickCancel();
        return videoJson;
    }

    public List<RokuProgramJson> getProgramObjects(List<String> items) {
        Utilities.logInfoMessage("Get List of Programs objects for published episodes for items count: " + items.size());
        List<RokuProgramJson> programs = new ArrayList<>();
        for (String programName : items) {
            programs.add(getProgramObject(programName));
        }
        return programs;
    }

    public RokuProgramJson getProgramObject(String programName) {
        EditTVEProgramContentPage editProgramPage = openEditOTTProgramPage(programName);
        RokuProgramJson programJson = editProgramPage.getProgramObjectFromNodeMetadata();
        programJson.addImages(editProgramPage.onRokuImagesTab().getAllImages());
        editProgramPage.clickSave();
        return programJson;
    }

    public String getContentType(final String titlePart) {
        searchByTitle(titlePart);
        final boolean isPresent = apply();
        if (!isPresent) {
            Utilities.logSevereMessageThenFail(String.format(CONTENT_TYPE_WITH_TITLE_S_NOT_FOUND, titlePart));
            return "";
        }
        return table.getRowByIndex(1).getCellByColumnTitle("TYPE").getCellInnerText();
    }

    public String getMPXId(final String titlePart) {
        searchByTitle(titlePart);
        final boolean isPresent = apply();
        if (!isPresent) {
            Utilities.logSevereMessageThenFail(String.format(CONTENT_TYPE_WITH_TITLE_S_NOT_FOUND, titlePart));
            return "";
        }
        return table.getRowByIndex(1).getCellByColumnTitle("MPX Media ID").getCellInnerText();
    }

    public String getNodeId(final String titlePart) {
        searchByTitle(titlePart);
        final boolean isPresent = apply();
        if (!isPresent) {
            Utilities.logSevereMessageThenFail(String.format(CONTENT_TYPE_WITH_TITLE_S_NOT_FOUND, titlePart));
            return "";
        }
        return table.getRowByIndex(1).element().findElement(By.xpath(EDIT_LINK_XPATH)).getAttribute(HtmlAttributes.HREF.get()).split("\\D+")[1];
    }

    public String getEntitlement(String titlePart) {
        searchByTitle(titlePart);
        final boolean isPresent = apply();
        if (!isPresent) {
            Utilities.logSevereMessageThenFail(String.format(" Content type partial title '%s' not found.", titlePart));
            return "";
        }
        return table.getRowByIndex(1).getCellByColumnTitle("ENTITLEMENT").getCellInnerText();
    }

    public String getContentFormat(String titlePart) {
        searchByTitle(titlePart);
        final boolean isPresent = apply();
        if (!isPresent) {
            Utilities.logSevereMessageThenFail(String.format(" Content type partial title '%s' not found.", titlePart));
            return "";
        }
        return table.getRowByIndex(0).getCellByColumnTitle("FULL EPISODE").getCellInnerText();
    }

    public boolean validatePublishedState(final String contentTitle) {
        searchByTitle(contentTitle);
        final boolean isPresent = apply();
        if (!isPresent) {
            Utilities.logSevereMessageThenFail(String.format(CONTENT_TYPE_WITH_TITLE_S_NOT_FOUND, contentTitle));
            return false;
        }
        return getContentState(contentTitle).equalsIgnoreCase("Published");
    }

    public boolean validateNotPublishedState(final String contentTitle) {
        searchByTitle(contentTitle);
        final boolean isPresent = apply();
        if (!isPresent) {
            Utilities.logSevereMessageThenFail(String.format(CONTENT_TYPE_WITH_PARTIAL_TITLE_S_NOT_FOUND, contentTitle));
            return false;
        }
        return getContentState(contentTitle).equalsIgnoreCase("Not published");
    }


    public void clickDeleteButton(final String... title) {
        if (title.length > 0) {
            searchByTitle(title[0]);
            apply();
        }
        WaitUtils.perform(webDriver).waitForPageLoad();
        optionsLink.click();
        WaitUtils.perform(webDriver).waitElementVisible(DELETE_LINK);
        WaitUtils.perform(webDriver).waitElementVisible(deleteLink.element());
        deleteLink.click();
        confirmExecute.clickWithProgressBarWait();
    }

    public EditTVEVideoContentPage openEditTVEVideoPage(final String episodeTitle) {
        searchByTitle(episodeTitle);
        contentType.selectFromDropDown(ContentType.TVE_VIDEO.get());
        apply();
        if (isContentPresent()) {
            optionsLink.click();
            EditTVEVideoContentPage editTVEVideoContentPage = new EditTVEVideoContentPage(webDriver, aid);
            editTVEVideoContentPage.setNodeUpdatedDate(getNodeUpdatedDate(1));
            editTVEVideoContentPage.setPublishState(getNodePublishState(1));
            optionsLink.click();
            editLink.click();
            Utilities.logInfoMessage("Open edit for episode: " + episodeTitle);

            if (!webDriver.getTitle().contains(EditTVEVideoContentPage.PAGE_TITLE)) {
                Utilities.logSevereMessageThenFail(INVALID_PAGE_TITLE_ON + webDriver.getCurrentUrl());
            } else {
                Utilities.logInfoMessage("User currently on Edit Episode page to collect old data");
            }
            return editTVEVideoContentPage;
        } else {
            Utilities.logSevereMessageThenFail(String.format(CONTENT_TYPE_WITH_TITLE_S_NOT_FOUND, episodeTitle));
            return null;
        }
    }

    public EditTVEVideoContentPage openRandomEditOTTVideoPage() {
        searchByType(ContentType.TVE_VIDEO).apply();
        if (!table.isPresent()) {
            throw new TestRuntimeException("Content not found");
        }
        int index = random.nextInt(table.getNumberOfRows()) + 1;
        table.getRowByIndex(index).getCellByColumnTitle(OPERATIONS).clickLinkByName("Edit");
        return new EditTVEVideoContentPage(webDriver, aid);
    }

    public EditTVEProgramContentPage openRandomEditOTTProgramPage() {
        searchByType(ContentType.TVE_PROGRAM).apply();
        if (!table.isPresent()) {
            throw new TestRuntimeException("Content not found");
        }
        int index = random.nextInt(table.getNumberOfRows()) + 1;
        table.getRowByIndex(index).getCellByColumnTitle(OPERATIONS).clickLinkByName("Edit");
        return new EditTVEProgramContentPage(webDriver, aid);
    }

    public MediaContentPage openRandomEditAssetPage() {
        ContentType contentType = new Random().nextBoolean() ? ContentType.TVE_VIDEO : ContentType.TVE_PROGRAM;
        searchByType(contentType).apply();
        int index = random.nextInt(table.getNumberOfRows()) + 1;
        table.getRowByIndex(index).getCellByColumnTitle(OPERATIONS).clickLinkByName("Edit");
        return new MediaContentPage(webDriver, aid);
    }

    public MediaContentPage openEditAssetPage(String assetName) {
        searchByTitle(assetName).apply();
        int index = random.nextInt(table.getNumberOfRows());
        table.getRowByIndex(index).getCellByColumnTitle(OPERATIONS).clickLinkByName("Edit");
        return new MediaContentPage(webDriver, aid);
    }

    public EditTVEVideoContentPage openEditOTTVideoPageById(final String id) {
        mpxId.enterText(id);
        contentType.selectFromDropDown(ContentType.TVE_VIDEO.get());
        apply();
        if (isContentPresent()) {
            optionsLink.click();
            editLink.click();
            Utilities.logInfoMessage("Open edit for episode: " + id);

            if (!webDriver.getTitle().contains(EditTVEVideoContentPage.PAGE_TITLE)) {
                Utilities.logSevereMessageThenFail(INVALID_PAGE_TITLE_ON + webDriver.getCurrentUrl());
            } else {
                Utilities.logInfoMessage("User currently on Edit Episode page to collect old data");
            }
            return new EditTVEVideoContentPage(webDriver, aid);
        } else {
            Utilities.logSevereMessageThenFail(CONTENT_TYPE_WITH_TITLE_S_NOT_FOUND + id);
            return null;
        }
    }

    public EditTVEProgramContentPage openEditOTTProgramPageById(final String id) {
        mpxId.enterText(id);
        contentType.selectFromDropDown(ContentType.TVE_PROGRAM.get());
        apply();
        if (isContentPresent()) {
            optionsLink.click();
            editLink.click();
            Utilities.logInfoMessage("Open edit for program: " + id);

            if (!webDriver.getTitle().contains(EditTVEProgramContentPage.PAGE_TITLE)) {
                WebDriverUtil.getInstance(webDriver).attachScreenshot();
                Utilities.logSevereMessageThenFail(INVALID_PAGE_TITLE_ON + webDriver.getCurrentUrl());
            } else {
                Utilities.logInfoMessage("User currently on Edit Program page to collect old data");
            }
            return new EditTVEProgramContentPage(webDriver, aid);
        } else {
            Utilities.logWarningMessage(String.format(CONTENT_TYPE_WITH_TITLE_S_NOT_FOUND, id));
            return null;
        }
    }

    public EditTVEProgramContentPage openEditOTTProgramPage(final String episodeTitle) {
        searchByTitle(episodeTitle);
        contentType.selectFromDropDown(ContentType.TVE_PROGRAM.get());
        apply();
        if (isContentPresent()) {
            optionsLink.click();
            editLink.click();
            Utilities.logInfoMessage("Open edit for show: " + episodeTitle);
            WaitUtils.perform(webDriver).waitForPageLoad();
            return new EditTVEProgramContentPage(webDriver, aid);
        } else {
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logWarningMessage(String.format(CONTENT_TYPE_WITH_TITLE_S_NOT_FOUND, episodeTitle));
            return null;
        }
    }

    public EditTVEVideoContentPage openEditTVEVideoPage(final String episodeTitle, final String mpxid) {
        searchByTitle(episodeTitle).searchByType(ContentType.TVE_VIDEO);
        apply();
        if (isContentPresent()) {
            for (TableRow row : table.getRows()) {
                if (row.getCellByColumnTitle("MPX Media ID").getCellInnerText().equalsIgnoreCase(mpxid)) {
                    row.getCellByColumnTitle(OPERATIONS).clickLinkByName("Edit");
                    return new EditTVEVideoContentPage(webDriver, aid);
                }
            }
        }
        throw new TestRuntimeException(String.format(CONTENT_TYPE_WITH_TITLE_S_NOT_FOUND, episodeTitle));
    }

    public void executeDelete() {
        operationDdl.selectFromDropDown("Delete item");
        execute.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        confirmExecute.click();
    }

    public void executePublishToServices() {
        operationDdl.selectFromDropDown("Publish to Services");
        execute.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        confirmExecute.click();
        confirmExecute.clickWithProgressBarWait();
    }

    public void confirmExecute() {
        confirmExecute.clickWithProgressBarWait();
    }

    public MainRokuAdminPage clickOnFirstElement() {
        if (!table.getRows().isEmpty()) {
            table.getRowByIndex(1).getCellByColumnTitle(OPERATIONS).clickLinkByName("Edit");
            WaitUtils.perform(webDriver).waitForPageLoad();
        } else {
            Utilities.logSevereMessageThenFail("Content type not found.");
        }
        ContentType type = ContentType.TVE_VIDEO;
        if (getPageTitle().contains(ContentType.TVE_VIDEO.get())) {
            type = ContentType.TVE_VIDEO;
        }
        if (getPageTitle().contains(ContentType.TVE_PROGRAM.get())) {
            type = ContentType.TVE_PROGRAM;
        }
        switch (type) {
            case TVE_PROGRAM: {
                return new EditTVEProgramContentPage(webDriver, aid);
            }
            case TVE_VIDEO: {
                return new EditTVEVideoContentPage(webDriver, aid);
            }
            default:
                return new EditTVEVideoContentPage(webDriver, aid);
        }
    }

    public int getCountEntriesOfPage() {
        if (!table.isPresent()) {
            return 0;
        }
        return table.getRows().size();
    }

    public void openFirstPage() {
        if (WebDriverUtil.getInstance(webDriver).isElementPresent(BASE_PAGINATION)
                && (!webDriver.findElementByXPath(BASE_PAGINATION).findElements(By.xpath(FIRST_PAGE)).isEmpty())) {
            webDriver.click(webDriver.findElementByXPath(BASE_PAGINATION).findElement(By.xpath(FIRST_PAGE)));
        }
    }

    public int openLastPage() {
        if (WebDriverUtil.getInstance(webDriver).isElementPresent(BASE_PAGINATION)) {
            if (!webDriver.findElementsByXPath(LAST_PAGE).isEmpty()) {
                webDriver.findElementByXPath(LAST_PAGE).click();
            }
            return Integer.parseInt(webDriver.findElementByXPath(CURRENT_PAGE).getText());
        } else {
            return 1;
        }
    }

    public int getCountEntries() {
        this.openFirstPage();
        int countOfFirstPage = this.getCountEntriesOfPage();
        int countPages = this.openLastPage();
        int countOfLastPage = this.getCountEntriesOfPage();
        return countOfFirstPage * (countPages - 1) + countOfLastPage;
    }

    public boolean isContentOnlyOne() {
        return getCountEntries() == 1;
    }

    public Table getTable() {
        return table;
    }

    public String getRandomAsset(ContentType contentType, ContentFormat contentFormat) {
        searchByType(contentType).searchByPublishedState(PublishState.YES);
        apply();
        if (contentType.equals(ContentType.TVE_PROGRAM)) {
            if (getTable().getRows().isEmpty()) {
                Assert.fail("There is no any published " + contentType);
            }
            return getTable().getRandomRow().getCellByColumnTitle(CONTENT_TITLE).getCellInnerText();
        }
        List<String> assetsList = this.filterByContentFormat(contentFormat);
        if (assetsList.isEmpty()) {
            Assert.fail("There is no any published " + contentType + " with format " + contentFormat);
        }
        return assetsList.get(random.nextInt(assetsList.size()));
    }

    public String getRandomAsset(ContentType contentType) {
        searchByType(contentType).searchByPublishedState(PublishState.YES);
        apply();
        if (!table.isPresent() || getTable().getNumberOfRows() == 0) {
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessageThenFail("There are NO " + contentType + " on the site.");
            throw new SkipException("There are NO " + contentType + " on the site.");
        }
        String asset = getTable().getRandomRow().getCellByColumnTitle(CONTENT_TITLE).getCellInnerText();
        if (asset.contains("AQA")) {
            asset = getTable().getRandomRow().getCellByColumnTitle(CONTENT_TITLE).getCellInnerText();
        }
        return asset;
    }

    public List<String> getRandomAssets(int number, ContentType contentType, ContentFormat contentFormat) {
        List<String> assets = new ArrayList<>();
        searchByTitle("").searchByType(contentType).searchByPublishedState(PublishState.YES);
        apply();
        WaitUtils.perform(webDriver).waitForPageLoad();
        if (contentType.equals(ContentType.TVE_PROGRAM)) {
            for (int i = 1; i <= number; i++) {
                assets.add(getTable().getCellTextByRowIndex(i, 2));
            }
            return assets;
        }
        List<String> assetsList = this.filterByContentFormat(contentFormat);
        for (int i = 0; i < number; i++) {
            assets.add(assetsList.get(i));
        }
        return assets;
    }

    @Override
    public boolean isPageOpened() {
        return this.getPageTitle().contains(PAGE_TITLE);
    }

    public enum Operation {

        DELETE_ITEM("Delete item"), PUBLISH_TO_API("Publish to Services"), UPDATE_MPX_METADATA("Update MPX metadata");

        private String value;

        private Operation(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
