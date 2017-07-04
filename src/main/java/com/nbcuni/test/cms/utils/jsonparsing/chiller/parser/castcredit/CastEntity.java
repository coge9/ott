package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.castcredit;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;

/**
 * Created by Aleksandra_Lishaeva on 6/1/16.
 */
public class CastEntity extends AbstractEntity {

    @SerializedName("personUuid")
    private String person;

    @SerializedName("roleUuid")
    private String role;

    public String getRole() {
        return role;
    }

    public CastEntity setRole(String role) {
        this.role = role;
        return this;
    }

    public String getPerson() {
        return person;
    }

    public CastEntity setPerson(String person) {
        this.person = person;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CastEntity that = (CastEntity) o;

        return Objects.equal(this.person, that.person) &&
                Objects.equal(this.role, that.role);
    }

    @Override
    public int hashCode() {
        int result = person.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("person", person)
                .add("role", role)
                .toString();
    }
}
