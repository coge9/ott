package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 1/22/16.
 */
public class LatestEpisodeJson implements ContentItemListObject {

    public static final String DEFAULT_ITEM_TYPE = "rule";
    private static final String DEFAULT_COLLECTION = "video";
    private static final String DEFAULT_OPERATION_TYPE = "latestEpisode";
    private static final int DEFAULT_MAX_COUNT = 1;

    @SerializedName("itemType")
    private String itemType;

    @SerializedName("collection")
    private String collection;

    @SerializedName("operationType")
    private String operationType;

    @SerializedName("conditions")
    private List<RuleListConditions> conditions;

    @SerializedName("maxCount")
    private int maxCount;

    @Override
    public String getItemType() {
        return itemType;
    }

    @Override
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public List<RuleListConditions> getConditions() {
        return conditions;
    }

    public void setConditions(List<RuleListConditions> conditions) {
        this.conditions = conditions;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        LatestEpisodeJson other = (LatestEpisodeJson) obj;
        if (itemType == null) {
            if (other.itemType != null) {
                return false;
            }
        } else if (!itemType.equals(other.itemType)) {
            return false;
        }
        if (collection == null) {
            if (other.collection != null) {
                return false;
            }
        } else if (!collection.equals(other.collection)) {
            return false;
        }
        if (operationType == null) {
            if (other.operationType != null) {
                return false;
            }
        } else if (!operationType.equals(other.operationType)) {
            return false;
        }
        if (conditions == null) {
            if (other.conditions != null) {
                return false;
            }
        } else if (!conditions.equals(other.conditions)) {
            return false;
        }
        if (maxCount != other.maxCount) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + ((itemType == null) ? 0 : itemType.hashCode());
        result = 31 * result + ((collection == null) ? 0 : collection.hashCode());
        result = 31 * result + ((conditions == null) ? 0 : conditions.hashCode());
        result = 31 * result + ((operationType == null) ? 0 : operationType.hashCode());
        result = 31 * result + ((conditions == null) ? 0 : conditions.hashCode());
        return result;
    }

    /**
     * @param programId -  GUID of the program that, marked as 'show latest' within curated list
     * @param maxCount - max count
     * @return - rule object
     */
    public LatestEpisodeJson getRuleObject(String programId, int... maxCount) {
        setCollection(DEFAULT_COLLECTION);
        if (maxCount.length > 0) {
            setMaxCount(maxCount[0]);
        } else {
            setMaxCount(DEFAULT_MAX_COUNT);
        }
        setItemType(DEFAULT_ITEM_TYPE);
        setOperationType(DEFAULT_OPERATION_TYPE);
        List<RuleListConditions> ruleListConditions = new ArrayList<>();
        RuleListConditionsJson ruleListConditionsJson = new RuleListConditionsJson();
        RuleListConditionsJsonDefault ruleListConditionsJsonDefault = new RuleListConditionsJsonDefault();
        ruleListConditions.add(ruleListConditionsJson.getListConditionsObject(programId));
        ruleListConditions.add(ruleListConditionsJsonDefault.getListConditionsObject());
        setConditions(ruleListConditions);
        return this;
    }

}
