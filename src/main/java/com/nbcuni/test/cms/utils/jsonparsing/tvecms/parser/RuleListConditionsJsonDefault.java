package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandra_Lishaeva on 1/22/16.
 */
public class RuleListConditionsJsonDefault implements RuleListConditions {

    private static final String DEFAULT_FIELD = "fullEpisode";
    private static final String DEFAULT_OPERATOR = "eq";
    private static final Boolean DEFAULT_VALUE = true;

    @SerializedName("field")
    private String field;

    @SerializedName("value")
    private Boolean value;

    @SerializedName("operator")
    private String operator;


    public String getDEFAULT_FIELD() {
        return DEFAULT_FIELD;
    }

    public String getDEFAULT_OPERATOR() {
        return DEFAULT_OPERATOR;
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public void setField(String field) {
        this.field = field;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public String getOperator() {
        return operator;
    }

    @Override
    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        RuleListConditionsJsonDefault other = (RuleListConditionsJsonDefault) obj;
        if (field == null) {
            if (other.field != null) {
                return false;
            }
        } else if (!field.equals(other.field)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        if (operator == null) {
            if (other.operator != null) {
                return false;
            }
        } else if (!operator.equals(other.operator)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + ((field == null) ? 0 : field.hashCode());
        result = 31 * result + ((value == null) ? 0 : value.hashCode());
        result = 31 * result + ((operator == null) ? 0 : operator.hashCode());
        return result;
    }

    public RuleListConditionsJsonDefault getListConditionsObject() {
        setField(DEFAULT_FIELD);
        setOperator(DEFAULT_OPERATOR);
        setValue(DEFAULT_VALUE);
        return this;
    }
}
