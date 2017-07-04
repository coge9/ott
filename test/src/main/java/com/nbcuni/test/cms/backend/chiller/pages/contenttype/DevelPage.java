package com.nbcuni.test.cms.backend.chiller.pages.contenttype;

import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.otherinfo.MediaGalleryMigrationData;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.otherinfo.PublishingOptions;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alekca on 12.05.2016.
 */
public class DevelPage extends MainRokuAdminPage {

    private static final String UUID_XPATH = ".//a[text()='uuid']/following-sibling::strong";
    private static final String FILE_NAME_XPATH = ".//a[text()='filename']/following-sibling::strong";
    private static final String VID_XPATH = ".//a[text()='vid']/following-sibling::strong";
    private static final String NEIGHBOR_OF_PARENT_XPATH = ".//parent::div/following-sibling::div";
    private static final String IMAGE_SOURCE_XPATH = ".//a[text()='%s']";

    @FindBy(xpath = ".//a[text()='uid']/following-sibling::strong")
    private Label uid;

    @FindBy(xpath = ".//a[text()='nid']/following-sibling::strong")
    private Label nid;

    @FindBy(xpath = ".//a[text()='title']/following-sibling::strong")
    private Label title;

    @FindBy(xpath = ".//a[text()='status']/following-sibling::strong")
    private Label status;

    @FindBy(xpath = ".//a[text()='comment']/following-sibling::strong")
    private Label comment;

    @FindBy(xpath = ".//a[text()='promote']/following-sibling::strong")
    private Label promote;

    @FindBy(xpath = ".//a[text()='sticky']/following-sibling::strong")
    private Label sticky;

    @FindBy(xpath = ".//a[text()='language']/following-sibling::strong")
    private Label language;

    @FindBy(xpath = ".//a[text()='created']/following-sibling::strong")
    private Label created;

    @FindBy(xpath = ".//a[text()='changed']/following-sibling::strong")
    private Label changed;

    @FindBy(xpath = ".//a[text()='translate']/following-sibling::strong")
    private Label translate;

    @FindBy(xpath = ".//a[text()='vid']/following-sibling::strong")
    private Label vid;

    @FindBy(xpath = ".//a[text()='uuid']/following-sibling::strong")
    private Label uuid;

    @FindBy(xpath = ".//a[text()='field_tags']")
    private Link tags;

    @FindBy(xpath = ".//a[text()='field_media']")
    private Link fieldMedia;

    @FindBy(xpath = ".//a[text()='field_cover_media']")
    private Link fieldCoverMedia;

    @FindBy(xpath = ".//a[text()='field_ios_1600x900']")
    private Link fieldProgramIos1600;

    @FindBy(xpath = ".//a[text()='field_ios_1965x1108']")
    private Link fieldProgramIos1965;

    @FindBy(xpath = ".//a[text()='field_ios_original_image']")
    private Link fieldPVideoIos;

    @FindBy(xpath = ".//a[text()='value' or text()='tid']/following-sibling::strong")
    private Label value;

    @FindBy(xpath = ".//*[@id='block-system-main' or @id='content']//ul[contains(@class,'krumo-first')]/li[1]")
    private Link section;

    @FindBy(xpath = ".//a[text()='published']//following-sibling::strong")
    private Link published;

    @FindBy(xpath = ".//a[text()='field_media']//parent::div//following-sibling::div/ul/li/div[2]/ul/li")
    private List<WebElement> mediaImages;

    public DevelPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
        section.click();
    }

    public boolean uuidIsPresent() {
        return uuid.isPresent() && (!uuid.getText().isEmpty());
    }

    public String getUuid() {
        return uuid.getText();
    }

    public String getVid() {
        return vid.getText();
    }

    public String getPublished() {
        return published.getText();
    }

    public String getChangedDate() {
        return changed.getText();
    }

    public List<String> getTags() {
        tags.click();
        return Arrays.asList(getWrappedValue((Link) tags.element(), 0));
    }

    public MediaGalleryMigrationData getData() {
        MediaGalleryMigrationData mediaGalleryMigrationData = new MediaGalleryMigrationData();
        PublishingOptions options = new PublishingOptions();
        options.setPublished(Boolean.valueOf(status.getText()));
        options.setSticky(Boolean.valueOf(sticky.getText()));
        options.setPromoted(Boolean.valueOf(promote.getText()));
        mediaGalleryMigrationData.setPublishingOptions(options);
        mediaGalleryMigrationData.setTitle(title.getText());
        mediaGalleryMigrationData.setLanguage(language.getText());
        mediaGalleryMigrationData.setTranslate(translate.getText());
        mediaGalleryMigrationData.setChanged(changed.getText());
        mediaGalleryMigrationData.setComment(comment.getText());
        mediaGalleryMigrationData.setCreated(created.getText());
        mediaGalleryMigrationData.setUid(uid.getText());
        mediaGalleryMigrationData.setVid(vid.getText());
        return mediaGalleryMigrationData;
    }

    public String getWrappedValue(Link link, int index) {
        collapseInnerLinks(link, index);
        return value.getText();
    }

    public List<MediaImage> getMediaUuids(List<MediaImage> mediaImages) {
        fieldMedia.click();
        for (int i = 0; i < mediaImages.size(); i++) {
            mediaImages.get(i).setUuid(getWrappedUuid(fieldMedia, i));
        }
        return mediaImages;
    }

    public Map<String, String> getMediaUuids() {
        fieldMedia.click();
        Map<String, String> uuids = new LinkedHashMap<String, String>();
        for (WebElement mediaImage : mediaImages) {
            WebElement nameField = mediaImage.findElement(By.xpath(FILE_NAME_XPATH));
            String name = WebDriverUtil.getInstance(webDriver).getText(nameField);
            WebElement uuidField = mediaImage.findElement(By.xpath(UUID_XPATH));
            String tempUuid = WebDriverUtil.getInstance(webDriver).getText(uuidField);
            uuids.put(name, tempUuid);
        }
        return uuids;
    }

    public List<MediaImage> getMediaUuidsByMachineName(List<MediaImage> mediaImages) {

        for (int i = 0; i < mediaImages.size(); i++) {
            Link fieldMediaLink = new Link(webDriver.findElementByXPath(String.format(IMAGE_SOURCE_XPATH,
                    mediaImages.get(i).getName())));
            fieldMediaLink.click();
            try {
                mediaImages.get(i).setUuid(getWrappedUuid(fieldMediaLink, 0));
            } catch (NoSuchElementException e) {
                Utilities.logSevereMessageThenFail("The wrapped element could not be find for particular media: " + Utilities.convertStackTraceToString(e));
            }
        }
        return mediaImages;
    }

    public String getMediaUuidByMachineName(String mashineName) {
        Utilities.logInfoMessage("Getting uuid for " + mashineName);
        Link fieldMediaLink = new Link(webDriver.findElementByXPath(String.format(IMAGE_SOURCE_XPATH, mashineName)));
        fieldMediaLink.click();
        return getWrappedUuid(fieldMediaLink, 0);
    }

    public String getCoverMediaUuid() {
        WebDriverUtil.getInstance(webDriver).scrollPageDown();
        fieldCoverMedia.click();
        return getWrappedUuid(fieldCoverMedia, 0);
    }

    private String getWrappedUuid(Link link, int index) {
        WebElement element = collapseInnerLinks(link, index);
        return element.findElement(By.xpath(UUID_XPATH)).getText();
    }

    private WebElement collapseInnerLinks(Link link, int index) {
        if (index <= 0) {
            WebElement und = link.element().findElement(By.xpath(NEIGHBOR_OF_PARENT_XPATH + "//a[text()='und']"));
            WaitUtils.perform(webDriver).waitElementVisible(und);
            und.click();
        }
        WebDriverUtil.getInstance(webDriver).scrollPageDown();
        WebElement zero = link.element().findElement(By.xpath(NEIGHBOR_OF_PARENT_XPATH + "//a[text()='" + index + "']"));
        WaitUtils.perform(webDriver).waitElementVisible(zero);
        zero.click();
        return zero.findElement(By.xpath(NEIGHBOR_OF_PARENT_XPATH));
    }

    public FilesMetadata getDataForImage(FilesMetadata filesMetadata) {
        filesMetadata.getImageGeneralInfo().setUuid(getUuid());
        filesMetadata.getImageGeneralInfo().setRevision(Integer.parseInt(getVid()));
        openEditPage();
        return filesMetadata;
    }

    public FilesMetadata getDataForConcertoImage(FilesMetadata filesMetadata) {
        try {
            String machineName = filesMetadata.getImageGeneralInfo().getImageSourceType().getMachineName();
            Link internalLink = new Link(webDriver.findElementByXPath(String.format(IMAGE_SOURCE_XPATH, machineName)));
            internalLink.click();
            WebElement element = collapseInnerLinks(internalLink, 0);
            filesMetadata.getImageGeneralInfo().setUuid(element.findElement(By.xpath(UUID_XPATH)).getText());
            filesMetadata.getImageGeneralInfo().setRevision(Integer.parseInt(element.findElement(By.xpath(VID_XPATH)).getText()));
            return filesMetadata;
        } catch (Exception e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
        throw new TestRuntimeException("The Image Source Type of given files metadata" + filesMetadata + " does not matched any of the iOS Program sources");
    }
}
