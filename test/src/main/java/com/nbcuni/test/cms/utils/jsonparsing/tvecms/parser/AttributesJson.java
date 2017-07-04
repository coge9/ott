package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandra_Lishaeva on 5/24/16.
 */
public class AttributesJson {

    @SerializedName("action")
    private AttributeValueObject action;

    @SerializedName("entityType")
    private AttributeValueObject entityType;

    public AttributeValueObject getEntityType() {
        return entityType;
    }

    public void setEntityType(AttributeValueObject entityType) {
        this.entityType = entityType;
    }

    public AttributeValueObject getAction() {
        return action;
    }

    public void setAction(AttributeValueObject action) {
        this.action = action;
    }

}
