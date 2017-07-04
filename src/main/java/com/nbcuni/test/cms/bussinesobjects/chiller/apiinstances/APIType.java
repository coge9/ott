package com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances;

import java.util.Arrays;

public enum APIType {
    AMAZON("Amazon SQS"), API_SERVICE("API Services");

    private String type;

    APIType(String type) {
        this.type = type;
    }

    /*
    * Method will allow you to get enum constant of APIType by the string name.
    * */
    public static APIType getTypeByName(String name) {
        for (APIType type : Arrays.asList(values())) {
            if (type.getType().toLowerCase().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }
}

