package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandra_Lishaeva on 5/24/16.
 */
public class AttributeValueObject {

    @SerializedName("StringValue")
    private String StringValue;

    @SerializedName("DataType")
    private String DataType;

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    public String getStringValue() {
        return StringValue;
    }

    public void setStringValue(String stringValue) {
        StringValue = stringValue;
    }

}
