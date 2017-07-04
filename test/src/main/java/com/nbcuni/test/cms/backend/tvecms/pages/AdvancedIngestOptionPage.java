package com.nbcuni.test.cms.backend.tvecms.pages;

import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.AdvancedIngestionOptions;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aleksandra_Lishaeva on 11/30/15.
 */
public class AdvancedIngestOptionPage extends MainRokuAdminPage {

    @FindBy(id = "edit-ott-mpx-exclude-criteria-ott-mpx-serversideadstitched-criteria-callback")
    private CheckBox serverSideAdStitchedOption;

    @FindBy(id = "edit-ott-mpx-exclude-criteria-ott-mpx-source-module-criteria-callback")
    private CheckBox programShelfOption;

    @FindBy(id = "edit-ott-mpx-exclude-criteria-ott-mpx-full-episodic-video-callback")
    private CheckBox fullEpisodicTveVideos;

    @FindBy(id = "edit-ott-mpx-skip-child-video-update")
    private CheckBox skipChildVideos;

    @FindBy(id = "edit-ott-mpx-force-images-update")
    private CheckBox forceImagesUpdate;

    @FindBy(id = "edit-ott-mpx-ingest-look-ahead-videos")
    private CheckBox ingestLookAheadVideos;

    @FindBy(xpath = "//select[@id='edit-ott-mpx-published-programs-source']")
    private DropDownList ottModuleDDL;

    @FindBy(xpath = "//*[@id='edit-submit']")
    private Button save;

    @FindBy(xpath = "//*[@id='edit-apply-ingest-options']")
    private Button apply;


    public AdvancedIngestOptionPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    /**
     * @param shelfName - Created Shelf name to select from Drop down list
     */
    public void selectShelf(String shelfName) {
        Utilities.logInfoMessage("Select from Drop-Down shelf: " + shelfName);
        ottModuleDDL.selectFromDropDown(shelfName);
    }

    /**
     * Method to set option due the reason rather Videos, from each field 'pl1$serverSideAdStitched' = false
     * or missed from MPX metadata, should be unpublished by Applying the rule.
     *
     * @param isChecked - get true or false in case rather option should be checked
     */
    public void checkServerSideAdStitchedOption(boolean isChecked) {
        Utilities.logInfoMessage("Set Server Side Ad Stiched Video Option to: " + isChecked);
        if (isChecked) {
            serverSideAdStitchedOption.check();
        } else {
            serverSideAdStitchedOption.uncheck();
        }
    }

    /**
     * Method require to be selected a shelf with program before.
     * By checking related options and applying rules.
     * Only Programs within the selected shelf and related Published Episodes will stay Published.
     *
     * @param isChecked -get true or false in case rather option should be checked
     */
    public void checkSelectedShelveRule(boolean isChecked) {
        Utilities.logInfoMessage("Set ' Programs (and referenced videos) that are NOT in the selected OTT Module' Option to: " + isChecked);
        if (isChecked) {
            programShelfOption.check();
        } else {
            programShelfOption.uncheck();
        }
    }


    public void setStatusForOption(AdvancedIngestionOptions option, boolean status) {
        switch (option) {
            case SERVER_SIDE_AD_STITCHED:
                serverSideAdStitchedOption.selectStatus(status);
                break;
            case PROGRAM_NOT_IN_MODULE:
                programShelfOption.selectStatus(status);
                break;
            case FULL_EPISODIC_VIDEOS:
                fullEpisodicTveVideos.selectStatus(status);
                break;
            case SKIP_CHILD_VIDEOS:
                skipChildVideos.selectStatus(status);
                break;
            case FORCE_IMAGES_UPDATE:
                forceImagesUpdate.selectStatus(status);
                break;
            case INGEST_FOR_LOOK_AHEAD:
                ingestLookAheadVideos.selectStatus(status);
                break;
            default:
                break;
        }
    }

    public Boolean getStatusForOption(AdvancedIngestionOptions option) {
        switch (option) {
            case SERVER_SIDE_AD_STITCHED:
                return serverSideAdStitchedOption.isSelected();
            case PROGRAM_NOT_IN_MODULE:
                return programShelfOption.isSelected();
            case FULL_EPISODIC_VIDEOS:
                return fullEpisodicTveVideos.isSelected();
            case SKIP_CHILD_VIDEOS:
                return skipChildVideos.isSelected();
            case FORCE_IMAGES_UPDATE:
                return forceImagesUpdate.isSelected();
            case INGEST_FOR_LOOK_AHEAD:
                return ingestLookAheadVideos.isSelected();
            default:
                throw new TestRuntimeException("Unable to get status for option " + option);
        }
    }

    public Map<AdvancedIngestionOptions, Boolean> getStatusForAllOptions() {
        Map<AdvancedIngestionOptions, Boolean> toReturn = new HashMap<AdvancedIngestionOptions, Boolean>();
        for (AdvancedIngestionOptions option : AdvancedIngestionOptions.values()) {
            toReturn.put(option, getStatusForOption(option));
        }
        return toReturn;
    }

    public void saveSettings() {
        save.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public void applyRules() {
        apply.click();
        WaitUtils.perform(webDriver).waitForProgressNotPresent(99999999);
    }


}

