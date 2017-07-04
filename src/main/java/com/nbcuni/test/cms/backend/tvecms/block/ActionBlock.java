package com.nbcuni.test.cms.backend.tvecms.block;

import com.nbcuni.test.cms.backend.chiller.block.Publish;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.pageobjectutils.chiller.ActionButtons;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by Alena_Aukhukova on 5/5/2016.
 */
public class ActionBlock extends AbstractContainer {

    @FindBy(xpath = "//*[@id='edit-submit']|//*[@id='edit-save']")
    private Button saveButton;

    @FindBy(id = "edit-publish")
    private Publish publish;

    @FindBy(id = "edit-unpublish")
    private Publish unpublish;

    @FindBy(xpath = "//*[@id='edit-cancel']|//a[text()='Cancel']")
    private Button cancelButton;

    @FindBy(id = "edit-delete")
    private Button deleteButton;

    public void save() {
        saveButton.click();
        waitFor().waitForPageLoad();
    }

    public Publish getPublish() {
        return publish;
    }

    public void publish() {
        publish.click();
        waitFor().waitForPageLoad();
    }

    public void unPublish() {
        unpublish.click();
        waitFor().waitForPageLoad();
    }

    public void publish(String instanceName) {
        publish.click(instanceName);
        waitFor().waitForPageLoad();
    }

    public boolean clickPublishAndCheckConfirmation(String expectedName, List<String> expectedNames) {
        return publish.clickPublishAndCheckConfirmation(expectedName, expectedNames);
    }

    public void cancel() {
        cancelButton.click();
        waitFor().waitForPageLoad();
    }

    public void delete() {
        deleteButton.click();
        waitFor().waitForPageLoad();
    }

    public boolean isActionButtonsPresent(List<ActionButtons> actionButtons, boolean isPresent) {
        SoftAssert softAssert = new SoftAssert();
        for (ActionButtons button : actionButtons) {
            if (isPresent) {
                buttonPresentLogic(softAssert, button);
            } else {
                buttonNotPresentLogic(softAssert, button);
            }
        }
        return softAssert.getTempStatus();
    }

    private void buttonNotPresentLogic(SoftAssert softAssert, ActionButtons button) {
        switch (button) {
            case PUBLISH:
            case SAVE_AND_PUBLISH:
                softAssert.assertFalse(publish.isVisible(), "The Publish button is Visible", "Publish button is not visible");
                break;
            case CANCEL:
                softAssert.assertFalse(cancelButton.isVisible(), "The Cancel button is Visible", "Cancel button is not visible");
                break;
            case DELETE:
                softAssert.assertFalse(deleteButton.isVisible(), "The Delete button is Visible", "Delete button is not visible");
                break;
            case SAVE_AS_DRAFT:
                softAssert.assertFalse(saveButton.isVisible(), "The Save As Draft button is Visible", "Save As Draft button is not visible");
                break;
            default:
                break;
        }
    }

    private void buttonPresentLogic(SoftAssert softAssert, ActionButtons button) {
        switch (button) {
            case PUBLISH:
            case SAVE_AND_PUBLISH:
                softAssert.assertTrue(publish.isVisible(), "The Publish button is not Visible", "Publish button is visible");
                break;
            case CANCEL:
                softAssert.assertTrue(cancelButton.isVisible(), "The Cancel button is not Visible", "Cancel button is visible");
                break;
            case DELETE:
                softAssert.assertTrue(deleteButton.isVisible(), "The Delete button is not Visible", "Delete button is visible");
                break;
            case SAVE_AS_DRAFT:
                softAssert.assertTrue(saveButton.isVisible(), "The Save As Draft button is not Visible", "Save As Draft button is visible");
                break;
            default:
                break;
        }
    }

    public void cancelPublishing() {
        publish.cancelPublishing();
    }

}
