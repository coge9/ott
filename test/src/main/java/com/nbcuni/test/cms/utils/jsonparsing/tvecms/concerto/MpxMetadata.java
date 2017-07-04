package com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto;

import com.google.common.base.Objects;

/**
 * The POJO object for inner MpxMetadata field - object of series JSON
 * (on CMS right now updated nad uses for iOS only)
 */
public class MpxMetadata {

    private String tmsId;
    private String mpxGuid;

    public String getMpxGuid() {
        return mpxGuid;
    }

    public MpxMetadata setMpxGuid(String mpxGuid) {
        this.mpxGuid = mpxGuid;
        return this;
    }

    @Override
    public String toString() {
        return "MpxMetadata{" +
                "tmsId='" + tmsId + '\'' +
                "guid='" + mpxGuid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MpxMetadata that = (MpxMetadata) o;
        return Objects.equal(tmsId, that.tmsId) &&
                Objects.equal(mpxGuid, that.mpxGuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tmsId, mpxGuid);
    }

    public String getTmsId() {
        return tmsId;
    }

    public MpxMetadata setTmsId(String tmsId) {
        this.tmsId = tmsId;
        return this;
    }
}
