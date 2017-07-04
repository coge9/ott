package com.nbcuni.test.cms.collectservices.video;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.*;
import com.nbcuni.test.cms.backend.interfaces.pages.EditVideoPage;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.SourceMatcher;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.pageobjectutils.chiller.CustomBrandNames;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.GlobalConstants;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Dzianis_Kulesh on 2/6/2017.
 * <p>
 * Implementation of interface which collect info about video node from UI. Collect data for all brands both chiller and NON-chiller.
 */
public class UiVideoDataCollector implements VideoDataCollector {

    private static final String FIELD_VIDEO_APPLETV_ORIGINAL = "field_video_appletv_original";
    private static final String PUBLISHED_STATE = "Published";
    private static final String FIELD_APPLETV_ORIGINAL_IMAGE = "field_appletv_original_image";
    private static final String FIELD_ORIGINAL_IMAGE = "field_original_image ";
    private static final String FIELD_IOS_ORIGINAL_IMAGE = "field_ios_original_image ";
    private static final String UPDATED_DATE_FORMAT = "MM/dd/yyyy - hh:mm a";
    private CustomWebDriver webDriver;
    private String brand;
    private MainRokuAdminPage mainRokuAdminPage;
    private AppLib aid;

    public UiVideoDataCollector(CustomWebDriver webDriver, AppLib aid, String brand) {
        this.webDriver = webDriver;
        this.brand = brand;
        this.aid = aid;
        mainRokuAdminPage = new MainRokuAdminPage(webDriver, aid);
    }

    /**
     * *********************************************************************************
     * Method Name: collectVideoInfo
     * Description: main method for collecting video DATA
     * the Json file path
     *
     * @param assetTitle - title of the video under test.
     * @return GlobalVideoObject
     * ***********************************************************************************
     */
    @Override
    public GlobalVideoEntity collectVideoInfo(String assetTitle) {
        EditVideoPage editVideo;
        // opening content page.
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        // searching for video under test.
        searchByTitle(contentPage, assetTitle);
        // collect NODE published state.
        String publishedState = contentPage.getNodePublishState(1);
        // collect node updated date.
        ZonedDateTime updatedDate = getUpdatedDate(contentPage);
        // identify implementation of Edit Video Page
        if (brand.equals(CustomBrandNames.CHILLER.getBrandID())) {
            editVideo = contentPage.clickEditLink(ChillerVideoPage.class, assetTitle);
        } else {
            editVideo = contentPage.clickEditLink(EditTVEVideoContentPage.class, assetTitle);
        }
        // collect video info.
        GlobalVideoEntity video = getVideo(editVideo);
        // update with last updated date and pupblish status.
        video.setUpdatedDate(updatedDate);
        video.setPublished(publishedState.equals(PUBLISHED_STATE) ? true : false);
        // identify way for collecting channel reference.
        if (!brand.equals(CustomBrandNames.CHILLER.getBrandID())) {
            addChannelReferenceForNonChiller(video);
        } else {
            addChannelReferenceChiller(video);
        }
        return video;
    }

    @Override
    public GlobalVideoEntity collectRandomVideoInfo() {
        throw new UnsupportedOperationException("This method is unsupported for UIVideoDataCollector.java");
    }

    // searching element on content page by title.
    private void searchByTitle(ContentPage contentPage, String assetTitle) {
        contentPage.searchByType(ContentType.TVE_VIDEO);
        contentPage.searchByTitle(assetTitle);
        contentPage.apply();
        checkItems(contentPage, assetTitle);
    }

    // check that there is only one item with define title. In case of duplicate throw error.
    private void checkItems(ContentPage contentPage, String assetTitle) {
        List<String> content = contentPage.getAllContentList();
        Iterator<String> iterator = content.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (item.equals(assetTitle)) {
                count++;
            }
        }
        Assertion.assertTrue(count != 0, "There are 0 items with title " + assetTitle, webDriver);
        Assertion.assertTrue(count == 1, "There are more than 1 item with title " + assetTitle, webDriver);
    }

    // getting updated date from content page.
    private ZonedDateTime getUpdatedDate(ContentPage contentPage) {
        String updatedDate = contentPage.getNodeUpdatedDate(1);
        LocalDateTime localDateTime = LocalDateTime.parse(updatedDate, DateTimeFormatter.ofPattern(UPDATED_DATE_FORMAT));
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, GlobalConstants.NY_ZONE);
        return zonedDateTime;
    }


    private void addChannelReferenceForNonChiller(GlobalVideoEntity video) {
        String programUuid = null;
        if (!StringUtils.isEmpty(video.getMpxAsset().getSeriesTitle())) {
            ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
            try {
                EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(video.getMpxAsset().getSeriesTitle());
                String guid = programContentPage.getMpxInfo().getGuid();
                video.setRelatedShowGuid(guid);
                programUuid = programContentPage.openDevelPage().getUuid();
            } catch (TestRuntimeException e) {
                programUuid = null;
            }
        }
        ChannelReference channelReference = new ChannelReference();
        channelReference.setSeries(programUuid);
        video.getAssociations().getChannelReferenceAssociations().setChannelReference(channelReference);
    }


    private void addChannelReferenceChiller(GlobalVideoEntity video) {
        String series = video.getAssociations().getChannelReferenceAssociations().getChannelReference().getSeries();
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        DevelPage develPage;
        if (series != null) {
            contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
            ContentType type = ContentType.TVE_EVENT;
            if (!type.get().toLowerCase().contains(video.getAssociations().getChannelReferenceAssociations().getChannelReference().getItemType())) {
                type = ContentType.TVE_SERIES;
            }
            contentPage.searchByType(type).apply();
            SeriesPage seriesPage = contentPage.clickEditLink(SeriesPage.class, series);
            develPage = seriesPage.openDevelPage();
            video.getAssociations().getChannelReferenceAssociations().getChannelReference().setSeries(develPage.getUuid());
        }
        String season = video.getAssociations().getChannelReferenceAssociations().getChannelReference().getSeason();
        if (season != null) {
            contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
            contentPage.searchByType(ContentType.TVE_SEASON).apply();
            SeasonPage seasonPage = contentPage.clickEditLink(SeasonPage.class, season);
            develPage = seasonPage.openDevelPage();
            video.getAssociations().getChannelReferenceAssociations().getChannelReference().setSeason(develPage.getUuid());
        }
        String episode = video.getAssociations().getChannelReferenceAssociations().getChannelReference().getEpisode();
        if (episode != null) {
            contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
            contentPage.searchByType(ContentType.TVE_EPISODE).apply();
            EpisodePage episodePage = contentPage.clickEditLink(EpisodePage.class, episode);
            develPage = episodePage.openDevelPage();
            video.getAssociations().getChannelReferenceAssociations().getChannelReference().setEpisode(develPage.getUuid());
        }
    }

    // Collecting video data from page.
    private GlobalVideoEntity getVideo(EditVideoPage editVideo) {
        GlobalVideoEntity video = new GlobalVideoEntity();
        video.setTitle(editVideo.getTitle());
        video.setMpxAsset(editVideo.getMpxInfo());
        video.setPromotional(editVideo.getPromotional());
        video.setSlugInfo(editVideo.getSlugInfo());
        video.setImageSources(editVideo.getImageSources(brand));
        video.setAssociations(editVideo.getAssociations());
        getInfoFromDevelPage(editVideo, video);
        return video;
    }

    // Getting info from devel page (UUID of VIDEO and IMAGES)
    private void getInfoFromDevelPage(EditVideoPage editVideo, GlobalVideoEntity video) {
        DevelPage develPage = editVideo.openDevelPage();
        video.getGeneralInfo().setRevision(Integer.parseInt(develPage.getVid()));
        video.getGeneralInfo().setUuid(develPage.getUuid());
        if (brand.equals(CustomBrandNames.CHILLER.getBrandID())) {
            getMediaForChiller(develPage, video);
        } else {
            getMediaForNonChiller(develPage, video);
        }
    }

    // getting media for devel page for chiller brand.
    private void getMediaForNonChiller(DevelPage develPage, GlobalVideoEntity video) {
        for (ImageSource source : video.getImageSources()) {
            SourceMatcher sourceEnumConstant = SourceMatcher.getSource(source.getImageName(), ContentType.TVE_VIDEO, source.getPlatform());
            String uuid = develPage.getMediaUuidByMachineName(sourceEnumConstant.getMachineName());
            source.setUuid(uuid);
        }
    }

    // get media for devel page for non chiller.
    private void getMediaForChiller(DevelPage develPage, GlobalVideoEntity video) {
        Map<String, String> uuids = develPage.getMediaUuids();
        for (ImageSource source : video.getImageSources()) {
            source.setUuid(uuids.get(source.getImageName()));
        }
    }

}
