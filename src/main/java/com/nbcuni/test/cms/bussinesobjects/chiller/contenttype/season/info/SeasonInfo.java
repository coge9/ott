package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.info;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;

import java.util.Date;

/**
 * Created by Ivan on 12.04.2016.
 */
public class SeasonInfo {

    private Content parentProgram;
    private String program = null;
    private Integer seasonNumber = null;
    private Integer productionNumber = null;
    private Date startDate = null;
    private Date endDate = null;

    public String getProgram() {
        return program;
    }

    public SeasonInfo setProgram(String program) {
        this.program = program;
        return this;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public SeasonInfo setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
        return this;
    }

    public Integer getProductionNumber() {
        return productionNumber;
    }

    public SeasonInfo setProductionNumber(Integer productionNumber) {
        this.productionNumber = productionNumber;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public SeasonInfo setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public SeasonInfo setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public boolean isObjectNull() {
        return program == null &&
                seasonNumber == null &&
                productionNumber == null &&
                startDate == null &&
                endDate == null;
    }

    public Content getParentProgram() {
        return parentProgram;
    }

    public SeasonInfo setParentProgram(Content program) {
        this.parentProgram = program;
        this.setProgram(program.getTitle());
        return this;
    }
}
