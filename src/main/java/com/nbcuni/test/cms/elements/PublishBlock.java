package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.pageobjectutils.tvecms.PublishInstance;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Alena_Aukhukova on 10/6/2015.
 */
public class PublishBlock extends AbstractElement {
    private static final String TABS_XPATH = ".//ul[@class='vertical-tabs-list']";
    private static final String PUBLISH_BLOCK_XPATH = ".//label[contains(text(),'Publishing')]/../following-sibling::div";
    private String buttonPublishXpath = ".//input[contains(@id,'edit-publish') and contains(@value,'%s')]";
    private String buttonRevertXpath = ".//input[contains(@id,'edit-revert') and contains(@value,'%s')]";
    private String uniButtonPublishXpath = ".//input[@id='edit-publish']";
    private String uniButtonRevertXpath = ".//input[@id='edit-revert']";
    private TabsGroup tabGroup;
    private Button publishButton;
    private Button revertButton;
    private WebElement block;

    public PublishBlock(CustomWebDriver webDriver) {
        super(webDriver, PUBLISH_BLOCK_XPATH);
        tabGroup = new TabsGroup(driver, TABS_XPATH);
    }

    public PublishBlock(WebElement webElement) {
        super(webElement);
    }

    public void publishByTabName() {
        String instance = Config.getInstance().getRokuApiInstance();
        goToPublishTab(PublishInstance.getInstanceByName(instance));
        publishButton.click();
        WaitUtils.perform(driver).waitForPageLoad();
        Utilities.logInfoMessage("Click by Publish button");
    }

    public void publishToCustomInstance(String instance) {
        Utilities.logInfoMessage("Publish to custom instance: " + instance);
        goToPublishTab(instance);
        publishButton.click();
        Utilities.logInfoMessage("Click by Publish button");
    }

    public Boolean isPublishButtonEnabled() {
        String instance = Config.getInstance().getRokuApiInstance().toUpperCase();
        goToPublishTab(PublishInstance.valueOf(instance));
        return publishButton.isEnable();
    }

    public void publishByTabName(PublishInstance publishInstance) {
        goToPublishTab(publishInstance);
        if (this.isPublishEnable(publishInstance)) {
            publishButton.click();
        }
        Utilities.logInfoMessage("Click by Publish button");
    }

    public void revertByTabName(PublishInstance publishInstance) {
        goToPublishTab(publishInstance);
        revertButton.click();
        WaitUtils.perform(driver).waitForPageLoad();
        Utilities.logInfoMessage("Click by Revert button");
    }

    public void revertByTabName() {
        String instance = Config.getInstance().getRokuApiInstance().toUpperCase();
        goToPublishTab(PublishInstance.valueOf(instance));
        revertButton.click();
        WaitUtils.perform(driver).waitForPageLoad();
        Utilities.logInfoMessage("Click by Revert button");
    }

    private void goToPublishTab(PublishInstance publishInstance) {
        String tabName = publishInstance.getName();
        tabGroup.openTabByName(tabName);
        initBlockElements(tabName);
        Utilities.logInfoMessage("Go to OTT Publishing '" + tabName + "' Tab");
    }

    public boolean isPublishTabPresent(PublishInstance publishInstance) {
        String tabName = publishInstance.getName();
        Utilities.logInfoMessage("Check rather Publishing '" + tabName + "' Tab is present");
        return tabGroup.isTabPresent(tabName);
    }

    private void goToPublishTab(String instance) {
        tabGroup.openTabByName(instance);
        initBlockElements(instance);
        Utilities.logInfoMessage("Go to OTT Publishing '" + instance + "' Tab");
    }

    private void initBlockElements(String publishInstance) {
        String publishXpath = String.format(buttonPublishXpath, publishInstance);
        String revertXpath = String.format(buttonRevertXpath, publishInstance);

        if (!element().findElements(By.xpath(publishXpath)).isEmpty()) {
            publishButton = new Button(driver, element().findElement(By.xpath(publishXpath)));
        } else if (!element().findElements(By.xpath(uniButtonPublishXpath)).isEmpty()) {
            publishButton = new Button(driver, element().findElement(By.xpath(uniButtonPublishXpath)));
        }

        if (!element().findElements(By.xpath(revertXpath)).isEmpty()) {
            revertButton = new Button(driver, element().findElement(By.xpath(revertXpath)));
        } else if (!element().findElements(By.xpath(uniButtonRevertXpath)).isEmpty()) {
            revertButton = new Button(driver, element().findElement(By.xpath(uniButtonRevertXpath)));
        }
    }

    public boolean isPublishEnable() {
        String instance = Config.getInstance().getRokuApiInstance();
        goToPublishTab(PublishInstance.getInstanceByName(instance));
        return publishButton.isEnable();
    }

    public boolean isPublishEnable(PublishInstance publishInstance) {
        goToPublishTab(publishInstance);
        return publishButton.isEnable();
    }

    public boolean isPublishEnable(String publishInstance) {
        goToPublishTab(publishInstance);
        return publishButton.isEnable();
    }

    public boolean isRevertEnable() {
        String instance = Config.getInstance().getRokuApiInstance();
        goToPublishTab(PublishInstance.getInstanceByName(instance));
        return revertButton.isEnable();
    }

    public boolean isRevertEnable(PublishInstance publishInstance) {
        goToPublishTab(publishInstance);
        return revertButton.isEnable();
    }

    public boolean isRevertEnable(String publishInstance) {
        goToPublishTab(publishInstance);
        return revertButton.isEnable();
    }

    public boolean isSaveAndPublishEnable(PublishInstance publishInstance) {
        goToPublishTab(publishInstance);
        return publishButton.isEnable();
    }

    public boolean isSaveAndPublishEnable() {
        String instance = Config.getInstance().getRokuApiInstance();
        goToPublishTab(PublishInstance.getInstanceByName(instance));
        return publishButton.isEnable();
    }
}
