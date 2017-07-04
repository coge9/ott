package com.nbcuni.test.cms.backend.chiller.block.contenttype.video;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.Element;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.DataUtil;
import com.nbcuni.test.cms.utils.DateUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuRatingJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;
import com.nbcuni.test.cms.utils.mpx.objects.MpxCategory;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;
import java.util.*;

import static com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata.MpxInfoFields.*;
import static com.nbcuni.test.cms.utils.fielddecorator.ExtendedFieldDecorator.isDecoratableElementList;

/**
 * Created by aleksandra_lishaeva on 10/16/15.
 */
public class MPXInfoTab extends BaseTabBlock {

    @FindBy(xpath = ".//input[contains(@id,'mpx-short-description')]")
    private TextField shortDescription;

    @FindBy(xpath = ".//input[contains(@id,'mpx-advertising-genre')]")
    private TextField advertisingGenre;

    @FindBy(xpath = ".//input[contains(@id,'mpx-episode-number')]")
    private TextField episodeNumber;

    @FindBy(xpath = ".//input[contains(@id,'mpx-season-number')]")
    private TextField seasonNumber;

    @FindBy(xpath = ".//input[contains(@id,'mpx-tunein-time')]")
    private TextField tuneInTime;

    @FindBy(xpath = ".//input[contains(@id,'mpx-entitlement')]")
    private TextField entitlement;

    @FindBy(xpath = ".//input[contains(@id,'mpx-external-advertiser-id')]")
    private TextField externalAdvertiserId;

    @FindBy(xpath = ".//input[contains(@id,'mpx-full-episode')]")
    private TextField fullEpisode;

    @FindBy(xpath = ".//input[contains(@id,'mpx-programming-type')]")
    private TextField programmingType;

    @FindBy(xpath = ".//input[contains(@id,'mpx-serverside-adstitched')]")
    private TextField serverSideAdStitched;

    @FindBy(xpath = ".//input[contains(@id,'-field-mpx-video-length-und-')]")
    private TextField videoLength;

    @FindBy(xpath = ".//input[contains(@id,'mpx-series-title')]")
    private TextField seriesTitle;

    @FindBy(xpath = ".//input[contains(@id,'mpx-guid')]")
    private TextField guid;

    @FindBy(xpath = ".//input[contains(@id,'mpx-day-part')]")
    private TextField dayPart;

    @FindBy(xpath = ".//textarea[contains(@id,'mpx-description')]")
    private TextField description;

    @FindBy(xpath = ".//input[contains(@id,'field-mpx-main-released-file')]")
    private TextField pid;

    @FindBy(xpath = ".//input[contains(@id,'mpx-id')]")
    private TextField id;

    @FindBy(xpath = ".//input[contains(@id,'mpx-airdate-und-0-value-date')]")
    private TextField airDate;

    @FindBy(xpath = ".//input[contains(@id,'mpx-airdate-und-0-value-time')]")
    private TextField airTime;

    @FindBy(xpath = ".//input[contains(@id,'mpx-available-date-und-0-value-date')]")
    private TextField availableDate;

    @FindBy(xpath = ".//input[contains(@id,'mpx-available-date-und-0-value-time')]")
    private TextField availableTime;

    @FindBy(xpath = ".//input[contains(@id,'mpx-expiration-date-und-0-value-date')]")
    private TextField expirationDate;

    @FindBy(xpath = ".//input[contains(@id,'mpx-expiration-date-und-0-value-time')]")
    private TextField expirationTime;

    @FindBy(xpath = ".//input[contains(@id,'mpx-media-categories') and @type='text']")
    private List<TextField> categories;

    @FindBy(xpath = ".//input[contains(@id,'mpx-series-category')]")
    private TextField seriesCategory;

    @FindBy(xpath = ".//input[contains(@id,'mpx-series-type')]")
    private TextField seriesType;

    @FindBy(xpath = ".//textarea[contains(@id,'mpx-keywords')]")
    private TextField keywords;

    @FindBy(xpath = ".//input[contains(@id,'mpx-fab-rating')]")
    private TextField advisoryRating;

    @FindBy(xpath = ".//input[contains(@id,'mpx-fab-subratings') and contains(@id,'value')]")
    private List<TextField> advisorySubrating;

    @FindBy(xpath = ".//input[contains(@id,'mpx-vchip-rating')]")
    private TextField vChipRating;

    @FindBy(xpath = "..//input[contains(@id,'mpx-vchip-subratings') and contains(@id,'value')]")
    private List<TextField> vChipSubrating;

    @FindBy(xpath = ".//input[contains(@id,'mpx-mpaa-rating')]")
    private TextField mpaaRating;

    @FindBy(xpath = ".//input[contains(@id,'mpx-mpaa-subratings') and contains(@id,'value')]")
    private List<TextField> mpaaSubrating;

    @FindBy(xpath = ".//input[contains(@id,'mpx-cc-available')]")
    private CheckBox closeCaptionFlaf;

    @FindBy(xpath = ".//input[contains(@id,'episode-tmsid')]")
    private TextField episodeTmsId;

    @FindBy(xpath = ".//input[contains(@id,'series-tmsid')]")
    private TextField seriesTmsId;

    @FindBy(xpath = ".//input[contains(@id,'mpx-event')]")
    private TextField event;

    private EditTVEVideoContentPage ottVideoContentPage;

    public Boolean getClosedCaptionFlag() {
        return closeCaptionFlaf.isSelected();
    }

    public Map<String, String> collectMetaData() {
        final Map<String, String> episodeData = new HashMap<String, String>();
        episodeData.put(SHORT_DESCRIPTION.getName(), shortDescription.getValue());
        episodeData.put(ADVERTISING_GENRE.getName(), advertisingGenre.getValue());
        episodeData.put(EPISODE_NUMBER.getName(), episodeNumber.getValue());
        episodeData.put(SEASON_NUMBER.getName(), seasonNumber.getValue());
        episodeData.put(TUNE_IN_TIME.getName(), tuneInTime.getValue());
        episodeData.put(ENTITLEMENT.getName(), entitlement.getValue());
        episodeData.put(EXTERNAL_ID.getName(), externalAdvertiserId.getValue());
        episodeData.put(FULL_EPISODE.getName(), fullEpisode.getValue());
        episodeData.put(PROGRAMMING_TYPE.getName(), programmingType.getValue());
        episodeData.put(DAYPART.getName(), dayPart.getValue());
        episodeData.put(VIDEOLENGTH.getName(), videoLength.getValue());
        episodeData.put(SERIES.getName(), seriesTitle.getValue());
        episodeData.put(CATEGORIES.getName(), categories.get(0).getValue());
        episodeData.put(ID.getName(), id.getValue());
        episodeData.put(GUID.getName(), guid.getValue());
        episodeData.put(DESCRIPTION.getName(), description.getValue());
        String date = airDate.getValue();
        episodeData.put(AIRDATE.getName(), date);
        date = availableDate.getValue();
        episodeData.put(AVAILABLE_DATE.getName(), date);
        date = expirationDate.getValue();
        episodeData.put(EXPIRATION_DATE.getName(), date);
        episodeData.put(SERIES_TYPE.getName(), seriesType.getValue());
        episodeData.put(TMS_ID.getName(), seriesTmsId.getValue());
        episodeData.put(EVENT.getName(), event.getValue());
        return episodeData;
    }

    public RokuVideoJson setMetaDataToVideoObject(RokuVideoJson videoJson, EditTVEVideoContentPage contentPage) {
        ottVideoContentPage = contentPage;
        videoJson.setType("video");
        videoJson.setShowName(seriesTitle.getValue());
        videoJson.setPublishState(ottVideoContentPage.getNodePublishState());
        videoJson.setSeriesCategories(seriesCategory.getValue());
        videoJson.setSeriesType(seriesType.getValue());
        videoJson.setDayPart(dayPart.getValue());
        videoJson.setId(guid.getValue());
        videoJson.setMpxId(id.getValue());
        Boolean fullEpisode = true;
        if (this.fullEpisode.getValue().equalsIgnoreCase("0")) {
            fullEpisode = false;
        }
        videoJson.setFullEpisode(fullEpisode);
        videoJson.setEpisodeNumber(episodeNumber.getValue());
        videoJson.setSeasonNumber(seasonNumber.getValue());
        videoJson.setExpirationDate(getConvertedExpirationDate(expirationDate, expirationTime));
        videoJson.setAirDate(getConvertedDate(airDate, airTime));
        videoJson.setAvailableDate(getConvertedDate(availableDate, airTime));
        videoJson.setDuration(videoLength.getValue().isEmpty() ? 0 : Integer.parseInt(videoLength.getValue()));
        videoJson.setDescription(description.getValue());
        videoJson.setExternalAdvertiserId(externalAdvertiserId.getValue());
        videoJson.setKeywords(keywords.getValue());
        videoJson.setEntitlement(entitlement.getValue());
        videoJson.setClosedCaptionFlag(getClosedCaptionFlag());
        videoJson.setRatings(getRatings());
        videoJson.setMediaCategories(getMediaCategories());
        videoJson.setShowId("");
        return videoJson;
    }

    public RokuProgramJson setMetaDataToProgramObject(RokuProgramJson programJson) {
        programJson.setType("program");
        programJson.setSeriesType(seriesType.getValue());
        programJson.setSeriesCategory(seriesCategory.getValue());
        programJson.setShowColor("");
        programJson.setId(guid.getValue());
        programJson.setDescription(description.getValue());
        programJson.setMpxId(id.getValue());
        return programJson;
    }

    public List<RokuRatingJson> getRatings() {
        List<RokuRatingJson> ratings = new ArrayList<>();
        RokuRatingJson ratingJson = new RokuRatingJson();
        List<String> subratings = new ArrayList<>();
        if (!vChipRating.getValue().isEmpty()) {
            ratingJson.setScheme("v-chip");
            ratingJson.setRating(vChipRating.getValue());

            for (TextField element : vChipSubrating) {
                if (!element.getValue().isEmpty()) {
                    subratings.add(element.element().getAttribute(HtmlAttributes.VALUE.get()));
                }
            }
            ratingJson.setSubRatings(subratings);
            ratings.add(ratingJson);
        }
        if (!advisoryRating.getValue().isEmpty()) {
            ratingJson.setScheme("Advisory Board");
            ratingJson.setRating(advisoryRating.getValue());

            for (TextField element : advisorySubrating) {
                if (!element.getValue().isEmpty()) {
                    subratings.add(element.element().getAttribute(HtmlAttributes.VALUE.get()));
                }
            }
            ratingJson.setSubRatings(subratings);
            ratings.add(ratingJson);
        }
        if (!mpaaRating.getValue().isEmpty()) {
            ratingJson.setScheme("MPAA");
            ratingJson.setRating(mpaaRating.getValue());

            for (TextField element : mpaaSubrating) {
                if (!element.getValue().isEmpty()) {
                    subratings.add(element.element().getAttribute(HtmlAttributes.VALUE.get()));
                }
            }
            ratingJson.setSubRatings(subratings);
            ratings.add(ratingJson);
        }
        return ratings;
    }

    public String getConvertedExpirationDate(TextField dayField, TextField timeField) {
        String date = DataUtil.convertDate(new Date(), Calendar.YEAR, 10);
        date = DataUtil.convertStringFormatToStringFormat(date, "MM_DD_YYYY", "yyyy-MM-dd");
        String day = dayField.getValue();
        if (!day.isEmpty()) {
            day = DataUtil.convertStringFormatToStringFormat(day, "MM/dd/yyyy", "yyyy-MM-dd");
            String time = timeField.getValue();
            time = DataUtil.convertStringFormatToStringFormat(time, "hh:mmaa", "HH:mm:ss");
            date = day + "T" + time + "Z";
        }
        return date;
    }

    public String getConvertedDate(TextField dayField, TextField timeField) {
        String date = ottVideoContentPage.getNodeUpdatedDate();
        String time = DataUtil.convertStringFormatToStringFormat(date, "MM/dd/yyyy - HH:mm aa", "HH:mm:ss");
        String day = dayField.getValue();
        if (!day.isEmpty()) {
            day = DataUtil.convertStringFormatToStringFormat(day, "MM/dd/yyyy", "yyyy-MM-dd");
            time = timeField.getValue();
            time = DataUtil.convertStringFormatToStringFormat(time, "hh:mmaa", "HH:mm:ss");
        } else {
            day = DataUtil.convertStringFormatToStringFormat(day, "MM/dd/yyyy - HH:mm aa", "yyyy-MM-dd");
        }

        date = day + "T" + time + "Z";

        return date;
    }

    public List<String> getMediaCategories() {
        List<String> categories = new ArrayList<>();
        for (TextField element : this.categories) {
            String category = element.getValue();
            if (!category.isEmpty()) {
                categories.add(category);
            }
        }
        return categories;
    }

    public SoftAssert verifyElements(SoftAssert softAssert) {
        Utilities.logInfoMessage("Verify page non-editable elements");
        final Class<?> thisClass = this.getClass();
        final Field[] fields = thisClass.getDeclaredFields();
        for (final Field field : fields) {
            try {
                if (Element.class.isAssignableFrom(field.getType())) {
                    final Element fieldElement = (Element) field.get(this);
                    softAssert.assertFalse(fieldElement.isEnable(),
                            "Element:  " + field.getName());
                }
                if (isDecoratableElementList(field, Element.class)) {
                    final List<Element> fieldElements = (List<Element>) field.get(this);
                    for (Element element : fieldElements) {
                        softAssert.assertFalse(element.isEnable(),
                                "Element:  " + field.getName());
                    }
                }

            } catch (final IllegalArgumentException e) {
                Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
            } catch (final IllegalAccessException e) {
                Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
            }
        }
        return softAssert;
    }

    private Date getAirDate() {
        String stringDate = airDate.getValue().isEmpty() ? "" : airDate.getValue() +
                (airTime.getValue().isEmpty() ? "" : " " + airTime.getValue());
        if (!stringDate.isEmpty()) {
            Date date = DateUtil.parseStringToDateInCertainTimeZone(stringDate,
                    "MM/dd/yyyy hh:mmaa", "America/New_York");
            return date;
        }
        return null;
    }

    private Date getAvailableDate() {
        String stringDate = availableDate.getValue().isEmpty() ? "" : availableDate.getValue() +
                (availableTime.getValue().isEmpty() ? "" : " " + availableTime.getValue());
        if (!stringDate.isEmpty()) {
            Date date = DateUtil.parseStringToDateInCertainTimeZone(stringDate,
                    "MM/dd/yyyy hh:mmaa", "America/New_York");
            return date;
        }
        return null;
    }

    private Date getExpirationDate() {
        String stringDate = expirationDate.getValue().isEmpty() ? "" : expirationDate.getValue() +
                (expirationTime.getValue().isEmpty() ? "" : " " + expirationTime.getValue());
        if (!stringDate.isEmpty()) {
            Date date = DateUtil.parseStringToDateInCertainTimeZone(stringDate,
                    "MM/dd/yyyy hh:mmaa", "America/New_York");
            return date;
        }
        return null;
    }

    private List<MpxCategory> getCategories() {
        List<MpxCategory> categoriesList = new ArrayList<>();
        for (TextField category : categories) {
            if (!category.getValue().isEmpty()) {
                MpxCategory mpxCategory = new MpxCategory();
                mpxCategory.setName(category.getValue());
                mpxCategory.setLabel("");
                mpxCategory.setScheme("");
                categoriesList.add(mpxCategory);
            }
        }
        return categoriesList;
    }

    public MpxAsset getMetadata() {
        MpxAsset mpxAsset = new MpxAsset();
        mpxAsset.setGuid(guid.getValue());
        mpxAsset.setId(id.getValue());
        mpxAsset.setPid(pid.getValue());
        mpxAsset.setPubDate(this.getAirDate() != null ? this.getAirDate().getTime() / 1000L : null);
        mpxAsset.setAvailableDate(this.getAvailableDate() != null ? this.getAvailableDate().getTime() / 1000L : null);
        mpxAsset.setExpirationDate(this.getExpirationDate() != null ? this.getExpirationDate().getTime() / 1000L : null);
        mpxAsset.setDayPart(dayPart.getValue());
        mpxAsset.setAdvertisingId(externalAdvertiserId.getValue());
        mpxAsset.setEntitlement(entitlement.getValue());
        mpxAsset.setKeywords(keywords.getValue());
        mpxAsset.setDescription(description.getValue());
        mpxAsset.setShortDescription(shortDescription.getValue());
        mpxAsset.setProgrammingType(programmingType.getValue());
        mpxAsset.setClosedCaptions(closeCaptionFlaf.isSelected());
        mpxAsset.setSeriesTitle(seriesTitle.getValue());
        mpxAsset.setSeriesCategory(seriesCategory.getValue().isEmpty() ? null : seriesCategory.getValue());
        mpxAsset.setSeriesType(seriesType.getValue());
        mpxAsset.setEpisodeNumber(episodeNumber.getValue().isEmpty() ? null : Integer.parseInt(episodeNumber.getValue()));
        mpxAsset.setSeasonNumber(seasonNumber.getValue().isEmpty() ? null : Integer.parseInt(seasonNumber.getValue()));
        mpxAsset.setDuration(videoLength.getValue().isEmpty() ? null : Integer.parseInt(videoLength.getValue()));
        mpxAsset.setRatings(getRatings());
        mpxAsset.setCategories(getCategories());
        mpxAsset.setAdvertisingGenre(advertisingGenre.getValue());
        mpxAsset.setFullEpisode(fullEpisode.getValue().equals("1"));
        mpxAsset.setEpisodeTmsId(episodeTmsId.getValue());
        mpxAsset.setSeriesTmsId(seriesTmsId.getValue());
        mpxAsset.setEvent(event.getValue());
        return mpxAsset;
    }

}
