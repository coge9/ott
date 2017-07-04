package com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.interfaces.pages.EditVideoPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MediaContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Promotional;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.Video;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.elements.PublishBlock;
import com.nbcuni.test.cms.elements.VideoContentImage;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.ContentTabs;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.logging.LoggerWriter;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.*;

public class EditTVEVideoContentPage extends MediaContentPage implements EditVideoPage {

    public static final String PAGE_TITLE = "Edit TVE Video";
    private static final String CONTENT_PAGE_TITLE_FIELD_XPATH = ".//*[@id='edit-metatags-und-title-value']";
    private static final String CONTENT_DESCRIPTION_FIELD_XPATH = ".//*[@id='edit-metatags-und-description-value']";
    private static final String CONTENT_KEYWORDS_FIELD_XPATH = ".//*[@id='edit-metatags-und-keywords-value']";
    public static final String WRONG_PAGE_TITLE_PATTERN_ON_EPISODE_ACTUAL = "Wrong page title pattern on episode. actual ";
    public static final String EXPECTED = " expected: ";
    private PublishBlock publishBlock;
    @FindBy(xpath = "//*[@id='ott-video-node-form']/div/div[2]/ul/li[2]/a")
    private VideoAllImagesTab allImagesTab;


    public EditTVEVideoContentPage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
        publishBlock = new PublishBlock(webDriver);
    }

    @Override
    public PublishBlock elementPublishBlock() {
        return publishBlock;
    }

    public VideoAllImagesTab onImagesTab() {
        tabsGroup.openTabByName(ContentTabs.ALL_IMAGES.getName());
        return allImagesTab;
    }


    public Map<String, String> collectMPXData() {
        final Map<String, String> episodeData = new HashMap<String, String>();
        episodeData.put("Title", this.getTitle());
        this.onMPXInfoTab();
        episodeData.putAll(mpxInfoTab.collectMetaData());
        return episodeData;
    }

    public String getPageTitlePattern() {
        WaitUtils.perform(webDriver).waitElementVisible(
                CONTENT_PAGE_TITLE_FIELD_XPATH);
        return webDriver.getAttribute(CONTENT_PAGE_TITLE_FIELD_XPATH, "value");
    }

    public String getDescription() {
        WaitUtils.perform(webDriver).waitElementPresent(
                CONTENT_DESCRIPTION_FIELD_XPATH);
        return webDriver.getValue(CONTENT_DESCRIPTION_FIELD_XPATH);
    }

    public String getKeyWords() {
        WaitUtils.perform(webDriver).waitElementVisible(CONTENT_KEYWORDS_FIELD_XPATH);
        return webDriver.getAttribute(CONTENT_KEYWORDS_FIELD_XPATH, "value");
    }

    public boolean verifyMetatags(String pageTitle, String description,
                                  String keywords) {
        boolean status = true;

        status = LoggerWriter.writeInfoIntoLogger(
                getPageTitlePattern().equals(pageTitle),
                WRONG_PAGE_TITLE_PATTERN_ON_EPISODE_ACTUAL
                        + getPageTitlePattern() + EXPECTED + pageTitle,
                "Page title pattern is correct on episode");

        status = LoggerWriter.writeInfoIntoLogger(
                getKeyWords().equals(keywords),
                WRONG_PAGE_TITLE_PATTERN_ON_EPISODE_ACTUAL
                        + getKeyWords() + EXPECTED + keywords,
                "Keywords is correct on episode")  && status;

        LoggerWriter.writeSuccessIntoLogger(status,
                "Some tags are wrong on episode",
                "All tags are correct on episode");
        return status;
    }

    public boolean verifyMetatags(String description, String keywords) {
        boolean status = true;

        status = LoggerWriter.writeInfoIntoLogger(
                getDescription().equals(description),
                WRONG_PAGE_TITLE_PATTERN_ON_EPISODE_ACTUAL
                        + getDescription() + EXPECTED
                        + description,
                "Description is correct on episode") && status;
        status = LoggerWriter.writeInfoIntoLogger(
                getKeyWords().equals(keywords),
                WRONG_PAGE_TITLE_PATTERN_ON_EPISODE_ACTUAL
                        + getKeyWords() + EXPECTED + keywords,
                "Keywords is correct on episode") && status;

        LoggerWriter.writeSuccessIntoLogger(status,
                "Some tags are wrong on episode",
                "All tags are correct on episode");
        return status;
    }

    public RokuVideoJson getVideoObjectFromNodeMetadata() {
        RokuVideoJson videoJson = new RokuVideoJson();
        videoJson.setTitle(this.getTitle());
        this.onMPXInfoTab();
        videoJson = mpxInfoTab
                .setMetaDataToVideoObject(videoJson, this);
        return videoJson;
    }

    public Video getVideo() {
        Video video = new Video();
        video.setPublished(getNodePublishState());
        video.setTitle(getTitle());
        MpxAsset mpxAsset = getMpxInfo();
        video.setMpxAsset(mpxAsset);
        video.getGeneralInfo().setUuid(mpxAsset.getGuid());
        video.setPromotional(onBasicBlock().getPromotional());
        video.setSlugInfo(onSlugTab().getSlug());
        DevelPage develPage = openDevelPage();
        video.getGeneralInfo().setRevision(Integer.parseInt(develPage.getVid()));
        openEditPage();
        List<MediaImage> mediaImages = this.getConcertoImages();
        video.setMediaImages(mediaImages);
        video.getAssociations().getChannelReferenceAssociations().setChannelReference(new ChannelReference().setSeries("")
                .setItemType("video"));
        return video;
    }

    public List<MediaImage> getConcertoImages() {
        List<MediaImage> mediaIosImages = onImagesTab().getMediaImages();
        // removed getting appleTV images because they have same source with iOS.
        DevelPage develPage = openDevelPage();
        mediaIosImages = develPage.getMediaUuidsByMachineName(mediaIosImages);
        openEditPage();
        List<MediaImage> mediaImages = new LinkedList<>();
        mediaImages.addAll(mediaIosImages);
        return mediaImages;
    }

    @Override
    public List<ImageSource>
    getImageSources(String brand) {
        List<ImageSource> imageSources = new ArrayList<ImageSource>();
        imageSources.addAll(getSourcesForPlatform(this.onImagesTab().getAllVideoSources(), null));
        return imageSources;
    }

    private List<ImageSource> getSourcesForPlatform(List<VideoContentImage> blocks, CmsPlatforms platform) {
        List<ImageSource> imageSources = new ArrayList<ImageSource>();
        for (VideoContentImage block : blocks) {
            if (block.isSourcePresent()) {
                ImageSource source = new ImageSource();
                source.setName(block.getTitleLabel());
                source.setImageUrl(block.getLinkToSourceImage());
                source.setImageName(block.getImageSourceName());
                source.setOverriden(block.isOverriden());
                source.setPlatform(platform);
                imageSources.add(source);
            }
        }
        return imageSources;
    }

    @Override
    public Associations getAssociations() {
        Associations associations = new Associations();
        associations.getChannelReferenceAssociations().setChannelReference(new ChannelReference().setSeries("")
                .setItemType("video"));
        return associations;
    }

    @Override
    public Promotional getPromotional() {
        return onBasicBlock().getPromotional();
    }

    @Override
    public Slug getSlugInfo() {
        return onSlugTab().getSlug();
    }


}