package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by Alena_Aukhukova on 6/20/2016.
 */
public class MetadataInfo {
    private String firstName = null;
    private String middleName = null;
    private String lastName = null;
    private String prefix = null;
    private String suffix = null;
    private String bio = null;
    private String itemType = null;


    public String getBio() {
        return bio;
    }

    public MetadataInfo setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public MetadataInfo setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public MetadataInfo setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public MetadataInfo setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public MetadataInfo setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    public MetadataInfo setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public String getItemType() {
        return itemType;
    }

    public MetadataInfo setItemType(String itemType) {
        this.itemType = itemType;
        return this;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("firstName", firstName)
                .add("middleName", middleName)
                .add("lastName", lastName)
                .add("prefix", prefix)
                .add("suffix", suffix)
                .add("bio", bio)
                .add("itemType", itemType)
                .toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MetadataInfo that = (MetadataInfo) o;

        return Objects.equal(this.firstName, that.firstName) &&
                Objects.equal(this.middleName, that.middleName) &&
                Objects.equal(this.lastName, that.lastName) &&
                Objects.equal(this.prefix, that.prefix) &&
                Objects.equal(this.suffix, that.suffix) &&
                Objects.equal(this.bio, that.bio) &&
                Objects.equal(this.itemType, that.itemType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstName, middleName, lastName, prefix, suffix, bio,
                itemType);
    }
}
