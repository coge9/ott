package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.elements.*;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.*;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 06-Apr-16.
 */
public class SeriesInfoBlock extends BaseTabBlock {

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

    @FindBy(id = "edit-field-series-related-series-und")
    private DropDownList relatedSeries;

    @FindBy(id = "edit-field-series-type")
    private RadioButtonsGroup type;

    @FindBy(id = "edit-field-daypart-und")
    private DropDownList programmingTimeframe;

    @FindBy(xpath = ".//input[contains(@id,'edit-field-series-duration')]")
    private TextField regularlyScheduledDuration;

    public void selectGenre(Genre genre) {
        if (genre != null) {
            this.genre.clearSelection();
            this.genre.select(genre.getValue());
        }
    }

    public void selectRating(Rating rating) {
        if (rating != null) {
            this.rating.selectFromDropDown(rating.getValue());
        }
    }

    public void checkUnscripted(Boolean unscripted) {
        this.unscripted.selectStatus(unscripted);
    }

    public void selectStatus(Status status) {
        if (status != null) {
            this.status.selectFromDropDown(status.getValue());
        }
    }

    public SeriesInfoBlock checkSyndicated(Boolean syndicated) {
        this.syndicated.selectStatus(syndicated);
        return this;
    }

    public void selectRelatedSeries(String relatedSeries) {
        if (relatedSeries != null) {
            this.relatedSeries.selectFromDropDown(relatedSeries);
        }
    }

    public void checkType(Type type) {
        if (type != null) {
            this.type.selectRadioButtonByName(type.getValue());
        }
    }

    public void selectProgrammingTimeframe(ProgrammingTimeframe programmingTimeframe) {
        if (programmingTimeframe != null) {
            this.programmingTimeframe.selectFromDropDown(programmingTimeframe.getValue());
        }
    }

    public void enterRegularlyScheduledDuration(Integer regularlyScheduledDuration) {
        if (regularlyScheduledDuration != null) {
            this.regularlyScheduledDuration.enterText(regularlyScheduledDuration.toString());
        }
    }
}
