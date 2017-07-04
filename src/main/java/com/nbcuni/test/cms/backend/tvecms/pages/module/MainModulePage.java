package com.nbcuni.test.cms.backend.tvecms.pages.module;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.ManageRevisionsTab;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.ManageScheduleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.ViewPublishTab;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;

/**
 * Created by Aleksandra_Lishaeva on 12/18/15.
 */
public class MainModulePage extends MainRokuAdminPage {

    private Button viewPublished = new Button(webDriver, ".//*[@id='branding']//a[contains(text(),'View published')]");
    private Button editDraft = new Button(webDriver, ".//*[@id='branding']//a[contains(text(),'Edit draft')]");
    private Button manageRevisions = new Button(webDriver, ".//*[@id='branding']//a[contains(text(),'Manage revisions')]");
    private Button manageSchedule = new Button(webDriver, ".//*[@id='page']//a[contains(text(),'Manage schedule')]");
    public MainModulePage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public boolean isViewPublishedVisible() {
        Utilities.logInfoMessage("Checking rather 'View Published' button is Visible and Enabled");
        return viewPublished.isVisible() && viewPublished.isEnable();
    }

    public boolean isEditDraftVisible() {
        Utilities.logInfoMessage("Checking rather 'Edit Draft' button is Visible and Enabled");
        return editDraft.isVisible() && editDraft.isEnable();
    }

    public boolean isManageRevisionVisible() {
        Utilities.logInfoMessage("Checking rather 'Manage Revision' button is Visible and Enabled");
        return manageRevisions.isVisible() && manageRevisions.isEnable();
    }

    public boolean isManageScheduleVisible() {
        Utilities.logInfoMessage("Checking rather 'Manage Schedule' button is Visible and Enabled");
        return manageSchedule.isVisible() && manageSchedule.isEnable();
    }

    public ViewPublishTab clickViewPublishTab() {
        Utilities.logInfoMessage("Open 'View published' tab");
        viewPublished.click();
        return new ViewPublishTab(webDriver, aid);
    }

    public DraftModuleTab clickEditDraftTab() {
        Utilities.logInfoMessage("Open 'Edit draft' tab");
        editDraft.click();
        return new DraftModuleTab(webDriver, aid);
    }

    public ManageRevisionsTab clickManageRevisionTab() {
        Utilities.logInfoMessage("Open 'Manage Schedule' tab");
        manageRevisions.click();
        return new ManageRevisionsTab(webDriver, aid);
    }

    public ManageScheduleTab clickManageScheduleTab() {
        Utilities.logInfoMessage("Open 'Manage Revision' tab");
        manageSchedule.click();
        return new ManageScheduleTab(webDriver, aid);
    }

}
