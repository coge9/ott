package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by Ivan_Karnilau on 05-Apr-16.
 */
public class CollectionGroupGeneralInfo extends GeneralInfo {

    private String machineName;

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("machineName", machineName)
                .add("title", getTitle())
                .add("uuid", getUuid())
                .add("byline", getByline())
                .add("dateLine", getDateLine())
                .add("subtitle", getSubhead())
                .add("shortDescription", getShortDescription())
                .add("mediumDescription", getMediumDescription())
                .add("longDescription", getLongDescription())
                .add("sortTitle", getSortTitle())
                .add("revision", getRevision())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollectionGroupGeneralInfo)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        CollectionGroupGeneralInfo that = (CollectionGroupGeneralInfo) o;
        return Objects.equal(machineName, that.machineName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), machineName);
    }
}
