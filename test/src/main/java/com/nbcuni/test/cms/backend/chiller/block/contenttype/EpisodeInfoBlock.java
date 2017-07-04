package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.RadioButtonsGroup;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Rating;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Type;
import com.nbcuni.test.cms.utils.DateUtil;
import org.openqa.selenium.support.FindBy;

import java.util.Date;

/**
 * Created by Ivan_Karnilau on 12-Apr-16.
 */
public class EpisodeInfoBlock extends BaseTabBlock {

    @FindBy(id = "edit-field-series-season-episode-und-0-show")
    private DropDownList series;

    @FindBy(xpath = ".//select[contains(@name,'[season]')]")
    private DropDownList season;

    @FindBy(id = "edit-field-episode-type")
    private RadioButtonsGroup episodeType;

    @FindBy(id = "edit-field-episode-number-und-0-value")
    private TextField episodeNumber;

    @FindBy(id = "edit-field-secondary-episode-number-und-0-value")
    private TextField secondaryEpisodeNumber;

    @FindBy(xpath = ".//input[contains(@id,'edit-field-original-airdate')]")
    private TextField originalAirDate;

    @FindBy(id = "edit-field-rating-und")
    private DropDownList rating;

    @FindBy(id = "edit-field-production-number-und-0-value")
    private TextField productionNumber;

    @FindBy(id = "edit-field-supplementary-airing-und-0-value-datepicker-popup-0")
    private TextField supplementaryAirDate;

    @FindBy(id = "edit-field-supplementary-airing-und-0-value-timeEntry-popup-1")
    private TextField supplementaryAirTime;

    public void selectSeries(String series) {
        this.series.selectWithAjaxWaiting(series);

    }

    public void selectSeason(String season) {
        this.season.selectWithAjaxWaiting(season);
    }

    public void checkEpisodeType(Type episodeType) {
        if (episodeType == null) {
            return;
        }
        this.episodeType.selectRadioButtonByName(episodeType.getValue());

    }

    public void enterEpisodeNumber(Integer episodeNumber) {
        if (episodeNumber == null) {
            return;
        }
        this.episodeNumber.enterText(episodeNumber.toString());
    }

    public void enterSecondaryEpisodeNumber(Integer secondaryEpisodeNumber) {
        if (secondaryEpisodeNumber == null) {
            return;
        }
        this.secondaryEpisodeNumber.enterText(secondaryEpisodeNumber.toString());
    }

    public void enterOriginalAirDate(Date originalAirDate) {
        if (originalAirDate == null) {
            return;
        }
        this.originalAirDate.enterText(DateUtil.getDate(originalAirDate, "MM/dd/yyyy"));
    }

    public void selectRating(Rating rating) {
        if (rating == null) {
            return;
        }
        this.rating.selectFromDropDown(rating.getValue());
    }

    public void enterProductionNumber(Integer productionNumber) {
        if (productionNumber == null) {
            return;
        }
        this.productionNumber.enterText(productionNumber.toString());
    }

    public void enterSupplementaryAirDate(Date supplementaryAirDate) {
        if (supplementaryAirDate == null) {
            return;
        }
        this.supplementaryAirDate.enterText(DateUtil.getDate(supplementaryAirDate, "MM/dd/yyyy"));
    }

    public void enterSupplementaryAirTime(Date supplementaryAirTime) {
        if (supplementaryAirTime == null) {
            return;
        }
        this.supplementaryAirTime.enterText(DateUtil.getDate(supplementaryAirTime, "HH:mm"));
    }
}
