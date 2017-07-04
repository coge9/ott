package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.castcredit;

import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;

/**
 * Created by Aleksandra_Lishaeva on 6/1/16.
 */
public class Credit extends AbstractEntity {

    private String title;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Credit that = (Credit) o;

        return Objects.equal(this.title, that.title) &&
                Objects.equal(this.name, that.name);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
