package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.MultiSelectConcertoTheme;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.EventType;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Genre;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Rating;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Status;
import com.nbcuni.test.cms.utils.DateUtil;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import org.openqa.selenium.support.FindBy;

import java.util.Date;

/**
 * Created by Ivan_Karnilau on 12-Apr-16.
 */
public class EventInfoBlock extends BaseTabBlock {

    @FindBy(id = "edit-field-genre")
    private MultiSelectConcertoTheme genre;

    @FindBy(id = "edit-field-rating-und")
    private DropDownList rating;

    @FindBy(id = "edit-field-unscripted-und")
    private CheckBox unscripted;

    @FindBy(id = "edit-field-program-status-und")
    private DropDownList status;

    @FindBy(id = "edit-field-syndicated-und")
    private CheckBox syndicated;

    @FindBy(id = "edit-field-related-series-und")
    private DropDownList relatedSeries;

    @FindBy(id = "edit-field-event-type-und")
    private DropDownList eventType;

    @FindBy(xpath = "//input[contains(@id,'dit-field-release-year-und')]")
    private TextField releaseYear;

    @FindBy(id = "edit-field-channel-original-und")
    private CheckBox chanelOriginal;

    @FindBy(xpath = ".//div[contains(@class,'-value-time')]//input")
    private TextField airTime;

    public void selectGenre(Genre genre) {
        if (genre == null) {
            return;
        }
        this.genre.clearSelection();
        this.genre.select(genre.getValue());
    }

    public void selectRating(Rating rating) {
        if (rating == null) {
            return;
        }
        this.rating.selectFromDropDown(rating.getValue());
    }

    public void checkUnscripted(Boolean unscripted) {
        this.unscripted.selectStatus(unscripted);
    }

    public void selectStatus(Status status) {
        if (status == null) {
            return;
        }
        this.status.selectFromDropDown(status.getValue());
    }

    public void checkSyndicated(Boolean syndicated) {
        this.syndicated.selectStatus(syndicated);
    }

    public void selectRelatedSeries(String relatedSeries) {
        this.relatedSeries.selectFromDropDown(relatedSeries);
    }

    public void checkType(EventType type) {
        if (type == null) {
            return;
        }
        this.eventType.selectFromDropDown(type.getValue());
    }

    public void enterReleaseYear(Date releaseYear) {
        if (releaseYear != null) {
            this.releaseYear.enterText(DateUtil.getDate(releaseYear, "yyyy"));
        }
    }

    public void checkChanelOriginal(Boolean chanelOriginal) {
        this.chanelOriginal.selectStatus(chanelOriginal);
    }

    public void enterAirTime(Date airTime) {
        if (airTime != null) {
            WebDriverUtil.getInstance(webDriver).scrollPageDown();
            this.airTime.enterText(DateUtil.getDate(airTime, "HH:mm"));
        }
    }

    public int getReleaseYear() {
        return Integer.parseInt(releaseYear.getValue());
    }
}