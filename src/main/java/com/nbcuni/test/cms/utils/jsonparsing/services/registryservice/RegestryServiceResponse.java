package com.nbcuni.test.cms.utils.jsonparsing.services.registryservice;

import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 5/19/16.
 */
public class RegestryServiceResponse {

    private String uuid;
    private List<String> keys;

    private String lastUpdated;

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }


}
