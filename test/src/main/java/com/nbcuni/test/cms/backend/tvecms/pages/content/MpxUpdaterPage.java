package com.nbcuni.test.cms.backend.tvecms.pages.content;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.ExpectedConditionAdapter;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.nbcuni.test.cms.utils.RegexUtil.getFirstSubstringByRegex;

public class MpxUpdaterPage extends MainRokuAdminPage {
    public static final String PAGE_TITLE = "MPX Updater | ";
    private static final String ID_INPUT_FIELD = ".//*[contains(@id,'mpx-id')]";
    private static final String SINGLE_ASSET_UPDATE_BUTTON = ".//*[contains(@id,'update-single')]";

    private static final String ALL_ASSETS_UPDATE_BUTTON = "//*[contains(@id,'update-all')][not (contains(@id,'all-'))]";
    private static final String PUBLISHED_ASSETS_UPDATE_BUTTON = "//*[contains(@id,'update-all-published')]";
    private static final String UNPUBLISHED_ASSETS_UPDATE_BUTTON = "//*[contains(@id,'update-all-unpublished')]";
    private static final String ALL_DIGIT_REGEX = "\\d+";

    private Button allAssetsUpdate = new Button(webDriver, ALL_ASSETS_UPDATE_BUTTON);
    private Button publishedAssetsUpdate = new Button(webDriver, PUBLISHED_ASSETS_UPDATE_BUTTON);
    private Button unpublishedAssetsUpdate = new Button(webDriver, UNPUBLISHED_ASSETS_UPDATE_BUTTON);
    private Button updateAssetByMpxId = new Button(webDriver, SINGLE_ASSET_UPDATE_BUTTON);

    private TextField idInput = new TextField(webDriver, ID_INPUT_FIELD);


    public MpxUpdaterPage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
    }

    public void updateAsset(String id) {
        setAssetIdIntoField(id);
        clickUpdateAssetByMpxId();
    }

    public void setAssetIdIntoField(String id) {
        Utilities.logInfoMessage("Use MPX Updater page");
        (new WebDriverWait(webDriver, 5)).until(new ExpectedConditionAdapter(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(ID_INPUT_FIELD))));
        webDriver.type(ID_INPUT_FIELD, id);
        Utilities.logInfoMessage("MPX ID " + id + " is entered");
    }

    public boolean isAssetChosen(String fullIdNumber) {
        return idInput.getValue().contains(fullIdNumber);
    }

    public void clickUpdateAssetByMpxId() {
        updateAssetByMpxId.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        WaitUtils.perform(webDriver).waitForProgressNotPresent(300);
        Utilities.logInfoMessage("Click on Fetch button");
    }

    public void clickAllAssetsUpdate() {
        allAssetsUpdate.click();
        Utilities.logInfoMessage("Click on All Assets Update button");
        Utilities.logInfoMessage("Start Update process...");
        WaitUtils.perform(webDriver).waitForProgressNotPresent(600);
    }

    public void clickUnpublishedAssetsUpdate() {
        unpublishedAssetsUpdate.click();
        Utilities.logInfoMessage("Click on Unpublished Assets Update button");
        Utilities.logInfoMessage("Start Update process...");
        WaitUtils.perform(webDriver).waitForProgressNotPresent(600);
    }

    public void clickPublishedAssetsUpdate() {
        publishedAssetsUpdate.click();
        Utilities.logInfoMessage("Click on Published Assets Update button");
        Utilities.logInfoMessage("Start Update process...");
        WaitUtils.perform(webDriver).waitForProgressNotPresent(600);
    }

    public void runUpdaterByMPXID(String id) {
        setAssetIdIntoField(id);
        clickUpdateAssetByMpxId();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public int getAllAssetsCount() {
        return Integer.valueOf(getFirstSubstringByRegex(allAssetsUpdate.getValue(), ALL_DIGIT_REGEX));
    }

    public int getPublishedAssetsCount() {
        return Integer.valueOf(getFirstSubstringByRegex(publishedAssetsUpdate.getValue(), ALL_DIGIT_REGEX));
    }

    public int getUnpublishedAssetsCount() {
        return Integer.valueOf(getFirstSubstringByRegex(unpublishedAssetsUpdate.getValue(), ALL_DIGIT_REGEX));
    }

    public boolean isAllAssetsCountEqualsSumPublishedAndUnpublishedAssets() {
        int allCount = getAllAssetsCount();
        int publishedCount = getPublishedAssetsCount();
        int unpublishedCount = getUnpublishedAssetsCount();
        return allCount == (publishedCount + unpublishedCount);
    }

}
