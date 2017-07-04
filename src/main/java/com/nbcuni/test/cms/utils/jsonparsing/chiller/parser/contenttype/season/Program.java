package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.season;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by Ivan_Karnilau on 01-Jun-16.
 */
public class Program {

    private String programUuid;
    private String programItemType;
    private String seasonUuid;

    public String getProgramItemType() {
        return programItemType;
    }

    public void setProgramItemType(String programItemType) {
        this.programItemType = programItemType;
    }

    public String getProgramUuid() {
        return programUuid;
    }

    public void setProgramUuid(String programUuid) {
        this.programUuid = programUuid;
    }

    public String getSeasonUuid() {
        return seasonUuid;
    }

    public void setSeasonUuid(String seasonsUuid) {
        this.seasonUuid = seasonsUuid;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("programUuid", programUuid)
                .add("programItemType", programItemType)
                .add("seasonUuid", seasonUuid)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return Objects.equal(programUuid, program.programUuid) &&
                Objects.equal(programItemType, program.programItemType) &&
                Objects.equal(seasonUuid, program.seasonUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(programUuid, programItemType, seasonUuid);
    }
}
