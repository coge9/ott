package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.DateUtil;
import org.openqa.selenium.support.FindBy;

import java.util.Date;

/**
 * Created by Ivan_Karnilau on 11-Apr-16.
 */
public class SeasonBlock extends BaseTabBlock {

    @FindBy(id = "edit-field-series-season-episode-und-0-show")
    private DropDownList program;

    @FindBy(xpath = ".//input[contains(@id,'edit-field-season-number')]")
    private TextField seasonNumber;

    @FindBy(xpath = ".//input[contains(@id,'edit-field-production-number')]")
    private TextField productionNumber;

    @FindBy(xpath = ".//div[contains(@class,'start-date')]//input[contains(@id,'edit-field-season-dates')]")
    private TextField startDate;

    @FindBy(xpath = ".//div[contains(@class,'end-date')]//input[contains(@id,'edit-field-season-dates')]")
    private TextField endDate;

    @FindBy(xpath = ".//input[contains(@id,'show-todate')]")
    private CheckBox showEndDate;

    public void selectProgram(String program, String... programType) {
        this.program.selectFromDropDown(program, programType);
    }

    public void enterSeasonNumber(Integer seasonNumber) {
        if (seasonNumber != null) {
            this.seasonNumber.enterText(seasonNumber.toString());
        }
    }

    public void enterProductionNumber(Integer productionNumber) {
        if (productionNumber != null) {
            this.productionNumber.enterText(productionNumber.toString());
        }
    }

    public void enterStartDate(Date startDate) {
        this.startDate.enterText(DateUtil.getDate(startDate, "MM/dd/yyyy"));
    }

    public void enterEndDate(Date endDate) {
        showEndDate.check();
        this.endDate.enterText(DateUtil.getDate(endDate, "MM/dd/yyyy"));
    }
}
