package com.nbcuni.test.cms.backend.chiller.pages.content;

import com.nbcuni.test.cms.backend.tvecms.pages.ConfirmationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.CheckBoxGroup;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.pageobjectutils.tvecms.PublishInstance;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Aleksandra_Lishaeva on 2/15/16.
 */
public class SelectInstancePage extends ContentPage {

    @FindBy(id = "edit-instances")
    private CheckBoxGroup instances;

    @FindBy(id = "edit-submit")
    private Button next;

    @FindBy(xpath = ".//*[@id='edit-actions']/a[text()='Cancel']")
    private Link cancel;

    public SelectInstancePage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    private void selectInstance(String instance) {
        instances.selectByLabel(instance, true);
    }

    //Method select all valid instances at the bulk operation page
    private void selectAllValid() {
        instances.checkAll();
        instances.uncheckContains("aqa");
    }

    public ContentPage publishToInstance(String instance) {
        selectInstance(instance);
        next.clickWithAjaxWait();
        new ConfirmationPage(webDriver, aid).clickSubmit();
        WaitUtils.perform(webDriver).waitForProgressNotPresent(99999999);
        return new ContentPage(webDriver, aid);
    }

    /**
     * Perform publishing to all instances by bulk operation
     * @return - object of Content page by the end of publishing
     */
    public ContentPage publishToAllInstances() {
        selectAllValid();
        next.clickWithAjaxWait();
        new ConfirmationPage(webDriver, aid).clickSubmit();
        WaitUtils.perform(webDriver).waitForProgressNotPresent(99999999);
        return new ContentPage(webDriver, aid);
    }

    public List<String> getConcertoInstanceName() {
        return instances.getLabelNames().stream().
                filter(item -> StringUtils.containsIgnoreCase(item, PublishInstance.CONCERTO.getName())).
                collect(Collectors.toList());
    }
}
